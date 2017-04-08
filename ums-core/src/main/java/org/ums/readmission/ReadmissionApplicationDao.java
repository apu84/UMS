package org.ums.readmission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import com.google.common.collect.Lists;

public class ReadmissionApplicationDao extends ReadmissionApplicationDaoDecorator {
  String SELECT_ALL =
      "SELECT STUDENT_ID, SEMESTER_ID, COURSE_ID, STATUS, APPLIED_ON, VERIFIED_ON, LAST_MODIFIED FROM READMISSION_APPLICATION";
  String INSERT_ALL =
      "INSERT INTO READMISSION_APPLICATION(STUDENT_ID, SEMESTER_ID, COURSE_ID, STATUS, APPLIED_ON, VERIFIED_ON, LAST_MODIFIED) "
          + "VALUES (?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";
  String UPDATE_ALL = "UPDATE READMISSION_APPLICATION SET STUDENT_ID = ?, SEMESTER_ID = ?, COURSE_ID = ?, STATUS = ?, "
      + "APPLIED_ON = ?, VERIFIED_ON = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM READMISSION_APPLICATION ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public ReadmissionApplicationDao(JdbcTemplate mJdbcTemplate, IdGenerator mIdGenerator) {
    this.mJdbcTemplate = mJdbcTemplate;
    this.mIdGenerator = mIdGenerator;
  }

  @Override
  public List<ReadmissionApplication> getReadmissionApplications(Integer pSemesterId) {
    String query = SELECT_ALL + "WHERE SEMESTER_ID = ? ORDER BY SEMESTER_ID, STUDENT_ID";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ReadmissionApplicationRowMapper());
  }

  @Override
  public List<ReadmissionApplication> getReadmissionApplication(Integer pSemesterId, String pStudentId) {
    String query = SELECT_ALL + "WHERE SEMESTER_ID = ? ORDER BY SEMESTER_ID";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new ReadmissionApplicationRowMapper());
  }

  @Override
  public List<ReadmissionApplication> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new ReadmissionApplicationRowMapper());
  }

  @Override
  public ReadmissionApplication get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new ReadmissionApplicationRowMapper());
  }

  @Override
  public int update(MutableReadmissionApplication pMutable) {
    return update(Lists.newArrayList(pMutable));
  }

  @Override
  public int update(List<MutableReadmissionApplication> pMutableList) {
    return mJdbcTemplate.update(UPDATE_ALL, getUpdateParamArray(pMutableList, false));
  }

  @Override
  public int delete(MutableReadmissionApplication pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableReadmissionApplication pMutable) {
    return create(Lists.newArrayList(pMutable)).get(0);
  }

  @Override
  public List<Long> create(List<MutableReadmissionApplication> pMutableList) {
    List<Object[]> params = getUpdateParamArray(pMutableList, true);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    return params.stream().map(param -> (Long) param[0]).collect(Collectors.toList());
  }

  private List<Object[]> getUpdateParamArray(List<MutableReadmissionApplication> pReadmissionApplications,
      boolean pCreate) {
    List<Object[]> params = new ArrayList<>();
    for(ReadmissionApplication pApplication : pReadmissionApplications) {
      params.add(new Object[] {pApplication.getStudentId(), pApplication.getSemesterId(), pApplication.getCourseId(),
          pApplication.getApplicationStatus().getId(), pApplication.getAppliedOn(), pApplication.getVerifiedOn(),
          pCreate ? mIdGenerator.getNumericId() : pApplication.getId()});
    }
    return params;
  }

  class ReadmissionApplicationRowMapper implements RowMapper<ReadmissionApplication> {
    @Override
    public ReadmissionApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableReadmissionApplication application = new PersistentReadmissionApplication();
      application.setSemesterId(rs.getInt("SEMESTER_ID"));
      application.setStudentId(rs.getString("STUDENT_ID"));
      application.setCourseId(rs.getString("COURSE_ID"));
      application.setApplicationStatus(ReadmissionApplication.Status.get(rs.getInt("STATUS")));
      application.setAppliedOn(rs.getTimestamp("APPLIED_ON"));
      if(rs.getObject("VERIFIED_ON") != null) {
        application.setVerifiedOn(rs.getTimestamp("VERIFIED_ON"));
      }
      application.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<ReadmissionApplication> atomicReference = new AtomicReference<>(application);
      return atomicReference.get();
    }
  }
}
