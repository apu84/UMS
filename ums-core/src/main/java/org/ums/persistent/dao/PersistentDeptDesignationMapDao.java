package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.DeptDesignationMapDaoDecorator;
import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.persistent.model.PersistentDeptDesignationMap;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentDeptDesignationMapDao extends DeptDesignationMapDaoDecorator {

  static String SELECT_ALL = "SELECT ID, DEPARTMENT, EMPLOYEE_ID, DESIGNATION, LAST_MODIFIED FROM DEPT_DESIGNATION_MAP";

  private JdbcTemplate mJdbcTemplate;

  public PersistentDeptDesignationMapDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  class DeptDesignationRowMapper implements RowMapper<DeptDesignationMap> {

    @Override
    public DeptDesignationMap mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentDeptDesignationMap persistentDeptDesignationMap = new PersistentDeptDesignationMap();
      persistentDeptDesignationMap.setId(rs.getInt("ID"));
      persistentDeptDesignationMap.setLastModified(rs.getString("LAST_MODIFIED"));
      return null;
    }
  }

}
