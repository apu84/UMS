package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Program;
import org.ums.usermanagement.user.User;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.manager.EmployeeManager;
import org.ums.manager.ProgramManager;
import org.ums.usermanagement.user.UserManager;
import org.ums.persistent.model.PersistentSemester;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.time.format.DateTimeFormatter;
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
  @Autowired
  SemesterBuilder mSemesterBuilder;
  @Autowired
  CourseBuilder mCourseBuilder;
  @Autowired
  ProgramBuilder mProgramBuilder;

  @Override
  public void build(JsonObjectBuilder pBuilder, Routine pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if(pReadOnly.getId() != null)
      pBuilder.add("id", pReadOnly.getId());
    JsonObjectBuilder semester = Json.createObjectBuilder();
    mSemesterBuilder.build(semester, pReadOnly.getSemester(), pUriInfo, pLocalCache);
    pBuilder.add("semester", semester);
    JsonObjectBuilder course = Json.createObjectBuilder();
    mCourseBuilder.build(course, pReadOnly.getCourse(), pUriInfo, pLocalCache);
    JsonObjectBuilder program = Json.createObjectBuilder();
    mProgramBuilder.build(program, pReadOnly.getProgram(), pUriInfo, pLocalCache);
    pBuilder.add("day", pReadOnly.getDay());
    pBuilder.add("section", pReadOnly.getSection());
    pBuilder.add("academicYear", pReadOnly.getAcademicYear());
    pBuilder.add("academicSemester", pReadOnly.getAcademicSemester());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
    pBuilder.add("startTime", formatter.format(pReadOnly.getStartTime()));
    pBuilder.add("endTime", formatter.format(pReadOnly.getEndTime()));
    pBuilder.add("duration", pReadOnly.getDuration());
    pBuilder.add("roomId", pReadOnly.getRoomId().toString());

    // pBuilder.add("self",
    // pUriInfo.getBaseUriBuilder().path("academic").path("routine").path(pReadOnly.getId().toString()).build().toString());
  }

  @Override
  public void build(MutableRoutine pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    String employeeId = user.getEmployeeId();
    Employee employee = mEmployeeManager.get(employeeId);
    String deptId = employee.getDepartment().getId();
    List<Program> programList = mProgramManager
        .getAll()
        .stream()
        .filter(pProgram -> pProgram.getDepartmentId().equals(deptId))
        .collect(Collectors.toList());


    if(pJsonObject.containsKey("id") && !pJsonObject.isNull("id")){
        pMutable.setId(new Long(pJsonObject.getInt("id")));
    }
    PersistentSemester persistentSemester = new PersistentSemester();
    persistentSemester.setId(pJsonObject.getInt("semesterId"));
    pMutable.setSemester(persistentSemester);
    pMutable.setProgram(programList.get(0));
    pMutable.setCourseId(pJsonObject.getString("courseId"));
    pMutable.setDay(pJsonObject.getInt("day"));
    pMutable.setSection(pJsonObject.getString("section"));
    pMutable.setAcademicYear(Integer.parseInt(pJsonObject.getString("academicYear")));
    pMutable.setAcademicSemester(Integer.parseInt(pJsonObject.getString("academicSemester")));
//    pMutable.setStartTime(pJsonObject.getString("startTime"));
//    pMutable.setEndTime(pJsonObject.getString("endTime"));
//    pMutable.setRoomId(pJsonObject.getInt("roomNo"));
    pMutable.setStatus(pJsonObject.getString("status"));
  }
}
