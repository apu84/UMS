package org.ums.resource.leavemanagement;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.common.LmsType;
import org.ums.domain.model.mutable.common.MutableLmsType;
import org.ums.enums.common.EmployeeLeaveType;
import org.ums.enums.common.EmployeeType;
import org.ums.enums.common.Gender;
import org.ums.manager.EmployeeManager;
import org.ums.manager.common.LmsTypeManager;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 07-May-17.
 */
@Component
public class LmsTypeResourceHelper extends ResourceHelper<LmsType, MutableLmsType, Integer> {

  @Autowired
  private LmsTypeManager mLmsTypeManager;

  @Autowired
  private LmsTypeBuilder mLmsTypeBuilder;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  public JsonObject getAllLeaveTypes(UriInfo pUriInfo) throws Exception {
    Employee loggedEmployee = getLoggedEmployee();
    List<LmsType> leaveTypes = new ArrayList<>();

    // todo Need to know gender, waiting till employee management completion. Gender is needed for
    // knowing whether the maternity leave is applicable or not
    if(loggedEmployee.getEmploymentType().equals(EmployeeType.TEACHER.getId() + "")) {
      if(loggedEmployee.getGender().equals("M"))
        leaveTypes = getContentManager().getLmsTypes(EmployeeLeaveType.TEACHERS_LEAVE, Gender.MALE);
      else
        leaveTypes = getContentManager().getLmsTypes(EmployeeLeaveType.TEACHERS_LEAVE, Gender.FEMALE);
    }
    else {
      if(loggedEmployee.getGender().equals("M"))
        leaveTypes = getContentManager().getLmsTypes(EmployeeLeaveType.COMMON_LEAVE, Gender.MALE);
      else
        leaveTypes = getContentManager().getLmsTypes(EmployeeLeaveType.COMMON_LEAVE, Gender.FEMALE);
    }
    return getJsonObject(pUriInfo, leaveTypes);

  }

  private JsonObject getJsonObject(UriInfo pUriInfo, List<LmsType> pLeaveTypes) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(LmsType lmsType : pLeaveTypes) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, lmsType, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  private Employee getLoggedEmployee() {
    User loggedUser = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    Employee loggedEmployee = mEmployeeManager.get(loggedUser.getEmployeeId());
    return loggedEmployee;
  }

  @Override
  protected LmsTypeManager getContentManager() {
    return mLmsTypeManager;
  }

  @Override
  protected Builder<LmsType, MutableLmsType> getBuilder() {
    return mLmsTypeBuilder;
  }

  @Override
  protected String getETag(LmsType pReadonly) {
    return pReadonly.getLastModified();
  }
}
