package org.ums.fee.latefee;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

public class UGLateFeeDao extends UGLateFeeDaoDecorator {
  String SELECT_ALL = "SELECT ID, FROM_DATE, TO_DATE, FEE, SEMESTER_ID, LAST_MODIFIED FROM LATE_FEE";
  String INSERT_ALL = "INSERT INTO LATE_FEE(ID, FROM_DATE, TO_DATE, FEE, SEMESTER_ID, LAST_MODIFIED) "
      + "VALUES(?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";
  String UPDATE_ALL = "UPDATE LATE_FEE SET FROM_DATE = ?, TO_DATE = ?, FEE = ?, SEMESTER_ID = ?, " + "LAST_MODIFIED = "
      + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM LATE_FEE ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public UGLateFeeDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  class LateFeeRowMapper implements RowMapper<UGLateFee> {
    @Override
    public UGLateFee mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableUGLateFee lateFee = new PersistentUGLateFee();
      lateFee.setId(rs.getLong("ID"));
      lateFee.setFrom(rs.getTimestamp("FROM_DATE"));
      lateFee.setTo(rs.getTimestamp("TO_DATE"));
      lateFee.setFee(rs.getInt("FEE"));

      lateFee.setLastModified(rs.getString("LAST_MODIFIED"));

      return null;
    }
  }
}
