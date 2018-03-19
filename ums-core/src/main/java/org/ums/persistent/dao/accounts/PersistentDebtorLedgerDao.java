package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.DebtorLedgerDaoDecorator;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.DebtorLedger;
import org.ums.domain.model.mutable.accounts.MutableDebtorLedger;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentDebtorLedger;
import org.ums.util.UmsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public class PersistentDebtorLedgerDao extends DebtorLedgerDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  String SELECT_ALL = "select * from dt_debtor_ledger";
  String UPDATE_ONE = "UPDATE DT_DEBTOR_LEDGER SET " + "COMP_CODE=:compCode, " + "  DIVISION_CODE=:divisionCode, "
      + "  CUSTOMER_CODE=:customerCode, " + "  VOUCHER_NO=:voucherNo, " + "  VOUCHER_DATE=:voucherDate, "
      + "  SR_NO=:serialNo, " + "  INVOICE_NO=:invoiceNo, " + "  INVOICE_DATE=:invoiceDate, " + "  AMOUNT=:amount, "
      + "  PAID_AMOUNT=:paidAmount, " + "  DUE_DATE=:dueDate, " + "  BALANCE_TYPE=:balanceType, "
      + "  INVOICE_CLOSE_FLAG=:invoiceCloseFlag, " + "  CURRENCY_CODE=:currencyCode, " + "  STAT_FLAG=:statFlag, "
      + "  STAT_UP_FLAG=:statUpFlag, " + "  MODIFIED_DATE=:modifiedDate, " + "  MODIFIED_BY=:modifiedBy, "
      + "  last_modified=:lastModified, " + "  TRANSACTION_ID=:transactionId " + "where id=:id";

  String INSERT_ONE =
      "INSERT INTO DT_DEBTOR_LEDGER(ID, COMP_CODE, DIVISION_CODE, CUSTOMER_CODE, VOUCHER_NO, VOUCHER_DATE, SR_NO, INVOICE_NO, INVOICE_DATE, AMOUNT, PAID_AMOUNT, DUE_DATE, BALANCE_TYPE, INVOICE_CLOSE_FLAG, CURRENCY_CODE, STAT_FLAG, STAT_UP_FLAG, MODIFIED_DATE, MODIFIED_BY, LAST_MODIFIED, TRANSACTION_ID) VALUES "
          + "  (:id, :compCode, :divisionCode,:customerCode, :voucherNo, :voucherDate, :serialNo, :invoiceNo, :invoiceDate, :amount, :paidAmount, :dueDate, :balanceType, :invoiceCloseFlag, :currencyCode, :statFlag, :statUpFlag, :modifiedDate, :modifiedBy, :lastModified, :transactionId)";

  String DELETE_ONE = "DELETE FROM DT_DEBTOR_LEDGER WHERE ID=:id";

  public PersistentDebtorLedgerDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<DebtorLedger> getAll() {
    String query = SELECT_ALL;
    return mNamedParameterJdbcTemplate.query(query, new PersistentDebtorLedgerRowMapper());
  }

  @Override
  public List<MutableDebtorLedger> get(List<AccountTransaction> pAccountTransactions) {
    if (pAccountTransactions.size() == 0)
      return new ArrayList<>();
    String query = SELECT_ALL + " WHERE TRANSACTION_ID IN (:TRANSACTION_ID_LIST)";
    Map parameterMap = new HashMap();
    parameterMap.put("TRANSACTION_ID_LIST", pAccountTransactions.stream().map(p -> p.getId()).collect(Collectors.toList()));
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new PersistentDebtorLedgerRowMapper());
  }

  @Override
  public DebtorLedger get(Long pId) {
    String query = SELECT_ALL + " where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pId);
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, new PersistentDebtorLedgerRowMapper());
  }

  @Override
  public int update(MutableDebtorLedger pMutable) {
    Map parameter = getInsertOrUpdateParameters(pMutable);
    return mNamedParameterJdbcTemplate.update(UPDATE_ONE, parameter);
  }

  @Override
  public int update(List<MutableDebtorLedger> pMutableList) {
    if(pMutableList.size() == 0)
      return UmsUtils.NO_VALUE;
    Map<String, Object>[] parameterObjects = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(UPDATE_ONE, parameterObjects).length;
  }

  @Override
  public int delete(MutableDebtorLedger pMutable) {
    String query = DELETE_ONE + " where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pMutable.getId());
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public int delete(List<MutableDebtorLedger> pMutableList) {
    if(pMutableList.size()==0) return UmsUtils.NO_VALUE;
    String query = DELETE_ONE + " where id in (:idList)";
    List<Long> idList = pMutableList.stream().map(d -> d.getId()).collect(Collectors.toList());
    Map parameterMap = new HashMap();
    parameterMap.put("idList", idList);
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public Long create(MutableDebtorLedger pMutable) {
    String query = INSERT_ONE;
    Map parameterMap = getInsertOrUpdateParameters(pMutable);
    mNamedParameterJdbcTemplate.update(query, parameterMap);
    return pMutable.getId();
  }

  @Override
  public List<Long> create(List<MutableDebtorLedger> pMutableList) {
    Map<String, Object>[] parameterObjects = getParameterObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(INSERT_ONE, parameterObjects);
    return pMutableList.stream().map(p -> p.getId()).collect(Collectors.toList());
  }

  @Override
  public boolean exists(Long pId) {
    return super.exists(pId);
  }

  @Override
  public int count(List<MutableDebtorLedger> pMutableList) {
    String query = " select count(*) from dt_debtor_ledger where id in (:idList)";
    Map parameterMap = new HashMap();
    parameterMap.put("idList", pMutableList.stream().map(p -> p.getId()).collect(Collectors.toList()));
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, Integer.class);
  }

  private Map<String, Object>[] getParameterObjects(List<MutableDebtorLedger> pMutableDebtorLedgers) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableDebtorLedgers.size()];
    for(int i = 0; i < pMutableDebtorLedgers.size(); i++) {
      parameterMaps[i] = getInsertOrUpdateParameters(pMutableDebtorLedgers.get(i));
    }
    return parameterMaps;
  }

  private Map getInsertOrUpdateParameters(MutableDebtorLedger pMutableDebtorLedger) {
    Map parameters = new HashMap();
    parameters.put("id", pMutableDebtorLedger.getId());
    parameters.put("compCode", pMutableDebtorLedger.getCompany().getId());
    parameters.put("divisionCode", pMutableDebtorLedger.getDivisionCode());
    parameters.put("customerCode", pMutableDebtorLedger.getCustomerCode());
    parameters.put("voucherNo", pMutableDebtorLedger.getVoucherNo());
    parameters.put("voucherDate", pMutableDebtorLedger.getVoucherDate());
    parameters.put("serialNo", pMutableDebtorLedger.getSerialNo());
    parameters.put("invoiceNo", pMutableDebtorLedger.getInvoiceNo());
    parameters.put("invoiceDate", pMutableDebtorLedger.getInvoiceDate());
    parameters.put("amount", pMutableDebtorLedger.getAmount());
    parameters.put("paidAmount", pMutableDebtorLedger.getPaidAmount());
    parameters.put("dueDate", pMutableDebtorLedger.getDueDate());
    parameters.put("balanceType", pMutableDebtorLedger.getBalanceType().getValue());
    parameters.put("invoiceCloseFlag", pMutableDebtorLedger.getInvoiceClosingFlag());
    parameters.put("currencyCode", pMutableDebtorLedger.getCurrencyCode());
    parameters.put("statFlag", pMutableDebtorLedger.getStatFlag());
    parameters.put("statUpFlag", pMutableDebtorLedger.getStatUpFlag());
    parameters.put("modifiedDate", pMutableDebtorLedger.getModificationDate());
    parameters.put("modifiedBy", pMutableDebtorLedger.getModifiedBy());
    parameters.put("lastModified", UmsUtils.formatDate(new Date(), "YYYYMMDDHHMMSS"));
    parameters.put("transactionId", pMutableDebtorLedger.getAccountTransactionId());
    return parameters;
  }

  class PersistentDebtorLedgerRowMapper implements RowMapper<DebtorLedger> {
    @Override
    public DebtorLedger mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableDebtorLedger debtorLedger = new PersistentDebtorLedger();
      debtorLedger.setId(rs.getLong("id"));
      debtorLedger.setCompanyId(rs.getString("comp_code"));
      debtorLedger.setDivisionCode(rs.getString("division_code"));
      debtorLedger.setCustomerCode(rs.getString("customer_code"));
      debtorLedger.setVoucherNo(rs.getString("voucher_no"));
      debtorLedger.setVoucherDate(rs.getDate("voucher_date"));
      debtorLedger.setSerialNo(rs.getInt("sr_no"));
      debtorLedger.setInvoiceNo(rs.getString("invoice_no"));
      debtorLedger.setInvoiceDate(rs.getDate("invoice_date"));
      debtorLedger.setAmount(rs.getBigDecimal("amount"));
      debtorLedger.setPaidAmount(rs.getBigDecimal("paid_amount"));
      debtorLedger.setDueDate(rs.getDate("due_date"));
      debtorLedger.setBalanceType(BalanceType.get(rs.getString("balance_type")));
      debtorLedger.setInvoiceClosingFlag(rs.getString("invoice_close_flag"));
      debtorLedger.setCurrencyCode(rs.getString("currency_code"));
      debtorLedger.setStatFlag(rs.getString("stat_flag"));
      debtorLedger.setStatUpFlag(rs.getString("stat_up_flag"));
      debtorLedger.setModificationDate(rs.getDate("modified_date"));
      debtorLedger.setModifiedBy(rs.getString("modified_by"));
      debtorLedger.setLastModified(rs.getString("last_modified"));
      debtorLedger.setAccountTransactionId(rs.getLong("transaction_id"));
      return debtorLedger;
    }
  }

}
