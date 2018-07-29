package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.EmpExamReserveDateDaoDecorator;
import org.ums.domain.model.immutable.EmpExamReserveDate;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;
import org.ums.generator.IdGenerator;

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
