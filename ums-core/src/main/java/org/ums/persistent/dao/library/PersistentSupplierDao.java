package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.AuthorDaoDecorator;
import org.ums.decorator.library.SupplierDaoDecorator;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.persistent.model.library.PersistentAuthor;
import org.ums.persistent.model.library.PersistentSupplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class PersistentSupplierDao extends SupplierDaoDecorator {
  static String SELECT_ALL =
      "Select ID,NAME,ADDRESS,CONTACT_PERSON,CONTACT_NUMBER,LAST_MODIFIED  FROM MST_SUPPLIER";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSupplierDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Supplier get(final Integer pId) {
    String query = SELECT_ALL + " Where Id = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId},
        new PersistentSupplierDao.SupplierRowMapper());
  }

  @Override
  public List<Supplier> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentSupplierDao.SupplierRowMapper());
  }

  class SupplierRowMapper implements RowMapper<Supplier> {
    @Override
    public Supplier mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentSupplier supplier = new PersistentSupplier();
      supplier.setId(resultSet.getInt("ID"));
      supplier.setName(resultSet.getString("NAME"));
      supplier.setAddress(resultSet.getString("ADDRESS"));
      supplier.setContactPerson(resultSet.getString("CONTACT_PERSON"));
      supplier.setContactNumber(resultSet.getString("CONTACT_NUMBER"));
      supplier.setLastModified(resultSet.getString("LAST_MODIFIED"));

      AtomicReference<Supplier> atomicReference = new AtomicReference<>(supplier);
      return atomicReference.get();
    }
  }

}
