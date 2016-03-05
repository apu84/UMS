package org.ums.academic.dao;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentSemesterEnrollment;
import org.ums.domain.model.mutable.MutableSemesterEnrollment;
import org.ums.domain.model.readOnly.SemesterEnrollment;
import org.ums.util.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentSemesterEnrollmentDao extends SemesterEnrollmentDaoDecorator {
  private String SELECT_ALL = "SELECT SEMESTER_ID, PROGRAM_ID, STUDENT_YEAR, STUDENT_SEMESTER, ENROLL_DATE, ENROLL_TYPE, LAST_MODIFIED, ID FROM SEMESTER_ENROLLMENT ";
  private String INSERT_ALL = "INSERT INTO SEMESTER_ENROLLMENT(SEMESTER_ID, PROGRAM_ID, STUDENT_YEAR, STUDENT_SEMESTER, ENROLL_DATE, ENROLL_TYPE, LAST_MODIFIED) VALUES" +
      "(?, ?, ?, ?, TO_DATE(?, " + Constants.DATE_FORMAT + "), ?, " + getLastModifiedSql() + ") ";
  private String UPDATE_ALL = "UPDATE SEMESTER_ENROLLMENT SET " +
      "SEMESTER_ID = ?," +
      "PROGRAM_ID = ?," +
      "STUDENT_YEAR = ?," +
      "STUDENT_SEMESTER = ?," +
      "ENROLL_DATE = TO_DATE(?, " + Constants.DATE_FORMAT + ")," +
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
        mDateFormat.format(pMutable.getEnrollmentDate()),
        pMutable.getType().getValue());
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
