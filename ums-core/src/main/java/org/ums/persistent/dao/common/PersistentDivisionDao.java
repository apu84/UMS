package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.DivisionDaoDecorator;
import org.ums.domain.model.immutable.common.Division;
import org.ums.persistent.model.common.PersistentDivision;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentDivisionDao extends DivisionDaoDecorator {
  static String SELECT_ALL = "Select DIVISION_ID, DIVISION_NAME FROM MST_DIVISION";

  private JdbcTemplate mJdbcTemplate;

  public PersistentDivisionDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Division get(final Integer pId) {
    String query = SELECT_ALL + " Where Id = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentDivisionDao.DivisionRowMapper());
  }

  @Override
  public List<Division> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentDivisionDao.DivisionRowMapper());
  }

  class DivisionRowMapper implements RowMapper<Division> {
    @Override
    public Division mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentDivision division = new PersistentDivision();
      division.setId(resultSet.getInt("division_id"));
      division.setDivisionName(resultSet.getString("division_name"));
      return division;
    }
  }

}
