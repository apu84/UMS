package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.CreditorLedgerDaoDecorator;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.CreditorLedger;
import org.ums.domain.model.mutable.accounts.MutableCreditorLedger;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentCreditorLedger;
import org.ums.util.UmsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 11-Mar-18.
 */
public class PersistentCreditorLedgerDao extends CreditorLedgerDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  String SELECT_ALL = "select * from dt_creditor_ledger";
  String UPDATE_ONE = "UPDATE DT_CREDITOR_LEDGER SET " + "COMP_CODE=:compCode, " + "  DIVISION_CODE=:divisionCode, "
      + "  SUPPLIER_CODE=:supplierCode, " + "  VOUCHER_NO=:voucherNo, " + "  VOUCHER_DATE=:voucherDate, "
      + "  SR_NO=:serialNo, " + "  BILL_NO=:billNo, " + "  bill_date=:billDate, " + "  AMOUNT=:amount, "
      + "  PAID_AMOUNT=:paidAmount, " + "  DUE_DATE=:dueDate, " + "  BALANCE_TYPE=:balanceType, "
      + "  bill_close_flag=:billCloseFlag, " + "  CURRENCY_CODE=:currencyCode, " + "  vat_no=:vatNo, "
      + "  cont_code=:contCode, " + "  order_no=:orderNo, " + "  STAT_FLAG=:statFlag, "
      + "  STAT_UP_FLAG=:statUpFlag, " + "  MODIFIED_DATE=:modifiedDate, " + "  MODIFIED_BY=:modifiedBy, "
      + "  last_modified=:lastModified, " + "  TRANSACTION_ID=:transactionId " + "where id=:id";

  String INSERT_ONE =
      "INSERT INTO dt_creditor_ledger(ID, COMP_CODE, DIVISION_CODE, SUPPLIER_CODE, VOUCHER_NO, VOUCHER_DATE, SR_NO, bill_no, bill_date, AMOUNT, PAID_AMOUNT, DUE_DATE, BALANCE_TYPE, bill_close_flag, CURRENCY_CODE, vat_no, cont_code, order_no, STAT_FLAG, STAT_UP_FLAG, MODIFIED_DATE, MODIFIED_BY, LAST_MODIFIED, TRANSACTION_ID) VALUES "
          + "  (:id, :compCode, :divisionCode,:supplierCode, :voucherNo, :voucherDate, :serialNo, :billNo, :billDate, :amount, :paidAmount, :dueDate, :balanceType, :billCloseFlag, :currencyCode,:vatNo, :contCode, :orderNo, :statFlag, :statUpFlag, :modifiedDate, :modifiedBy, :lastModified, :transactionId)";

  String DELETE_ONE = "DELETE FROM dt_creditor_ledger WHERE ID=:id";

  public PersistentCreditorLedgerDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<CreditorLedger> getAll() {
    String query = SELECT_ALL;
    return mNamedParameterJdbcTemplate.query(query, new PersistentCreditorLedgerRowMapper());
  }

  @Override
  public CreditorLedger get(Long pId) {
    String query = SELECT_ALL + " where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pId);
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, new PersistentCreditorLedgerRowMapper());
  }

  @Override
  public int update(MutableCreditorLedger pMutable) {
    Map parameterMap = getInsertOrUpdateParameters(pMutable);
    return mNamedParameterJdbcTemplate.update(UPDATE_ONE, parameterMap);
  }

  @Override
  public int update(List<MutableCreditorLedger> pMutableList) {
    Map<String, Object>[] parameterObjects = getParameterObjects(pMutableList);
    return mNamedParameterJdbcTemplate.batchUpdate(UPDATE_ONE, parameterObjects).length;
  }

  @Override
  public int delete(MutableCreditorLedger pMutable) {
    String query = DELETE_ONE + " where id=:id";
    Map parameterMap = getInsertOrUpdateParameters(pMutable);
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public int delete(List<MutableCreditorLedger> pMutableList) {
    String query = DELETE_ONE + " where id in (:idList)";
    Map parameterMap = new HashMap();
    parameterMap.put("idList", pMutableList.stream().map(i -> i.getId()).collect(Collectors.toList()));
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  @Override
  public Long create(MutableCreditorLedger pMutable) {
    Map parameterMap = getInsertOrUpdateParameters(pMutable);
    mNamedParameterJdbcTemplate.update(INSERT_ONE, parameterMap);
    return pMutable.getId();
  }

  @Override
  public List<Long> create(List<MutableCreditorLedger> pMutableList) {
    Map<String, Object>[] parameterObjects = getParameterObjects(pMutableList);
    mNamedParameterJdbcTemplate.batchUpdate(INSERT_ONE, parameterObjects);
    return pMutableList.stream().map(i -> i.getId()).collect(Collectors.toList());
  }

  @Override
  public List<MutableCreditorLedger> get(List<AccountTransaction> pAccountTransactions) {
    if (pAccountTransactions.size() == 0)
      return new ArrayList<>();
    String query = SELECT_ALL + " WHERE TRANSACTION_ID IN (:TRANSACTION_ID_LIST)";
    Map parameterMap = new HashMap();
    parameterMap.put("TRANSACTION_ID_LIST", pAccountTransactions.stream().map(p -> p.getId()).collect(Collectors.toList()));
    return mNamedParameterJdbcTemplate.query(query, parameterMap, new PersistentCreditorLedgerRowMapper());
  }

  @Override
  public boolean exists(Long pId) {
    return super.exists(pId);
  }

  @Override
  public int count(List<MutableCreditorLedger> pMutableList) {
    String query = "select count(*) where id in (:idList)";
    Map parameterMap = new HashMap();
    parameterMap.put("idList", pMutableList.stream().map(p -> p.getId()).collect(Collectors.toList()));
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, Integer.class);
  }

  private Map<String, Object>[] getParameterObjects(List<MutableCreditorLedger> pMutableCreditorLedgers) {
    Map<String, Object>[] parameterMaps = new HashMap[pMutableCreditorLedgers.size()];
    for(int i = 0; i < pMutableCreditorLedgers.size(); i++) {
      parameterMaps[i] = getInsertOrUpdateParameters(pMutableCreditorLedgers.get(i));
    }
    return parameterMaps;
  }

  private Map getInsertOrUpdateParameters(MutableCreditorLedger pMutableCreditorLedger) {
    Map parameters = new HashMap();
    parameters.put("id", pMutableCreditorLedger.getId());
    parameters.put("compCode", pMutableCreditorLedger.getCompany().getId());
    parameters.put("divisionCode", pMutableCreditorLedger.getDivisionCode());
    parameters.put("supplierCode", pMutableCreditorLedger.getSupplierCode());
    parameters.put("voucherNo", pMutableCreditorLedger.getVoucherNo());
    parameters.put("voucherDate", pMutableCreditorLedger.getVoucherDate());
    parameters.put("serialNo", pMutableCreditorLedger.getSerialNo());
    parameters.put("billNo", pMutableCreditorLedger.getBillNo());
    parameters.put("billDate", pMutableCreditorLedger.getBillDate());
    parameters.put("amount", pMutableCreditorLedger.getAmount());
    parameters.put("paidAmount", pMutableCreditorLedger.getPaidAmount());
    parameters.put("dueDate", pMutableCreditorLedger.getDueDate());
    parameters.put("balanceType", pMutableCreditorLedger.getBalanceType().getValue());
    parameters.put("billCloseFlag", pMutableCreditorLedger.getBillClosingFlag());
    parameters.put("vatNo", pMutableCreditorLedger.getVatNo());
    parameters.put("contCode", pMutableCreditorLedger.getContCode());
    parameters.put("orderNo", pMutableCreditorLedger.getOrderNo());
    parameters.put("currencyCode", pMutableCreditorLedger.getCurrencyCode());
    parameters.put("statFlag", pMutableCreditorLedger.getStatFlag());
    parameters.put("statUpFlag", pMutableCreditorLedger.getStatUpFlag());
    parameters.put("modifiedDate", pMutableCreditorLedger.getModificationDate());
    parameters.put("modifiedBy", pMutableCreditorLedger.getModifiedBy());
    parameters.put("lastModified", UmsUtils.formatDate(new Date(), "YYYYMMDDHHMMSS"));
    parameters.put("transactionId", pMutableCreditorLedger.getAccountTransactionId());
    return parameters;
  }

  class PersistentCreditorLedgerRowMapper implements RowMapper<CreditorLedger> {
    @Override
    public CreditorLedger mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableCreditorLedger creditorLedger = new PersistentCreditorLedger();
      creditorLedger.setId(rs.getLong("id"));
      creditorLedger.setCompanyId(rs.getString("comp_code"));
      creditorLedger.setDivisionCode(rs.getString("division_code"));
      creditorLedger.setSupplierCode(rs.getString("supplier_code"));
      creditorLedger.setVoucherNo(rs.getString("voucher_no"));
      creditorLedger.setVoucherDate(rs.getDate("voucher_date"));
      creditorLedger.setSerialNo(rs.getInt("sr_no"));
      creditorLedger.setBillNo(rs.getString("bill_no"));
      creditorLedger.setBillDate(rs.getDate("bill_date"));
      creditorLedger.setAmount(rs.getBigDecimal("amount"));
      creditorLedger.setPaidAmount(rs.getBigDecimal("paid_amount"));
      creditorLedger.setBalanceType(BalanceType.get(rs.getString("balance_type")));
      creditorLedger.setBillClosingFlag(rs.getString("bill_close_flag"));
      creditorLedger.setDueDate(rs.getDate("due_date"));
      creditorLedger.setCurrencyCode(rs.getString("currency_code"));
      creditorLedger.setVatNo(rs.getString("vat_no"));
      creditorLedger.setContCode(rs.getString("cont_code"));
      creditorLedger.setOrderNo(rs.getString("order_no"));
      creditorLedger.setStatFlag(rs.getString("stat_flag"));
      creditorLedger.setStatUpFlag(rs.getString("stat_up_flag"));
      creditorLedger.setModificationDate(rs.getDate("modified_date"));
      creditorLedger.setModifiedBy(rs.getString("modified_by"));
      creditorLedger.setAccountTransactionId(rs.getLong("transaction_id"));
      return creditorLedger;
    }
  }
}
