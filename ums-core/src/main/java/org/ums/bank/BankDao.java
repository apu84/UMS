package org.ums.bank;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BankDao extends BankDaoDecorator {
  String SELECT_ALL = "SELECT ID, CODE, NAME, LAST_MODIFIED FROM BANK ";
  String CREATE_ALL = "INSERT INTO BANK(ID, CODE, NAME, LAST_MODIFIED) VALUES (?, ?, ?, " + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE BANK SET NAME = ?, CODE = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM BANK ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public BankDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Bank> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new BankRowMapper());
  }

  @Override
  public Bank get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new BankRowMapper());
  }

  @Override
  public int update(MutableBank pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getName(), pMutable.getCode(), pMutable.getId());
  }

  @Override
  public int delete(MutableBank pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableBank pMutable) {
    String code =
        StringUtils.isEmpty(pMutable.getCode()) ? mIdGenerator.getAlphaNumericId("BN", 3) : pMutable.getCode();
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(CREATE_ALL, id, code, pMutable.getName());
    return id;
  }

  @Override
  public Bank getByCode(String pCode) {
    String query = SELECT_ALL + "WHERE CODE = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pCode}, new BankRowMapper());
  }

  class BankRowMapper implements RowMapper<Bank> {
    @Override
    public Bank mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableBank mutableBank = new PersistentBank();
      mutableBank.setId(rs.getLong("ID"));
      mutableBank.setCode(rs.getString("CODE"));
      mutableBank.setName(rs.getString("NAME"));
      mutableBank.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<Bank> atomicReference = new AtomicReference<>(mutableBank);
      return atomicReference.get();
    }
  }
}
