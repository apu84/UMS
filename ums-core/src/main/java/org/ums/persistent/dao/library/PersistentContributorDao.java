package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.ContributorDaoDecorator;
import org.ums.domain.model.immutable.library.Contributor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class PersistentContributorDao extends ContributorDaoDecorator {
  static String SELECT_ALL =
      " SELECT  ID, MFN, ROLE,  CONTRIBUTOR_ID, VIEW_ORDER, LAST_MODIFIED FROM  CONTRIBUTOR";

  private JdbcTemplate mJdbcTemplate;

  public PersistentContributorDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Contributor get(final Integer pId) {
    String query = SELECT_ALL + " Where Id = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentContributorDao.ContributorRowMapper());
  }

  @Override
  public List<Contributor> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentContributorDao.ContributorRowMapper());
  }

  class ContributorRowMapper implements RowMapper<Contributor> {
    @Override
    public Contributor mapRow(ResultSet resultSet, int i) throws SQLException {

      // PersistentCountry country = new PersistentCountry();
      // country.setId(resultSet.getInt("ID"));
      // country.setCode(resultSet.getString("CODE"));
      // country.setName(resultSet.getString("NAME"));
      // country.setLastModified(resultSet.getString("LAST_MODIFIED"));

      AtomicReference<Contributor> atomicReference = new AtomicReference<>(null);
      return atomicReference.get();
    }
  }

}
