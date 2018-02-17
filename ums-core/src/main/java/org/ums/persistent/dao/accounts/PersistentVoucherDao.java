package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.VoucherDaoDecorator;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.domain.model.mutable.accounts.MutableVoucher;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentVoucher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */
public class PersistentVoucherDao extends VoucherDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentVoucherDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
                              IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<Voucher> getAll() {
    String query = "select * from mst_voucher";
    return mJdbcTemplate.query(query, new PersistentVoucherRowMapper());
  }

  @Override
  public Voucher get(Long pId) {
    String query = "select * from mst_voucher where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new PersistentVoucherRowMapper());
  }

  @Override
  public Voucher validate(Voucher pReadonly) {
    return super.validate(pReadonly);
  }

  @Override
  public int update(MutableVoucher pMutable) {
    return super.update(pMutable);
  }

  @Override
  public int update(List<MutableVoucher> pMutableList) {
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableVoucher pMutable) {
    return super.delete(pMutable);
  }

  @Override
  public int delete(List<MutableVoucher> pMutableList) {
    return super.delete(pMutableList);
  }

  @Override
  public Long create(MutableVoucher pMutable) {
    return super.create(pMutable);
  }

  @Override
  public List<Long> create(List<MutableVoucher> pMutableList) {
    return super.create(pMutableList);
  }

  @Override
  public boolean exists(Long pId) {
    return super.exists(pId);
  }

  class PersistentVoucherRowMapper implements RowMapper<Voucher> {
    @Override
    public Voucher mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableVoucher voucher = new PersistentVoucher();
      voucher.setId(rs.getLong("id"));
      voucher.setName(rs.getString("name"));
      voucher.setShortName(rs.getString("short_name"));
      voucher.setLastModified(rs.getString("last_modified"));
      return voucher;
    }
  }
}
