package org.ums.bank;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class BranchUserDao extends BranchUserDaoDecorator {
  String SELECT_ALL = "SELECT ID, NAME, BRANCH_ID, DESIGNATION_ID, LAST_MODIFIED FROM BRANCH_USER ";
  String UPDATE_ALL = "UPDATE BRANCH_USER SET ID = ?, NAME = ?, BRANCH_ID = ?, DESIGNATION_ID = ?, LAST_MODIFIED = "
      + getLastModifiedSql() + " ";
  String CREATE_ALL = "INSERT INTO BRANCH_USER(ID, NAME, BRANCH_ID, DESIGNATION_ID, LAST_MODIFIED) VALUES "
      + "(?, ?, ?, ?, " + getLastModifiedSql() + ") ";
  String DELETE_ALL = "DELETE FROM BRANCH_USER ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  class BranchUserRowMapper implements RowMapper<BranchUser> {
    @Override
    public BranchUser mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableBranchUser branchUser = new PersistentBranchUser();
      branchUser.setId(rs.getString("ID"));
      branchUser.setName(rs.getString("NAME"));
      branchUser.setBankDesignationId(rs.getLong("DESIGNATION_ID"));
      branchUser.setBranchId(rs.getString("BRANCH_ID"));
      branchUser.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<BranchUser> reference = new AtomicReference<>(branchUser);
      return reference.get();
    }
  }
}
