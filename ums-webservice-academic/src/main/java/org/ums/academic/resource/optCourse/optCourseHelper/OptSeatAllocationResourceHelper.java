package org.ums.academic.resource.optCourse.optCourseHelper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptSeatAllocationBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.optCourse.OptSeatAllocation;
import org.ums.domain.model.mutable.optCourse.MutableOptSeatAllocation;
import org.ums.manager.ContentManager;
import org.ums.manager.EmployeeManager;
import org.ums.manager.optCourse.OptSeatAllocationManager;
import org.ums.persistent.model.optCourse.PersistentOptSeatAllocation;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
@Component
public class OptSeatAllocationResourceHelper extends ResourceHelper<OptSeatAllocation, MutableOptSeatAllocation, Long> {
  @Autowired
  OptSeatAllocationManager mManager;
  @Autowired
  OptSeatAllocationBuilder mBuilder;
  @Autowired
  EmployeeManager mEmployeeManager;
  @Autowired
  UserManager mUserManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    String deptId = mEmployeeManager.get(userId).getDepartment().getId();
    List<MutableOptSeatAllocation> applications = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentOptSeatAllocation application = new PersistentOptSeatAllocation();
      getBuilder().build(application, jsonObject, localCache);
      application.setDepartmentId(deptId);
      applications.add(application);
    }
    mManager.create(applications);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  protected ContentManager<OptSeatAllocation, MutableOptSeatAllocation, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<OptSeatAllocation, MutableOptSeatAllocation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(OptSeatAllocation pReadonly) {
    return null;
  }
}
