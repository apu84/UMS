package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.PaymentInfoDaoDecorator;
import org.ums.domain.model.immutable.PaymentInfo;
import org.ums.domain.model.mutable.MutablePaymentInfo;
import org.ums.enums.PaymentMode;
import org.ums.enums.PaymentType;
import org.ums.persistent.model.PersistentPaymentInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 25-Jan-17.
 */
public class PersistentPaymentInfoDao extends PaymentInfoDaoDecorator {

  String SELECT_ALL =
      "SELECT id, reference_id, semester_id, payment_type, amount, to_char(payment_date,'dd/mm/yyyy') payment_date, last_modified, PAYMENT_MODE from PAYMENT_INFO ";
  String INSERT_ONE =
      "INSERT INTO  PAYMENT_INFO (REFERENCE_ID, SEMESTER_ID, PAYMENT_TYPE, AMOUNT, PAYMENT_DATE, LAST_MODIFIED, PAYMENT_MODE)"
          + "    VALUES (?,?,?,?,sysdate," + getLastModifiedSql() + ",?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentPaymentInfoDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public Integer create(MutablePaymentInfo pMutable) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutable.getReferenceId(), pMutable.getSemester().getId(), pMutable
        .getPaymentType().getId(), pMutable.getAmount(), pMutable.getPaymentMode().getId());
  }

  @Override
  public List<PaymentInfo> getPaymentInfo(String pReferenceId, int pSemesterId) {
    String query = SELECT_ALL + " WHERE REFERENCE_ID=? AND SEMESTER_ID=?";
    return mJdbcTemplate.query(query, new Object[] {pReferenceId, pSemesterId}, new PaymentInfoRowMapper());
  }

  class PaymentInfoRowMapper implements RowMapper<PaymentInfo> {
    @Override
    public PaymentInfo mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentPaymentInfo paymentInfo = new PersistentPaymentInfo();
      paymentInfo.setId(pResultSet.getInt("id"));
      paymentInfo.setReferenceId(pResultSet.getString("reference_id"));
      paymentInfo.setSemesterId(pResultSet.getInt("semester_id"));
      paymentInfo.setPaymentType(PaymentType.get(pResultSet.getInt("payment_type")));
      paymentInfo.setAmount(pResultSet.getInt("amount"));
      paymentInfo.setPaymentDate(pResultSet.getString("payment_date"));
      paymentInfo.setLastModified(pResultSet.getString("last_modified"));
      paymentInfo.setPaymentMode(PaymentMode.get(pResultSet.getInt("payment_mode")));
      return paymentInfo;
    }
  }

}
