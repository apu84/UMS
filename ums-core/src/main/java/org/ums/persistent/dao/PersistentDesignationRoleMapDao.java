package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.DesignationRoleMapDaoDecorator;
import org.ums.domain.model.immutable.Designation;
import org.ums.domain.model.immutable.DesignationRoleMap;
import org.ums.persistent.model.PersistentDesignation;
import org.ums.persistent.model.PersistentDesignationRoleMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentDesignationRoleMapDao extends DesignationRoleMapDaoDecorator {

  static String SELECT_ALL = "SELECT ID, DESIGNATION, IUMS_ROLE FROM DESIGNATION_ROLE_MAP ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentDesignationRoleMapDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public DesignationRoleMap get(final Integer pDesignationId) {
    String query = SELECT_ALL + " WHERE DESIGNATION = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pDesignationId},
        new PersistentDesignationRoleMapDao.RoleRowMapper());
  }

  @Override
  public List<DesignationRoleMap> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentDesignationRoleMapDao.RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<DesignationRoleMap> {
    @Override
    public DesignationRoleMap mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentDesignationRoleMap persistentDesignationRoleMap = new PersistentDesignationRoleMap();
      persistentDesignationRoleMap.setId(resultSet.getInt("ID"));
      persistentDesignationRoleMap.setDesignationId(resultSet.getInt("DESIGNATION"));
      persistentDesignationRoleMap.setRoleId(resultSet.getInt("IUMS_ROLE"));
      return persistentDesignationRoleMap;
    }
  }
}
