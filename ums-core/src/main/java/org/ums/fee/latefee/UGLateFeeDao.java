package org.ums.fee.latefee;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

public class UGLateFeeDao extends UGLateFeeDaoDecorator {
  String SELECT_ALL = "SELECT ID, FROM_DATE, TO_DATE, FEE, SEMESTER_ID, ADMISSION_TYPE, LAST_MODIFIED FROM LATE_FEE";
  String INSERT_ALL = "INSERT INTO LATE_FEE(ID, FROM_DATE, TO_DATE, FEE, SEMESTER_ID, ADMISSION_TYPE, LAST_MODIFIED) "
      + "VALUES(?, ?, ?, ?, ?, ?," + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE LATE_FEE SET FROM_DATE = ?, TO_DATE = ?, FEE = ?, SEMESTER_ID = ?, ADMISSION_TYPE = ?,"
      + "LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM LATE_FEE ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public UGLateFeeDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<UGLateFee> getLateFees(Integer pSemesterId) {
    String query = SELECT_ALL + "WHERE SEMESTER_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new LateFeeRowMapper());
  }

  @Override
  public UGLateFee get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new LateFeeRowMapper());
  }

  @Override
  public int update(MutableUGLateFee pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getFrom(), pMutable.getTo(), pMutable.getFee(), pMutable.getSemester()
        .getId(), pMutable.getAdmissionType().getId(), pMutable.getId());
  }

  @Override
  public int delete(MutableUGLateFee pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableUGLateFee pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getFrom(), pMutable.getTo(), pMutable.getFee(), pMutable
        .getSemester().getId(), pMutable.getAdmissionType().getId());
    return id;
  }

  class LateFeeRowMapper implements RowMapper<UGLateFee> {
    @Override
    public UGLateFee mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableUGLateFee lateFee = new PersistentUGLateFee();
      lateFee.setId(rs.getLong("ID"));
      lateFee.setFrom(rs.getTimestamp("FROM_DATE"));
      lateFee.setTo(rs.getTimestamp("TO_DATE"));
      lateFee.setFee(new BigDecimal(rs.getInt("FEE")));
      lateFee.setAdmissionType(UGLateFee.AdmissionType.get(rs.getInt("ADMISSION_TYPE")));
      lateFee.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<UGLateFee> atomicReference = new AtomicReference<>(lateFee);
      return atomicReference.get();
    }
  }
}
