package org.ums.ems.profilemanagement.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentServiceInformationDetailDao extends ServiceInformationDetailDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_SERVICE_DETAIL (ID, EMPLOYMENT_PERIOD, START_DATE, END_DATE, COMMENTS, SERVICE_ID, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, ?, "
          + getLastModifiedSql() + ")";

  static String GET_ALL =
      "SELECT ID, EMPLOYMENT_PERIOD, START_DATE, END_DATE, COMMENTS, SERVICE_ID FROM EMP_SERVICE_DETAIL ";

  static String UPDATE_ONE =
      "UPDATE EMP_SERVICE_DETAIL SET EMPLOYMENT_PERIOD = ?, START_DATE = ?, END_DATE = ?, COMMENTS = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM EMP_SERVICE_DETAIL ";

  static String EXISTS_ONE = "SELECT COUNT(ID) FROM EMP_SERVICE_DETAIL ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentServiceInformationDetailDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long create(MutableServiceInformationDetail pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getEmploymentPeriod().getId(), pMutable.getStartDate(),
        pMutable.getEndDate(), pMutable.getComment(), pMutable.getServiceId());
    return id;
  }

  @Override
  public ServiceInformationDetail get(Long pId) {
    String query = GET_ALL + " WHERE ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentServiceInformationDetailDao.RoleRowMapper());
  }

  @Override
  public List<ServiceInformationDetail> getServiceDetail(Long pServiceId) {
    String query = GET_ALL + " WHERE SERVICE_ID = ? ORDER BY START_DATE DESC ";
    return mJdbcTemplate.query(query, new Object[] {pServiceId},
        new PersistentServiceInformationDetailDao.RoleRowMapper());
  }

  @Override
  public int update(MutableServiceInformationDetail pMutable) {
    String query = UPDATE_ONE + " WHERE ID = ? AND SERVICE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getEmploymentPeriod().getId(), pMutable.getStartDate(),
        pMutable.getEndDate(), pMutable.getComment(), pMutable.getId(), pMutable.getServiceId());
  }

  @Override
  public int delete(MutableServiceInformationDetail pMutable) {
    String query = DELETE_ONE + " WHERE ID = ? AND EMPLOYMENT_PERIOD = ? AND SERVICE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getEmploymentPeriodId(), pMutable.getServiceId());
  }

  @Override
  public boolean exists(Long pId) {
    String query = EXISTS_ONE + " WHERE SERVICE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, Boolean.class);
  }

  class RoleRowMapper implements RowMapper<ServiceInformationDetail> {
    @Override
    public ServiceInformationDetail mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentServiceInformationDetail persistentServiceInformationDetail = new PersistentServiceInformationDetail();
      persistentServiceInformationDetail.setId(resultSet.getLong("ID"));
      persistentServiceInformationDetail.setEmploymentPeriodId(resultSet.getInt("EMPLOYMENT_PERIOD"));
      persistentServiceInformationDetail.setStartDate(resultSet.getDate("START_DATE"));
      persistentServiceInformationDetail.setEndDate(resultSet.getDate("END_DATE"));
      persistentServiceInformationDetail.setComment(resultSet.getString("COMMENTS"));
      persistentServiceInformationDetail.setServiceId(resultSet.getLong("SERVICE_ID"));
      return persistentServiceInformationDetail;
    }
  }
}
