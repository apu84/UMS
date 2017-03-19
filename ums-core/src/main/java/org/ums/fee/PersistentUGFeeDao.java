package org.ums.fee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

public class PersistentUGFeeDao extends UGFeeDaoDecorator {
  String SELECT_ALL = "SELECT ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT, LAST_MODIFIED FROM FEE ";
  String UPDATE_ALL = "UPDATE FEE SET FEE_CATEGORY_ID = ?, SEMESTER_ID = ?, FACULTY_ID = ?, AMOUNT = ?, "
      + "LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM FEE ";
  String INSERT_ALL = "INSERT INTO FEE(ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, AMOUNT, LAST_MODIFIED) "
      + "VALUES (?, ?, ?, ?, ?, " + getLastModifiedSql() + ") ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentUGFeeDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<UGFee> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new FeeRowMapper());
  }

  @Override
  public UGFee get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new FeeRowMapper());
  }

  @Override
  public int update(MutableUGFee pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getFeeCategoryId(), pMutable.getSemesterId(), pMutable.getFacultyId(),
        pMutable.getAmount(), pMutable.getId());
  }

  @Override
  public int delete(MutableUGFee pMutable) {
    String query = DELETE_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableUGFee pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getFeeCategoryId(), pMutable.getSemesterId(),
        pMutable.getFacultyId(), pMutable.getAmount());
    return id;
  }

  private class FeeRowMapper implements RowMapper<UGFee> {
    @Override
    public UGFee mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableUGFee fee = new PersistentUGFee();
      fee.setId(rs.getLong("ID"));
      fee.setFeeCategoryId(rs.getString("FEE_CATEGORY_ID"));
      fee.setSemesterId(rs.getInt("SEMESTER_ID"));
      fee.setFacultyId(rs.getInt("FACULTY_ID"));
      fee.setAmount(rs.getDouble("AMOUNT"));
      fee.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<UGFee> atomicReference = new AtomicReference<>(fee);
      return atomicReference.get();
    }
  }
}
