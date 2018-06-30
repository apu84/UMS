package org.ums.academic.resource.routine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.*;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.mutable.routine.MutableRoutine;
import org.ums.generator.IdGenerator;
import org.ums.manager.EmployeeManager;
import org.ums.manager.ProgramManager;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
  @Autowired
  IdGenerator mIdGenerator;
  @Autowired
  ClassRoomBuilder mClassRoomBuilder;

  @Override
  public void build(JsonObjectBuilder pBuilder, Routine pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    JsonObjectBuilder semester = Json.createObjectBuilder();
    mSemesterBuilder.build(semester, pReadOnly.getSemester(), pUriInfo, pLocalCache);
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    pBuilder.add("semester", semester);
    JsonObjectBuilder course = Json.createObjectBuilder();
    mCourseBuilder.build(course, pReadOnly.getCourse(), pUriInfo, pLocalCache);
    pBuilder.add("courseId", pReadOnly.getCourseId());
    pBuilder.add("course", course);
    JsonObjectBuilder program = Json.createObjectBuilder();
    mProgramBuilder.build(program, pReadOnly.getProgram(), pUriInfo, pLocalCache);
    pBuilder.add("programId", pReadOnly.getProgramId());
    pBuilder.add("program", program);
    pBuilder.add("day", pReadOnly.getDay());
    pBuilder.add("section", pReadOnly.getSection());
    pBuilder.add("academicYear", pReadOnly.getAcademicYear());
    pBuilder.add("academicSemester", pReadOnly.getAcademicSemester());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
    pBuilder.add("startTime", formatter.format(pReadOnly.getStartTime()));
    pBuilder.add("endTime", formatter.format(pReadOnly.getEndTime()));
    pBuilder.add("duration", pReadOnly.getDuration());
    JsonObjectBuilder room = Json.createObjectBuilder();
    mClassRoomBuilder.build(room, pReadOnly.getRoom(), pUriInfo, pLocalCache);
    pBuilder.add("room", room);
    pBuilder.add("roomId", pReadOnly.getRoomId().toString());
  }

  @Override
  public void build(MutableRoutine pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong("id"));
    else
      pMutable.setId(mIdGenerator.getNumericId());
    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setCourseId(pJsonObject.getString("courseId"));
    pMutable.setSection(pJsonObject.getString("section'"));
    pMutable.setAcademicYear(pJsonObject.getInt("academicYear"));
    pMutable.setAcademicSemester(pJsonObject.getInt("academicSemester"));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
    pMutable.setStartTime(LocalTime.parse(pJsonObject.getString("startTime"), formatter));
    pMutable.setEndTime(LocalTime.parse(pJsonObject.getString("endTime"), formatter));
    pMutable.setDuration(pJsonObject.getInt("duration"));
    pMutable.setRoomId(Long.parseLong(pJsonObject.getString("roomId")));
    pMutable.setDay(pJsonObject.getInt("day"));
    pMutable.setProgramId(pJsonObject.getInt("programId"));
  }
}
