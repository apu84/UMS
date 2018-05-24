package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.enums.routine.DayType;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class RoutineConfigBuilder implements Builder<RoutineConfig, MutableRoutineConfig> {
  @Autowired
  private SemesterBuilder mSemesterBuilder;
  @Autowired
  private ProgramBuilder mProgramBuilder;

  @Override
  public void build(JsonObjectBuilder pBuilder, RoutineConfig pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if(pReadOnly.getId() != null)
      pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("programId", pReadOnly.getProgramId());
    JsonObjectBuilder program = Json.createObjectBuilder();
    mProgramBuilder.build(program, pReadOnly.getProgram(), pUriInfo, pLocalCache);
    pBuilder.add("program", program);
    JsonObjectBuilder semester = Json.createObjectBuilder();
    mSemesterBuilder.build(semester, pReadOnly.getSemester(), pUriInfo, pLocalCache);
    pBuilder.add("semester", semester);
    pBuilder.add("dayFrom", pReadOnly.getDayFrom().getValue());
    pBuilder.add("dayTo", pReadOnly.getDayTo().getValue());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
    pBuilder.add("startTime", formatter.format(pReadOnly.getStartTime()));
    pBuilder.add("endTime", formatter.format(pReadOnly.getEndTime()));
    pBuilder.add("duraton", pReadOnly.getDuration());

  }

  @Override
  public void build(MutableRoutineConfig pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id") || pJsonObject.isNull("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    pMutable.setProgramId(pJsonObject.getInt("programId"));
    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setDayFrom(DayType.get(pJsonObject.getInt("dayFrom")));
    pMutable.setDayTo(DayType.get(pJsonObject.getInt("dayTo")));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
    pMutable.setStartTime(LocalTime.parse(pJsonObject.getString("startTime"), formatter));
    pMutable.setEndTime(LocalTime.parse(pJsonObject.getString("endTime"), formatter));
    pMutable.setDuration(pJsonObject.getInt("duration"));
  }
}
