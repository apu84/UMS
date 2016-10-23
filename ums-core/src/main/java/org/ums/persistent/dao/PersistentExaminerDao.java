package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.domain.model.immutable.Examiner;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.persistent.model.PersistentExaminer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentExaminerDao extends
    AbstractAssignedTeacherDao<Examiner, MutableExaminer, Integer> {
  static String SELECT_ALL =
      "SELECT SEMESTER_ID, PREPARER, SCRUTINIZER, COURSE_ID, LAST_MODIFIED, ID FROM PREPARER_SCRUTINIZER ";
  static String UPDATE_ALL =
      "UPDATE PREPARER_SCRUTINIZER SET SEMESTER_ID = ?, PREPARER = ?, SCRUTINIZER= ?, COURSE_ID = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  static String DELETE_ALL = "DELETE FROM PREPARER_SCRUTINIZER ";
  static String INSERT_ONE =
      "INSERT INTO PREPARER_SCRUTINIZER(SEMESTER_ID, PREPARER, SCRUTINIZER, COURSE_ID, LAST_MODIFIED) VALUES"
          + "(?, ?, ?, ?," + getLastModifiedSql() + ")";

  private String SELECT_BY_SEMESTER_PROGRAM = "SELECT t3.*,\n" + "       t4.preparer,\n"
      + "       t4.scrutinizer,\n" + "       t4.last_modified,\n" + "       t4.id\n"
      + "  FROM    (  SELECT t1.SEMESTER_ID,\n" + "                    T2.COURSE_ID\n"
      + "               FROM semester_syllabus_map t1, mst_course t2,COURSE_SYLLABUS_MAP t3\n"
      + "              WHERE     t1.program_id = ?\n"
      + "                    AND t1.semester_id = ?\n"
      + "                    AND t1.syllabus_id = t3.syllabus_id\n"
      + "                    AND t1.year = t2.year\n"
      + "                    AND T1.SEMESTER = t2.semester\n"
      + "                    AND T2.COURSE_ID=T3.COURSE_ID\n" + "%s" + "%s"
      + "                    AND T2.OFFER_BY = ? " + "           ORDER BY t3.syllabus_id,\n"
      + "                    t2.year,\n" + "                    t2.semester) t3\n"
      + "       LEFT JOIN\n" + "          PREPARER_SCRUTINIZER t4\n"
      + "       ON t3.course_id = t4.course_id " + "%s"
      + "ORDER BY t3.COURSE_ID, t4.PREPARER, t4.SCRUTINIZER";

  public PersistentExaminerDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  protected String getSelectSql() {
    return SELECT_ALL;
  }

  @Override
  protected String getSelectBySemesterProgram() {
    return SELECT_BY_SEMESTER_PROGRAM;
  }

  @Override
  protected RowMapper<Examiner> getRowMapper() {
    return new ExaminerRowMapper();
  }

  @Override
  public int delete(MutableExaminer pMutable) throws Exception {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableExaminer pMutable) throws Exception {
    return mJdbcTemplate.update(INSERT_ONE, pMutable.getSemester().getId(), pMutable.getPreparer()
        .getId(), pMutable.getScrutinizer().getId(), pMutable.getCourse().getId());
  }

  @Override
  public int update(MutableExaminer pMutable) throws Exception {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate
        .update(query, pMutable.getSemester().getId(), pMutable.getPreparer().getId(), pMutable
            .getScrutinizer().getId(), pMutable.getCourse().getId(), pMutable.getId());
  }

  class ExaminerRowMapper implements RowMapper<Examiner> {
    @Override
    public Examiner mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableExaminer examiner = new PersistentExaminer();
      examiner.setId(rs.getInt("ID"));
      examiner.setCourseId(rs.getString("COURSE_ID"));
      examiner.setSemesterId(rs.getInt("SEMESTER_ID"));
      examiner.setPreparerId(rs.getString("PREPARER"));
      examiner.setScrutinizerId(rs.getString("SCRUTINIZER"));
      examiner.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<Examiner> atomicReference = new AtomicReference<>(examiner);
      return atomicReference.get();
    }
  }
}
