package org.ums.persistent.dao.optCourse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.optCourse.OptSeatAllocationDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptSeatAllocation;
import org.ums.domain.model.mutable.optCourse.MutableOptSeatAllocation;
import org.ums.enums.ProgramType;
import org.ums.enums.common.DepartmentType;
import org.ums.generator.IdGenerator;
import org.ums.manager.EmployeeManager;
import org.ums.persistent.model.optCourse.PersistentOptSeatAllocation;
import org.ums.usermanagement.user.UserManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
public class PersistentOptSeatAllocationDao extends OptSeatAllocationDaoDecorator {

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentOptSeatAllocationDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String INSERT_GROUP = "MERGE INTO OPT_GROUP_SEAT_ALLOCATION D " + "          using (select 1 from DUAL) s "
      + "               on (D.GROUP_ID=?) " + "          WHEN MATCHED THEN UPDATE SET D.SEAT=? "
      + "           WHEN NOT MATCHED THEN " + "           INSERT (ID,GROUP_ID,SEAT) " + "            VALUES (?,?,?)";
  String INSERT_SUB_GROUP = "MERGE INTO OPT_SUB_GROUP_SEAT_ALLOCATION D " + "          using (select 1 from DUAL) s "
      + "               on (D.GROUP_ID=?) " + "          WHEN MATCHED THEN UPDATE SET D.SEAT=? "
      + "           WHEN NOT MATCHED THEN " + "           INSERT (ID,GROUP_ID,SEAT) " + "            VALUES (?,?,?)";

  String GET_GROUP_INFO =
      "SELECT b.GROUP_ID,b.SEAT FROM OPT_GROUP a,OPT_GROUP_SEAT_ALLOCATION b WHERE a.SEMESTER_ID=? AND a.PROGRAM_ID=? AND a.\"YEAR\"=? AND a.SEMESTER=? AND a.ID=b.GROUP_ID";
  String GET_SUB_GROUP_INFO =
      "SELECT c.GROUP_ID,c.SEAT FROM OPT_GROUP a,OPT_GROUP_SUB_GROUP_MAP b,OPT_SUB_GROUP_SEAT_ALLOCATION c  "
          + "WHERE a.SEMESTER_ID=? AND a.PROGRAM_ID=? AND a.\"YEAR\"=? AND a.SEMESTER=? AND a.ID=b.GROUP_ID AND c.GROUP_ID=b.SUB_GROUP_ID";

  @Override
  public List<OptSeatAllocation> getInfoBySemesterId(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {
    String query = "";
    query = pProgramId != 110500 ? GET_GROUP_INFO : GET_SUB_GROUP_INFO;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramId, pYear, pSemester},
        new OptSeatAllocationRowMapper());
  }

  @Override
    public List<Long> create(List<MutableOptSeatAllocation> pMutableList) {
    String  deptId="";
       for(MutableOptSeatAllocation app: pMutableList){
         deptId=app.getDepartmentId();
         break;
       }
        String query=deptId.equals(DepartmentType.EEE.getId()) ? INSERT_SUB_GROUP : INSERT_GROUP;
        List<Object[]>  parameters  =  getInsertParamList(pMutableList);
        mJdbcTemplate.batchUpdate(query,  parameters);
        return  parameters.stream()
                .map(paramArray  ->  (Long)  paramArray[0])
                .collect(Collectors.toCollection(ArrayList::new));
    }

  private List<Object[]> getInsertParamList(List<MutableOptSeatAllocation> pOptOfferedGroup) {
    List<Object[]> params = new ArrayList<>();
    for(OptSeatAllocation app : pOptOfferedGroup) {
      params.add(new Object[] {app.getGroupID(), app.getSeatNumber(), mIdGenerator.getNumericId(), app.getGroupID(),
          app.getSeatNumber()});
    }

    return params;
  }

}


class OptSeatAllocationRowMapper implements RowMapper<OptSeatAllocation> {
  @Override
  public OptSeatAllocation mapRow(ResultSet pResultSet, int pI) throws SQLException {
    PersistentOptSeatAllocation application = new PersistentOptSeatAllocation();
    application.setGroupID(pResultSet.getLong("GROUP_ID"));
    application.setSeatNumber(pResultSet.getInt("SEAT"));
    return application;
  }
}
