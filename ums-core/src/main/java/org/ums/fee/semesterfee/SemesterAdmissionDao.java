package org.ums.fee.semesterfee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

public class SemesterAdmissionDao extends SemesterAdmissionStatusDaoDecorator {
  private static final Logger mLogger = LoggerFactory.getLogger(SemesterAdmissionDao.class);

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

  @Override
  public Optional<SemesterAdmissionStatus> getAdmissionStatus(String pStudentId, Integer pSemesterId) {
    String query = SELECT_ALL + "WHERE STUDENT_ID = ? AND SEMESTER_ID = ?";
    return Optional.of(mJdbcTemplate.queryForObject(query, new Object[] {pStudentId, pSemesterId},
        new SemesterAdmissionStatusRowMapper()));
  }

  @Override
  public Optional<SemesterAdmissionStatus> lastAdmissionStatus(String pStudentId) {
    String query = "WHERE STUDENT_ID = ?";
    List<SemesterAdmissionStatus> statuses =
        mJdbcTemplate.query(query, new Object[] {pStudentId}, new SemesterAdmissionStatusRowMapper());
    if(statuses != null && statuses.size() > 0) {
      Collections.sort(statuses, (SemesterAdmissionStatus o1, SemesterAdmissionStatus o2) -> o1.getSemester()
          .getStartDate().compareTo(o2.getSemester().getStartDate()));
      return Optional.of(statuses.get(0));
    }
    return Optional.empty();
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
