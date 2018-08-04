package org.ums.persistent.dao.accounts;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.accounts.PredefinedNarrationDaoDecorator;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.accounts.PersistentPredefinedNarration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 12-Jan-18.
 */
public class PersistentPredefinedNarrationDao extends PredefinedNarrationDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentPredefinedNarrationDao(JdbcTemplate pJdbcTemplate,
      NamedParameterJdbcTemplate pNamedParameterJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<PredefinedNarration> getAll() {
    String query = "select * from DT_PREDEFINED_NARATION";
    return mJdbcTemplate.query(query, new PersistentPredefinedNarrationRowMapper());
  }

  @Override
  public List<PredefinedNarration> getAll(Company pCompany) {
    String query = "select * from DT_PREDEFINED_NARATION where comp_code=?";
    return mJdbcTemplate.query(query, new Object[] {pCompany.getId()}, new PersistentPredefinedNarrationRowMapper());
  }

  @Override
  public PredefinedNarration get(Long pId) {
    String query = "select * from dt_predefined_narration where id=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentPredefinedNarrationRowMapper());
  }

  @Override
  public PredefinedNarration validate(PredefinedNarration pReadonly) {
    return get(pReadonly.getId());
  }

  @Override
  public int update(MutablePredefinedNarration pMutable) {
    String query = "update DT_PREDEFINED_NARATION set NARRATION=?, MODIFIED_BY=?, MODIFIED_DATE=? where id=?";
    return mJdbcTemplate.update(query, pMutable.getNarration(), pMutable.getModifiedBy(), pMutable.getModifiedDate(),
        pMutable.getId());
  }

  @Override
  public int update(List<MutablePredefinedNarration> pMutableList) {
    String query = "update DT_PREDEFINED_NARATION set NARRATION=?, MODIFIED_BY=?, MODIFIED_DATE=? where id=?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParams(pMutableList)).length;
  }

  private List<Object[]> getUpdateParams(List<MutablePredefinedNarration> pMutablePredefinedNarrations) {
    List<Object[]> params = new ArrayList<>();
    for(PredefinedNarration predefinedNarration : pMutablePredefinedNarrations) {
      params.add(new Object[] {predefinedNarration.getNarration(), predefinedNarration.getModifiedBy(),
          predefinedNarration.getModifiedDate(), predefinedNarration.getId()});
    }
    return params;
  }

  private List<Object[]> getCreateParams(List<MutablePredefinedNarration> pMutablePredefinedNarrations) {
    List<Object[]> params = new ArrayList<>();
    for(PredefinedNarration predefinedNarration : pMutablePredefinedNarrations) {
      params.add(new Object[] {predefinedNarration.getId(), predefinedNarration.getVoucher().getId(),
          predefinedNarration.getNarration(), predefinedNarration.getStatFlag(), predefinedNarration.getStatUpFlag(),
          predefinedNarration.getModifiedDate(), predefinedNarration.getModifiedBy(),
          predefinedNarration.getCompany().getId()});
    }
    return params;
  }

  @Override
  public List<Long> create(List<MutablePredefinedNarration> pMutableList) {
    String query = "insert into DT_PREDEFINED_NARATION(ID, VOUCHER_ID, NARRATION, STAT_FLAG, STAT_UP_FLAG, MODIFIED_DATE, MODIFIED_BY, COMP_CODE) VALUES (?,?,?,?,?,?,?, ?)";
    mJdbcTemplate.batchUpdate(query, getCreateParams(pMutableList));
    return pMutableList.stream().map(pMutablePredefinedNarration -> pMutablePredefinedNarration.getId()).collect(Collectors.toList());
  }

  @Override
  public boolean exists(Long pId) {
    return super.exists(pId);
  }

  class PersistentPredefinedNarrationRowMapper implements RowMapper<PredefinedNarration> {
    @Override
    public PredefinedNarration mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MutablePredefinedNarration narration = new PersistentPredefinedNarration();
      narration.setId(pResultSet.getLong("id"));
      narration.setVoucherId(pResultSet.getLong("voucher_id"));
      narration.setStatFlag(pResultSet.getString("stat_flag"));
      narration.setNarration(pResultSet.getString("narration"));
      narration.setStatUpFlag(pResultSet.getString("stat_up_flag"));
      narration.setModifiedDate(pResultSet.getDate("modified_date"));
      narration.setModifiedBy(pResultSet.getString("modified_by"));
      narration.setCompanyId(pResultSet.getString("comp_code"));
      return narration;
    }
  }
}
