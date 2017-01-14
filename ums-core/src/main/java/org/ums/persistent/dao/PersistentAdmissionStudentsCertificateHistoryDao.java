package org.ums.persistent.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionStudentsCertificateHistoryDaoDecorator;
import org.ums.domain.model.immutable.AdmissionStudentsCertificateHistory;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateHistory;
import org.ums.persistent.model.PersistentAdmissionStudentsCertificateHistory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentAdmissionStudentsCertificateHistoryDao extends
    AdmissionStudentsCertificateHistoryDaoDecorator {

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionStudentsCertificateHistoryDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  String INSERT_ONE =
      "INSERT INTO DB_IUMS.STUDENTS_CERTIFICATE_HISTORY (SEMESTER_ID, RECEIPT_ID, CERTIFICATE_ID) VALUES (? ,? ,?)";

  public int saveAdmissionStudentsCertificates(
      List<MutableAdmissionStudentsCertificateHistory> pMutableAdmissionStudentsCertificateHistory) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query,
        getAdmissionStudentsCertificateHistoryParams(pMutableAdmissionStudentsCertificateHistory)).length;
  }

  private List<Object[]> getAdmissionStudentsCertificateHistoryParams(
      List<MutableAdmissionStudentsCertificateHistory> pMutableAdmissionStudentsCertificateHistory) {
    List<Object[]> params = new ArrayList<>();

    for(AdmissionStudentsCertificateHistory studentsCertificateHistory : pMutableAdmissionStudentsCertificateHistory) {
      params
          .add(new Object[] {studentsCertificateHistory.getSemesterId(),
              studentsCertificateHistory.getReceiptId(),
              studentsCertificateHistory.getCertificateId()});

    }
    return params;
  }

  public List<AdmissionStudentsCertificateHistory> getStudentsSavedCertificateLists(
      int pSemesterId, String pReceiptId) {
    String query =
        "select * from STUDENTS_CERTIFICATE_HISTORY WHERE SEMESTER_ID=? AND RECEIPT_ID=?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pReceiptId}, new RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<AdmissionStudentsCertificateHistory> {

    @Override
    public AdmissionStudentsCertificateHistory mapRow(ResultSet resultSet, int i)
        throws SQLException {
      MutableAdmissionStudentsCertificateHistory studentsCertificate =
          new PersistentAdmissionStudentsCertificateHistory();
      studentsCertificate.setSemesterId(resultSet.getInt("semester_id"));
      studentsCertificate.setReceiptId(resultSet.getString("receipt_id"));
      studentsCertificate.setCertificateId(resultSet.getInt("certificate_id"));
      return studentsCertificate;
    }
  }

}
