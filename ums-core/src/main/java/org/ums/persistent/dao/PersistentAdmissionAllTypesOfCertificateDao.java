package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionAllTypesOfCertificateDaoDecorator;
import org.ums.domain.model.immutable.AdmissionAllTypesOfCertificate;
import org.ums.domain.model.mutable.MutableAdmissionAllTypesOfCertificate;
import org.ums.persistent.model.PersistentAdmissionAllTypesOfCertificate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by kawsu on 1/8/2017.
 */

public class PersistentAdmissionAllTypesOfCertificateDao extends AdmissionAllTypesOfCertificateDaoDecorator {

  static String GET_ALL = "SELECT ID, CERTIFICATE_NAME, CERTIFICATE_TYPE FROM ug_adm_certificates ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionAllTypesOfCertificateDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public List<AdmissionAllTypesOfCertificate> getAdmissionStudentCertificateLists() {
    String query = GET_ALL;
    return mJdbcTemplate.query(query, new Object[] {},
        new PersistentAdmissionAllTypesOfCertificateDao.AdmissionAllTypesOfCertificateRowMapper());
  }

  public List<AdmissionAllTypesOfCertificate> getCertificates(String pCertificateType) {
    String query = GET_ALL + "Where certificate_type=?";
    return mJdbcTemplate.query(query, new Object[] {pCertificateType}, new AdmissionAllTypesOfCertificateRowMapper());

  }

  class AdmissionAllTypesOfCertificateRowMapper implements RowMapper<AdmissionAllTypesOfCertificate> {
    @Override
    public AdmissionAllTypesOfCertificate mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MutableAdmissionAllTypesOfCertificate certificate = new PersistentAdmissionAllTypesOfCertificate();
      certificate.setCertificateId(pResultSet.getInt("ID"));
      certificate.setCertificateTitle(pResultSet.getString("CERTIFICATE_NAME"));
      certificate.setCertificateType(pResultSet.getString("CERTIFICATE_TYPE"));
      return certificate;
    }
  }
}
