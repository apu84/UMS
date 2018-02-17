package org.ums.usermanagement;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.*;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;
import org.ums.usermanagement.permission.PermissionManager;
import org.ums.usermanagement.permission.UserRolePermissions;
import org.ums.usermanagement.role.PersistentRole;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.role.RoleManager;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Component
@Path("/user-management")
@Produces(Resource.MIME_TYPE_JSON)
public class UserManagementResource extends Resource {
  @Autowired
  RoleManager mRoleManager;
  @Autowired
  PermissionManager mPermissionManager;
  @Autowired
  UserRolePermissions mUserRolePermissions;

  @GET
  @Path("/all/roles")
  @RequiresPermissions("user-management")
  public List<Role> getAllRoles() {
    return mRoleManager.getAll();
  }

  @GET
  @Path("/all/permissions")
  @RequiresPermissions("user-management")
  public Set<String> getAllPermissions() {
    return mUserRolePermissions.getAllPermissions();
  }

  @GET
  @Path("/role/{role-id}/permissions")
  @RequiresPermissions("user-management")
  public Set<String> getPermissionsByRole(final @PathParam("role-id") Integer pRoleId) {
    return mPermissionManager.getPermissionByRole(mRoleManager.get(pRoleId)).get(0).getPermissions();
  }

  @GET
  @Path("/user/{user-id}/roles")
  @RequiresPermissions("user-management")
  public List<Role> getUserRoles(final @PathParam("user-id") String pUserId) {
    return mUserRolePermissions.getUserRoles(pUserId);
  }

  @GET
  @Path("/user/{user-id}/permissions")
  @RequiresPermissions("user-management")
  public Set<String> getUserPermissions(final @PathParam("user-id") String pUserId) {
    return mUserRolePermissions.getUserAdditionalPermissions(pUserId);
  }

  @PUT
  @Path("/user/{user-id}/roles")
  @RequiresPermissions("user-management")
  @JsonDeserialize(as = PersistentRole.class)
  public List<Role> updateUserRoles(final @PathParam("user-id") String pUserId, final List<PersistentRole> pRoles) {
    mUserRolePermissions.updateUserRoles(pUserId, pRoles.stream().map(Role.class::cast).collect(Collectors.toList()));
    return mUserRolePermissions.getUserRoles(pUserId);
  }

  @PUT
  @Path("/user/{user-id}/permissions")
  @RequiresPermissions("user-management")
  public Set<String> updateUserPermissions(final @PathParam("user-id") String pUserId, final Set<String> pPermissions) {
    mUserRolePermissions.updateUserPermissions(pUserId, pPermissions);
    return mUserRolePermissions.getUserAdditionalPermissions(pUserId);
  }

  @PUT
  @Path("/role/{role-id}/permissions")
  @RequiresPermissions("user-management")
  public Set<String> updateRolePermissions(final @PathParam("role-id") Integer pRoleId, final Set<String> pPermissions) {
    mUserRolePermissions.updateRolePermissions(pRoleId, pPermissions);
    return mPermissionManager.getPermissionByRole(mRoleManager.get(pRoleId)).get(0).getPermissions();
  }
}
