package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
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
  public void build(JsonObjectBuilder pBuilder, SeatPlan pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
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
}
