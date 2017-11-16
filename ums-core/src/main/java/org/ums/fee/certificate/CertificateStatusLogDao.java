package org.ums.fee.certificate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.generator.IdGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 14-Nov-17.
 */
public class CertificateStatusLogDao extends CertificateStatusLogDaoDecorator {

  String SELECT_ALL = "SELECT CERTIFICATE_STATUS_ID, STATUS, PROCESSED_ON, PROCESSED_BY FROM CERTIFICATE_STATUS_LOG";
  String INSERT_ALL =
      "INSERT INTO CERTIFICATE_STATUS_LOG(CERTIFICATE_STATUS_ID,STATUS,PROCESSED_ON,PROCESSED_BY) VALUES(?,?,?,?)";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public CertificateStatusLogDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long create(MutableCertificateStatusLog pMutable) {
    String query = INSERT_ALL;
    mJdbcTemplate.update(query, pMutable.getCertificateStatusId(), pMutable.getStatus().getId(),
        pMutable.getProcessedOn(), pMutable.getProcessedBy());
    return null;
  }

  @Override
  public List<Long> create(List<MutableCertificateStatusLog> pMutableList) {
    String query = INSERT_ALL;
    mJdbcTemplate.batchUpdate(query, getLogParams(pMutableList));
    return null;
  }

  private List<Object[]> getLogParams(List<MutableCertificateStatusLog> pLogs) {
    List<Object[]> params = new ArrayList<>();

    for(CertificateStatusLog log : pLogs) {
      params.add(new Object[] {log.getCertificateStatusId(), log.getStatus().getId(), log.getProcessedOn(),
          log.getProcessedBy()});
    }
    return params;
  }

  @Override
  public List<CertificateStatusLog> getByStatus(CertificateStatus.Status pStatus) {
    String query = SELECT_ALL + " WHERE STATUS=?";
    return mJdbcTemplate.query(query, new Object[] {pStatus.getId()}, new CertificateStatusLogRowMapper());
  }

  class CertificateStatusLogRowMapper implements RowMapper<CertificateStatusLog> {
    @Override
    public CertificateStatusLog mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableCertificateStatusLog log = new PersistentCertificateStatusLog();
      log.setCertificateStatusId(rs.getLong("CERTIFICATE_STATUS_ID"));
      log.setStatus(CertificateStatus.Status.get(rs.getInt("STATUS")));
      log.setProcessedOn(rs.getDate("PROCESSED_ON"));
      log.setProcessedBy(rs.getString("PROCESSED_BY"));
      return log;
    }
  }
}
