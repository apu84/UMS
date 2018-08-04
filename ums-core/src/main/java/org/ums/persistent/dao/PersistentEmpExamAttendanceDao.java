package org.ums.persistent.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.EmpExamAttendanceDaoDecorator;
import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentEmpExamAttendance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class PersistentEmpExamAttendanceDao extends EmpExamAttendanceDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentEmpExamAttendanceDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String INSERT_ALL =
      "Insert into DER_EMP_ATTENDANT (ID,SEMESTER_ID,EXAM_TYPE,IS_ROOM_IN_CHARGE,ROOM_ID,EMPLOYEE_ID) values (?,?,?,?,?,?)";
  String GET_BY_SEM_EXAM_TYPE =
      "SELECT a.ID,a.SEMESTER_ID,a.EXAM_TYPE,a.IS_ROOM_IN_CHARGE,a.ROOM_ID,a.EMPLOYEE_ID from DER_EMP_ATTENDANT a WHERE SEMESTER_ID=? AND EXAM_TYPE=?";
  String DELETE = "DELETE FROM DER_EMP_ATTENDANT WHERE ID=?";
  String GET_BY_ID =
      "SELECT a.ID,a.SEMESTER_ID,a.EXAM_TYPE,a.IS_ROOM_IN_CHARGE,a.ROOM_ID,a.EMPLOYEE_ID from DER_EMP_ATTENDANT a WHERE ID=?";
  String EXAM_ATTENDANCE =
      "SELECT ea.ID,ea.SEMESTER_ID,ea.EXAM_TYPE,ea.IS_ROOM_IN_CHARGE,ea.ROOM_ID,ea.EMPLOYEE_ID FROM DER_EMP_ATTENDANT ea,DER_EMP_EXAM_DATE_MAP emap WHERE emap.INVIGILATION_DATE= TO_DATE(?,'DD-MM-YYYY') AND "
          + "ea.ID=emap.ATTENDANT_ID AND ea.SEMESTER_ID=? AND EXAM_TYPE=?";
  String RESERVE_DAY =
      "SELECT ea.ID,ea.SEMESTER_ID,ea.EXAM_TYPE,ea.IS_ROOM_IN_CHARGE,ea.ROOM_ID,ea.EMPLOYEE_ID FROM DER_EMP_ATTENDANT ea,DER_EMP_RESERVE_DATE_MAP emap WHERE emap.RESERVE_DATE= TO_DATE(?,'DD-MM-YYYY') AND "
          + "ea.ID=emap.ATTENDANT_ID AND ea.SEMESTER_ID=? AND EXAM_TYPE=?";

  @Override
  public List<EmpExamAttendance> getInfoByInvigilatorDate(Integer pSemesterId, Integer pExamType, String pExamDate) {
    return mJdbcTemplate.query(EXAM_ATTENDANCE, new Object[] {pExamDate, pSemesterId, pExamType},
        new EmpExamAttendanceRowMapper());
  }

  @Override
  public List<EmpExamAttendance> getInfoByReserveDate(Integer pSemesterId, Integer pExamType, String pExamDate) {
    return mJdbcTemplate.query(RESERVE_DAY, new Object[] {pExamDate, pSemesterId, pExamType},
        new EmpExamAttendanceRowMapper());
  }

  @Override
  public EmpExamAttendance get(Long pId) {
    return mJdbcTemplate.queryForObject(GET_BY_ID, new Object[] {pId}, new EmpExamAttendanceRowMapper());
  }

  @Override
  public int delete(MutableEmpExamAttendance pMutable) {
    return mJdbcTemplate.update(DELETE, new Object[] {pMutable.getId()});
  }

  @Override
  public List<EmpExamAttendance> getInfoBySemesterAndExamType(Integer pSemesterId, Integer pExamType) {
    return mJdbcTemplate.query(GET_BY_SEM_EXAM_TYPE, new Object[] {pSemesterId, pExamType},
        new EmpExamAttendanceRowMapper());
  }

  @Override
  public Long create(MutableEmpExamAttendance pMutable) {
    mJdbcTemplate.update(INSERT_ALL, pMutable.getId(), pMutable.getSemesterId(), pMutable.getExamType(),
        pMutable.getRoomInCharge(), pMutable.getInvigilatorRoomId(), pMutable.getEmployeeId());
    return pMutable.getId();
  }
}


class EmpExamAttendanceRowMapper implements RowMapper<EmpExamAttendance> {
  @Override
  public EmpExamAttendance mapRow(ResultSet pResultSet, int pI) throws SQLException {
    PersistentEmpExamAttendance application = new PersistentEmpExamAttendance();
    application.setId(pResultSet.getLong("ID"));
    application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
    application.setExamType(pResultSet.getInt("EXAM_TYPE"));
    application.setRoomInCharge(pResultSet.getInt("IS_ROOM_IN_CHARGE"));
    application.setInvigilatorRoomId(pResultSet.getLong("ROOM_ID"));
    application.setEmployeeId(pResultSet.getString("EMPLOYEE_ID"));
    return application;
  }
}
