package org.ums.usermanagement;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;
import org.ums.usermanagement.permission.PermissionManager;
import org.ums.usermanagement.permission.UserRolePermissions;
import org.ums.usermanagement.role.Role;
import org.ums.usermanagement.role.RoleManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Set;

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
  @Path("/{role-id}/permissions")
  @RequiresPermissions("user-management")
  public Set<String> getPermissionsByRole(final @PathParam("role-id") Integer pRoleId) {
    return mPermissionManager.getPermissionByRole(mRoleManager.get(pRoleId)).get(0).getPermissions();
  }

  @GET
  @Path("/{user-id}/roles")
  @RequiresPermissions("user-management")
  public Set<Role> getUserRoles(final @PathParam("user-id") String pUserId) {
    return mUserRolePermissions.getUserRoles(pUserId);
  }

  @GET
  @Path("/{user-id}/permissions")
  @RequiresPermissions("user-management")
  public Set<String> getUserPermissions(final @PathParam("user-id") String pUserId) {
    return mUserRolePermissions.getUserRolePermissions(pUserId);
  }
}
