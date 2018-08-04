package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationTesSelectedCourses;
import org.ums.domain.model.mutable.MutableApplicationTesSelectedCourses;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
@Component
public class ApplicationTesSelectedCourseBuilder implements
    Builder<ApplicationTesSelectedCourses, MutableApplicationTesSelectedCourses> {
  @Override
  public void build(JsonObjectBuilder pBuilder, ApplicationTesSelectedCourses pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    if(pReadOnly.getApplicationDate() != null)
      pBuilder.add("applicationDate", pReadOnly.getApplicationDate());
    if(pReadOnly.getCourseId() != null)
      pBuilder.add("courseId", pReadOnly.getCourseId());
    if(pReadOnly.getSemester() != null)
      pBuilder.add("semesterId", pReadOnly.getSemester());
    if(pReadOnly.getTeacherId() != null)
      pBuilder.add("teacherId", pReadOnly.getTeacherId());
    if(pReadOnly.getSection() != null)
      pBuilder.add("section", pReadOnly.getSection());
    if(pReadOnly.getDeptId() != null)
      pBuilder.add("deptId", pReadOnly.getDeptId());
    if(pReadOnly.getInsertionDate() != null)
      pBuilder.add("insertionDate", pReadOnly.getInsertionDate());

  }

  @Override
  public void build(MutableApplicationTesSelectedCourses pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
