package org.ums.academic.resource.optCourse.optCourseBuilder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroup;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
@Component
public class OptOfferedGroupBuilder implements Builder<OptOfferedGroup, MutableOptOfferedGroup> {
  @Override
  public void build(JsonObjectBuilder pBuilder, OptOfferedGroup pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId().toString());
    }
    if(pReadOnly.getGroupName() != null) {
      pBuilder.add("groupName", pReadOnly.getGroupName());
    }
    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
    }
    if(pReadOnly.getDepartmentId() != null) {
      pBuilder.add("departmentId", pReadOnly.getDepartmentId());
    }
    if(pReadOnly.getDepartmentId() != null) {
      Department department = pReadOnly.getDepartment();
      pBuilder.add("departmentId", department.getId());
      pBuilder.add("department",
          pUriInfo.getBaseUriBuilder().path("academic").path("department").path(String.valueOf(department.getId()))
              .build().toString());
      pBuilder.add("departmentShortName", department.getShortName());
    }
    if(pReadOnly.getProgramId() != null) {
      pBuilder.add("programId", pReadOnly.getProgramId());
    }
    if(pReadOnly.getProgramName() != null) {
      pBuilder.add("programName", pReadOnly.getProgramName());
    }
    if(pReadOnly.getIsMandatory() != null) {
      pBuilder.add("isMandatory", pReadOnly.getIsMandatory());
    }
    if(pReadOnly.getYear() != null) {
      pBuilder.add("year", pReadOnly.getYear());
    }
    if(pReadOnly.getSemester() != null) {
      pBuilder.add("semester", pReadOnly.getSemester());
    }

  }

  @Override
  public void build(MutableOptOfferedGroup pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("groupName"))
      pMutable.setGroupName(pJsonObject.getString("groupName"));
    if(pJsonObject.containsKey("semesterId"))
      pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    if(pJsonObject.containsKey("programId"))
      pMutable.setProgramId(pJsonObject.getInt("programId"));
    if(pJsonObject.containsKey("programName"))
      pMutable.setProgramName(pJsonObject.getString("programName"));
    if(pJsonObject.containsKey("isMandatory"))
      pMutable.setIsMandatory(pJsonObject.getInt("isMandatory"));
    if(pJsonObject.containsKey("year"))
      pMutable.setYear(pJsonObject.getInt("year"));
    if(pJsonObject.containsKey("semester"))
      pMutable.setSemesterId(pJsonObject.getInt("semester"));
  }
}
