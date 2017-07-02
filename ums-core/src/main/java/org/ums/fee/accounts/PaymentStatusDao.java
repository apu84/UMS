package org.ums.fee.accounts;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import org.ums.generator.IdGenerator;

import com.google.common.collect.Lists;

public class PaymentStatusDao extends PaymentStatusDaoDecorator {
  String SELECT_ALL =
      "SELECT ID, ACCOUNT, TRANSACTION_ID, METHOD_OF_PAYMENT, RECEIPT_NO, PAYMENT_COMPLETE, RECEIVED_ON, "
          + "COMPLETED_ON, AMOUNT, PAYMENT_DETAILS, LAST_MODIFIED FROM PAYMENT_STATUS ";
  String INSERT_ALL =
      "INSERT INTO PAYMENT_STATUS (ID, ACCOUNT, TRANSACTION_ID, METHOD_OF_PAYMENT, PAYMENT_COMPLETE, "
          + "RECEIVED_ON, COMPLETED_ON, AMOUNT, PAYMENT_DETAILS, RECEIPT_NO, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
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
    return mJdbcTemplate.batchUpdate(query, getUpdateParamList(pMutableList)).length;
  }

  @Override
  public Long create(MutablePaymentStatus pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getAccount(), pMutable.getTransactionId(), pMutable
        .getMethodOfPayment().getId(), pMutable.isPaymentComplete(), pMutable.getReceivedOn(), pMutable
        .getCompletedOn(), pMutable.getAmount(), pMutable.getPaymentDetails(), pMutable.getReceiptNo());
    return id;
  }

  @Override
  public List<PaymentStatus> getByTransactionId(String pTransactionId) {
    String query = SELECT_ALL + "WHERE TRANSACTION_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pTransactionId}, new PaymentStatusRowMapper());
  }

  @Override
  public List<PaymentStatus> paginatedList(int itemsPerPage, int pageNumber) {
    int startIndex = (itemsPerPage * (pageNumber - 1)) + 1;
    int endIndex = startIndex + itemsPerPage - 1;
    String query =
        "SELECT TMP2.*, IND FROM (SELECT ROWNUM IND, TMP1.* FROM (" + SELECT_ALL
            + " ORDER BY LAST_MODIFIED DESC) TMP1) TMP2 WHERE IND >= ? and IND <= ?  ";
    return mJdbcTemplate.query(query, new Object[] {startIndex, endIndex}, new PaymentStatusRowMapper());
  }

  @Override
  public List<PaymentStatus> paginatedList(PaymentStatusFilter filter, int itemsPerPage, int pageNumber) {
    int startIndex = (itemsPerPage * (pageNumber - 1)) + 1;
    int endIndex = startIndex + itemsPerPage - 1;
    FilterQueryParam filterQueryParam = buildFilterQuery(filter, startIndex, endIndex);
    String query =
        "SELECT TMP2.*, IND FROM (SELECT ROWNUM IND, TMP1.* FROM (" + SELECT_ALL + filterQueryParam.getQuery()
            + " ORDER BY LAST_MODIFIED DESC) TMP1) TMP2 WHERE IND >= ? and IND <= ?  ";
    return mJdbcTemplate.query(query, filterQueryParam.getParams(), new PaymentStatusRowMapper());
  }

  private FilterQueryParam buildFilterQuery(PaymentStatusFilter pStatusFilter, int startIndex, int endIndex) {
    List<String> filter = new ArrayList<>();
    List<Object> filterParams = new ArrayList<>();
    if(pStatusFilter.getReceivedStart() != null) {
      filter.add("RECEIVED_ON >= ?");
      filterParams.add(pStatusFilter.getReceivedStart());
    }
    if(pStatusFilter.getReceivedEnd() != null) {
      filter.add("RECEIVED_ON <= ?");
      filterParams.add(pStatusFilter.getReceivedEnd());
    }
    if(!StringUtils.isEmpty(pStatusFilter.isPaymentCompleted())) {
      filter.add("PAYMENT_COMPLETE = ?");
      filterParams.add(!pStatusFilter.isPaymentCompleted().equalsIgnoreCase("no"));
    }
    if(pStatusFilter.getPaymentMethod() != null) {
      filter.add("METHOD_OF_PAYMENT = ?");
      filterParams.add(pStatusFilter.getPaymentMethod().getId());
    }
    if(!StringUtils.isEmpty(pStatusFilter.getTransactionId())) {
      filter.add("TRANSACTION_ID = ?");
      filterParams.add(pStatusFilter.getTransactionId());
    }
    if(!StringUtils.isEmpty(pStatusFilter.getAccount())) {
      filter.add("ACCOUNT = ?");
      filterParams.add(pStatusFilter.getAccount());
    }
    filterParams.add(startIndex);
    filterParams.add(endIndex);
    String WHERE_CLAUSE = filter.size() > 0 ? " WHERE " : "";
    return new FilterQueryParam(WHERE_CLAUSE + filter.stream().map(x -> x).collect(Collectors.joining(" AND ")),
        filterParams.toArray());
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
      status.setCompletedOn(rs.getTimestamp("COMPLETED_ON"));
      status.setAmount(new BigDecimal(rs.getDouble("AMOUNT")));
      status.setPaymentDetails(rs.getString("PAYMENT_DETAILS"));
      status.setReceiptNo(rs.getString("RECEIPT_NO"));
      status.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<PaymentStatus> reference = new AtomicReference<>(status);
      return reference.get();
    }
  }

  class FilterQueryParam {
    private String query;
    private Object[] params;

    FilterQueryParam(String pQuery, Object[] pParams) {
      query = pQuery;
      params = pParams;
    }

    public String getQuery() {
      return query;
    }

    public Object[] getParams() {
      return params;
    }
  }
}
