package org.ums.fee.accounts;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PaymentStatusDao extends PaymentStatusDaoDecorator {
  String SELECT_ALL = "SELECT ID, ACCOUNT, TRANSACTION_ID, METHOD_OF_PAYMENT, PAYMENT_COMPLETE, RECEIVED_ON, "
      + "COMPLETED_ON, AMOUNT, PAYMENT_DETAILS, LAST_MODIFIED FROM PAYMENT_STATUS ";
  String INSERT_ALL = "INSERT INTO PAYMENT_STATUS (ID, ACCOUNT, TRANSACTION_ID, METHOD_OF_PAYMENT, PAYMENT_COMPLETE, "
      + "RECEIVED_ON, COMPLETED_ON, AMOUNT, PAYMENT_DETAILS, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, "
      + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE PAYMENT_STATUS SET PAYMENT_COMPLETE = ?, COMPLETED_ON = ?, LAST_MODIFIED = "
      + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM PAYMENT_STATUS ";

  JdbcTemplate mJdbcTemplate;
  IdGenerator mIdGenerator;

  public PaymentStatusDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<PaymentStatus> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new PaymentStatusRowMapper());
  }

  @Override
  public PaymentStatus get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PaymentStatusRowMapper());
  }

  @Override
  public int update(MutablePaymentStatus pMutable) {
    return update(Lists.newArrayList(pMutable));
  }

  @Override
  public int update(List<MutablePaymentStatus> pMutableList) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, getUpdateParamList(pMutableList));
  }

  @Override
  public Long create(MutablePaymentStatus pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getAccount(), pMutable.getTransactionId(), pMutable
        .getMethodOfPayment().getId(), pMutable.isPaymentComplete(), pMutable.getReceivedOn(), pMutable
        .getCompletedOn(), pMutable.getAmount(), pMutable.getPaymentDetails());
    return id;
  }

  private List<Object[]> getUpdateParamList(List<MutablePaymentStatus> pMutablePaymentStatuse) {
    List<Object[]> params = new ArrayList<>();
    for(PaymentStatus paymentStatus : pMutablePaymentStatuse) {
      params
          .add(new Object[] {paymentStatus.isPaymentComplete(), paymentStatus.getCompletedOn(), paymentStatus.getId()});
    }
    return params;
  }

  class PaymentStatusRowMapper implements RowMapper<PaymentStatus> {
    @Override
    public PaymentStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutablePaymentStatus status = new PersistentPaymentStatus();
      status.setId(rs.getLong("ID"));
      status.setAccount(rs.getString("ACCOUNT"));
      status.setTransactionId(rs.getString("TRANSACTION_ID"));
      status.setMethodOfPayment(PaymentStatus.PaymentMethod.get(rs.getInt("METHOD_OF_PAYMENT")));
      status.setPaymentComplete(rs.getBoolean("PAYMENT_COMPLETE"));
      status.setReceivedOn(rs.getTimestamp("RECEIVED_ON"));
      status.setCompletedOn(rs.getTime("COMPLETED_ON"));
      status.setAmount(new BigDecimal(rs.getDouble("AMOUNT")));
      status.setPaymentDetails(rs.getString("PAYMENT_DETAILS"));
      status.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<PaymentStatus> reference = new AtomicReference<>(status);
      return reference.get();
    }
  }
}
