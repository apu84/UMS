package org.ums.academic.resource.optCourse.optCourseBuilder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.optCourse.OptOfferedSubGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedSubGroupCourseMap;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
@Component
public class OptOfferedSubGroupCourseMapBuilder implements Builder<OptOfferedSubGroupCourseMap, MutableOptOfferedSubGroupCourseMap> {
  @Override
  public void build(JsonObjectBuilder pBuilder, OptOfferedSubGroupCourseMap pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId().toString());
    }
    if(pReadOnly.getSubGroupId() != null) {
      pBuilder.add("subGroupId", pReadOnly.getSubGroupId().toString());
    }
    if(pReadOnly.getCourseId() != null) {
      pBuilder.add("courseId", pReadOnly.getCourseId());
    }
    if(pReadOnly.getCourseId() != null) {
      Course course = pReadOnly.getCourses();
      pBuilder.add("courseNo", course.getNo());
      pBuilder.add("courseTitle", course.getTitle());
    }

  }

  @Override
  public void build(MutableOptOfferedSubGroupCourseMap pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("subGroupId"))
      pMutable.setSubGroupId(Long.parseLong(pJsonObject.getString("subGroupId")));
    if(pJsonObject.containsKey("courseId"))
      pMutable.setCourseId(pJsonObject.getString("courseId"));

  }
}
