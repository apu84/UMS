package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.ReceiptDaoDecorator;
import org.ums.domain.model.immutable.accounts.Receipt;
import org.ums.domain.model.mutable.accounts.MutableReceipt;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentReceipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 29-Jan-18.
 */
public class PersistentReceiptDao extends ReceiptDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentReceiptDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Receipt> getAll() {
    String query = "select * from mst_receipt";
    return mJdbcTemplate.query(query, new PersistentReceiptRowMapper());
  }

  @Override
  public Receipt get(Long pId) {
    String query = "Select * from mst_receipt where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentReceiptRowMapper());
  }

  @Override
  public Receipt validate(Receipt pReadonly) {
    return get(pReadonly.getId());
  }

  class PersistentReceiptRowMapper implements RowMapper<Receipt> {
    @Override
    public Receipt mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableReceipt receipt = new PersistentReceipt();
      receipt.setId(rs.getLong("id"));
      receipt.setName(rs.getString("name"));
      receipt.setShortName(rs.getString("short_name"));
      return receipt;
    }
  }

}
