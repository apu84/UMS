package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.ItemDaoDecorator;
import org.ums.domain.model.dto.GradeChartDataDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.immutable.library.Item;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.enums.library.ItemStatus;
import org.ums.generator.IdGenerator;
import org.ums.persistent.dao.PersistentExamGradeDao;
import org.ums.persistent.model.PersistentTeacher;
import org.ums.persistent.model.library.PersistentItem;
import org.ums.persistent.model.library.PersistentSupplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 04-Mar-17.
 */
public class PersistentItemDao extends ItemDaoDecorator {
  static String SELECT_ALL =
      "Select ID, MFN, COPY_NUMBER,  ACCESSION_NUMBER, TO_CHAR(ACCESSION_DATE,'DD-MM-YYYY') ACCESSION_DATE, BARCODE,    PRICE, SUPPLIER, INTERNAL_NOTE,  STATUS, INSERTED_BY, INSERTED_ON, "
          + "   LAST_UPDATED_BY, LAST_UPDATED_ON, LAST_MODIFIED FROM ITEMS ";

  static String UPDATE_ONE =
      "UPDATE ITEMS SET COPY_NUMBER = ?, ACCESSION_NUMBER=?,  ACCESSION_DATE=to_date(?, 'DD-MM-YYYY'), BARCODE=?, PRICE=?, SUPPLIER=?, INTERNAL_NOTE=?, STATUS=?, LAST_UPDATED_BY=?, "
          + "   LAST_UPDATED_ON=?, LAST_MODIFIED=" + getLastModifiedSql();

  static String INSERT_ONE =
      "INSERT INTO ITEMS(ID, MFN, COPY_NUMBER, ACCESSION_NUMBER, ACCESSION_DATE, BARCODE, PRICE, SUPPLIER, INTERNAL_NOTE, "
          + "   STATUS, INSERTED_BY, INSERTED_ON, LAST_UPDATED_BY, LAST_UPDATED_ON, LAST_MODIFIED)  " // 15
                                                                                                      // Fields
          + "  VALUES(?, ?, ?, ?, TO_DATE(?,'DD-MM-YYYY'), ?, ?, ?, ?, ?, ?, sysdate, ?, sysdate, "
          + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;
  public IdGenerator mIdGenerator;

  public PersistentItemDao(final JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Item get(final Long pId) {
    String query = SELECT_ALL + " Where ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentItemDao.ItemRowMapper());
  }

  public Item getByAccessionNumber(final Long pAccessionNumber) {
    String query = SELECT_ALL + " Where ACCESSION_NUMBER = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pAccessionNumber}, new PersistentItemDao.ItemRowMapper());
  }

  public List<Item> getByMfn(final Long pMfn) {
    String query = SELECT_ALL + " Where MFN = ? Order by Copy_Number";
    return mJdbcTemplate.query(query, new Object[] {pMfn}, new PersistentItemDao.ItemRowMapper());
  }

  @Override
  public int update(final MutableItem pItem) {
    String query = UPDATE_ONE + "   Where ID= ? ";
    return mJdbcTemplate.update(query, pItem.getCopyNumber(), pItem.getAccessionNumber(), pItem.getAccessionDate(),
        pItem.getBarcode(), pItem.getPrice(), 4, pItem.getInternalNote(), pItem.getStatus().getId(), "", "",
        pItem.getId());
  }

  @Override
  public Long create(final MutableItem pItem) {
    Long id = mIdGenerator.getNumericId();
    pItem.setId(id);
    mJdbcTemplate.update(INSERT_ONE, pItem.getId(), pItem.getMfn(), pItem.getCopyNumber(), pItem.getAccessionNumber(),
        pItem.getAccessionDate(), pItem.getBarcode(), pItem.getPrice(), pItem.getSupplier() == null ? null : pItem
            .getSupplier().getId(), pItem.getInternalNote(), pItem.getStatus().getId(), "inset", "update");

    return id;
  }

  @Override
  public List<Long> create(final List<MutableItem> items) {

    mJdbcTemplate.batchUpdate(INSERT_ONE, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Item item = items.get(i);
        Long id = mIdGenerator.getNumericId();
        ps.setLong(1, id);
        ps.setLong(2, item.getMfn());
        ps.setInt(3, item.getCopyNumber());
        ps.setString(4, item.getAccessionNumber());
        ps.setString(5, item.getAccessionDate());
        ps.setString(6, id.toString());
        ps.setDouble(7, item.getPrice());
        ps.setInt(8, 2);
        ps.setString(9, item.getInternalNote());
        ps.setInt(10, item.getStatus().getId());
        ps.setString(11, "insert");
        ps.setString(12, "update");

      }

      @Override
      public int getBatchSize() {
        return items.size();
      }

    });

    return null;
  }

  @Override
  public List<Item> getAll() {
    String query = SELECT_ALL +" Order by Copy_Number ";
    return mJdbcTemplate.query(query, new PersistentItemDao.ItemRowMapper());
  }

  class ItemRowMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {

      MutableItem item = new PersistentItem();
      item.setId(resultSet.getLong("ID"));
      item.setMfn(resultSet.getLong("MFN"));
      item.setCopyNumber(resultSet.getInt("COPY_NUMBER"));
      item.setAccessionNumber(resultSet.getString("ACCESSION_NUMBER"));
      item.setAccessionDate(resultSet.getString("ACCESSION_DATE"));
      item.setBarcode(resultSet.getString("BARCODE"));
      item.setPrice(resultSet.getDouble("PRICE"));

      item.setSupplierId(resultSet.getLong("SUPPLIER"));
      item.setInternalNote(resultSet.getString("INTERNAL_NOTE"));
      item.setStatus(ItemStatus.get(resultSet.getInt("STATUS")));
      item.setInsertedBy(resultSet.getString("INSERTED_BY"));
      item.setInsertedOn(resultSet.getString("INSERTED_ON"));
      item.setLastUpdatedBy(resultSet.getString("LAST_UPDATED_BY"));
      item.setLastUpdatedOn(resultSet.getString("LAST_UPDATED_ON"));
      item.setLastModified(resultSet.getString("LAST_MODIFIED"));

      AtomicReference<Item> atomicReference = new AtomicReference<>(item);
      return atomicReference.get();
    }
  }

}
