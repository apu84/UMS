package org.ums.employee.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentServiceInformationDetailDao extends ServiceInformationDetailDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_SERVICE_DETAIL (ID, EMPLOYMENT_PERIOD, START_DATE, END_DATE, SERVICE_ID, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, "
          + getLastModifiedSql() + ")";

  static String GET_ONE = "SELECT ID, EMPLOYMENT_PERIOD, START_DATE, END_DATE, SERVICE_ID FROM EMP_SERVICE_DETAIL ";

  static String UPDATE_ONE =
      "UPDATE EMP_SERVICE_DETAIL SET EMPLOYMENT_PERIOD = ?, START_DATE = ?, END_DATE = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM EMP_SERVICE_DETAIL ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentServiceInformationDetailDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public int saveServiceInformationDetail(List<MutableServiceInformationDetail> pMutableServiceInformationDetail) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getServiceInformationDetailInsertParams(pMutableServiceInformationDetail)).length;
  }

  private List<Object[]> getServiceInformationDetailInsertParams(
      List<MutableServiceInformationDetail> pMutableServiceInformationDetail) {
    List<Object[]> params = new ArrayList<>();
    for(ServiceInformationDetail serviceInformationDetail : pMutableServiceInformationDetail) {
      params.add(new Object[] {mIdGenerator.getNumericId(), serviceInformationDetail.getEmploymentPeriod().getId(),
          serviceInformationDetail.getStartDate(), serviceInformationDetail.getEndDate(),
          serviceInformationDetail.getServiceId()});

    }
    return params;
  }

  @Override
  public List<ServiceInformationDetail> getServiceInformationDetail(Long pServiceId) {
    String query = GET_ONE + " WHERE SERVICE_ID = ? ORDER BY START_DATE ASC ";
    return mJdbcTemplate.query(query, new Object[] {pServiceId},
        new PersistentServiceInformationDetailDao.RoleRowMapper());
  }

  @Override
  public int updateServiceInformationDetail(List<MutableServiceInformationDetail> pMutableServiceInformationDetail) {
    String query = UPDATE_ONE + " WHERE ID = ? AND SERVICE_ID = ?";
    return mJdbcTemplate.batchUpdate(query, getServiceInformationDetailUpdateParams(pMutableServiceInformationDetail)).length;
  }

  private List<Object[]> getServiceInformationDetailUpdateParams(
      List<MutableServiceInformationDetail> pMutableServiceInformationDetail) {
    List<Object[]> params = new ArrayList<>();
    for(ServiceInformationDetail serviceInformationDetail : pMutableServiceInformationDetail) {
      params.add(new Object[] {serviceInformationDetail.getEmploymentPeriod().getId(),
          serviceInformationDetail.getStartDate(), serviceInformationDetail.getEndDate(),
          serviceInformationDetail.getId(), serviceInformationDetail.getServiceId()});

    }
    return params;
  }

  @Override
  public int deleteServiceInformationDetail(List<MutableServiceInformationDetail> pMutableServiceInformationDetail) {
    String query = DELETE_ONE + " WHERE ID = ? AND EMPLOYMENT_PERIOD = ? AND SERVICE_ID = ?";
    return mJdbcTemplate.batchUpdate(query, getServiceInformationDetailDeleteParams(pMutableServiceInformationDetail)).length;
  }

  private List<Object[]> getServiceInformationDetailDeleteParams(
      List<MutableServiceInformationDetail> pMutableServiceInformationDetail) {
    List<Object[]> params = new ArrayList<>();
    for(ServiceInformationDetail serviceInformationDetail : pMutableServiceInformationDetail) {
      params.add(new Object[] {serviceInformationDetail.getId(),
          serviceInformationDetail.getEmploymentPeriod().getId(), serviceInformationDetail.getServiceId()});
    }
    return params;
  }

  class RoleRowMapper implements RowMapper<ServiceInformationDetail> {
    @Override
    public ServiceInformationDetail mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentServiceInformationDetail persistentServiceInformationDetail = new PersistentServiceInformationDetail();
      persistentServiceInformationDetail.setId(resultSet.getLong("ID"));
      persistentServiceInformationDetail.setEmploymentPeriodId(resultSet.getInt("EMPLOYMENT_PERIOD"));
      persistentServiceInformationDetail.setStartDate(resultSet.getDate("START_DATE"));
      persistentServiceInformationDetail.setEndDate(resultSet.getDate("END_DATE"));
      persistentServiceInformationDetail.setServiceId(resultSet.getLong("SERVICE_ID"));
      return persistentServiceInformationDetail;
    }
  }
}
