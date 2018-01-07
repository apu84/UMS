package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.ums.decorator.accounts.AccountDaoDecorator;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.domain.model.mutable.accounts.MutableAccount;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentAccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
public class PersistentAccountDao extends AccountDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAccountDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Account> getAll() {
    String query = "select * from mst_account";
    return mJdbcTemplate.query(query, new PersistentAccountRowMapper());
  }

  @Override
  public List<Account> getAccounts(String pAccountName) {
    String query = "select * from MST_ACCOUNT where ACCOUNT_NAME like :accountName";
    SqlParameterSource namedParameters = new MapSqlParameterSource("accountName", "%" + pAccountName + "%");
    return this.mNamedParameterJdbcTemplate.query(query, namedParameters, new PersistentAccountRowMapper());
    // return mJdbcTemplate.query(query, new Object[]{pAccountName}, new
    // PersistentAccountRowMapper());
  }

  @Override
  public List<Account> getAllPaginated(int itemPerPage, int pageNumber) {
    int startIndex = (itemPerPage * (pageNumber - 1)) + 1;
    int endIndex = startIndex + itemPerPage - 1;
    String query =
        "select * from (select ROWNUM row_num,mst_account.* from mst_account)tmp where row_num>=? and row_num<=? ";
    return mJdbcTemplate.query(query, new Object[] {startIndex, endIndex}, new PersistentAccountRowMapper());
  }

  @Override
  public Integer getSize() {
    String query = "select count(*) from mst_account";
    return mJdbcTemplate.queryForObject(query, Integer.class);
  }

  @Override
  public Account get(Long pId) {
    String query = "select * from mst_account where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentAccountRowMapper());
  }

  @Override
  public Account validate(Account pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutableAccount pMutable) {
    return super.update(pMutable);
  }

  @Override
  public int update(List<MutableAccount> pMutableList) {
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableAccount pMutable) {
    return super.delete(pMutable);
  }

  @Override
  public int delete(List<MutableAccount> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public Long create(MutableAccount pMutable) {
    String query =
        "insert into MST_ACCOUNT(ID, ACCOUNT_CODE, ACCOUNT_NAME, ACC_GROUP_CODE,  MODIFIED_DATE, MODIFIED_BY) "
            + "            VALUES (?,?,?,?,?,?)";
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(query, id, pMutable.getAccountCode(), pMutable.getAccountName(), pMutable.getAccGroupCode(),
        pMutable.getModifiedDate(), pMutable.getModifiedBy());
    return id;
  }

  @Override
  public List<Long> create(List<MutableAccount> pMutableList) {
    return super.create(pMutableList);
  }

  @Override
  public boolean exists(Long pId) {
    return super.exists(pId);
  }

  class PersistentAccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableAccount account = new PersistentAccount();
      account.setId(rs.getLong("id"));
      try {
        account.setRowNumber(rs.getInt("row_num"));
      } catch(Exception e) {
        // e.printStackTrace();
      }
      account.setAccountCode(rs.getLong("account_code"));
      account.setAccountName(rs.getString("account_name"));
      account.setAccGroupCode(rs.getString("acc_group_code"));
      account.setReserved(rs.getBoolean("reserved"));
      account.setTaxLimit(rs.getBigDecimal("tax_limit"));
      account.setTaxCode(rs.getString("tax_code"));
      account.setStatFlag(rs.getString("stat_flag"));
      account.setStatUpFlag(rs.getString("stat_up_flag"));
      account.setModifiedDate(rs.getDate("modified_date"));
      account.setModifiedBy("modified_by");
      return account;
    }
  }
}
