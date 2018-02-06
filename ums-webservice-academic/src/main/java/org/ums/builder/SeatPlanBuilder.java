package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.SeatPlan;
import org.ums.domain.model.mutable.MutableSeatPlan;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by My Pc on 5/8/2016.
 */
@Component
public class SeatPlanBuilder implements Builder<SeatPlan, MutableSeatPlan> {
  @Override
  public void build(JsonObjectBuilder pBuilder, SeatPlan pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("idStr", pReadOnly.getId().toString());
    pBuilder.add("roomId", pReadOnly.getClassRoom().getId());
    pBuilder.add("rowNo", pReadOnly.getRowNo());
    pBuilder.add("colNo", pReadOnly.getColumnNo());
    pBuilder.add("studentId", pReadOnly.getStudent().getId());
    pBuilder.add("examType", pReadOnly.getExamType());
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    pBuilder.add("groupNo", pReadOnly.getGroupNo());
  }

  // not needed.
  @Override
  public void build(MutableSeatPlan pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }

  public void build(MutableSeatPlan pMutable, JsonObject pJsonObject) {
    if(pJsonObject.containsKey("idStr"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("idStr")));
    if(pJsonObject.containsKey("roomId"))
      pMutable.setClassRoomId(new Long(pJsonObject.getInt("roomId")));
    if(pJsonObject.containsKey("rowNo"))
      pMutable.setRowNo(pJsonObject.getInt("rowNo"));
    if(pJsonObject.containsKey("colNo"))
      pMutable.setColumnNo(pJsonObject.getInt("colNo"));
  }
}
