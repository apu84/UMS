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
  String SELECT_ALL = "SELECT ID, NAME, LAST_MODIFIED FROM BANK ";
  String CREATE_ALL = "INSERT INTO BANK(ID, NAME, LAST_MODIFIED) VALUES (?, ?, " + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE BANK SET NAME = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
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
  public Bank get(String pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new BankRowMapper());
  }

  @Override
  public int update(MutableBank pMutable) {
    String query = UPDATE_ALL + "WEHRE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getName(), pMutable.getId());
  }

  @Override
  public int delete(MutableBank pMutable) {
    String query = DELETE_ALL + "WEHRE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public String create(MutableBank pMutable) {
    String id = StringUtils.isEmpty(pMutable.getId()) ? mIdGenerator.getAlphaNumericId("BN", 3) : pMutable.getId();
    mJdbcTemplate.update(CREATE_ALL, pMutable.getName(), id);
    return id;
  }

  class BankRowMapper implements RowMapper<Bank> {
    @Override
    public Bank mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableBank mutableBank = new PersistentBank();
      mutableBank.setId(rs.getString("ID"));
      mutableBank.setName(rs.getString("NAME"));
      mutableBank.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<Bank> atomicReference = new AtomicReference<>(mutableBank);
      return atomicReference.get();
    }
  }
}
