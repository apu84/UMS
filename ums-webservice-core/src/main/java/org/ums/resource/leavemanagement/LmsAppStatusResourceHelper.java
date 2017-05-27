package org.ums.resource.leavemanagement;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdditionalRolePermissions;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.enums.common.LeaveApprovalStatus;
import org.ums.enums.common.RoleType;
import org.ums.manager.AdditionalRolePermissionsManager;
import org.ums.manager.EmployeeManager;
import org.ums.manager.RoleManager;
import org.ums.manager.UserManager;
import org.ums.manager.common.LmsAppStatusManager;
import org.ums.manager.common.LmsApplicationManager;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
  AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
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
    List<LmsAppStatus> appStatuses = getContentManager().getLmsAppStatusList(user.getEmployeeId());
    return getJsonObject(pUriInfo, appStatuses);
  }

  public JsonObject getLeaveApplications(LeaveApprovalStatus pStatus, int pageNumber, int pageSize, UriInfo pUriInfo) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    List<LmsAppStatus> statuses = new ArrayList<>();
    int statusSize;
    List<AdditionalRolePermissions> additionalRolePermissions = mAdditionalRolePermissionsManager.getPermissionsByUser(user.getId()).stream().filter(p -> p.getRoleId() == RoleType.DEPT_HEAD.getId()).collect(Collectors.toList());
    if (additionalRolePermissions.size() > 0) {
      statuses =
          getContentManager().getLmsAppStatusList(pStatus, mRoleManager.get(RoleType.DEPT_HEAD.getId()), user,
              pageNumber, pageSize);
      statusSize = getContentManager().getLmsAppStatusList(pStatus, user, mRoleManager.get(RoleType.DEPT_HEAD.getId())).size();
    } else {
      statuses = getContentManager().getLmsAppStatusList(pStatus, user.getPrimaryRole(), user, pageNumber, pageSize);
      statusSize = getContentManager().getLmsAppStatusList(pStatus, user, user.getPrimaryRole()).size();
    }
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (LmsAppStatus status : statuses) {
      JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
      getBuilder().build(objectBuilder, status, pUriInfo, localCache);
      children.add(objectBuilder);
    }
    object.add("entries", children);
    object.add("totalSize", statusSize);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getLeaveApplications(LeaveApprovalStatus pStatus, UriInfo pUriInfo) {
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    List<LmsAppStatus> statuses = new ArrayList<>();
    List<AdditionalRolePermissions> additionalRolePermissions = mAdditionalRolePermissionsManager.getPermissionsByUser(user.getId()).stream().filter(p -> p.getRoleId() == RoleType.DEPT_HEAD.getId()).collect(Collectors.toList());
    if (additionalRolePermissions.size() > 0) {
      statuses =
          getContentManager().getLmsAppStatusList(pStatus, user, mRoleManager.get(RoleType.DEPT_HEAD.getId()));
    } else {
      statuses = getContentManager().getLmsAppStatusList(pStatus, user, user.getPrimaryRole());
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
