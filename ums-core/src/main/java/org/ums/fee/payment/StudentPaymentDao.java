package org.ums.fee.payment;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.ums.fee.FeeType;
import org.ums.generator.IdGenerator;

import com.google.common.collect.Lists;

public class StudentPaymentDao extends StudentPaymentDaoDecorator {
  String SELECT_ALL = "SELECT ID, TRANSACTION_ID, STUDENT_ID, SEMESTER_ID, AMOUNT, STATUS, APPLIED_ON, VERIFIED_ON, "
      + "TRANSACTION_VALID_TILL, BRANCH_ID, LAST_MODIFIED, FEE_CATEGORY FROM STUDENT_PAYMENT ";
  String INSERT_ALL =
      "INSERT INTO STUDENT_PAYMENT (ID, TRANSACTION_ID, STUDENT_ID, SEMESTER_ID, AMOUNT, STATUS, APPLIED_ON, "
          + "TRANSACTION_VALID_TILL, BRANCH_ID, LAST_MODIFIED, FEE_CATEGORY) VALUES (?, ?, ?, ?, ?, ?, SYSDATE, ?, "
          + getLastModifiedSql() + ", ?) ";
  String UPDATE_ALL = "UPDATE STUDENT_PAYMENT SET STATUS = ?, VERIFIED_ON = SYSDATE, LAST_MODIFIED = "
      + getLastModifiedSql() + " ";

  String DELETE_ALL = "DELETE FROM STUDENT_PAYMENT ";

  String ORDER_BY = "ORDER BY APPLIED_ON";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public StudentPaymentDao(JdbcTemplate mJdbcTemplate, IdGenerator mIdGenerator) {
    this.mJdbcTemplate = mJdbcTemplate;
    this.mIdGenerator = mIdGenerator;
  }

  @Override
  public StudentPayment get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new StudentPaymentRowMapper());
  }

  @Override
  public int update(MutableStudentPayment pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getStatus().getValue(), pMutable.getId());
  }

  @Override
  @Transactional
  public int update(List<MutableStudentPayment> pMutableList) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    List<Object[]> params = getUpdateParamArray(pMutableList);
    mJdbcTemplate.batchUpdate(query, params);
    return super.update(pMutableList);
  }

  @Override
  public Long create(MutableStudentPayment pMutable) {
    return create(Lists.newArrayList(pMutable)).get(0);
  }

  @Override
  public List<Long> create(List<MutableStudentPayment> pMutableList) {
    List<Object[]> params = getInsertParamArray(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    return params.stream().map(param -> (Long) param[0]).collect(Collectors.toList());
  }

  @Override
  public int delete(MutableStudentPayment pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query);
  }

  @Override
  public List<StudentPayment> getToExpirePayments() {
    String query = SELECT_ALL + "WHERE TRANSACTION_VALID_TILL <= SYSDATE AND STATUS = 0";
    return mJdbcTemplate.query(query, new StudentPaymentRowMapper());
  }

  private List<Object[]> getUpdateParamArray(List<MutableStudentPayment> pStudentPayments) {
    List<Object[]> params = new ArrayList<>();
    for(StudentPayment studentPayment : pStudentPayments) {
      params.add(new Object[] {studentPayment.getStatus().getValue(), studentPayment.getId()});
    }
    return params;
  }

  private List<Object[]> getInsertParamArray(List<MutableStudentPayment> pStudentPayments) {
    String transactionId = mIdGenerator.getAlphaNumericId();
    List<Object[]> params = new ArrayList<>();
    for(StudentPayment studentPayment : pStudentPayments) {
      params
          .add(new Object[] {mIdGenerator.getNumericId(), transactionId, studentPayment.getStudentId(),
              studentPayment.getSemesterId(), studentPayment.getAmount(), studentPayment.getStatus().getValue(),
              studentPayment.getTransactionValidTill(), studentPayment.getBankBranchId(),
              studentPayment.getFeeCategoryId()});
    }
    return params;
  }

  @Override
  public List<StudentPayment> getPayments(String pStudentId, Integer pSemesterId) {
    String query = SELECT_ALL + "WHERE STUDENT_ID = ? AND SEMESTER_ID = ? " + ORDER_BY;
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId}, new StudentPaymentRowMapper());
  }

  @Override
  public List<StudentPayment> getPayments(String pStudentId, Integer pSemesterId, FeeType pFeeType) {
    return getPayments(pStudentId, pSemesterId).stream()
        .filter(payment -> payment.getFeeCategory().getType().getId().intValue() == pFeeType.getId())
        .collect(Collectors.toList());
  }

  @Override
  public List<StudentPayment> getPayments(String pStudentId, FeeType pFeeType) {
    String query = SELECT_ALL + "WHERE STUDENT_ID = ? " + ORDER_BY;
    return mJdbcTemplate.query(query, new Object[] {pStudentId}, new StudentPaymentRowMapper()).stream()
        .filter(payment -> payment.getFeeCategory().getType().getId().intValue() == pFeeType.getId())
        .collect(Collectors.toList());
  }

  @Override
  public List<StudentPayment> getTransactionDetails(String pStudentId, String pTransactionId) {
    String query = SELECT_ALL + "WHERE TRANSACTION_ID = ? AND STUDENT_ID = ? " + ORDER_BY;
    return mJdbcTemplate.query(query, new Object[] {pTransactionId, pStudentId}, new StudentPaymentRowMapper());
  }

  @Override
  public List<StudentPayment> getPayments(String pStudentId) {
    String query = SELECT_ALL + "WHERE STUDENT_ID = ? " + ORDER_BY;
    return mJdbcTemplate.query(query, new Object[] {pStudentId}, new StudentPaymentRowMapper());
  }

  @Override
  public List<StudentPayment> getTransactionDetails(String pTransactionId) {
    String query = SELECT_ALL + "WHERE TRANSACTION_ID = ? " + ORDER_BY;
    return mJdbcTemplate.query(query, new Object[] {pTransactionId}, new StudentPaymentRowMapper());
  }

  class StudentPaymentRowMapper implements RowMapper<StudentPayment> {
    @Override
    public StudentPayment mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableStudentPayment studentPayment = new PersistentStudentPayment();
      studentPayment.setId(rs.getLong("ID"));
      studentPayment.setTransactionId(rs.getString("TRANSACTION_ID"));
      studentPayment.setSemesterId(rs.getInt("SEMESTER_ID"));
      studentPayment.setStudentId(rs.getString("STUDENT_ID"));
      studentPayment.setAmount(new BigDecimal(rs.getDouble("AMOUNT")));
      studentPayment.setStatus(StudentPayment.Status.get(rs.getInt("STATUS")));
      studentPayment.setAppliedOn(rs.getTimestamp("APPLIED_ON"));
      studentPayment.setVerifiedOn(rs.getTimestamp("VERIFIED_ON"));
      studentPayment.setTransactionValidTill(rs.getTimestamp("TRANSACTION_VALID_TILL"));
      studentPayment.setLastModified(rs.getString("LAST_MODIFIED"));
      studentPayment.setFeeCategoryId(rs.getString("FEE_CATEGORY"));
      studentPayment.setBankBranchId(rs.getLong("BRANCH_ID"));
      AtomicReference<StudentPayment> atomicReference = new AtomicReference<>(studentPayment);
      return atomicReference.get();
    }
  }
}
