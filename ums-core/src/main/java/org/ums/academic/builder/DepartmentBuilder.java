package org.ums.academic.builder;


import org.ums.domain.model.Department;
import org.ums.domain.model.MutableDepartment;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

public class DepartmentBuilder implements Builder<Department, MutableDepartment> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Department pReadOnly, UriInfo pUriInfo) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("shortName", pReadOnly.getShortName());
    pBuilder.add("longName", pReadOnly.getLongName());
    pBuilder.add("type", pReadOnly.getType());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("department")
        .path(String.valueOf(pReadOnly.getId())).build().toString());
  }

  @Override
  public void build(MutableDepartment pMutable, JsonObject pJsonObject) throws Exception {
    //Do nothing
  }
}
