package org.ums.common.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;
import org.ums.manager.DepartmentManager;
import org.ums.manager.ProgramManager;
import org.ums.manager.SpStudentManager;
import org.ums.persistent.model.PersistentProgram;
import org.ums.persistent.model.PersistentSemester;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by My Pc on 4/21/2016.
 */

@Component
public class SeatPlanGroupBuilder implements Builder<SeatPlanGroup,MutableSeatPlanGroup> {

  @Autowired
  ProgramManager mProgramManager;

  @Autowired
  DepartmentManager mDepartmentManager;

  @Autowired
  SpStudentManager mSpStudentManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, SeatPlanGroup pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    pBuilder.add("id",pReadOnly.getId());
    pBuilder.add("semesterId",pReadOnly.getSemester().getId());
    pBuilder.add("programId",pReadOnly.getProgram().getId());
    pBuilder.add("programName",mProgramManager.get(pReadOnly.getProgram().getId()).getShortName());
    pBuilder.add("year",pReadOnly.getAcademicYear());
    pBuilder.add("semester",pReadOnly.getAcademicSemester());
    pBuilder.add("groupNo",pReadOnly.getGroupNo());
    pBuilder.add("type",pReadOnly.getExamType());
    pBuilder.add("studentNumber",mSpStudentManager.getStudentByProgramYearSemesterStatus(pReadOnly.getProgram().getId(),pReadOnly.getAcademicYear(),pReadOnly.getAcademicSemester(),1).size());
    String lastUpdated = pReadOnly.getLastUpdateDate();
    pBuilder.add("lastUpdated",pReadOnly.getLastUpdateDate());
  }

  @Override
  public void build(MutableSeatPlanGroup pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {
    PersistentSemester persistentSemester= new PersistentSemester();
    persistentSemester.setId(pJsonObject.getInt("semesterId"));
    pMutable.setSemester(persistentSemester);

    PersistentProgram program = new PersistentProgram();
    program.setId(pJsonObject.getInt("programId"));
    pMutable.setProgram(program);

    pMutable.setAcademicYear(pJsonObject.getInt("year"));
    pMutable.setAcademicSemester(pJsonObject.getInt("semester"));
    pMutable.setGroupNo(pJsonObject.getInt("groupNo"));
  }
}
