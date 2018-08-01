package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.EmpExamInvigilatorDateDaoDecorator;
import org.ums.domain.model.immutable.EmpExamInvigilatorDate;
import org.ums.domain.model.mutable.MutableEmpExamInvigilatorDate;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentEmpExamInvigilatorDate;

import java.sql.ResultSet;
import java.sql.SQLException;
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
  String SELECT_ALL =
      "select ATTENDANT_ID,to_char(INVIGILATION_DATE,'DD-MM-YYYY')INVIGILATION_DATE from DER_EMP_EXAM_DATE_MAP";
  String DELETE = "DELETE FROM DER_EMP_EXAM_DATE_MAP WHERE ATTENDANT_ID=?";

  @Override
  public int delete(MutableEmpExamInvigilatorDate pMutable) {
    return mJdbcTemplate.update(DELETE, new Object[] {pMutable.getAttendantId()});
  }

  @Override
  public List<EmpExamInvigilatorDate> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new EmpExamInvRowMapper());
  }

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


class EmpExamInvRowMapper implements RowMapper<EmpExamInvigilatorDate> {
  @Override
  public EmpExamInvigilatorDate mapRow(ResultSet pResultSet, int pI) throws SQLException {
    PersistentEmpExamInvigilatorDate application = new PersistentEmpExamInvigilatorDate();
    application.setAttendantId(pResultSet.getLong("ATTENDANT_ID"));
    application.setExamDate(pResultSet.getString("INVIGILATION_DATE"));
    return application;
  }
}
