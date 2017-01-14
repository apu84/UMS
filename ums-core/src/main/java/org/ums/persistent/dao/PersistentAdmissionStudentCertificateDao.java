package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionStudentCertificateDaoDecorator;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudentCertificate;
import org.ums.persistent.model.PersistentAdmissionStudentCertificate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by kawsu on 1/8/2017.
 */

public class PersistentAdmissionStudentCertificateDao extends
    AdmissionStudentCertificateDaoDecorator {

  static String GET_ALL =
      "SELECT CERTIFICATE_ID, CERTIFICATE_NAME, CERTIFICATE_TYPE, CERTIFICATE_CATEGORY FROM CERTIFICATES";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionStudentCertificateDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public List<AdmissionStudentCertificate> getAdmissionStudentCertificateLists() {
    String query = GET_ALL;
    return mJdbcTemplate.query(query, new Object[] {},
        new PersistentAdmissionStudentCertificateDao.AdmissionStudentCertificateRowMapper());
  }

  class AdmissionStudentCertificateRowMapper implements RowMapper<AdmissionStudentCertificate> {
    @Override
    public AdmissionStudentCertificate mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MutableAdmissionStudentCertificate certificate = new PersistentAdmissionStudentCertificate();
      certificate.setCertificateId(pResultSet.getInt("CERTIFICATE_ID"));
      certificate.setCertificateTitle(pResultSet.getString("CERTIFICATE_NAME"));
      certificate.setCertificateType(pResultSet.getString("CERTIFICATE_TYPE"));
      certificate.setCertificateCategory(pResultSet.getString("CERTIFICATE_CATEGORY"));
      return certificate;
    }
  }
}
