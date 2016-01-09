package org.ums.academic.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.ums.cache.LocalCache;
import org.ums.domain.model.CourseGroup;
import org.ums.domain.model.MutableCourseGroup;
import org.ums.domain.model.MutableSyllabus;
import org.ums.domain.model.Syllabus;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

public class CourseGroupBuilder implements Builder<CourseGroup, MutableCourseGroup> {
  @Autowired
  @Qualifier("syllabusManager")
  ContentManager<Syllabus, MutableSyllabus, String> mSemesterManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, CourseGroup pReadOnly,
                    UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getName());
    Syllabus syllabus = (Syllabus) pLocalCache.cache(() -> pReadOnly.getSyllabus(),
        pReadOnly.getSyllabusId(), Syllabus.class);
    pBuilder.add("syllabus", pUriInfo.getBaseUriBuilder().path("academic").path("syllabus")
        .path(String.valueOf(syllabus.getId())).build().toString());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("courseGroup")
        .path(String.valueOf(pReadOnly.getId())).build().toString());
  }

  @Override
  public void build(MutableCourseGroup pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) throws Exception {
    pMutable.setId(pJsonObject.getInt("id"));
    pMutable.setName(pJsonObject.getString("name"));
    pMutable.setSyllabus(mSemesterManager.get(pJsonObject.getString("syllabus")));
  }
}
