package org.ums.academic.resource.optCourse.optCourseBuilder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.optCourse.OptSeatAllocation;
import org.ums.domain.model.mutable.optCourse.MutableOptSeatAllocation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
@Component
public class OptSeatAllocationBuilder implements Builder<OptSeatAllocation, MutableOptSeatAllocation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, OptSeatAllocation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId().toString());
    }
    if(pReadOnly.getGroupID() != null) {
      pBuilder.add("groupId", pReadOnly.getGroupID().toString());
    }
    if(pReadOnly.getSeatNumber() != null) {
      pBuilder.add("seat", pReadOnly.getSeatNumber().toString());
    }
  }

  @Override
  public void build(MutableOptSeatAllocation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("groupId"))
      pMutable.setGroupID(Long.parseLong(pJsonObject.getString("groupId")));
    if(pJsonObject.containsKey("seat"))
      pMutable.setSeatNumber(pJsonObject.getInt("seat"));

  }
}
