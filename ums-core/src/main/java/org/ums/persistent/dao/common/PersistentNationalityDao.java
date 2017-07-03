package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.NationalityDaoDecorator;
import org.ums.domain.model.immutable.common.Nationality;
import org.ums.persistent.model.common.PersistentNationality;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentNationalityDao extends NationalityDaoDecorator {
  static String SELECT_ALL = "Select ID, NAME FROM MST_NATIONALITY_TYPE";

  private JdbcTemplate mJdbcTemplate;

  public PersistentNationalityDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Nationality get(final Integer pId) {
    String query = SELECT_ALL + " WHERE ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentNationalityDao.NationalityRowMapper());
  }

  @Override
  public List<Nationality> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentNationalityDao.NationalityRowMapper());
  }

  class NationalityRowMapper implements RowMapper<Nationality> {
    @Override
    public Nationality mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentNationality persistentNationality = new PersistentNationality();
      persistentNationality.setId(resultSet.getInt("id"));
      persistentNationality.setNationality(resultSet.getString("name"));
      return persistentNationality;
    }
  }
}
