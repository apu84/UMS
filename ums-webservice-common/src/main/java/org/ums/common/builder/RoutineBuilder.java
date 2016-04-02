package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableRoutine;
import org.ums.domain.model.immutable.Routine;
import org.ums.persistent.model.PersistentProgram;
import org.ums.persistent.model.PersistentSemester;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;


@Component
public class RoutineBuilder implements Builder<Routine, MutableRoutine> {

  @Override
  public void build(JsonObjectBuilder pBuilder, Routine pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    pBuilder.add("courseId", pReadOnly.getCourseId());
    pBuilder.add("programId", pReadOnly.getProgram().getId());

    pBuilder.add("day", pReadOnly.getDay());
    pBuilder.add("section", pReadOnly.getSection());
    pBuilder.add("academicYear", pReadOnly.getAcademicYear());
    pBuilder.add("academicSemester", pReadOnly.getAcademicSemester());
    pBuilder.add("startTime", pReadOnly.getStartTime());
    pBuilder.add("endTime", pReadOnly.getEndTime());
    pBuilder.add("duration", pReadOnly.getDuration());
    pBuilder.add("roomNo", pReadOnly.getRoomNo());
    //pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("routine").path(pReadOnly.getId().toString()).build().toString());
  }

  @Override
  public void build(MutableRoutine pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {
    pMutable.setId(pJsonObject.getString("id"));
    PersistentSemester persistentSemester = new PersistentSemester();
    persistentSemester.setId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutable.setSemester(persistentSemester);
    PersistentProgram program = new PersistentProgram();
    program.setId(Integer.parseInt(pJsonObject.getString("programId")));
    pMutable.setProgram(program);
    pMutable.setCourseId(pJsonObject.getString("courseId"));
    pMutable.setDay(Integer.parseInt(pJsonObject.getString("day")));
    pMutable.setSection(pJsonObject.getString("section"));
    pMutable.setAcademicYear(Integer.parseInt(pJsonObject.getString("academicYear")));
    pMutable.setAcademicSemester(Integer.parseInt(pJsonObject.getString("academicSemester")));
    pMutable.setStartTime(pJsonObject.getString("startTime"));
    pMutable.setEndTime(pJsonObject.getString("endTime"));
    pMutable.setRoomNo(pJsonObject.getString("roomNo"));
  }


}
