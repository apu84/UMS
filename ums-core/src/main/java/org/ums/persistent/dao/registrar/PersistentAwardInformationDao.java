package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.AwardInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.AwardInformation;
import org.ums.domain.model.mutable.registrar.MutableAwardInformation;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.registrar.PersistentAwardInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentAwardInformationDao extends AwardInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_AWARD_INFO (ID, EMPLOYEE_ID, AWARD_NAME, INSTITUTION, AWARDED_YEAR, AWARD_SHORT_DESCRIPTION, LAST_MODIFIED) VALUES (?, ? ,? ,?, ?, ?,"
          + getLastModifiedSql() + ")";

  static String GET_ONE =
      "Select ID, EMPLOYEE_ID, AWARD_NAME, INSTITUTION, AWARDED_YEAR, AWARD_SHORT_DESCRIPTION, LAST_MODIFIED From EMP_AWARD_INFO";

  static String DELETE_ALL = "DELETE FROM EMP_AWARD_INFO ";

  static String UPDATE_ALL =
      "UPDATE EMP_AWARD_INFO SET AWARD_NAME=?, INSTITUTION=?, AWARDED_YEAR=?, AWARD_SHORT_DESCRIPTION=?, LAST_MODIFIED="
          + getLastModifiedSql() + " ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAwardInformationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public int saveAwardInformation(List<MutableAwardInformation> pMutableAwardInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getEmployeeAwardInformationParams(pMutableAwardInformation)).length;
  }

  @Override
  public int deleteAwardInformation(String pEmployeeId) {
    String query = DELETE_ALL + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pEmployeeId);
  }

  private List<Object[]> getEmployeeAwardInformationParams(List<MutableAwardInformation> pMutableAwardInformation) {
    List<Object[]> params = new ArrayList<>();
    for(AwardInformation awardInformation : pMutableAwardInformation) {
      params.add(new Object[] {mIdGenerator.getNumericId(), awardInformation.getEmployeeId(),
          awardInformation.getAwardName(), awardInformation.getAwardInstitute(), awardInformation.getAwardedYear(),
          awardInformation.getAwardShortDescription()});

    }
    return params;
  }

  @Override
  public List<AwardInformation> getEmployeeAwardInformation(final String employeeId) {
    String query = GET_ONE + " Where employee_id = ?";
    return mJdbcTemplate.query(query, new Object[] {employeeId}, new PersistentAwardInformationDao.RoleRowMapper());
  }

  @Override
  public int updateAwardInformation(List<MutableAwardInformation> pMutableAwardInformation) {
    String query = UPDATE_ALL + "WHERE EMPLOYEE_ID = ? and ID = ? ";
    return mJdbcTemplate.batchUpdate(query, getUpdateParams(pMutableAwardInformation)).length;
  }

  private List<Object[]> getUpdateParams(List<MutableAwardInformation> pMutableAwardInformation) {
    List<Object[]> params = new ArrayList<>();
    for(AwardInformation pAwardInformation : pMutableAwardInformation) {
      params.add(new Object[] {pAwardInformation.getAwardName(), pAwardInformation.getAwardInstitute(),
          pAwardInformation.getAwardedYear(), pAwardInformation.getAwardShortDescription(),
          pAwardInformation.getEmployeeId(), pAwardInformation.getId()});
    }
    return params;
  }

  @Override
  public int deleteAwardInformation(List<MutableAwardInformation> pMutableAwardInformation) {
    String query = DELETE_ALL + "WHERE ID=? AND EMPLOYEE_ID=?";
    return mJdbcTemplate.batchUpdate(query, getDeleteParams(pMutableAwardInformation)).length;
  }

  private List<Object[]> getDeleteParams(List<MutableAwardInformation> pMutableAwardInformation) {
    List<Object[]> params = new ArrayList<>();
    for(AwardInformation pAwardInformation : pMutableAwardInformation) {
      params.add(new Object[] {pAwardInformation.getId(), pAwardInformation.getEmployeeId()});
    }
    return params;
  }

  class RoleRowMapper implements RowMapper<AwardInformation> {
    @Override
    public AwardInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAwardInformation awardInformation = new PersistentAwardInformation();
      awardInformation.setId(resultSet.getLong("id"));
      awardInformation.setEmployeeId(resultSet.getString("employee_id"));
      awardInformation.setAwardName(resultSet.getString("award_name"));
      awardInformation.setAwardInstitute(resultSet.getString("institution"));
      awardInformation.setAwardedYear(resultSet.getInt("awarded_year"));
      awardInformation.setAwardShortDescription(resultSet.getString("award_short_description"));
      awardInformation.setLastModified(resultSet.getString("last_modified"));
      return awardInformation;
    }
  }
}
