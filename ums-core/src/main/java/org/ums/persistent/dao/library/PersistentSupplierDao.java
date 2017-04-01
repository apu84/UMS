package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.SupplierDaoDecorator;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.generator.IdGenerator;
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
      "Select ID,NAME,EMAIL,CONTACT_PERSON,CONTACT_NUMBER,ADDRESS,NOTE, LAST_MODIFIED  FROM MST_SUPPLIER";

  static String SELECT_COUNT = "Select COUNT(ID) FROM MST_SUPPLIER";

  static String UPDATE_ONE =
      "UPDATE MST_SUPPLIER SET NAME = ? ,EMAIL= ? ,CONTACT_PERSON= ? ,CONTACT_NUMBER= ? ,ADDRESS= ? ,NOTE= ? , LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM MST_SUPPLIER ";

  private JdbcTemplate mJdbcTemplate;
  public IdGenerator mIdGenerator;

  public PersistentSupplierDao(final JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Supplier get(final Long pId) {
    String query = SELECT_ALL + " Where Id = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentSupplierDao.SupplierRowMapper());
  }

  @Override
  public List<Supplier> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentSupplierDao.SupplierRowMapper());
  }

  @Override
  public List<Supplier> getAllForPagination(final Integer pItemPerPage, final Integer pPage, final String pWhereClause,
      final String pOrder) {
    int startIndex = pItemPerPage * pPage - pItemPerPage + 1;
    int endIndex = startIndex + pItemPerPage;
    String query =
        "Select tmp2.*,ind  From (Select ROWNUM ind, tmp1.* From (" + SELECT_ALL + pWhereClause + pOrder
            + ")tmp1 ) tmp2  WHERE ind >=? and ind<=?  ";
    return mJdbcTemplate.query(query, new Object[] {startIndex, endIndex},
        new PersistentSupplierDao.SupplierRowMapper());
  }

  @Override
  public int getTotalForPagination(final String pWhereClause) {
    String query = SELECT_COUNT + pWhereClause;
    return mJdbcTemplate.queryForObject(query, new Object[] {}, Integer.class);
  }

  @Override
  public Long create(final MutableSupplier pSupplier) {
    Long id = mIdGenerator.getNumericId();
    pSupplier.setId(id);
    String INSERT_ONE =
        "INSERT INTO MST_SUPPLIER(ID, NAME, EMAIL, CONTACT_PERSON, CONTACT_NUMBER, ADDRESS, NOTE, LAST_MODIFIED) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?," + getLastModifiedSql() + ")";

    mJdbcTemplate.update(INSERT_ONE, pSupplier.getId(), pSupplier.getName(), pSupplier.getEmail(),
        pSupplier.getContactPerson(), pSupplier.getContactNumber(), pSupplier.getAddress(), pSupplier.getNote());

    return pSupplier.getId();
  }

  @Override
  public int update(final MutableSupplier pSupplier) {
    String query = UPDATE_ONE + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pSupplier.getName(), pSupplier.getEmail(), pSupplier.getContactPerson(),
        pSupplier.getContactNumber(), pSupplier.getAddress(), pSupplier.getNote(), pSupplier.getId());
  }

  @Override
  public int delete(final MutableSupplier pSupplier) {
    String query = DELETE_ONE + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pSupplier.getId());
  }

  class SupplierRowMapper implements RowMapper<Supplier> {
    @Override
    public Supplier mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentSupplier supplier = new PersistentSupplier();
      supplier.setId(resultSet.getLong("ID"));
      supplier.setName(resultSet.getString("NAME"));
      supplier.setEmail(resultSet.getString("EMAIL"));
      supplier.setContactPerson(resultSet.getString("CONTACT_PERSON"));
      supplier.setContactNumber(resultSet.getString("CONTACT_NUMBER"));
      supplier.setAddress(resultSet.getString("ADDRESS"));
      supplier.setNote(resultSet.getString("NOTE"));
      supplier.setLastModified(resultSet.getString("LAST_MODIFIED"));

      AtomicReference<Supplier> atomicReference = new AtomicReference<>(supplier);
      return atomicReference.get();
    }
  }

}
