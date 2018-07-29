package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.EmpExamAttendanceDaoDecorator;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;
import org.ums.generator.IdGenerator;

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

  @Override
  public Long create(MutableEmpExamAttendance pMutable) {
    mJdbcTemplate.update(INSERT_ALL, pMutable.getId(), pMutable.getSemesterId(), pMutable.getExamType(),
        pMutable.getRoomInCharge(), pMutable.getInvigilatorRoomId(), pMutable.getEmployeeId());
    return pMutable.getId();
  }
}
