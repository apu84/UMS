package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.VoucherNumberControlDaoDecorator;
import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.domain.model.mutable.accounts.MutableVoucherNumberControl;
import org.ums.enums.accounts.definitions.voucher.number.control.ResetBasis;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentVoucherNumberControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
public class PersistentVoucherNumberControlDao extends VoucherNumberControlDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentVoucherNumberControlDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<VoucherNumberControl> getAll() {
    String query = "select * from mst_voucher_number_control";
    return mJdbcTemplate.query(query, new PersistentVoucherNumberControlRowMapper());
  }

  @Override
  public VoucherNumberControl get(Long pId) {
    String query = "select * from mst_voucher_number_control where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentVoucherNumberControlRowMapper());
  }

  @Override
  public VoucherNumberControl validate(VoucherNumberControl pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutableVoucherNumberControl pMutable) {

    return super.update(pMutable);
  }

  @Override
  public int update(List<MutableVoucherNumberControl> pMutableList) {
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableVoucherNumberControl pMutable) {
    return super.delete(pMutable);
  }

  @Override
  public int delete(List<MutableVoucherNumberControl> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public Long create(MutableVoucherNumberControl pMutable) {
    return super.create(pMutable);
  }

  @Override
  public List<Long> create(List<MutableVoucherNumberControl> pMutableList) {
    return super.create(pMutableList);
  }

  @Override
  public boolean exists(Long pId) {
    return super.exists(pId);
  }

  class PersistentVoucherNumberControlRowMapper implements RowMapper<VoucherNumberControl> {
    @Override
    public VoucherNumberControl mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableVoucherNumberControl voucher = new PersistentVoucherNumberControl();
      voucher.setId(rs.getLong("id"));
      voucher.setFinAccountYearId(rs.getLong("fin_account_year_id"));
      voucher.setVoucherId(rs.getLong("voucher_id"));
      voucher.setResetBasis(ResetBasis.get(rs.getString("reset_basis")));
      voucher.setStartVoucherNo(rs.getInt("start_voucher_no"));
      voucher.setVoucherLimit(rs.getBigDecimal("voucher_limit"));
      voucher.setStatFlag(rs.getString("stat_flat"));
      voucher.setStatUpFlag(rs.getString("stat_up_flag"));
      voucher.setModifiedDate(rs.getDate("modified_date"));
      voucher.setModifiedBy(rs.getString("modified_by"));
      return voucher;
    }
  }
}
