package org.ums.fee.dues;

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

import com.google.common.collect.Lists;

public class StudentDuesDao extends StudentDuesDaoDecorator {
  String SELECT_ALL = "SELECT ID, FEE_CATEGORY, DESCRIPTION, STUDENT_ID, AMOUNT, ADDED_ON, ADDED_BY, PAY_BEFORE, "
      + "TRANSACTION_ID, LAST_MODIFIED FROM STUDENT_DUES ";
  String INSERT_ALL =
      "INSERT INTO STUDENT_DUES (ID, FEE_CATEGORY, DESCRIPTION, STUDENT_ID, AMOUNT, ADDED_ON, ADDED_BY, "
          + "PAY_BEFORE, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, SYSDATE, ?, ?, " + getLastModifiedSql() + ")";
  String UPDATE_ALL = "UPDATE STUDENT_DUES SET TRANSACTION_ID = ?, LAST_MODIFIED = " + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM STUDENT_DUES ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public StudentDuesDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<StudentDues> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new DuesRowMapper());
  }

  @Override
  public StudentDues get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new DuesRowMapper());
  }

  @Override
  public int update(MutableStudentDues pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getTransactionId(), pMutable.getId());
  }

  @Override
  public int update(List<MutableStudentDues> pMutableList) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamList(pMutableList)).length;
  }

  @Override
  public int delete(MutableStudentDues pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public Long create(MutableStudentDues pMutable) {
    return create(Lists.newArrayList(pMutable)).get(0);
  }

  @Override
  public List<Long> create(List<MutableStudentDues> pMutableList) {
    List<Object[]> params = getInsertParamArray(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    return params.stream().map(param -> (Long) param[0]).collect(Collectors.toList());
  }

  @Override
  public List<StudentDues> getByStudent(String pStudentId) {
    String query = SELECT_ALL + "WHERE STUDENT_ID = ? ORDER BY ADDED_ON DESC ";
    return mJdbcTemplate.query(query, new Object[] {pStudentId}, new DuesRowMapper());
  }

  private List<Object[]> getUpdateParamList(List<MutableStudentDues> pMutableStudentDues) {
    List<Object[]> params = new ArrayList<>();
    for(StudentDues dues : pMutableStudentDues) {
      params.add(new Object[] {dues.getTransactionId(), dues.getId()});
    }
    return params;
  }

  private List<Object[]> getInsertParamArray(List<MutableStudentDues> pMutableStudentDues) {
    List<Object[]> params = new ArrayList<>();
    for(StudentDues dues : pMutableStudentDues) {
      params.add(new Object[] {dues.getTransactionId(), dues.getId()});
    }
    return params;
  }

  private class DuesRowMapper implements RowMapper<StudentDues> {
    @Override
    public StudentDues mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableStudentDues dues = new PersistentStudentDues();
      dues.setId(rs.getLong("ID"));
      dues.setFeeCategoryId(rs.getString("FEE_CATEGORY"));
      dues.setDescription(rs.getString("DESCRIPTION"));
      dues.setStudentId(rs.getString("STUDENT_ID"));
      dues.setAmount(new BigDecimal(rs.getDouble("AMOUNT")));
      dues.setAddedOn(rs.getTimestamp("ADDED_ON"));
      dues.setUserId(rs.getString("ADDED_BY"));
      dues.setPayBefore(rs.getTimestamp("PAY_BEFORE"));
      dues.setLastModified(rs.getString("LAST_MODIFIED"));
      dues.setTransactionId(rs.getString("TRANSACTION_ID"));
      AtomicReference<StudentDues> reference = new AtomicReference<>(dues);
      return reference.get();
    }
  }
}
