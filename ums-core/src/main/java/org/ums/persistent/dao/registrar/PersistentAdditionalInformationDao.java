package org.ums.persistent.dao.registrar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.registrar.AdditionalInformationDaoDecorator;
import org.ums.domain.model.immutable.registrar.AdditionalInformation;
import org.ums.domain.model.mutable.registrar.MutableAdditionalInformation;
import org.ums.persistent.model.registrar.PersistentAdditionalInformation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentAdditionalInformationDao extends AdditionalInformationDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO EMP_ADDITIONAL_INFO (EMPLOYEE_ID, ROOM_NO, EXT_NO, ACADEMIC_INITIAL, LAST_MODIFIED) VALUES (?, ?, ?, ?, "
          + getLastModifiedSql() + ")";

  static String GET_ONE = "SELECT EMPLOYEE_ID, ROOM_NO, EXT_NO, ACADEMIC_INITIAL FROM EMP_ADDITIONAL_INFO ";

  static String UPDATE_ONE =
      "UPDATE EMP_ADDITIONAL_INFO SET ROOM_NO = ?, EXT_NO = ?, ACADEMIC_INITIAL = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM EMP_ADDITIONAL_INFO ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdditionalInformationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int saveAdditionalInformation(MutableAdditionalInformation pMutableAdditionalInformation) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutableAdditionalInformation.getId(),
        pMutableAdditionalInformation.getRoomNo(), pMutableAdditionalInformation.getExtNo(),
        pMutableAdditionalInformation.getAcademicInitial());
  }

  @Override
  public AdditionalInformation getAdditionalInformation(String pEmployeeId) {
    String query = GET_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pEmployeeId},
        new PersistentAdditionalInformationDao.RoleRowMapper());
  }

  @Override
  public int updateAdditionalInformation(MutableAdditionalInformation pMutableAdditionalInformation) {
    String query = UPDATE_ONE + " WHERE EMPLOYEE_ID = ?";
    return mJdbcTemplate.update(query, pMutableAdditionalInformation.getId());
  }

  class RoleRowMapper implements RowMapper<AdditionalInformation> {

    @Override
    public AdditionalInformation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAdditionalInformation persistentAdditionalInformation = new PersistentAdditionalInformation();
      persistentAdditionalInformation.setId(resultSet.getString("EMPLOYEE_ID"));
      persistentAdditionalInformation.setRoomNo(resultSet.getString("ROOM_NO"));
      persistentAdditionalInformation.setExtNo(resultSet.getInt("EXT_NO"));
      persistentAdditionalInformation.setAcademicInitial(resultSet.getString("ACADEMIC_INITIAL"));
      return persistentAdditionalInformation;
    }
  }
}
