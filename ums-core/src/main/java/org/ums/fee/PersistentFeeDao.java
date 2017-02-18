package org.ums.fee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class PersistentFeeDao extends FeeDaoDecorator {
  String SELECT_ALL =
      "SELECT ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, PROGRAM_TYPE_ID, PROGRAM_CATEGORY, "
          + "AMOUNT, LAST_MODIFIED FROM FEE ";
  String UPDATE_ALL =
      "UPDATE FEE SET FEE_CATEGORY_ID = ?, SEMESTER_ID = ?, FACULTY_ID = ?, PROGRAM_TYPE_ID = ?, "
          + "PROGRAM_CATEGORY = ?, AMOUNT = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM FEE ";
  String INSERT_ALL =
      "INSERT INTO FEE(ID, FEE_CATEGORY_ID, SEMESTER_ID, FACULTY_ID, PROGRAM_TYPE_ID, PROGRAM_CATEGORY, "
          + "AMOUNT, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ") ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentFeeDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<Fee> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new FeeRowMapper());
  }

  @Override
  public Fee get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new FeeRowMapper());
  }

  @Override
  public int update(MutableFee pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getFeeCategoryId(), pMutable.getSemesterId(),
        pMutable.getFacultyId(), pMutable.getProgramTypeId(), pMutable.getProgramCategory()
            .getValue(), pMutable.getAmount(), pMutable.getId());
  }

  @Override
  public int delete(MutableFee pMutable) {
    String query = DELETE_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableFee pMutable) {
    return mJdbcTemplate.update(INSERT_ALL, pMutable.getFeeCategoryId(), pMutable.getSemesterId(),
        pMutable.getFacultyId(), pMutable.getProgramTypeId(), pMutable.getProgramCategory()
            .getValue(), pMutable.getAmount());
  }

  private class FeeRowMapper implements RowMapper<Fee> {
    @Override
    public Fee mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableFee fee = new PersistentFee();
      fee.setId(rs.getLong("ID"));
      fee.setFeeCategoryId(rs.getString("FEE_CATEGORY_ID"));
      fee.setSemesterId(rs.getInt("SEMESTER_ID"));
      fee.setFacultyId(rs.getInt("FACULTY_ID"));
      fee.setProgramTypeId(rs.getInt("PROGRAM_TYPE_ID"));
      fee.setProgramCategory(Fee.ProgramCategory.get(rs.getInt("PROGRAM_CATEGORY")));
      fee.setAmount(rs.getDouble("AMOUNT"));
      fee.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<Fee> atomicReference = new AtomicReference<>(fee);
      return atomicReference.get();
    }
  }
}
