package org.ums.bank.branch.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.ums.generator.IdGenerator;

import com.google.common.collect.Lists;

public class BranchUserDao extends BranchUserDaoDecorator {
  String SELECT_ALL = "SELECT ID, USER_ID, NAME, BRANCH_ID, DESIGNATION_ID, EMAIL, LAST_MODIFIED FROM BRANCH_USER ";
  String UPDATE_ALL =
      "UPDATE BRANCH_USER SET USER_ID = ?, NAME = ?, BRANCH_ID = ?, DESIGNATION_ID = ?, EMAIL = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  String CREATE_ALL =
      "INSERT INTO BRANCH_USER(ID, USER_ID, NAME, BRANCH_ID, DESIGNATION_ID, EMAIL, LAST_MODIFIED) VALUES "
          + "(?, ?, ?, ?, ?, ?," + getLastModifiedSql() + ") ";
  String DELETE_ALL = "DELETE FROM BRANCH_USER ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public BranchUserDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<BranchUser> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new BranchUserRowMapper());
  }

  @Override
  public BranchUser get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new BranchUserRowMapper());
  }

  @Override
  public int update(MutableBranchUser pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getUserId(), pMutable.getName(), pMutable.getBranchId(),
        pMutable.getBankDesignationId(), pMutable.getEmail(), pMutable.getId());
  }

  @Override
  public int delete(MutableBranchUser pMutable) {
    String query = DELETE_ALL + "WHERE Id = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableBranchUser pMutable) {
    pMutable.setId(create(Lists.newArrayList(pMutable)).get(0));
    return super.create(pMutable);
  }

  @Override
  @Transactional
  public List<Long> create(List<MutableBranchUser> pMutableList) {
    List<Object[]> insertParams = getInsertParams(pMutableList);
    List<Long> userIds = insertParams.stream().map(params -> (Long) params[0]).collect(Collectors.toList());
    mJdbcTemplate.batchUpdate(CREATE_ALL, insertParams);
    for(int i = 0; i < pMutableList.size(); i++) {
      pMutableList.get(i).setUserId(insertParams.get(i)[1].toString());
    }
    return userIds;
  }

  @Override
  public BranchUser getByUserId(String pUserId) {
    String query = SELECT_ALL + "WHERE USER_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pUserId}, new BranchUserRowMapper());
  }

  @Override
  public List<BranchUser> getUsersByBranch(Long pBranchId) {
    String query = SELECT_ALL + "WHERE BRANCH_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pBranchId}, new BranchUserRowMapper());
  }

  @Override
  public BranchUser getUserByEmail(String pEmail) {
    String query = SELECT_ALL + "WHERE EMAIL = ?";
    return mJdbcTemplate.query(query, new Object[] {pEmail}, new BranchUserRowMapper()).get(0);
  }

  private List<Object[]> getInsertParams(List<MutableBranchUser> pUsers) {
    return pUsers.stream().map(pUser -> new Object[]{
        mIdGenerator.getNumericId(),
        StringUtils.isEmpty(pUser.getUserId()) ? mIdGenerator.getAlphaNumericId("BU", 4) : pUser.getUserId(),
        pUser.getName(),
        pUser.getBranchId(),
        pUser.getBankDesignationId(),
        pUser.getEmail()
    }).collect(Collectors.toList());
  }

  class BranchUserRowMapper implements RowMapper<BranchUser> {
    @Override
    public BranchUser mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableBranchUser branchUser = new PersistentBranchUser();
      branchUser.setId(rs.getLong("ID"));
      branchUser.setUserId(rs.getString("USER_ID"));
      branchUser.setName(rs.getString("NAME"));
      branchUser.setBankDesignationId(rs.getLong("DESIGNATION_ID"));
      branchUser.setBranchId(rs.getLong("BRANCH_ID"));
      branchUser.setEmail(rs.getString("EMAIL"));
      branchUser.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<BranchUser> reference = new AtomicReference<>(branchUser);
      return reference.get();
    }
  }
}
