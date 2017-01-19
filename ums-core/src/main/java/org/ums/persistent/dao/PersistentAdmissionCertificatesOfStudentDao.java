package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionCertificatesOfStudentDaoDecorator;
import org.ums.domain.model.immutable.AdmissionCertificatesOfStudent;
import org.ums.domain.model.mutable.MutableAdmissionCertificatesOfStudent;
import org.ums.persistent.model.PersistentAdmissionCertificatesOfStudent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentAdmissionCertificatesOfStudentDao extends
    AdmissionCertificatesOfStudentDaoDecorator {

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionCertificatesOfStudentDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  String INSERT_ONE =
      "INSERT INTO DB_IUMS.ALL_CERTIFICATES_OF_STUDENTS (SEMESTER_ID, RECEIPT_ID, CERTIFICATE_ID) VALUES (? ,? ,?)";

  public int saveAdmissionStudentsCertificates(
      List<MutableAdmissionCertificatesOfStudent> pMutableAdmissionStudentsCertificateHistory) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query,
        getAdmissionStudentsCertificateHistoryParams(pMutableAdmissionStudentsCertificateHistory)).length;
  }

  private List<Object[]> getAdmissionStudentsCertificateHistoryParams(
      List<MutableAdmissionCertificatesOfStudent> pMutableAdmissionStudentsCertificateHistory) {
    List<Object[]> params = new ArrayList<>();

    for(AdmissionCertificatesOfStudent studentsCertificateHistory : pMutableAdmissionStudentsCertificateHistory) {
      params
          .add(new Object[] {studentsCertificateHistory.getSemesterId(),
              studentsCertificateHistory.getReceiptId(),
              studentsCertificateHistory.getCertificateId()});

    }
    return params;
  }

  public List<AdmissionCertificatesOfStudent> getStudentsSavedCertificateLists(int pSemesterId,
      String pReceiptId) {
    String query =
        "select * from ALL_CERTIFICATES_OF_STUDENTS WHERE SEMESTER_ID=? AND RECEIPT_ID=?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pReceiptId}, new RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<AdmissionCertificatesOfStudent> {

    @Override
    public AdmissionCertificatesOfStudent mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableAdmissionCertificatesOfStudent studentsCertificate =
          new PersistentAdmissionCertificatesOfStudent();
      studentsCertificate.setSemesterId(resultSet.getInt("semester_id"));
      studentsCertificate.setReceiptId(resultSet.getString("receipt_id"));
      studentsCertificate.setCertificateId(resultSet.getInt("certificate_id"));
      return studentsCertificate;
    }
  }

}
