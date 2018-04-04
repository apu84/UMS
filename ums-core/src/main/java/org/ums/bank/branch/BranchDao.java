package org.ums.bank.branch;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class BranchDao extends BranchDaoDecorator {
  String SELECT_ALL = "SELECT ID, CODE, BANK_ID, NAME, CONTACT_NO, LOCATION, LAST_MODIFIED FROM BRANCH ";
  String CREATE_ALL = "INSERT INTO BRANCH(ID, CODE, BANK_ID, NAME, CONTACT_NO, LOCATION, LAST_MODIFIED) VALUES"
      + "(?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE BRANCH SET CODE = ?, BANK_ID = ?, NAME = ?, CONTACT_NO = ?, LOCATION = ?, "
      + "LAST_MODIFIED =" + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM BRANCH ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public BranchDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Branch> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new BranchRowMapper());
  }

  @Override
  public Branch get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new BranchRowMapper());
  }

  @Override
  public int update(MutableBranch pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getCode(), pMutable.getBankId(), pMutable.getName(),
        pMutable.getContactNo(), pMutable.getLocation(), pMutable.getId());
  }

  @Override
  public int delete(MutableBranch pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableBranch pMutable) {
    return create(Lists.newArrayList(pMutable)).get(0);
  }

  @Override
  public List<Long> create(List<MutableBranch> pMutableList) {
    List<Object[]> params = getInsertParams(pMutableList);
    mJdbcTemplate.batchUpdate(CREATE_ALL, params);
    return params.stream().map(parameters -> (Long) parameters[0]).collect(Collectors.toList());
  }

  @Override
  public Branch getByCode(String pCode) {
    String query = SELECT_ALL + "WHERE CODE = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pCode}, new BranchRowMapper());
  }

  @Override
  public List<Branch> getBranchesByBank(Long pBankId) {
    String query = SELECT_ALL + "WHERE BANK_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pBankId}, new BranchRowMapper());
  }

  private List<Object[]> getInsertParams(List<MutableBranch> pMutableBranches) {
    return pMutableBranches.stream().map(pMutableBranch -> new Object[]{
        mIdGenerator.getNumericId(),
        StringUtils.isEmpty(pMutableBranch.getCode()) ? mIdGenerator.getAlphaNumericId("BR", 4) : pMutableBranch.getCode(),
        pMutableBranch.getBankId(),
        pMutableBranch.getName(),
        pMutableBranch.getContactNo(),
        pMutableBranch.getLocation()
    }).collect(Collectors.toList());
  }

  class BranchRowMapper implements RowMapper<Branch> {
    @Override
    public Branch mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableBranch mutableBranch = new PersistentBranch();
      mutableBranch.setId(rs.getLong("ID"));
      mutableBranch.setCode(rs.getString("CODE"));
      mutableBranch.setBankId(rs.getLong("BANK_ID"));
      mutableBranch.setName(rs.getString("NAME"));
      mutableBranch.setContactNo(rs.getString("CONTACT_NO"));
      mutableBranch.setLocation(rs.getString("LOCATION"));
      mutableBranch.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<Branch> reference = new AtomicReference<>(mutableBranch);
      return reference.get();
    }
  }
}
