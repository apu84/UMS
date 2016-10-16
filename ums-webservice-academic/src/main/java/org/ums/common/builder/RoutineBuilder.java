package org.ums.common.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.mutable.MutableRoutine;
import org.ums.domain.model.immutable.Routine;
import org.ums.manager.EmployeeManager;
import org.ums.manager.ProgramManager;
import org.ums.manager.UserManager;
import org.ums.persistent.model.PersistentProgram;
import org.ums.persistent.model.PersistentSemester;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class RoutineBuilder implements Builder<Routine, MutableRoutine> {

  @Autowired
  UserManager mUserManager;

  @Autowired
  EmployeeManager mEmployeeManager;

  @Autowired
  ProgramManager mProgramManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, Routine pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    if(pReadOnly.getId()!=null)
    pBuilder.add("id", pReadOnly.getId());
    if(pReadOnly.getSemester().getId()!=null)
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    if(pReadOnly.getCourseId()!=null)
    pBuilder.add("courseId", pReadOnly.getCourseId());
    if(pReadOnly.getProgram().getId()!=null)
    pBuilder.add("programId", pReadOnly.getProgram().getId());

    pBuilder.add("day", pReadOnly.getDay());
    pBuilder.add("section", pReadOnly.getSection());
    pBuilder.add("academicYear", pReadOnly.getAcademicYear());
    pBuilder.add("academicSemester", pReadOnly.getAcademicSemester());
    pBuilder.add("startTime", pReadOnly.getStartTime());
    pBuilder.add("endTime", pReadOnly.getEndTime());
    pBuilder.add("duration", pReadOnly.getDuration());
    pBuilder.add("roomNo", pReadOnly.getRoomNo());
    if(pReadOnly.getCourseNo()!=null){
      pBuilder.add("courseNo",pReadOnly.getCourseNo());
    }
    //pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("routine").path(pReadOnly.getId().toString()).build().toString());
  }

  @Override
  public void build(MutableRoutine pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {

    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    String employeeId = user.getEmployeeId();
    Employee employee = mEmployeeManager.getByEmployeeId(employeeId);
    String deptId = employee.getDepartment().getId();
    List<Program> programList = mProgramManager
        .getAll()
        .stream()
        .filter(pProgram -> pProgram.getDepartmentId().equals(deptId))
        .collect(Collectors.toList());


    pMutable.setId(pJsonObject.getString("id"));
    PersistentSemester persistentSemester = new PersistentSemester();
    persistentSemester.setId(pJsonObject.getInt("semesterId"));
    pMutable.setSemester(persistentSemester);
    PersistentProgram program = new PersistentProgram();
    pMutable.setProgram(programList.get(0));
    pMutable.setCourseId(pJsonObject.getString("courseId"));
    pMutable.setDay(pJsonObject.getInt("day"));
    pMutable.setSection(pJsonObject.getString("section"));
    pMutable.setAcademicYear(Integer.parseInt(pJsonObject.getString("academicYear")));
    pMutable.setAcademicSemester(Integer.parseInt(pJsonObject.getString("academicSemester")));
    pMutable.setStartTime(pJsonObject.getString("startTime"));
    pMutable.setEndTime(pJsonObject.getString("endTime"));
    pMutable.setRoomNo(pJsonObject.getString("roomNo"));
    pMutable.setStatus(pJsonObject.getString("status"));
  }


}
