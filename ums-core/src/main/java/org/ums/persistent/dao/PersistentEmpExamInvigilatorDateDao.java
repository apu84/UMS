package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.EmpExamInvigilatorDateDaoDecorator;
import org.ums.domain.model.immutable.EmpExamInvigilatorDate;
import org.ums.domain.model.mutable.MutableEmpExamInvigilatorDate;
import org.ums.generator.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
public class PersistentEmpExamInvigilatorDateDao extends EmpExamInvigilatorDateDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentEmpExamInvigilatorDateDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String INSERT_ALL =
      "Insert into DER_EMP_EXAM_DATE_MAP (ID,ATTENDANT_ID,INVIGILATION_DATE) values (?,?,TO_DATE(?,'DD-MM-YYYY'))";

  @Override
  public List<Long> create(List<MutableEmpExamInvigilatorDate> pMutableList) {
    List<Object[]>  parameters  =  getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL,  parameters);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableEmpExamInvigilatorDate> pMutableEmpExamInvDate) {
    List<Object[]> params = new ArrayList<>();
    for(EmpExamInvigilatorDate app : pMutableEmpExamInvDate) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getAttendantId(), app.getExamDate()});
    }
    return params;
  }
}
