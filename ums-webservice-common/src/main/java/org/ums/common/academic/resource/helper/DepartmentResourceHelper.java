package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.regular.Department;
import org.ums.domain.model.mutable.MutableDepartment;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class DepartmentResourceHelper extends ResourceHelper<Department, MutableDepartment, Integer> {
  @Autowired
  @Qualifier("departmentManager")
  private ContentManager<Department, MutableDepartment, Integer> mManager;

  @Autowired
  private List<Builder<Department, MutableDepartment>> mBuilders;

  @Override
  public ContentManager<Department, MutableDepartment, Integer> getContentManager() {
    return mManager;
  }

  @Override
  public List<Builder<Department, MutableDepartment>> getBuilders() {
    return mBuilders;
  }

  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected String getEtag(Department pReadonly) {
    return "";
  }
}
