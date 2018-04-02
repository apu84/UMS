package org.ums.bank;

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
  String SELECT_ALL = "SELECT ID, BANK_ID, NAME, CONTACT_NO, LOCATION, LAST_MODIFIED FROM BRANCH ";
  String CREATE_ALL = "INSERT INTO BRANCH(ID, BANK_ID, NAME, CONTACT_NO, LOCATION, LAST_MODIFIED) VALUES"
      + "(?, ?, ?, ?, ?, " + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE BRANCH SET BANK_ID = ?, NAME = ?, CONTACT_NO = ?, LOCATION = ?, " + "LAST_MODIFIED ="
      + getLastModifiedSql() + " ";
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
  public Branch get(String pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new BranchRowMapper());
  }

  @Override
  public int update(MutableBranch pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getBankId(), pMutable.getName(), pMutable.getContactNo(),
        pMutable.getLocation(), pMutable.getId());
  }

  @Override
  public int delete(MutableBranch pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public String create(MutableBranch pMutable) {
    return create(Lists.newArrayList(pMutable)).get(0);
  }

  @Override
  public List<String> create(List<MutableBranch> pMutableList) {
    List<Object[]> params = getInsertParams(pMutableList);
    mJdbcTemplate.batchUpdate(CREATE_ALL, params);
    return params.stream().map(parameters -> (String) parameters[0]).collect(Collectors.toList());
  }

  private List<Object[]> getInsertParams(List<MutableBranch> pMutableBranches) {
    return pMutableBranches.stream().map(pMutableBranch -> new Object[]{
        StringUtils.isEmpty(pMutableBranch.getId()) ? mIdGenerator.getAlphaNumericId("BR", 4) : pMutableBranch.getId(),
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
      mutableBranch.setId(rs.getString("ID"));
      mutableBranch.setBankId(rs.getString("BANK_ID"));
      mutableBranch.setName(rs.getString("NAME"));
      mutableBranch.setContactNo(rs.getString("CONTACT_NO"));
      mutableBranch.setLocation(rs.getString("LOCATION"));
      mutableBranch.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<Branch> reference = new AtomicReference<>(mutableBranch);
      return reference.get();
    }
  }
}
