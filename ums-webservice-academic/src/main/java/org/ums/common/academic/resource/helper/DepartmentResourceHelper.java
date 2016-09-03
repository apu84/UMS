package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.builder.DepartmentBuilder;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.mutable.MutableDepartment;
import org.ums.manager.DepartmentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class DepartmentResourceHelper extends ResourceHelper<Department, MutableDepartment, String> {
  @Autowired
  private DepartmentManager mManager;

  @Autowired
  private DepartmentBuilder mBuilder;

  @Override
  public DepartmentManager getContentManager() {
    return mManager;
  }

  @Override
  public DepartmentBuilder getBuilder() {
    return mBuilder;
  }

  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected String getEtag(Department pReadonly) {
    return "";
  }
}
