package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionStudentsCertificateCommentDaoDecorator;
import org.ums.domain.model.immutable.AdmissionStudentsCertificateComment;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateComment;
import org.ums.persistent.model.PersistentAdmissionStudentsCertificateComment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by kawsu on 1/12/2017.
 */
public class PersistentAdmissionStudentsCertificateCommentDao extends
    AdmissionStudentsCertificateCommentDaoDecorator {

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionStudentsCertificateCommentDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  String INSERT_ONE =
      "INSERT INTO STUDENTS_CERTIFICATE_COMMENT (SEMESTER_ID, RECEIPT_ID, COMMENTS) VALUES (? ,? ,?)";

  String GET_COMMENTS =
      "SELECT ROW_ID, SEMESTER_ID, RECEIPT_ID, COMMENTS FROM STUDENTS_CERTIFICATE_COMMENT ";

  public int create(
      MutableAdmissionStudentsCertificateComment pMutableAdmissionStudentsCertificateComment) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutableAdmissionStudentsCertificateComment.getSemesterId(),
        pMutableAdmissionStudentsCertificateComment.getReceiptId(),
        pMutableAdmissionStudentsCertificateComment.getComment());
  }

  public List<AdmissionStudentsCertificateComment> getComments(final int pSemesterId,
      final String pReceiptId) {

    String query = GET_COMMENTS + "WHERE SEMESTER_ID=? AND RECEIPT_ID=?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pReceiptId}, new RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<AdmissionStudentsCertificateComment> {

    @Override
    public AdmissionStudentsCertificateComment mapRow(ResultSet resultSet, int i)
        throws SQLException {
      MutableAdmissionStudentsCertificateComment studentsCertificate =
          new PersistentAdmissionStudentsCertificateComment();
      studentsCertificate.setSemesterId(resultSet.getInt("semester_id"));
      studentsCertificate.setReceiptId(resultSet.getString("receipt_id"));
      studentsCertificate.setComment(resultSet.getString("comments"));
      return studentsCertificate;
    }
  }

}
