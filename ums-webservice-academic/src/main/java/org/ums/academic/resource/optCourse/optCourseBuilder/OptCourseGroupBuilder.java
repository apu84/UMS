package org.ums.academic.resource.optCourse.optCourseBuilder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.optCourse.OptCourseGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseGroup;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
@Component
public class OptCourseGroupBuilder implements Builder<OptCourseGroup, MutableOptCourseGroup> {

  @Override
  public void build(JsonObjectBuilder pBuilder, OptCourseGroup pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId().toString());
    }
    if(pReadOnly.getOptGroupId() != null) {
      pBuilder.add("groupId", pReadOnly.getOptGroupId());
    }
    if(pReadOnly.getOptGroupName() != null) {
      pBuilder.add("groupName", pReadOnly.getOptGroupName());
    }

  }

  @Override
  public void build(MutableOptCourseGroup pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
