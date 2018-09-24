package org.ums.academic.resource.optCourse.optCourseBuilder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupCourseMap;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
@Component
public class OptOfferedGroupCourseMapBuilder implements
    Builder<OptOfferedGroupCourseMap, MutableOptOfferedGroupCourseMap> {
  @Override
  public void build(JsonObjectBuilder pBuilder, OptOfferedGroupCourseMap pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId().toString());
    }
    if(pReadOnly.getGroupId() != null) {
      pBuilder.add("groupId", pReadOnly.getGroupId().toString());
    }
    if(pReadOnly.getCourseId() != null) {
      pBuilder.add("courseId", pReadOnly.getCourseId());
    }
    if(pReadOnly.getCourseId() != null) {
      Course course = pReadOnly.getCourses();
      pBuilder.add("courseId", course.getId());
      pBuilder.add("courseNo", course.getNo());
      pBuilder.add("courseTitle", course.getTitle());
    }

  }

  @Override
  public void build(MutableOptOfferedGroupCourseMap pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("groupId"))
      pMutable.setGroupId(Long.parseLong(pJsonObject.getString("groupId")));
    if(pJsonObject.containsKey("courseId"))
      pMutable.setCourseId(pJsonObject.getString("courseId"));
  }
}
