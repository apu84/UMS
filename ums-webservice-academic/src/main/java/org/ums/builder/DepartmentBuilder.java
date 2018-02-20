package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.MutableDepartment;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class DepartmentBuilder implements Builder<Department, MutableDepartment> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Department pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("shortName", pReadOnly.getShortName());
    pBuilder.add("longName", pReadOnly.getLongName());
    pBuilder.add("type", pReadOnly.getType());
    pBuilder.add("category", pReadOnly.getType() == 1 ? "Departments" : "Offices");
    pBuilder.add("self",
        pUriInfo.getBaseUriBuilder().path("academic").path("department").path(String.valueOf(pReadOnly.getId()))
            .build().toString());
  }

  @Override
  public void build(MutableDepartment pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) {
    // Do nothing
  }
}
