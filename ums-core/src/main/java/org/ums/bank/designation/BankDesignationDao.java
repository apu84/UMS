package org.ums.bank.designation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BankDesignationDao extends BankDesignationDaoDecorator {
  String SELECT_ALL = "SELECT ID, CODE, NAME, LAST_MODIFIED FROM BANK_DESIGNATION ";
  String UPDATE_ALL = "UPDATE BANK_DESIGNATION SET CODE = ?, NAME = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String CREATE_ALL = "INSERT INTO BANK_DESIGNATION(ID, CODE, NAME, LAST_MODIFIED) VALUES (?, ?, ?,"
      + getLastModifiedSql() + ") ";
  String DELETE_ALL = "DELETE FROM BANK_DESIGNATION ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public BankDesignationDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<BankDesignation> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new BankDesignationRowMapper());
  }

  @Override
  public BankDesignation get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new BankDesignationRowMapper());
  }

  @Override
  public int update(MutableBankDesignation pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getCode(), pMutable.getName(), pMutable.getId());
  }

  @Override
  public int delete(MutableBankDesignation pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableBankDesignation pMutable) {
    Long id = mIdGenerator.getNumericId();
    String code = StringUtils.isEmpty(pMutable.getCode()) ? mIdGenerator.getAlphaNumericId("D", 2) : pMutable.getCode();
    mJdbcTemplate.update(CREATE_ALL, id, code, pMutable.getName());
    return id;
  }

  @Override
  public BankDesignation getByCode(String pCode) {
    String query = SELECT_ALL + "WHERE CODE = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pCode}, new BankDesignationRowMapper());
  }

  class BankDesignationRowMapper implements RowMapper<BankDesignation> {
    @Override
    public BankDesignation mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableBankDesignation bankDesignation = new PersistentBankDesignation();
      bankDesignation.setId(rs.getLong("ID"));
      bankDesignation.setCode(rs.getString("CODE"));
      bankDesignation.setName(rs.getString("NAME"));
      bankDesignation.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<BankDesignation> reference = new AtomicReference<>(bankDesignation);
      return reference.get();
    }
  }
}
