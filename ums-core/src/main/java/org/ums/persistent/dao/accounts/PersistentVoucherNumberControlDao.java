package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.ums.decorator.accounts.VoucherNumberControlDaoDecorator;
import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.domain.model.mutable.accounts.MutableVoucherNumberControl;
import org.ums.enums.accounts.definitions.voucher.number.control.ResetBasis;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentVoucherNumberControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  public List<VoucherNumberControl> getByCurrentFinancialYear() {
    String query =
        "select * from MST_VOUCHER_NUMBER_CONTROL where FIN_ACCOUNT_YEAR_ID in (select id from FIN_ACCOUNT_YEAR where YEAR_CLOSING_FLAG='O')";
    return mNamedParameterJdbcTemplate.query(query, new PersistentVoucherNumberControlRowMapper());
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
  public int[] updateVoucherNumberControls(List<MutableVoucherNumberControl> pMutableList) {
    String query =
        "update MST_VOUCHER_NUMBER_CONTROL set  RESET_BASIS=:resetBasis,START_VOUCHER_NO=:startVoucherNo, VOUCHER_LIMIT=:voucherLimit, "
            + "MODIFIED_DATE=:modifiedDate, MODIFIED_BY=:modifiedBy where id=:id";

    SqlParameterSource[] parameters = new SqlParameterSource[pMutableList.size()];
    for(int i = 0; i < pMutableList.size(); i++) {
      BeanPropertySqlParameterSource beanPropertySqlParameterSource =
          new BeanPropertySqlParameterSource(pMutableList.get(i));
      beanPropertySqlParameterSource.registerSqlType("resetBasis", Types.CHAR);
      parameters[i] = beanPropertySqlParameterSource;
    }
    int[] value = new int[pMutableList.size()];
    try {

      value = mNamedParameterJdbcTemplate.batchUpdate(query, parameters);
    } catch(Exception e) {
      e.printStackTrace();
    }
    return value;
    /*
     * String query =
     * "update MST_VOUCHER_NUMBER_CONTROL set  RESET_BASIS=?,START_VOUCHER_NO=?, VOUCHER_LIMIT=?, "
     * + "MODIFIED_DATE=?, MODIFIED_BY=? where id=?"; List<Object[]> params =
     * getUpdateParamList(pMutableList); int[] values = mJdbcTemplate.batchUpdate(query, params);
     * return values;
     */
  }

  @Override
  public int update(List<MutableVoucherNumberControl> pMutableList) {
    String query =
        "update MST_VOUCHER_NUMBER_CONTROL set  RESET_BASIS=:resetBasis,START_VOUCHER_NO=:startVoucherNo, VOUCHER_LIMIT=:voucherLimit, "
            + "MODIFIED_DATE=:modifiedDate, MODIFIED_BY=:modifiedBy where id=:id";
    Map<String, Object>[] maps = new HashMap[pMutableList.size()];
    createMapArrayFromObject(pMutableList, maps);
    return mNamedParameterJdbcTemplate.batchUpdate(query, maps).length;
  }

  private List<Object[]> getUpdateParamList(List<MutableVoucherNumberControl> pMutableVoucherNumberControls) {
    List<Object[]> params = new ArrayList<>();

    for(MutableVoucherNumberControl v : pMutableVoucherNumberControls) {
      params.add(new Object[] {v.getStartVoucherNo(), v.getId()});
    }
    return params;
  }

  @Override
  public List<Long> create(List<MutableVoucherNumberControl> pMutableList) {
    String query = "insert into MST_VOUCHER_NUMBER_CONTROL(ID, FIN_ACCOUNT_YEAR_ID,VOUCHER_ID, RESET_BASIS, START_VOUCHER_NO, VOUCHER_LIMIT, STAT_FLAG, STAT_UP_FLAG, MODIFIED_DATE, MODIFIED_BY)" +
        " values(:id, :finAccountYearId, :voucherId, :resetBasis, :startVoucherNo, :voucherLimit, :statFlag, :statUpFlag, :modifiedDate, :modifiedBy)";
    pMutableList.forEach(v -> v.setId(mIdGenerator.getNumericId()));
    Map<String, Object>[] maps = new HashMap[pMutableList.size()];

    createMapArrayFromObject(pMutableList, maps);
    mNamedParameterJdbcTemplate.batchUpdate(query, maps);
    return null;
  }

  private void createMapArrayFromObject(List<MutableVoucherNumberControl> pMutableList, Map<String, Object>[] pMaps) {
    for(int i = 0; i < pMutableList.size(); i++) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", pMutableList.get(i).getId());
      map.put("finAccountYearId", pMutableList.get(i).getFinAccountYearId());
      map.put("voucherId", pMutableList.get(i).getVoucher().getId());
      map.put("resetBasis", pMutableList.get(i).getResetBasis().getId());
      map.put("startVoucherNo", pMutableList.get(i).getStartVoucherNo());
      map.put("voucherLimit", pMutableList.get(i).getVoucherLimit());
      map.put("statFlag", pMutableList.get(i).getStatFlag());
      map.put("statUpFlag", pMutableList.get(i).getStatUpFlag());
      map.put("modifiedDate", pMutableList.get(i).getModifiedDate());
      map.put("modifiedBy", pMutableList.get(i).getModifiedBy());
      pMaps[i] = map;
    }
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
      voucher.setStatFlag(rs.getString("stat_flag"));
      voucher.setStatUpFlag(rs.getString("stat_up_flag"));
      voucher.setModifiedDate(rs.getDate("modified_date"));
      voucher.setModifiedBy(rs.getString("modified_by"));
      return voucher;
    }
  }
}
