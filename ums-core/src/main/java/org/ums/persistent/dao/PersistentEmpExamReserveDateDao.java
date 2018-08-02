package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.EmpExamReserveDateDaoDecorator;
import org.ums.domain.model.immutable.EmpExamReserveDate;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentEmpExamReserveDate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class PersistentEmpExamReserveDateDao extends EmpExamReserveDateDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentEmpExamReserveDateDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String INSERT_ALL =
      "Insert into DER_EMP_RESERVE_DATE_MAP (ID,ATTENDANT_ID,RESERVE_DATE) values (?,?,TO_DATE(?,'DD-MM-YYYY'))";
  String SELECT_ALL =
      "select ATTENDANT_ID,to_char(RESERVE_DATE,'DD-MM-YYYY')RESERVE_DATE from DER_EMP_RESERVE_DATE_MAP";
  String DELETE = "DELETE FROM DER_EMP_RESERVE_DATE_MAP WHERE ATTENDANT_ID=?";
  String SELECT_BY_SEM_EXAM_TYPE =
      "SELECT emap.ATTENDANT_ID,to_char(emap.RESERVE_DATE,'DD-MM-YYYY') RESERVE_DATE FROM DER_EMP_ATTENDANT ea,DER_EMP_RESERVE_DATE_MAP emap WHERE "
          + "ea.ID=emap.ATTENDANT_ID AND ea.SEMESTER_ID=? AND EXAM_TYPE=?";

  @Override
  public List<EmpExamReserveDate> getBySemesterAndExamType(Integer pSemesterId, Integer pExamType) {
    return mJdbcTemplate.query(SELECT_BY_SEM_EXAM_TYPE, new Object[] {pSemesterId, pExamType},
        new EmpExamReserveDateRowMapper());
  }

  @Override
  public int delete(MutableEmpExamReserveDate pMutable) {
    mJdbcTemplate.update(DELETE, new Object[] {pMutable.getAttendantId()});
    return 1;
  }

  @Override
  public List<EmpExamReserveDate> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new EmpExamReserveDateRowMapper());
  }

  @Override
  public List<Long> create(List<MutableEmpExamReserveDate> pMutableList) {
    List<Object[]>  parameters  =  getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL,  parameters);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableEmpExamReserveDate> pMutableEmpExamReserveDate) {
    List<Object[]> params = new ArrayList<>();
    for(EmpExamReserveDate app : pMutableEmpExamReserveDate) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getAttendantId(), app.getExamDate()});
    }
    return params;
  }
}


class EmpExamReserveDateRowMapper implements RowMapper<EmpExamReserveDate> {
  @Override
  public EmpExamReserveDate mapRow(ResultSet pResultSet, int pI) throws SQLException {
    PersistentEmpExamReserveDate application = new PersistentEmpExamReserveDate();
    application.setAttendantId(pResultSet.getLong("ATTENDANT_ID"));
    application.setExamDate(pResultSet.getString("RESERVE_DATE"));
    return application;
  }
}
