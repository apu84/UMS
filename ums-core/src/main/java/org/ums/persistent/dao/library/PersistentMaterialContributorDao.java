package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.MaterialContributorDaoDecorator;
import org.ums.decorator.library.SubjectDaoDecorator;
import org.ums.domain.model.immutable.library.MaterialContributor;
import org.ums.domain.model.immutable.library.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class PersistentMaterialContributorDao extends MaterialContributorDaoDecorator {
  static String SELECT_ALL =
      " SELECT    ROWID, ID, MFN, ROLE,   CONTRIBUTOR_ID, VIEW_ORDER, LAST_MODIFIED FROM  CONTRIBUTOR";

  private JdbcTemplate mJdbcTemplate;

  public PersistentMaterialContributorDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public MaterialContributor get(final Integer pId) {
    String query = SELECT_ALL + " Where Id = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentMaterialContributorDao.MaterialContributorRowMapper());
  }

  @Override
  public List<MaterialContributor> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentMaterialContributorDao.MaterialContributorRowMapper());
  }

  class MaterialContributorRowMapper implements RowMapper<MaterialContributor> {
    @Override
    public MaterialContributor mapRow(ResultSet resultSet, int i) throws SQLException {

      // PersistentCountry country = new PersistentCountry();
      // country.setId(resultSet.getInt("ID"));
      // country.setCode(resultSet.getString("CODE"));
      // country.setName(resultSet.getString("NAME"));
      // country.setLastModified(resultSet.getString("LAST_MODIFIED"));

      AtomicReference<MaterialContributor> atomicReference = new AtomicReference<>(null);
      return atomicReference.get();
    }
  }

}
