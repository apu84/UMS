package org.ums.ems.profilemanagement.award;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentAwardInformationDao extends AwardInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_AWARD_INFO (ID, EMPLOYEE_ID, AWARD_NAME, INSTITUTION, AWARDED_YEAR, AWARD_SHORT_DESCRIPTION, LAST_MODIFIED) VALUES (?, ? ,? ,?, ?, ?,"
          + getLastModifiedSql() + ")";

  static String GET_ALL =
      "SELECT ID, EMPLOYEE_ID, AWARD_NAME, INSTITUTION, AWARDED_YEAR, AWARD_SHORT_DESCRIPTION, LAST_MODIFIED FROM EMP_AWARD_INFO";

  static String DELETE_ONE = "DELETE FROM EMP_AWARD_INFO ";

  static String UPDATE_ONE =
      "UPDATE EMP_AWARD_INFO SET AWARD_NAME=?, INSTITUTION=?, AWARDED_YEAR=?, AWARD_SHORT_DESCRIPTION=?, LAST_MODIFIED="
          + getLastModifiedSql() + " ";

  static String EXISTS_ONE = "SELECT COUNT(EMPLOYEE_ID) FROM EMP_AWARD_INFO ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAwardInformationDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long create(MutableAwardInformation pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getEmployeeId(), pMutable.getAwardName(),
        pMutable.getAwardInstitute(), pMutable.getAwardedYear(), pMutable.getAwardShortDescription());
    return id;
  }

  @Override
  public AwardInformation get(final Long pId) {
    String query = GET_ALL + " WHERE ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentAwardInformationDao.RoleRowMapper());
  }

  @Override
  public List<AwardInformation> get(final String pEmployeeId) {
    String query = GET_ALL + " WHERE EMPLOYEE_ID = ? ORDER BY AWARDED_YEAR DESC ";
    return mJdbcTemplate.query(query, new Object[] {pEmployeeId}, new PersistentAwardInformationDao.RoleRowMapper());
  }

  @Override
  public int update(MutableAwardInformation pMutable) {
    String query = UPDATE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getAwardName(), pMutable.getAwardInstitute(),
        pMutable.getAwardedYear(), pMutable.getAwardShortDescription(), pMutable.getId(), pMutable.getEmployeeId());
  }

  @Override
  public int delete(MutableAwardInformation pMutable) {
    String query = DELETE_ONE + " WHERE ID = ? AND EMPLOYEE_ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getEmployeeId());
  }

  @Override
  public boolean exists(String pEmployeeId) {
    String query = EXISTS_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId}, Boolean.class);
  }

  class RoleRowMapper implements RowMapper<AwardInformation> {
    @Override
    public AwardInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAwardInformation awardInformation = new PersistentAwardInformation();
      awardInformation.setId(resultSet.getLong("ID"));
      awardInformation.setEmployeeId(resultSet.getString("EMPLOYEE_ID"));
      awardInformation.setAwardName(resultSet.getString("AWARD_NAME"));
      awardInformation.setAwardInstitute(resultSet.getString("INSTITUTION"));
      awardInformation.setAwardedYear(resultSet.getInt("AWARDED_YEAR"));
      awardInformation.setAwardShortDescription(resultSet.getString("AWARD_SHORT_DESCRIPTION"));
      awardInformation.setLastModified(resultSet.getString("LAST_MODIFIED"));
      return awardInformation;
    }
  }
}
