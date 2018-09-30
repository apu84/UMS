package org.ums.academic.resource.optCourse.optCourseBuilder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.optCourse.OptStudentCourseSelection;
import org.ums.domain.model.mutable.optCourse.MutableOptStudentCourseSelection;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
@Component
public class OptStudentCourseSelectionBuilder implements
    Builder<OptStudentCourseSelection, MutableOptStudentCourseSelection> {
  @Override
  public void build(JsonObjectBuilder pBuilder, OptStudentCourseSelection pReadOnly, UriInfo pUriInfo,
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
    if(pReadOnly.getStudentChoice() != null) {
      pBuilder.add("choice", pReadOnly.getStudentChoice());
    }
    if(pReadOnly.getProgramId() != null) {
      pBuilder.add("programId", pReadOnly.getProgramId());
    }
    if(pReadOnly.getYear() != null) {
      pBuilder.add("year", pReadOnly.getYear());
    }
    if(pReadOnly.getSemester() != null) {
      pBuilder.add("semester", pReadOnly.getSemester());
    }
    if(pReadOnly.getStudentId() != null) {
      pBuilder.add("studentId", pReadOnly.getStudentId());
    }
    if(pReadOnly.getDepartmentId() != null) {
      pBuilder.add("departmentId", pReadOnly.getDepartmentId());
    }
    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
    }
  }

  @Override
  public void build(MutableOptStudentCourseSelection pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
