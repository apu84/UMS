package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.PublisherDaoDecorator;
import org.ums.decorator.library.SupplierDaoDecorator;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.persistent.model.library.PersistentPublisher;
import org.ums.persistent.model.library.PersistentSupplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class PersistentPublisherDao extends PublisherDaoDecorator {
  static String SELECT_ALL = "Select ID,NAME,COUNTRY,LAST_MODIFIED  FROM MST_PUBLISHER";

  private JdbcTemplate mJdbcTemplate;

  public PersistentPublisherDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Publisher get(final Integer pId) {
    String query = SELECT_ALL + " Where Id = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentPublisherDao.PublisherRowMapper());
  }

  @Override
  public List<Publisher> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentPublisherDao.PublisherRowMapper());
  }

  class PublisherRowMapper implements RowMapper<Publisher> {
    @Override
    public Publisher mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentPublisher publisher = new PersistentPublisher();
      publisher.setId(resultSet.getInt("ID"));
      publisher.setName(resultSet.getString("NAME"));
      publisher.setCountryId(resultSet.getInt("COUNTRY"));
      publisher.setLastModified(resultSet.getString("LAST_MODIFIED"));

      AtomicReference<Publisher> atomicReference = new AtomicReference<>(publisher);
      return atomicReference.get();
    }
  }

}
