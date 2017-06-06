package org.ums.resource.leavemanagement;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.enums.common.RoleType;
import org.ums.manager.EmployeeManager;
import org.ums.manager.common.LmsAppStatusManager;
import org.ums.manager.common.LmsApplicationManager;
import org.ums.persistent.model.common.PersistentLmsAppStatus;
import org.ums.resource.ResourceHelper;
import org.ums.services.leave.management.LeaveManagementService;
import org.ums.usermanagement.permission.AdditionalRolePermissions;
import org.ums.usermanagement.permission.AdditionalRolePermissionsManager;
import org.ums.usermanagement.role.RoleManager;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 20-May-17.
 */
@Component
public class LmsAppStatusResourceHelper extends ResourceHelper<LmsAppStatus, MutableLmsAppStatus, Long> {

  @Autowired
  LmsApplicationManager mLmsApplicationManager;

  @Autowired
  LmsAppStatusManager mLmsAppStatusManager;

  @Autowired
  LmsAppStatusBuilder mLmsAppStatusBuilder;

  @Autowired
  UserManager mUserManager;

  @Autowired
  RoleManager mRoleManager;

  @Autowired
  EmployeeManager mEmployeeManager;

  @Autowired
  LeaveManagementService mLeaveManagementService;

  @Autowired
  AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentLmsAppStatus lmsAppStatus = new PersistentLmsAppStatus();
    lmsAppStatus.setLmsApplicationId(Long.parseLong(jsonObject.getString("appId")));
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    List<Integer> additionalRoleIds = mAdditionalRolePermissionsManager.getPermissionsByUser(user.getId()).stream().map(a -> a.getRole().getId()).collect(Collectors.toList());
    lmsAppStatus.setActionTakenById(user.getEmployeeId());
    lmsAppStatus.setComments(jsonObject.getString("comments"));
    int approvalStatus = jsonObject.getInt("leaveApprovalStatus");
    LmsAppStatus latestStatusOfTheApplication = mLmsAppStatusManager.getLatestStatusOfTheApplication(lmsAppStatus.getLmsApplication().getId());
    mLeaveManagementService.setApplicationStatus(lmsAppStatus, user, additionalRoleIds, approvalStatus, latestStatusOfTheApplication);
    getContentManager().create(lmsAppStatus);

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getApplicationStatus(final Long pApplicationId, UriInfo pUriInfo) {
    List<LmsAppStatus> appStatuses = getContentManager().getAppStatus(pApplicationId);
    return getJsonObject(pUriInfo, appStatuses);
  }

  private JsonObject getJsonObject(UriInfo pUriInfo, List<LmsAppStatus> pAppStatuses) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(LmsAppStatus status : pAppStatuses) {
      JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
      getBuilder().build(objectBuilder, status, pUriInfo, localCache);
      children.add(objectBuilder);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getPendingApplicationsOfEmployee(UriInfo pUriInfo) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    List<LmsAppStatus> appStatuses = getContentManager().getPendingApplications(user.getEmployeeId());
    return getJsonObject(pUriInfo, appStatuses);
  }

  public JsonObject getAllApplicationsOfEmployee(String pEmployeeId,
      LeaveApplicationApprovalStatus pLeaveApplicationApprovalStatus, int pageNumber, int pageSize, UriInfo pUriInfo) {
    List<LmsAppStatus> appStatuses =
        getContentManager().getAllApplications(pEmployeeId, pLeaveApplicationApprovalStatus, pageNumber, pageSize);
    int statusSize = getContentManager().getAllApplications(pEmployeeId, pLeaveApplicationApprovalStatus).size();
    return getJsonObjectWithTotalSize(pUriInfo, appStatuses, statusSize);
  }

  public JsonObject getLeaveApplications(LeaveApplicationApprovalStatus pStatus, int pageNumber, int pageSize, UriInfo pUriInfo) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    List<LmsAppStatus> statuses = new ArrayList<>();
    int statusSize;
    List<AdditionalRolePermissions> additionalRolePermissions = mAdditionalRolePermissionsManager.getPermissionsByUser(user.getId()).stream().filter(p -> p.getRoleId() == RoleType.DEPT_HEAD.getId()).collect(Collectors.toList());
    if (additionalRolePermissions.size() > 0) {
      statuses =
          getContentManager().getPendingApplications(pStatus, mRoleManager.get(RoleType.DEPT_HEAD.getId()), user,
              pageNumber, pageSize);
      statusSize = getContentManager().getPendingApplications(pStatus, user, mRoleManager.get(RoleType.DEPT_HEAD.getId())).size();
    } else {
      statuses = getContentManager().getPendingApplications(pStatus, user.getPrimaryRole(), user, pageNumber, pageSize);
      statusSize = getContentManager().getPendingApplications(pStatus, user, user.getPrimaryRole()).size();
    }
    return getJsonObjectWithTotalSize(pUriInfo, statuses, statusSize);
  }

  private JsonObject getJsonObjectWithTotalSize(UriInfo pUriInfo, List<LmsAppStatus> pStatuses, int pStatusSize) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(LmsAppStatus status : pStatuses) {
      JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
      getBuilder().build(objectBuilder, status, pUriInfo, localCache);
      children.add(objectBuilder);
    }
    object.add("entries", children);
    object.add("totalSize", pStatusSize);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getLeaveApplications(LeaveApplicationApprovalStatus pStatus, UriInfo pUriInfo) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    List<LmsAppStatus> statuses = new ArrayList<>();
    List<AdditionalRolePermissions> additionalRolePermissions = mAdditionalRolePermissionsManager.getPermissionsByUser(user.getId()).stream().filter(p -> p.getRoleId() == RoleType.DEPT_HEAD.getId()).collect(Collectors.toList());
    if (additionalRolePermissions.size() > 0) {
      statuses =
          getContentManager().getPendingApplications(pStatus, user, mRoleManager.get(RoleType.DEPT_HEAD.getId()));
    } else {
      statuses = getContentManager().getPendingApplications(pStatus, user, user.getPrimaryRole());
    }
    return getJsonObject(pUriInfo, statuses);

  }

  @Override
  protected LmsAppStatusManager getContentManager() {
    return mLmsAppStatusManager;
  }

  @Override
  protected Builder<LmsAppStatus, MutableLmsAppStatus> getBuilder() {
    return mLmsAppStatusBuilder;
  }

  @Override
  protected String getETag(LmsAppStatus pReadonly) {
    return pReadonly.getLastModified();
  }
}
