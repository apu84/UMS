package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionCommentForStudentDaoDecorator;
import org.ums.domain.model.immutable.AdmissionCommentForStudent;
import org.ums.domain.model.mutable.MutableAdmissionCommentForStudent;
import org.ums.persistent.model.PersistentAdmissionCommentForStudent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PersistentAdmissionCommentForStudentDao extends AdmissionCommentForStudentDaoDecorator {

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionCommentForStudentDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  String INSERT_ONE =
      "INSERT INTO COMMENTS_FOR_STUDENTS (SEMESTER_ID, RECEIPT_ID, COMMENTS, COMMENTED_ON) VALUES (? ,? ,?, sysdate)";

  String GET_COMMENTS =
      "SELECT SEMESTER_ID, RECEIPT_ID, COMMENTS, COMMENTED_ON FROM COMMENTS_FOR_STUDENTS ";

  public List<AdmissionCommentForStudent> getComments(final int pSemesterId, final String pReceiptId) {

    String query = GET_COMMENTS + "WHERE SEMESTER_ID=? AND RECEIPT_ID=?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pReceiptId}, new RoleRowMapper());
  }

  @Override
  public int saveComment(
      MutableAdmissionCommentForStudent pMutableAdmissionStudentsCertificateComment) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutableAdmissionStudentsCertificateComment.getSemesterId(),
        pMutableAdmissionStudentsCertificateComment.getReceiptId(),
        pMutableAdmissionStudentsCertificateComment.getComment());
  }

  class RoleRowMapper implements RowMapper<AdmissionCommentForStudent> {

    @Override
    public AdmissionCommentForStudent mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableAdmissionCommentForStudent studentsCertificate =
          new PersistentAdmissionCommentForStudent();
      studentsCertificate.setSemesterId(resultSet.getInt("semester_id"));
      studentsCertificate.setReceiptId(resultSet.getString("receipt_id"));
      studentsCertificate.setComment(resultSet.getString("comments"));
      studentsCertificate.setCommentedOn(resultSet.getString("commented_on"));
      return studentsCertificate;
    }
  }

}
