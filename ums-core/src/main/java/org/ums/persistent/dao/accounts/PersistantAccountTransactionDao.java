package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.AccountTransactionDaoDecorator;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.generator.IdGenerator;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class PersistantAccountTransactionDao extends AccountTransactionDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistantAccountTransactionDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
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
}
