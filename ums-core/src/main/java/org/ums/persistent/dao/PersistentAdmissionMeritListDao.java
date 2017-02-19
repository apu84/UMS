package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AdmissionMeritListDaoDecorator;
import org.ums.domain.model.immutable.AdmissionMeritList;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableAdmissionMeritList;
import org.ums.enums.QuotaType;
import org.ums.persistent.model.PersistentAdmissionMeritList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Monjur-E-Morshed on 10-Dec-16.
 */
public class PersistentAdmissionMeritListDao extends AdmissionMeritListDaoDecorator {
  String SELECT_ONE =
      "select a.ID, a.SEMESTER_ID, a.MERIT_SL_NO, a.RECEIPT_ID, a.ADMISSION_ROLL, a.CANDIDATE_NAME, a.ADMISSION_GROUP, a.FACULTY_ID, a.LAST_MODIFIED from ADMISSION_MERIT_LIST a";
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

  @Override
  public List<AdmissionMeritList> getAll() {
    String query = SELECT_ONE;
    return mJdbcTemplate.query(query, new AdmissionRowMapper());
  }

  @Override
  public List<AdmissionMeritList> getMeritList(Semester pSemester, Faculty pFaculty,
      QuotaType pAdmissionGroup) {
    return super.getMeritList(pSemester, pFaculty, pAdmissionGroup);
  }

  @Override
  public List<Integer> create(List<MutableAdmissionMeritList> pMutableList) {
    int[] updates = mJdbcTemplate.batchUpdate(INSERT_ONE, getInsertParamList(pMutableList));
    return IntStream.of(updates).boxed().collect(Collectors.toList());
  }

  private List<Object[]> getInsertParamList(List<MutableAdmissionMeritList> pMeritLists) {
    List<Object[]> params = new ArrayList<>();

    for(AdmissionMeritList meritList : pMeritLists) {
      params.add(new Object[] {meritList.getSemester().getId(), meritList.getMeritListSerialNo(),
          meritList.getReceiptId(), meritList.getAdmissionRoll(), meritList.getCandidateName(),
          meritList.getAdmissionGroup().getId(), meritList.getFaculty().getId()});
    }
    return params;
  }

  class AdmissionRowMapper implements RowMapper<AdmissionMeritList> {
    @Override
    public AdmissionMeritList mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentAdmissionMeritList admission = new PersistentAdmissionMeritList();
      admission.setId(pResultSet.getInt("id"));
      admission.setSemesterId(pResultSet.getInt("semester_id"));
      admission.setMeritListSerialNo(pResultSet.getInt("merit_sl_no"));
      admission.setReceiptId(pResultSet.getInt("receipt_id"));
      admission.setAdmissionRoll(pResultSet.getInt("admission_roll"));
      admission.setCandidateName(pResultSet.getString("candidate_name"));
      admission.setAdmissionGroup(QuotaType.get(pResultSet.getInt("admission_group")));
      admission.setFacultyId(pResultSet.getInt("faculty_id"));
      admission.setLastModified(pResultSet.getString("last_modified"));
      return admission;
    }
  }
}
