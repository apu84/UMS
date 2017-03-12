package org.ums.fee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class PersistentFeeCategoryDao extends FeeCategoryDaoDecorator {
  String SELECT_ALL = "SELECT ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED FROM FEE_CATEGORY ";
  String UPDATE_ALL =
      "UPDATE FEE_CATEGORY SET NAME = ?, DESCRIPTION = ?, TYPE = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM FEE_CATEGORY ";
  String INSERT_ALL =
      "INSERT INTO FEE_CATEGORY(ID, NAME, DESCRIPTION, TYPE, LAST_MODIFIED) VALUES (?, ?, ?, ?, "
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
  public List<FeeCategory> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new FeeCategoryRowMapper());
  }

  @Override
  public int update(MutableFeeCategory pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getName(),
        pMutable.getDescription(), pMutable.getType().getValue());
  }

  @Override
  public int delete(MutableFeeCategory pMutable) {
    String query = DELETE_ALL + "WHERE ID = ? ";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public String create(MutableFeeCategory pMutable) {
    mJdbcTemplate.update(INSERT_ALL, pMutable.getName(), pMutable.getDescription(), pMutable
        .getType().getValue(), pMutable.getId());
    return pMutable.getId();
  }

  class FeeCategoryRowMapper implements RowMapper<FeeCategory> {
    @Override
    public FeeCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableFeeCategory feeCategory = new PersistentFeeCategory();
      feeCategory.setId(rs.getString("ID"));
      feeCategory.setName(rs.getString("NAME"));
      feeCategory.setType(FeeCategory.Type.get(rs.getInt("TYPE")));
      feeCategory.setDescription(rs.getString("Description"));
      feeCategory.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<FeeCategory> atomicReference = new AtomicReference<>(feeCategory);
      return atomicReference.get();
    }
  }
}
