package org.ums.persistent.dao;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.persistent.model.PersistentSemesterEnrollment;
import org.ums.decorator.SemesterEnrollmentDaoDecorator;
import org.ums.domain.model.mutable.MutableSemesterEnrollment;
import org.ums.domain.model.immutable.SemesterEnrollment;
import org.ums.util.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentSemesterEnrollmentDao extends SemesterEnrollmentDaoDecorator {
  private String SELECT_ALL = "SELECT SEMESTER_ID, PROGRAM_ID, STUDENT_YEAR, STUDENT_SEMESTER, ENROLL_DATE, ENROLL_TYPE, LAST_MODIFIED, ID FROM SEMESTER_ENROLLMENT ";
  private String INSERT_ALL = "INSERT INTO SEMESTER_ENROLLMENT(SEMESTER_ID, PROGRAM_ID, STUDENT_YEAR, STUDENT_SEMESTER, ENROLL_DATE, ENROLL_TYPE, LAST_MODIFIED) VALUES" +
      "(?, ?, ?, ?, SYSDATE, ?, " + getLastModifiedSql() + ") ";
  private String UPDATE_ALL = "UPDATE SEMESTER_ENROLLMENT SET " +
      "SEMESTER_ID = ?," +
      "PROGRAM_ID = ?," +
      "STUDENT_YEAR = ?," +
      "STUDENT_SEMESTER = ?," +
      "ENROLL_DATE = TO_DATE(?, '" + Constants.DATE_FORMAT + "')," +
      "ENROLL_TYPE = ?," +
      "LAST_MODIFIED = " + getLastModifiedSql() + " ";
  private String DELETE_ALL = "DELETE FROM SEMESTER_ENROLLMENT ";

  private JdbcTemplate mJdbcTemplate;
  private DateFormat mDateFormat;

  public PersistentSemesterEnrollmentDao(JdbcTemplate pJdbcTemplate, DateFormat pDateFormat) {
    mJdbcTemplate = pJdbcTemplate;
    mDateFormat = pDateFormat;
  }

  @Override
  public SemesterEnrollment get(Integer pId) throws Exception {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new SemesterEnrollmentRowMapper());
  }

  @Override
  public List<SemesterEnrollment> getAll() throws Exception {
    return mJdbcTemplate.query(SELECT_ALL, new SemesterEnrollmentRowMapper());
  }

  @Override
  public int update(MutableSemesterEnrollment pMutable) throws Exception {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query,
        pMutable.getSemester().getId(),
        pMutable.getProgram().getId(),
        pMutable.getYear(),
        pMutable.getAcademicSemester(),
        mDateFormat.format(pMutable.getEnrollmentDate()),
        pMutable.getType().getValue(),
        pMutable.getId());
  }

  @Override
  public int delete(MutableSemesterEnrollment pMutable) throws Exception {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableSemesterEnrollment pMutable) throws Exception {
    return mJdbcTemplate.update(INSERT_ALL,
        pMutable.getSemester().getId(),
        pMutable.getProgram().getId(),
        pMutable.getYear(),
        pMutable.getAcademicSemester(),
        pMutable.getType().getValue());
  }

  @Override
  public List<SemesterEnrollment> getEnrollmentStatus(SemesterEnrollment.Type pType, Integer pProgramId,
                                                      Integer pSemesterId) {
    String query = SELECT_ALL + "WHERE %s AND PROGRAM_ID = ? AND SEMESTER_ID = ?";
    if (pType == SemesterEnrollment.Type.TEMPORARY) {
      query = String.format(query, "(ENROLL_TYPE = ? OR ENROLL_TYPE = ?)");
      return mJdbcTemplate.query(query, new Object[]{SemesterEnrollment.Type.TEMPORARY.getValue(),
          SemesterEnrollment.Type.PERMANENT.getValue(),
          pProgramId, pSemesterId}, new SemesterEnrollmentRowMapper());
    } else {
      query = String.format(query, "ENROLL_TYPE = ?");
      return mJdbcTemplate.query(query, new Object[]{pType.getValue(), pProgramId, pSemesterId}, new SemesterEnrollmentRowMapper());
    }
  }

  @Override
  public SemesterEnrollment getEnrollmentStatus(SemesterEnrollment.Type pType, Integer pProgramId, Integer pSemesterId,
                                                Integer pYear, Integer pAcademicSemester) {
    SemesterEnrollment semesterEnrollment;
    String query = SELECT_ALL + "WHERE ENROLL_TYPE = ? AND PROGRAM_ID = ? AND SEMESTER_ID = ? AND STUDENT_YEAR = ? " +
        "AND STUDENT_SEMESTER = ?";
    try {
      semesterEnrollment = mJdbcTemplate.queryForObject(query,
          new Object[]{pType.getValue(), pProgramId, pSemesterId, pYear, pAcademicSemester}, new SemesterEnrollmentRowMapper());
    } catch (EmptyResultDataAccessException em) {
      semesterEnrollment = null;
    }
    return semesterEnrollment;
  }

  class SemesterEnrollmentRowMapper implements RowMapper<SemesterEnrollment> {
    @Override
    public SemesterEnrollment mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableSemesterEnrollment enrollment = new PersistentSemesterEnrollment();
      enrollment.setId(rs.getInt("ID"));
      enrollment.setSemesterId(rs.getInt("SEMESTER_ID"));
      enrollment.setProgramId(rs.getInt("PROGRAM_ID"));
      enrollment.setYear(rs.getInt("STUDENT_YEAR"));
      enrollment.setAcademicSemester(rs.getInt("STUDENT_SEMESTER"));
      enrollment.setEnrollmentDate(rs.getDate("ENROLL_DATE"));
      enrollment.setType(SemesterEnrollment.Type.get(rs.getInt("ENROLL_TYPE")));
      enrollment.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<SemesterEnrollment> atomicReference = new AtomicReference<>(enrollment);
      return atomicReference.get();
    }
  }
}
