package org.ums.fee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentFeeCategoryDao extends FeeCategoryDaoDecorator {
  String SELECT_ALL = "SELECT ID, FEE_ID, NAME, DESCRIPTION,DEPENDENCIES, TYPE, LAST_MODIFIED FROM FEE_CATEGORY ";
  String UPDATE_ALL =
      "UPDATE FEE_CATEGORY SET NAME = ?, FEE_ID = ?, DESCRIPTION = ?, DEPENDENCIES=?, TYPE = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM FEE_CATEGORY ";
  String INSERT_ALL =
      "INSERT INTO FEE_CATEGORY(FEE_ID, NAME, DESCRIPTION, DEPENDENCIES, TYPE, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, "
          + getLastModifiedSql() + ") ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentFeeCategoryDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public FeeCategory get(String pId) {
    String query = SELECT_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new FeeCategoryRowMapper());
  }

  @Override
  public FeeCategory getByFeeId(String pFeeId) {
    String query = SELECT_ALL + "WHERE FEE_ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pFeeId}, new FeeCategoryRowMapper());
  }

  @Override
  public List<FeeCategory> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new FeeCategoryRowMapper());
  }

  @Override
  public int update(MutableFeeCategory pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getFeeId(), pMutable.getName(),
        pMutable.getDescription(), pMutable.getType().getId());
  }

  @Override
  public int delete(MutableFeeCategory pMutable) {
    String query = DELETE_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public String create(MutableFeeCategory pMutable) {
    mJdbcTemplate.update(INSERT_ALL, pMutable.getFeeId(), pMutable.getName(), pMutable.getDescription(), pMutable
        .getType().getId(), pMutable.getId());
    return pMutable.getId();
  }

  @Override
  public List<FeeCategory> getFeeCategories(Integer pFeeTypeId) {
    String query = SELECT_ALL + "WHERE TYPE = ? ";
    return mJdbcTemplate.query(query, new Object[] {pFeeTypeId}, new FeeCategoryRowMapper());
  }

  class FeeCategoryRowMapper implements RowMapper<FeeCategory> {
    @Override
    public FeeCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableFeeCategory feeCategory = new PersistentFeeCategory();
      feeCategory.setId(rs.getString("ID"));
      feeCategory.setFeeId(rs.getString("FEE_ID"));
      feeCategory.setName(rs.getString("NAME"));
      feeCategory.setFeeTypeId(rs.getInt("TYPE"));
      feeCategory.setDescription(rs.getString("DESCRIPTION"));
      feeCategory.setDependencies(rs.getString("DEPENDENCIES"));
      feeCategory.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<FeeCategory> atomicReference = new AtomicReference<>(feeCategory);
      return atomicReference.get();
    }
  }
}
