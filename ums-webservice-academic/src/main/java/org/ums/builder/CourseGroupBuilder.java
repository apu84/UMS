package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.CourseGroup;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.mutable.MutableCourseGroup;
import org.ums.domain.model.mutable.MutableSyllabus;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class CourseGroupBuilder implements Builder<CourseGroup, MutableCourseGroup> {
  @Autowired
  ContentManager<Syllabus, MutableSyllabus, String> mSemesterManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, CourseGroup pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getName());
    pBuilder.add("self",
        pUriInfo.getBaseUriBuilder().path("academic").path("courseGroup").path(String.valueOf(pReadOnly.getId()))
            .build().toString());
  }

  @Override
  public void build(MutableCourseGroup pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getInt("id"));
    pMutable.setName(pJsonObject.getString("name"));
  }
}
