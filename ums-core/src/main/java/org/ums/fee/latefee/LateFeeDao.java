package org.ums.fee.latefee;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

public class LateFeeDao extends LateFeeDaoDecorator {
  String SELECT_ALL = "SELECT ID, FROM_DATE, TO_DATE, FEE, SEMESTER_ID, ADMISSION_TYPE, LAST_MODIFIED FROM LATE_FEE ";
  String INSERT_ALL = "INSERT INTO LATE_FEE(ID, FROM_DATE, TO_DATE, FEE, SEMESTER_ID, ADMISSION_TYPE, LAST_MODIFIED) "
      + "VALUES(?, ?, ?, ?, ?, ?," + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE LATE_FEE SET FROM_DATE = ?, TO_DATE = ?, FEE = ?, SEMESTER_ID = ?, ADMISSION_TYPE = ?,"
      + "LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM LATE_FEE ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public LateFeeDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<LateFee> getLateFees(Integer pSemesterId) {
    String query = SELECT_ALL + "WHERE SEMESTER_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new LateFeeRowMapper());
  }

  @Override
  public LateFee get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new LateFeeRowMapper());
  }

  @Override
  public int update(MutableLateFee pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getFrom(), pMutable.getTo(), pMutable.getFee(), pMutable.getSemester()
        .getId(), pMutable.getAdmissionType().getId(), pMutable.getId());
  }

  @Override
  public int delete(MutableLateFee pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableLateFee pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getFrom(), pMutable.getTo(), pMutable.getFee(), pMutable
        .getSemester().getId(), pMutable.getAdmissionType().getId());
    return id;
  }

  @Override
  public List<Long> create(List<MutableLateFee> pMutableList) {
    List<Object[]> params = getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    return params.stream().map(param -> (Long) param[0]).collect(Collectors.toList());
  }

  private List<Object[]> getInsertParamList(List<MutableLateFee> pMutableUGLateFees) {
    List<Object[]> params = new ArrayList<>();
    for(LateFee mutable : pMutableUGLateFees) {
      params.add(new Object[] {mIdGenerator.getNumericId(), mutable.getFrom(), mutable.getTo(), mutable.getFee(),
          mutable.getSemester().getId(), mutable.getAdmissionType().getId()});
    }
    return params;
  }

  @Override
  public List<LateFee> getLateFees(Integer pSemesterId, LateFee.AdmissionType pAdmissionType) {
    String query = SELECT_ALL + "WHERE SEMESTER_ID = ? AND ADMISSION_TYPE = ?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pAdmissionType.getId()}, new LateFeeRowMapper());
  }

  class LateFeeRowMapper implements RowMapper<LateFee> {
    @Override
    public LateFee mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableLateFee lateFee = new PersistentLateFee();
      lateFee.setId(rs.getLong("ID"));
      lateFee.setFrom(rs.getTimestamp("FROM_DATE"));
      lateFee.setTo(rs.getTimestamp("TO_DATE"));
      lateFee.setFee(new BigDecimal(rs.getInt("FEE")));
      lateFee.setAdmissionType(LateFee.AdmissionType.get(rs.getInt("ADMISSION_TYPE")));
      lateFee.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<LateFee> atomicReference = new AtomicReference<>(lateFee);
      return atomicReference.get();
    }
  }
}
