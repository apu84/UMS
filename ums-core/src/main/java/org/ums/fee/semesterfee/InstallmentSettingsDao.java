package org.ums.fee.semesterfee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

public class InstallmentSettingsDao extends InstallmentSettingsDaoDecorator {
  String SELECT_ALL = "SELECT ID, SEMESTER_ID, IS_ENABLED, LAST_MODIFIED FROM INSTALLMENT_SETTINGS ";
  String INSERT_ALL = "INSERT INTO INSTALLMENT_SETTINGS(ID, SEMESTER_ID, IS_ENABLED, LAST_MODIFIED) VALUES (?,?, "
      + getLastModifiedSql() + ") ";
  String UPDATE_ALL = "UPDATE INSTALLMENT_SETTINGS SET SEMESTER_ID = ?, IS_ENABLED = ? ";
  String DELETE_ALL = "DELETE FROM INSTALLMENT_SETTINGS ";
  String EXISTS = "SELECT COUNT(ID) EXISTS FROM INSTALLMENT_SETTINGS ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public InstallmentSettingsDao(JdbcTemplate mJdbcTemplate, IdGenerator mIdGenerator) {
    this.mJdbcTemplate = mJdbcTemplate;
    this.mIdGenerator = mIdGenerator;
  }

  @Override
  public List<InstallmentSettings> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new InstallmentSettingsRowMapper());
  }

  @Override
  public InstallmentSettings get(Long pId) {
    String query = SELECT_ALL + "WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new InstallmentSettingsRowMapper());
  }

  @Override
  public int update(MutableInstallmentSettings pMutable) {
    String query = UPDATE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, new Object[] {pMutable.getSemesterId(), pMutable.isEnabled(), pMutable.getId()},
        new InstallmentSettingsRowMapper());
  }

  @Override
  public int delete(MutableInstallmentSettings pMutable) {
    String query = DELETE_ALL + "WHERE ID = ?";
    return mJdbcTemplate.update(query, new Object[] {pMutable.getId()}, new InstallmentSettingsRowMapper());
  }

  @Override
  public Long create(MutableInstallmentSettings pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getSemester().getId(), pMutable.isEnabled());
    return id;
  }

  @Override
  public Optional<InstallmentSettings> getInstallmentSettings(Integer pSemesterId) {
    String exists = EXISTS + "WHERE SEMESTER_ID = ?";
    boolean settingsExists = mJdbcTemplate.queryForObject(exists, new Object[] {pSemesterId}, Boolean.class);
    if(settingsExists) {
      String query = SELECT_ALL + "WHERE SEMESTER_ID = ?";
      return Optional.of(mJdbcTemplate.queryForObject(query, new Object[] {pSemesterId},
          new InstallmentSettingsRowMapper()));
    }
    return Optional.empty();
  }

  class InstallmentSettingsRowMapper implements RowMapper<InstallmentSettings> {
    @Override
    public InstallmentSettings mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableInstallmentSettings installmentSettings = new PersistentInstallmentSettings();
      installmentSettings.setId(rs.getLong("ID"));
      installmentSettings.setSemesterId(rs.getInt("SEMESTER_ID"));
      installmentSettings.setEnabled(rs.getBoolean("IS_ENABLED"));
      installmentSettings.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<InstallmentSettings> reference = new AtomicReference<>(installmentSettings);
      return reference.get();
    }
  }
}
