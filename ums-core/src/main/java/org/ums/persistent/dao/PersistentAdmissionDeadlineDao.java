package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionDeadlineDaoDecorator;
import org.ums.domain.model.immutable.AdmissionDeadline;
import org.ums.domain.model.mutable.MutableAdmissionDeadline;
import org.ums.persistent.model.PersistentAdmissionDeadline;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Monjur-E-Morshed on 29-Dec-16.
 */
public class PersistentAdmissionDeadlineDao extends AdmissionDeadlineDaoDecorator {

  String INSERT_ONE =
      "INSERT INTO ADMISSION_DEADLINE (SEMESTER_ID, MERIT_START_RANGE, MERIT_LIMIT_RANGE, START_DATE, END_DATE, LAST_MODIFIED) "
          + "VALUES (?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'),"
          + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;

  public PersistentAdmissionDeadlineDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int create(MutableAdmissionDeadline pMutable) {
    return super.create(pMutable);
  }

  @Override
  public int create(List<MutableAdmissionDeadline> pMutableList) {
    String query = INSERT_ONE;
    return mJdbcTemplate.batchUpdate(query, getAdmissionDeadlineParams(pMutableList)).length;
  }

  @Override
  public int update(MutableAdmissionDeadline pMutable) {
    String query =
        "UPDATE ADMISSION-DEADLINE SET MERIT_START_RANGE=?, MERIT_LIMIT_RANGE=?, START_DATE=TO_DATE(?,'DD/MM/YYYY'), END_DATE=(?,'DD/MM/YYYY')";
    return super.update(pMutable);
  }

  @Override
  public int update(List<MutableAdmissionDeadline> pMutableList) {
    String query =
        "UPDATE ADMISSION-DEADLINE SET SEMESTER_ID=?, MERIT_START_RANGE=?, MERIT_LIMIT_RANGE=?, START_DATE=TO_DATE(?,'DD/MM/YYYY'), END_DATE=(?,'DD/MM/YYYY') where id=?";
    return mJdbcTemplate.batchUpdate(query, getAdmissionDeadlineParams(pMutableList)).length;
  }

  private List<Object[]> getAdmissionDeadlineParams(List<MutableAdmissionDeadline> pDeadlines) {
    List<Object[]> params = new ArrayList<>();

    for(AdmissionDeadline deadline : pDeadlines) {
      params.add(new Object[] {deadline.getSemesterId(), deadline.getMeritListStartNo(),
          deadline.getMeritListEndNo(), deadline.getStartDate(), deadline.getEndDate()});
    }
    return params;
  }

  private List<Object[]> getAdmissionDeadlineUpdateParams(List<MutableAdmissionDeadline> pDeadlines) {
    List<Object[]> params = new ArrayList<>();

    for(AdmissionDeadline deadline : pDeadlines) {
      params.add(new Object[] {deadline.getSemesterId(), deadline.getMeritListStartNo(),
          deadline.getMeritListEndNo(), deadline.getStartDate(), deadline.getEndDate(),
          deadline.getId()});
    }
    return params;
  }

  class AdmissionDeadlineRowMapper implements RowMapper<AdmissionDeadline> {
    @Override
    public AdmissionDeadline mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentAdmissionDeadline persistentAdmissionDeadline = new PersistentAdmissionDeadline();
      persistentAdmissionDeadline.setSemesterId(pResultSet.getInt("semester_id"));
      persistentAdmissionDeadline.setMeritListStartNo(pResultSet.getInt("merit_start_range"));
      persistentAdmissionDeadline.setMeritListEndNo(pResultSet.getInt("merit_limit_range"));
      persistentAdmissionDeadline.setStartDate(pResultSet.getString("start_date"));
      persistentAdmissionDeadline.setEndDate(pResultSet.getString("end_date"));
      persistentAdmissionDeadline.setLastModified(pResultSet.getString("last_modified"));
      return persistentAdmissionDeadline;
    }
  }
}
