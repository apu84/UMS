package org.ums.persistent.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AbsLateComingInfoDaoDecorator;
import org.ums.domain.model.immutable.AbsLateComingInfo;
import org.ums.domain.model.mutable.MutableAbsLateComingInfo;
import org.ums.generator.IdGenerator;
import org.ums.persistent.dao.PersistentAbsLateComingInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public class PersistentAbsLateComingInfoDao extends AbsLateComingInfoDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAbsLateComingInfoDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String INSERT_ALL =
      "Insert into DER_ABSENT_LATE_COMING_INFO (ID,SEMESTER_ID,EXAM_TYPE,PRESENT_TYPE,REMARKS,EMPLOYEE_ID,INVIGILATOR_ROOM_ID,EXAM_DATE,ARRIVAL_TIME) values (?,?,?,?,?,?,?,TO_DATE(?,'DD-MM-YYYY'),?)";

  String SELECT_ALL =
      "select  SEMESTER_ID,EXAM_TYPE,PRESENT_TYPE,REMARKS,EMPLOYEE_ID,INVIGILATOR_ROOM_ID,to_char(EXAM_DATE,'DD-MM-YYYY') EXAM_DATE,ARRIVAL_TIME from DER_ABSENT_LATE_COMING_INFO";

  String SEM_EXAM_TYPE_DATE_WISE_RECORDS =
      "select  SEMESTER_ID,EXAM_TYPE,PRESENT_TYPE,REMARKS,EMPLOYEE_ID,INVIGILATOR_ROOM_ID,to_char(EXAM_DATE,'DD-MM-YYYY') EXAM_DATE,ARRIVAL_TIME from DER_ABSENT_LATE_COMING_INFO WHERE  SEMESTER_ID=? AND EXAM_TYPE=? "
          + " AND EXAM_DATE = TO_DATE(?,'DD-MM-YYYY')";
  String DELETE_ALL = "DELETE FROM DER_ABSENT_LATE_COMING_INFO";
  String SELECT_RECORDS =
      "select  SEMESTER_ID,EXAM_TYPE,PRESENT_TYPE,REMARKS,EMPLOYEE_ID,INVIGILATOR_ROOM_ID,to_char(EXAM_DATE,'DD-MM-YYYY') EXAM_DATE,ARRIVAL_TIME "
          + "from DER_ABSENT_LATE_COMING_INFO WHERE  SEMESTER_ID=? AND EXAM_TYPE=?";

  @Override
  public List<AbsLateComingInfo> getInfoBySemesterExamType(Integer pSemesterId, Integer pExamType) {
    return mJdbcTemplate.query(SELECT_RECORDS, new Object[] {pSemesterId, pExamType}, new AbsLateComingRowMapper());
  }

  @Override
  public List<AbsLateComingInfo> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new AbsLateComingRowMapper());
  }

  @Override
  public List<AbsLateComingInfo> getInfoBySemesterExamTypeAndExamDate(Integer pSemesterId, Integer pExamType,
      String pExamDate) {
    return mJdbcTemplate.query(SEM_EXAM_TYPE_DATE_WISE_RECORDS, new Object[] {pSemesterId, pExamType, pExamDate},
        new AbsLateComingRowMapper());
  }

  @Override
  public Long create(MutableAbsLateComingInfo pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pMutable.getSemesterId(), pMutable.getExamType(), pMutable.getPresentType(),
        pMutable.getRemarks(), pMutable.getEmployeeId(), pMutable.getInvigilatorRoomId(), pMutable.getExamDate(),
        pMutable.getArrivalTime() == "null" ? null : pMutable.getArrivalTime());
    return id;
  }

  public int delete(List<MutableAbsLateComingInfo> pMutableList) {
    String query =
        DELETE_ALL + " WHERE  SEMESTER_ID=? AND EXAM_TYPE=? AND EMPLOYEE_ID=? AND EXAM_DATE=TO_DATE(?,'DD-MM-YYYY')";
    List<Object[]> parameters = deleteParamList(pMutableList);
    return mJdbcTemplate.batchUpdate(query, parameters).length;
  }

  private List<Object[]> deleteParamList(List<MutableAbsLateComingInfo> pMutableApplicationTES) {
    List<Object[]> params = new ArrayList<>();
    for(AbsLateComingInfo app : pMutableApplicationTES) {
      params.add(new Object[] {app.getSemesterId(), app.getExamType(), app.getEmployeeId(), app.getExamDate()});
    }
    return params;
  }

  class AbsLateComingRowMapper implements RowMapper<AbsLateComingInfo> {
    @Override
    public AbsLateComingInfo mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentAbsLateComingInfo application = new PersistentAbsLateComingInfo();
      application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      application.setExamType(pResultSet.getInt("EXAM_TYPE"));
      application.setPresentType(pResultSet.getInt("PRESENT_TYPE"));
      application.setRemarks(pResultSet.getString("REMARKS"));
      application.setEmployeeId(pResultSet.getString("EMPLOYEE_ID"));
      application.setInvigilatorRoomId(pResultSet.getLong("INVIGILATOR_ROOM_ID"));
      application.setExamDate(pResultSet.getString("EXAM_DATE"));
      application.setArrivalTime(pResultSet.getString("ARRIVAL_TIME"));
      return application;
    }
  }
}
