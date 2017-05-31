package org.ums.fee.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PaymentAccountMappingDao extends PaymentAccountsMappingDaoDecorator {
  String SELECT_ALL =
      "SELECT ID, FEE_TYPE, FACULTY, ACCOUNT, ACCOUNTS_DETAILS, LAST_MODIFIED FROM PAYMENT_ACCOUNT_MAPPING ";

  JdbcTemplate mJdbcTemplate;

  public PaymentAccountMappingDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<PaymentAccountsMapping> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new PaymentAccountMappingRowMapper());
  }

  @Override
  public PaymentAccountsMapping get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PaymentAccountMappingRowMapper());
  }

  class PaymentAccountMappingRowMapper implements RowMapper<PaymentAccountsMapping> {
    @Override
    public PaymentAccountsMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutablePaymentAccountsMapping mapping = new PersistentPaymentAccountsMapping();
      mapping.setId(rs.getLong("ID"));
      mapping.setFacultyId(rs.getInt("FACULTY"));
      mapping.setFeeTypeId(rs.getInt("FEE_TYPE"));
      mapping.setAccount(rs.getString("ACCOUNT"));
      mapping.setAccountDetails(rs.getString("ACCOUNT_DETAILS"));
      mapping.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<PaymentAccountsMapping> reference = new AtomicReference<>(mapping);
      return reference.get();
    }
  }
}
