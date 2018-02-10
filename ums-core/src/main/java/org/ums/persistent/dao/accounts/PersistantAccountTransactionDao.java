package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.AccountTransactionDaoDecorator;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableAccountTransaction;
import org.ums.enums.accounts.definitions.account.balance.BalanceType;
import org.ums.enums.accounts.general.ledger.vouchers.AccountTransactionType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentAccountTransaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class PersistantAccountTransactionDao extends AccountTransactionDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  String INSERT_ONE =
      "insert INTO DT_TRANSACTION(ID, COMP_CODE, VOUCHER_NO, VOUCHER_DATE, SERIAL_NO, ACCOUNT_ID, VOUCHER_ID, AMOUNT, BALANCE_TYPE, NARRATION, F_CURRENCY, CURRENCY_ID, CONV_FACTOR,  POST_DATE, TYPE, MODIFIED_BY, MODIFIED_DATE) VALUES "
          + "  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  public PersistantAccountTransactionDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Long> create(List<MutableAccountTransaction> pMutableList) {
    String query=INSERT_ONE;
    List<Object[]> params = getCreateParams(pMutableList);
    mJdbcTemplate.batchUpdate(query, params);
    return params.stream().map(param-> (Long) param[0])
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getCreateParams(List<MutableAccountTransaction> pMutableAccountTransactions) {
    List<Object[]> params = new ArrayList<>();

    for(AccountTransaction accountTransaction : pMutableAccountTransactions) {
      params.add(new Object[] {accountTransaction.getId(), accountTransaction.getCompany().getId(),
          accountTransaction.getVoucherNo(), accountTransaction.getVoucherDate(), accountTransaction.getSerialNo(),
          accountTransaction.getAccount().getId(), accountTransaction.getVoucher().getId(),
          accountTransaction.getAmount(), accountTransaction.getBalanceType().getValue(),
          accountTransaction.getNarration(), accountTransaction.getForeignCurrency(),
          accountTransaction.getCurrency().getId(), accountTransaction.getConversionFactor(),
          accountTransaction.getPostDate(), accountTransaction.getAccountTransactionType().getValue(),
          accountTransaction.getModifiedBy(), accountTransaction.getModifiedDate()});
    }
    return params;
  }

  @Override
  public Integer getTotalVoucherNumberBasedOnCurrentDay(Voucher pVoucher) {
    String query = "";
    return super.getTotalVoucherNumberBasedOnCurrentDay(pVoucher);
  }

  @Override
  public Integer getVoucherNumber(Voucher pVoucher, Date pStartDate, Date pEndDate) {
    String query =
        "SELECT COUNT(VOUCHER_NO) " + "FROM ( " + "  SELECT DISTINCT (VOUCHER_NO) VOUCHER_NO "
            + "  FROM DT_TRANSACTION " + "  WHERE VOUCHER_ID = ? AND (MODIFIED_DATE >= ? OR MODIFIED_DATE <= ?))";
    return mJdbcTemplate.queryForObject(query, new Object[] {pVoucher.getId(), pStartDate, pEndDate}, Integer.class);
  }

  @Override
  public List<MutableAccountTransaction> getAllPaginated(int itemPerPage, int pageNumber, Voucher voucher) {
    int startIndex = (itemPerPage * (pageNumber - 1)) + 1;
    int endIndex = startIndex + itemPerPage - 1;
    String query =
        "SELECT * "
            + "FROM (SELECT "
            + "        ROWNUM row_num, "
            + "        DT_TRANSACTION.* "
            + "      FROM DT_TRANSACTION "
            + "      WHERE (VOUCHER_NO, POST_DATE) IN (SELECT "
            + "                                          DT_TRANSACTION.VOUCHER_NO, "
            + "                                          MAX(DT_TRANSACTION.POST_DATE) "
            + "                                        FROM DT_TRANSACTION, FIN_ACCOUNT_YEAR "
            + "                                        WHERE YEAR_CLOSING_FLAG = 'O' AND "
            + "                                              DT_TRANSACTION.POST_DATE >= FIN_ACCOUNT_YEAR.CURRENT_START_DATE AND "
            + "                                              DT_TRANSACTION.POST_DATE <= FIN_ACCOUNT_YEAR.CURRENT_END_DATE AND "
            + "                                              VOUCHER_ID = ? "
            + "                                        GROUP BY DT_TRANSACTION.VOUCHER_NO)) temp "
            + "WHERE row_num >= ? AND row_num <= ? " + "ORDER BY POST_DATE, SERIAL_NO";
    return mJdbcTemplate.query(query, new Object[] {voucher.getId(), startIndex, endIndex},
        new PersistentAccountTransactionRowMapper());
  }

  class PersistentAccountTransactionRowMapper implements RowMapper<MutableAccountTransaction> {
    @Override
    public MutableAccountTransaction mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MutableAccountTransaction transaction = new PersistentAccountTransaction();
      transaction.setId(pResultSet.getLong("id"));
      transaction.setCompanyId(pResultSet.getString("comp_code"));
      transaction.setDivisionCode(pResultSet.getString("division_code"));
      transaction.setVoucherNo(pResultSet.getString("voucher_no"));
      transaction.setVoucherDate(pResultSet.getDate("voucher_date"));
      transaction.setSerialNo(pResultSet.getInt("serial_no"));
      transaction.setAccountId(pResultSet.getLong("account_id"));
      transaction.setVoucherId(pResultSet.getLong("voucher_id"));
      transaction.setAmount(pResultSet.getBigDecimal("amount"));
      transaction.setBalanceType(BalanceType.get(pResultSet.getString("balance_type")));
      transaction.setNarration(pResultSet.getString("narration"));
      transaction.setForeignCurrency(pResultSet.getBigDecimal("f_currency"));
      transaction.setCurrencyId(pResultSet.getLong("currency_id"));
      transaction.setConversionFactor(pResultSet.getBigDecimal("conv_factor"));
      transaction.setProjNo(pResultSet.getString("proj_no"));
      transaction.setStatFlag(pResultSet.getString("stat_flag"));
      transaction.setStatUpFlag(pResultSet.getString("stat_up_flag"));
      transaction.setReceiptId(pResultSet.getLong("receipt_id"));
      transaction.setPostDate(pResultSet.getDate("post_date"));
      transaction.setAccountTransactionType(AccountTransactionType.get(pResultSet.getString("type")));
      transaction.setModifiedBy(pResultSet.getString("modified_by"));
      transaction.setModifiedDate(pResultSet.getDate("modified_date"));
      return transaction;
    }
  }
}
