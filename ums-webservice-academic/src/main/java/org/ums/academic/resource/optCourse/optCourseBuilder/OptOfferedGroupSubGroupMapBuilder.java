package org.ums.academic.resource.optCourse.optCourseBuilder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupSubGroupMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
@Component
public class OptOfferedGroupSubGroupMapBuilder implements
    Builder<OptOfferedGroupSubGroupMap, MutableOptOfferedGroupSubGroupMap> {

  @Override
  public void build(JsonObjectBuilder pBuilder, OptOfferedGroupSubGroupMap pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId().toString());
    }
    if(pReadOnly.getGroupId() != null) {
      pBuilder.add("groupId", pReadOnly.getGroupId().toString());
    }
    if(pReadOnly.getSubGroupId() != null) {
      pBuilder.add("subGroupId", pReadOnly.getSubGroupId().toString());
    }
    if(pReadOnly.getSubGroupName() != null) {
      pBuilder.add("subGroupName", pReadOnly.getSubGroupName());
    }

  }

  @Override
  public void build(MutableOptOfferedGroupSubGroupMap pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("groupId"))
      pMutable.setGroupId(Long.parseLong(pJsonObject.getString("groupId")));
    if(pJsonObject.containsKey("subGroupId"))
      pMutable.setSubGroupId(Long.parseLong(pJsonObject.getString("subGroupId")));
    if(pJsonObject.containsKey("subGroupName"))
      pMutable.setSubGroupName(pJsonObject.getString("subGroupName"));
  }
}
