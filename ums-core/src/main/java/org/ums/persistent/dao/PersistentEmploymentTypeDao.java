package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.EmploymentTypeDaoDecorator;
import org.ums.domain.model.immutable.EmploymentType;
import org.ums.persistent.model.PersistentEmploymentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentEmploymentTypeDao extends EmploymentTypeDaoDecorator {

  static String SELECT_ALL = "SELECT ID, TYPE, LAST_MODIFIED FROM MST_EMPLOYMENT_TYPE";

  private JdbcTemplate mJdbcTemplate;

  public PersistentEmploymentTypeDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<EmploymentType> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentEmploymentTypeDao.RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<EmploymentType> {
    @Override
    public EmploymentType mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentEmploymentType persistentAcademicInformation = new PersistentEmploymentType();
      persistentAcademicInformation.setId(resultSet.getInt("id"));
      persistentAcademicInformation.setType(resultSet.getString("type"));
      persistentAcademicInformation.setLastModified(resultSet.getString("last_modified"));
      return persistentAcademicInformation;
    }
  }
}
