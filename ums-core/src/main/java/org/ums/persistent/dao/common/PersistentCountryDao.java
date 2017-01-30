package org.ums.persistent.dao.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.common.CountryDaoDecorator;
import org.ums.domain.model.immutable.common.Country;
import org.ums.persistent.model.common.PersistentCountry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 31-Jan-17.
 */
public class PersistentCountryDao extends CountryDaoDecorator {
  static String SELECT_ALL = "Select ID,CODE, NAME,LAST_MODIFIED  From MST_COUNTRY ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentCountryDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Country get(final Integer pId) {
    String query = SELECT_ALL + " Where Id = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentCountryDao.CountryRowMapper());
  }

  @Override
  public List<Country> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentCountryDao.CountryRowMapper());
  }

  class CountryRowMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentCountry country = new PersistentCountry();
      country.setId(resultSet.getInt("ID"));
      country.setCode(resultSet.getString("CODE"));
      country.setName(resultSet.getString("NAME"));
      country.setLastModified(resultSet.getString("LAST_MODIFIED"));

      AtomicReference<Country> atomicReference = new AtomicReference<>(country);
      return atomicReference.get();
    }
  }

}
