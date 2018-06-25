package org.ums.fee.payment;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.ums.fee.FeeType;
import org.ums.generator.IdGenerator;

import com.google.common.collect.Lists;
import org.ums.util.UmsUtils;

public class StudentPaymentDao extends StudentPaymentDaoDecorator {
  String SELECT_ALL = "SELECT ID, TRANSACTION_ID, STUDENT_ID, SEMESTER_ID, AMOUNT, STATUS, APPLIED_ON, VERIFIED_ON, "
      + "TRANSACTION_VALID_TILL, BRANCH_ID, LAST_MODIFIED, FEE_CATEGORY FROM STUDENT_PAYMENT ";
  String INSERT_ALL =
      "INSERT INTO STUDENT_PAYMENT (ID, TRANSACTION_ID, STUDENT_ID, SEMESTER_ID, AMOUNT, STATUS, APPLIED_ON, "
          + "TRANSACTION_VALID_TILL, BRANCH_ID, LAST_MODIFIED, FEE_CATEGORY) VALUES (:id, :transactionId, :studentId, :semesterId, :amount, :status, :appliedOn, :transactionValidTill, :branchId, :lastModified, :feeCategory)";
  String UPDATE_ALL =
      "UPDATE STUDENT_PAYMENT SET STATUS = :status, VERIFIED_ON = :verifiedOn, LAST_MODIFIED = :lastModified";

  String DELETE_ALL = "DELETE FROM STUDENT_PAYMENT ";

  String ORDER_BY = "ORDER BY APPLIED_ON";

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public StudentPaymentDao(JdbcTemplate mJdbcTemplate, NamedParameterJdbcTemplate mNamedParameterJdbcTemplate,
      IdGenerator mIdGenerator) {
    this.mJdbcTemplate = mJdbcTemplate;
    this.mNamedParameterJdbcTemplate = mNamedParameterJdbcTemplate;
    this.mIdGenerator = mIdGenerator;
  }

  @Override
  public StudentPayment get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = :id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pId);
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, new StudentPaymentRowMapper());
  }

  @Override
  public int update(MutableStudentPayment pMutable) {
    String query = UPDATE_ALL + "WHERE ID = :id";
    Map parameterMap = insertOrUpdateParameter(pMutable);
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  @Transactional
  public int update(List<MutableStudentPayment> pMutableList) {
    String query = UPDATE_ALL + "WHERE ID = :id";
    Map<String, Object>[] parameterObjects = getParameterObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(query, parameterObjects);
    return super.update(pMutableList);
  }

  @Override
  public Long create(MutableStudentPayment pMutable) {
    pMutable.setId(mIdGenerator.getNumericId());
    return create(Lists.newArrayList(pMutable)).get(0);
  }

  @Override
  public List<Long> create(List<MutableStudentPayment> pMutableList) {
    Map<String, Object>[] parameterObjects = getParameterObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(INSERT_ALL, parameterObjects);
    return pMutableList.stream().map(p->p.getId()).collect(Collectors.toList());
  }

  @Override
  public int delete(MutableStudentPayment pMutable) {
    String query = DELETE_ALL + "WHERE ID = :id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pMutable.getId());
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public List<StudentPayment> getToExpirePayments() {
    String query = SELECT_ALL + "WHERE TRANSACTION_VALID_TILL <= SYSDATE AND STATUS = 0";
    return mNamedParameterJdbcTemplate.query(query, new StudentPaymentRowMapper());
  }

  @Override
  public List<StudentPayment> getPayments(String pStudentId, Integer pSemesterId) {
    String query = SELECT_ALL + "WHERE STUDENT_ID = :studentId AND SEMESTER_ID = :semesterId " + ORDER_BY;
    Map parameterMap = new HashMap();
    parameterMap.put("studentId", pStudentId);
    parameterMap.put("semesterId", pSemesterId);
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new StudentPaymentRowMapper());
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

  private Map insertOrUpdateParameter(MutableStudentPayment studentPayment) {
    Map parameterMap = new HashMap();
    parameterMap.put("id", studentPayment.getId());
    parameterMap.put("transactionId", studentPayment.getTransactionId() == null ? mIdGenerator.getAlphaNumericId()
        : studentPayment.getTransactionId());
    parameterMap.put("semesterId", studentPayment.getSemesterId());
    parameterMap.put("studentId", studentPayment.getStudentId());
    parameterMap.put("amount", studentPayment.getAmount());
    parameterMap.put("status", studentPayment.getStatus().getValue());
    parameterMap.put("appliedOn", studentPayment.getAppliedOn() != null ? studentPayment.getAppliedOn() : new Date());
    parameterMap.put("verifiedOn", studentPayment.getVerifiedOn());
    parameterMap.put("feeCategory", studentPayment.getFeeCategoryId());
    parameterMap.put("feeType", studentPayment.getFeeTypeId());
    parameterMap.put("transactionValidTill", studentPayment.getTransactionValidTill());
    parameterMap.put("branchId", studentPayment.getBankBranchId());
    parameterMap.put("lastModified", UmsUtils.formatDate(new Date(), "YYYYMMDDHHMMSS"));
    return parameterMap;
  }

  private Map<String, Object>[] getParameterObjects(List<MutableStudentPayment> pMutable) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutable.size()];
    for(int i = 0; i < pMutable.size(); i++) {
      parameterMaps[i] = insertOrUpdateParameter(pMutable.get(i));
    }
    return parameterMaps;
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
