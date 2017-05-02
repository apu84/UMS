package org.ums.fee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class FeeTypeDao extends FeeTypeDaoDecorator {
  String SELECT_ALL = "SELECT ID, DESCRIPTION, LAST_MODIFIED FROM FEE_TYPE ";
  String INSERT_ALL = "INSERT INTO FEE_TYPE (ID, DESCRIPTION, LAST_MODIFIED) VALUES ( ?, ?, " + getLastModifiedSql()
      + ")";
  String UPDATE_ALL = "UPDATE FEE_TYPE SET DESCRIPTION = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM FEE_TYPE ";

  private JdbcTemplate mJdbcTemplate;

  public FeeTypeDao(JdbcTemplate mJdbcTemplate) {
    this.mJdbcTemplate = mJdbcTemplate;
  }

  @Override
  public List<FeeType> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new FeeTypeRowMapper());
  }

  @Override
  public FeeType get(Integer pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new FeeTypeRowMapper());
  }

  @Override
  public int update(MutableFeeType pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int delete(MutableFeeType pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Integer create(MutableFeeType pMutable) {
    mJdbcTemplate.update(INSERT_ALL, pMutable.getId(), pMutable.getDescription());
    return pMutable.getId();
  }

  class FeeTypeRowMapper implements RowMapper<FeeType> {
    @Override
    public FeeType mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableFeeType feeType = new PersistentFeeType();
      feeType.setId(rs.getInt("ID"));
      feeType.setDescription(rs.getString("DESCRIPTION"));
      feeType.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<FeeType> reference = new AtomicReference<>(feeType);
      return reference.get();
    }
  }
}
