package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.LmsApplicationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.immutable.common.LmsType;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.enums.common.EmployeeLeaveType;
import org.ums.enums.common.EmployeeType;
import org.ums.enums.common.Gender;
import org.ums.enums.common.LeaveApplicationStatus;
import org.ums.manager.EmployeeManager;
import org.ums.manager.UserManager;
import org.ums.manager.common.LmsApplicationManager;
import org.ums.manager.common.LmsTypeManager;
import org.ums.persistent.model.common.PersistentLmsApplication;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 08-May-17.
 */
@Component
public class LmsApplicationResourceHelper extends ResourceHelper<LmsApplication, MutableLmsApplication, Integer> {

  @Autowired
  private LmsApplicationManager mManager;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private LmsApplicationBuilder mBuilder;

  @Autowired
  private LmsTypeManager mLmsTypeManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentLmsApplication application = new PersistentLmsApplication();
    getBuilder().build(application, jsonObject, localCache);
    if(application.getId() == 0)
      getContentManager().create(application);
    else
      getContentManager().update(application);

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getPendingLeavesOfEmployee(UriInfo pUriInfo) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    List<LmsApplication> applications = getContentManager().getPendingLmsApplication(user.getEmployeeId());
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(LmsApplication application : applications) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, application, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getRemainingLeaves() {
    List<LmsType> lmsTypes = getLeaveTypes();
    int year = Calendar.getInstance().get(Calendar.YEAR);
    List<LmsApplication> applications = getContentManager()
        .getLmsApplication(mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId(), year);
    Map<Integer, List<LmsApplication>> applicationMap = applications
        .stream()
        .filter(app -> app.getApplicationStatus() == LeaveApplicationStatus.ACCEPTED)
        .collect(Collectors.groupingBy(LmsApplication::getLeaveTypeId));

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for (LmsType lmsType : lmsTypes) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      jsonObject.add("leaveTypeId", lmsType.getId());
      jsonObject.add("leaveName", lmsType.getName());
      int leavesTaken = getLeavesTaken(applicationMap, lmsType);
      jsonObject.add("daysLeft", applicationMap.get(lmsType.getId()) != null ? (lmsType.getMaxDuration() - leavesTaken) : lmsType.getMaxDuration());
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  private int getLeavesTaken(Map<Integer, List<LmsApplication>> pApplicationMap, LmsType lmsType) {
    int leavesTaken = 0;
    if(pApplicationMap.get(lmsType.getId()) != null)
      for(LmsApplication application : pApplicationMap.get(lmsType.getId())) {
        leavesTaken +=
            (application.getToDate().getTime() - application.getFromDate().getTime()) / (1000 * 60 * 60 * 24);
      }
    return leavesTaken;
  }

  private List<LmsType> getLeaveTypes() {
    List<LmsType> lmsTypes = new ArrayList<>();
    Employee employee =
        mEmployeeManager.get(mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    if(employee.getEmploymentType().equals(EmployeeType.TEACHER.getId() + "")) {
      if(employee.getGender().equals("M"))
        lmsTypes = mLmsTypeManager.getLmsTypes(EmployeeLeaveType.TEACHERS_LEAVE, Gender.MALE);
      else
        lmsTypes = mLmsTypeManager.getLmsTypes(EmployeeLeaveType.TEACHERS_LEAVE, Gender.FEMALE);
    }
    else {
      if(employee.getGender().equals("M"))
        lmsTypes = mLmsTypeManager.getLmsTypes(EmployeeLeaveType.COMMON_LEAVE, Gender.MALE);
      else
        lmsTypes = mLmsTypeManager.getLmsTypes(EmployeeLeaveType.COMMON_LEAVE, Gender.FEMALE);
    }
    return lmsTypes;
  }

  @Override
  protected LmsApplicationManager getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<LmsApplication, MutableLmsApplication> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(LmsApplication pReadonly) {
    return pReadonly.getLastModified();
  }
}
