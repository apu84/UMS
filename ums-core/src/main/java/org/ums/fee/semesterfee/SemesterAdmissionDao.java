package org.ums.fee.semesterfee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

public class SemesterAdmissionDao extends SemesterAdmissionStatusDaoDecorator {
  String SELECT_ALL = "SELECT STUDENT_ID, SEMESTER_ID, IS_ADMITTED, LAST_MODIFIED FROM SEMESTER_ADMISSION_STATUS ";
  String INSERT_ALL = "INSERT INTO SEMESTER_ADMISSION_STATUS (STUDENT_ID, SEMESTER_ID, IS_ADMITTED, LAST_MODIFIED) "
      + "VALUES(?, ?, ?, " + getLastModifiedSql() + ")";
  String UPDATE_ALL = "UPDATE SEMESTER_ADMISSION_STATUS SET IS_ADMITTED = ?, LAST_MODIFIED = " + getLastModifiedSql()
      + " ";
  String DELETE_ALL = "DELETE FROM SEMESTER_ADMISSION_STATUS ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public SemesterAdmissionDao(JdbcTemplate mJdbcTemplate, IdGenerator mIdGenerator) {
    this.mJdbcTemplate = mJdbcTemplate;
    this.mIdGenerator = mIdGenerator;
  }

  class SemesterAdmissionStatusRowMapper implements RowMapper<SemesterAdmissionStatus> {
    @Override
    public SemesterAdmissionStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableSemesterAdmissionStatus admissionStatus = new PersistentSemesterAdmissionStatus();
      admissionStatus.setStudentId(rs.getString("STUDENT_ID"));
      admissionStatus.setSemesterId(rs.getInt("SEMESTER_ID"));
      admissionStatus.setAdmitted(rs.getBoolean("IS_ADMITTED"));
      admissionStatus.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<SemesterAdmissionStatus> atomicReference = new AtomicReference<>(admissionStatus);
      return atomicReference.get();
    }
  }
}
