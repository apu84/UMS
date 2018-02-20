package org.ums.persistent.dao;

import jdk.nashorn.internal.objects.annotations.Where;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.DesignationDaoDecorator;
import org.ums.domain.model.immutable.Designation;
import org.ums.persistent.model.PersistentDesignation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentDesignationDao extends DesignationDaoDecorator {

  static String SELECT_ALL = "SELECT DESIGNATION_ID, DESIGNATION_NAME, EMPLOYEE_TYPE FROM MST_DESIGNATION";

  private JdbcTemplate mJdbcTemplate;

  public PersistentDesignationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Designation get(final Integer pDesignationId) {
    String query = SELECT_ALL + " WHERE DESIGNATION_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pDesignationId},
        new PersistentDesignationDao.RoleRowMapper());
  }

  @Override
  public List<Designation> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentDesignationDao.RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<Designation> {
    @Override
    public Designation mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentDesignation designation = new PersistentDesignation();
      designation.setId(resultSet.getInt("DESIGNATION_ID"));
      designation.setDesignationName(resultSet.getString("DESIGNATION_NAME"));
      designation.setEmployeeType(resultSet.getInt("EMPLOYEE_TYPE"));
      return designation;
    }
  }
}
