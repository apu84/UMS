package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.UgAdmissionLogDaoDecorator;
import org.ums.domain.model.immutable.UgAdmissionLog;
import org.ums.domain.model.mutable.MutableUgAdmissionLog;
import org.ums.persistent.model.PersistentUgAdmissionLog;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Monjur-E-Morshed on 02-Jan-17.
 */
public class PersistentUgAdmissionLogDao extends UgAdmissionLogDaoDecorator {

  private final String INSERT_ONE =
      "INSERT INTO UG_ADMISSION_LOG (RECEIPT_ID, SEMESTER_ID, LOG_TEXT, ACTOR, INSERTED_ON) "
          + "VALUES (?, ?, ?, ?, sysdate)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentUgAdmissionLogDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int create(MutableUgAdmissionLog pMutable) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutable.getReceiptId(), pMutable.getSemesterId(),
        pMutable.getLogText());
  }

  class UgAdmissionRowMapper implements RowMapper<UgAdmissionLog> {
    @Override
    public UgAdmissionLog mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentUgAdmissionLog log = new PersistentUgAdmissionLog();
      log.setReceiptId(pResultSet.getString("receipt_id"));
      log.setSemesterId(pResultSet.getInt("semester_id"));
      log.setLogText(pResultSet.getString("log_text"));
      log.setActorId(pResultSet.getString("actor"));
      log.setInsertionDate(pResultSet.getString("inserted_on"));
      return log;
    }
  }
}
