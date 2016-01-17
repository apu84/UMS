package org.ums.academic.builder;

import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.domain.model.regular.Department;
import org.ums.domain.model.regular.Teacher;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;


public class TeacherBuilder implements Builder<Teacher, MutableTeacher> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Teacher pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("name", pReadOnly.getName());

    Department department = (Department) pLocalCache.cache(() -> pReadOnly.getDepartment(),
        pReadOnly.getDepartmentId(), Department.class);

    pBuilder.add("departmentName", department.getShortName());
    pBuilder.add("departmentId", department.getId());
    pBuilder.add("designationName", pReadOnly.getDesignationName());
    pBuilder.add("designationId", pReadOnly.getDepartmentId());
    pBuilder.add("status", pReadOnly.getStatus());

    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("teacher")
        .path(pReadOnly.getId()).build().toString());
  }

  @Override
  public void build(MutableTeacher pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {

  }
}
