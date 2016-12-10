package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionMeritListDaoDecorator;
import org.ums.domain.model.immutable.AdmissionMeritList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Monjur-E-Morshed on 10-Dec-16.
 */
public class PersistentAdmissionMeritListDao extends AdmissionMeritListDaoDecorator {
  String SELECT_ONE =
      "select a.ID, a.SEMESTER_ID, a.MERIT_SL_NO, a.RECEIPT_ID, a.ADMISSION_ROLL, a.CANDIDATE_NAME, a.ADMISSION_GROUP, a.LAST_MODIFIED from ADMISSION_MERIT_LIST a";
  String INSERT_ONE = "Insert into DB_IUMS.ADMISSION_MERIT_LIST "
      + "   ( SEMESTER_ID, MERIT_SL_NO, RECEIPT_ID, ADMISSION_ROLL,  "
      + "    CANDIDATE_NAME, ADMISSION_GROUP, FACULTY_ID, LAST_MODIFIED) " + " Values "
      + "   ( ?, ?, ?, ?,  " + "    ?, ?, ?, " + getLastModifiedSql() + ")";
  String UPDATE_ONE =
      "update ADMISSION_MERIT_LIST SET SEMESTER_ID=?, MERIT_SL_NO=?, RECEIPT_ID=?,ADMISSION_ROLL=?, CANDIDATE_NAME=?, ADMISSION_GROUP=?, FACULTY_ID=?, LAST_MODIFIED=?"
          + getLastModifiedSql() + " WHERE ID=?";
  String DELETE_ONE = "DELETE FROM ADMISSION_MERIT_LIST ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionMeritListDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  class AdmissionRowMapper implements RowMapper<AdmissionMeritList> {
    @Override
    public AdmissionMeritList mapRow(ResultSet pResultSet, int pI) throws SQLException {
      return null;
    }
  }
}
