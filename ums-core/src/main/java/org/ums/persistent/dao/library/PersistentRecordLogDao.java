package org.ums.persistent.dao.library;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.library.RecordLogDaoDecorator;
import org.ums.domain.model.immutable.library.RecordLog;
import org.ums.domain.model.mutable.library.MutableRecordLog;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.library.PersistentRecordLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentRecordLogDao extends RecordLogDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO RECORDS_LOG (ID, MFN, MODIFIED_BY, MODIFIED_ON, MODIFICATION_TYPE, PREVIOUS_JSON, MODIFIED_JSON, LAST_MODIFIED) VALUES(?, ?, ?, ?, ?, ?, ?, "
          + getLastModifiedSql() + " ) ";

  static String SELECT_ALL =
      "SELECT ID, MFN, MODIFIED_BY, MODIFIED_ON, MODIFICATION_TYPE, PREVIOUS_JSON, MODIFIED_JSON, LAST_MODIFIED FROM RECORDS_LOG ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentRecordLogDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<RecordLog> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new PersistentRecordLogDao.RecordLogRowMapper());
  }

  @Override
  public RecordLog get(final Long pId) {
    String query = SELECT_ALL + " WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentRecordLogDao.RecordLogRowMapper());
  }

  @Override
  public List<RecordLog> get(final String pClause) {
    String query = SELECT_ALL + pClause + " ORDER BY MODIFIED_ON DESC";
    return mJdbcTemplate.query(query, new PersistentRecordLogDao.RecordLogRowMapper());
  }

  @Override
  public Long create(MutableRecordLog pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getMfn(), pMutable.getModifiedBy(), pMutable.getModifiedOn(),
        pMutable.getModificationType(), pMutable.getPreviousJson(), pMutable.getModifiedJson());
    return id;
  }

  class RecordLogRowMapper implements RowMapper<RecordLog> {

    @Override
    public RecordLog mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentRecordLog persistentRecordLog = new PersistentRecordLog();
      persistentRecordLog.setId(rs.getLong("ID"));
      persistentRecordLog.setMfn(rs.getLong("MFN"));
      persistentRecordLog.setModifiedBy(rs.getString("MODIFIED_BY"));
      persistentRecordLog.setModifiedOn(rs.getDate("MODIFIED_ON"));
      persistentRecordLog.setModificationType(rs.getInt("MODIFICATION_TYPE"));
      persistentRecordLog.setPreviousJson(rs.getString("PREVIOUS_JSON"));
      persistentRecordLog.setModifiedJson(rs.getString("MODIFIED_JSON"));
      persistentRecordLog.setLastModified(rs.getString("LAST_MODIFIED"));
      return persistentRecordLog;
    }
  }
}
