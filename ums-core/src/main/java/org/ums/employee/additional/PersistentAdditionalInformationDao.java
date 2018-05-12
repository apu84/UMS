package org.ums.employee.additional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentAdditionalInformationDao extends AdditionalInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_ADDITIONAL_INFO (EMPLOYEE_ID, ROOM_NO, EXT_NO, ACADEMIC_INITIAL, LAST_MODIFIED) VALUES (?, ?, ?, ?, "
          + getLastModifiedSql() + ")";

  static String GET_ALL = "SELECT EMPLOYEE_ID, ROOM_NO, EXT_NO, ACADEMIC_INITIAL FROM EMP_ADDITIONAL_INFO ";

  static String DELETE_ONE = "DELETE FROM EMP_ADDITIONAL_INFO ";

  static String EXISTS_ONE = "SELECT COUNT(EMPLOYEE_ID) FROM EMP_ADDITIONAL_INFO";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdditionalInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public String create(MutableAdditionalInformation pMutable) {
    mJdbcTemplate.update(INSERT_ONE, pMutable.getId(), pMutable.getRoomNo(), pMutable.getExtNo(),
        pMutable.getAcademicInitial());
    return pMutable.getId();
  }

  @Override
  public AdditionalInformation get(String pEmployeeId) {
    String query = GET_ALL + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId},
        new PersistentAdditionalInformationDao.RoleRowMapper());
  }

  @Override
  public int delete(MutableAdditionalInformation pMutable) {
    String query = DELETE_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public boolean exists(String pEmployeeId) {
    String query = EXISTS_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId}, Boolean.class);
  }

  class RoleRowMapper implements RowMapper<AdditionalInformation> {

    @Override
    public AdditionalInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAdditionalInformation persistentAdditionalInformation = new PersistentAdditionalInformation();
      persistentAdditionalInformation.setId(resultSet.getString("EMPLOYEE_ID"));
      persistentAdditionalInformation.setRoomNo(resultSet.getString("ROOM_NO"));
      persistentAdditionalInformation.setExtNo(resultSet.getString("EXT_NO"));
      persistentAdditionalInformation.setAcademicInitial(resultSet.getString("ACADEMIC_INITIAL"));
      return persistentAdditionalInformation;
    }
  }
}
