package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.ContributorDaoDecorator;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.mutable.library.MutableContributor;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.enums.common.Gender;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.library.PersistentContributor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class PersistentContributorDao extends ContributorDaoDecorator {
  static String SELECT_ALL =
      " SELECT  ID, FULL_NAME, SHORT_NAME, GENDER, ADDRESS, COUNTRY, CATEGORY_ID, LAST_MODIFIED "
          + "FROM  MST_CONTRIBUTOR";
  static String SELECT_COUNT = "Select COUNT(ID) FROM MST_CONTRIBUTOR";
  static String UPDATE_ONE = "UPDATE MST_CONTRIBUTOR SET FULL_NAME = ? ,SHORT_NAME= ? ,GENDER= ?, ADDRESS = ?, "
      + " COUNTRY=?,CATEGORY_ID=?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM MST_CONTRIBUTOR ";

  private JdbcTemplate mJdbcTemplate;
  public IdGenerator mIdGenerator;

  public PersistentContributorDao(final JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Contributor get(final Long pId) {
    String query = SELECT_ALL + " Where Id = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentContributorDao.ContributorRowMapper());
  }

  @Override
  public List<Contributor> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentContributorDao.ContributorRowMapper());
  }

  @Override
  public List<Contributor> getAllForPagination(final Integer pItemPerPage, final Integer pPage,
      final String pWhereClause, final String pOrder) {
    int startIndex = pItemPerPage * pPage - pItemPerPage + 1;
    int endIndex = startIndex + pItemPerPage;
    String query =
        "Select tmp2.*,ind  From (Select ROWNUM ind, tmp1.*  From (" + SELECT_ALL + pWhereClause + pOrder
            + ")tmp1 ) tmp2  WHERE ind >=? and ind<=?  ";
    return mJdbcTemplate.query(query, new Object[] {startIndex, endIndex},
        new PersistentContributorDao.ContributorRowMapper());
  }

  @Override
  public int getTotalForPagination(final String pWhereClause) {
    String query = SELECT_COUNT + pWhereClause;
    return mJdbcTemplate.queryForObject(query, new Object[] {}, Integer.class);
  }

  @Override
  public Long create(final MutableContributor pContributor) {
    Long id = mIdGenerator.getNumericId();
    pContributor.setId(id);
    String INSERT_ONE =
        "INSERT INTO MST_CONTRIBUTOR(ID, FULL_NAME, SHORT_NAME, GENDER, ADDRESS, COUNTRY, CATEGORY_ID, LAST_MODIFIED) "
            + "VALUES(?, ?,  ?, ?, ?,?, ?," + getLastModifiedSql() + ")";

    mJdbcTemplate.update(INSERT_ONE, pContributor.getId(), pContributor.getFullName(), pContributor.getShortName(),
        pContributor.getGender().getId(), pContributor.getAddress(), pContributor.getCountry().getId(), 1);

    return pContributor.getId();
  }

  @Override
  public int update(final MutableContributor pContributor) {
    String query = UPDATE_ONE + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pContributor.getFullName(), pContributor.getShortName(), pContributor
        .getGender().getId(), pContributor.getAddress(), pContributor.getCountryId(), pContributor.getLastModified(),
        pContributor.getId());
  }

  @Override
  public int delete(final MutableContributor pContributor) {
    String query = DELETE_ONE + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pContributor.getId());
  }

  class ContributorRowMapper implements RowMapper<Contributor> {
    @Override
    public Contributor mapRow(ResultSet resultSet, int i) throws SQLException {

      PersistentContributor contributor = new PersistentContributor();
      contributor.setId(resultSet.getLong("ID"));
      contributor.setFullName(resultSet.getString("FULL_NAME"));
      contributor.setShortName(resultSet.getString("SHORT_NAME"));
      contributor.setGender(Gender.get(resultSet.getInt("GENDER")));
      contributor.setAddress(resultSet.getString("ADDRESS"));
      contributor.setCountryId(resultSet.getInt("COUNTRY"));
      contributor.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<Contributor> atomicReference = new AtomicReference<>(contributor);
      return atomicReference.get();
    }
  }

}
