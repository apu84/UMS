package org.ums.academic.resource.routine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.ProgramBuilder;
import org.ums.builder.SemesterBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.enums.ProgramType;
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
    /*
     * pBuilder.add("programId", pReadOnly.getProgramId()); JsonObjectBuilder program =
     * Json.createObjectBuilder(); mProgramBuilder.build(program, pReadOnly.getProgram(), pUriInfo,
     * pLocalCache); pBuilder.add("program", program);
     */
    pBuilder.add("programType", pReadOnly.getProgramType().getValue());
    JsonObjectBuilder semester = Json.createObjectBuilder();
    mSemesterBuilder.build(semester, pReadOnly.getSemester(), pUriInfo, pLocalCache);
    pBuilder.add("semester", semester);
    pBuilder.add("dayFrom", pReadOnly.getDayFrom().getValue() + "");
    pBuilder.add("dayTo", pReadOnly.getDayTo().getValue() + "");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
    pBuilder.add("startTime", formatter.format(pReadOnly.getStartTime()));
    pBuilder.add("endTime", formatter.format(pReadOnly.getEndTime()));
    pBuilder.add("duration", pReadOnly.getDuration());

  }

  @Override
  public void build(MutableRoutineConfig pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    pMutable.setProgramType(ProgramType.get(pJsonObject.getInt("programType")));
    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setDayFrom(DayType.get(Integer.parseInt(pJsonObject.getString("dayFrom"))));
    pMutable.setDayTo(DayType.get(Integer.parseInt(pJsonObject.getString("dayTo"))));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
    pMutable.setStartTime(LocalTime.parse(pJsonObject.getString("startTime"), formatter));
    pMutable.setEndTime(LocalTime.parse(pJsonObject.getString("endTime"), formatter));
    pMutable.setDuration(pJsonObject.getInt("duration"));
  }
}
