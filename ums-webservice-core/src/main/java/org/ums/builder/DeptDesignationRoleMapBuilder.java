package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.DeptDesignationRoleMap;
import org.ums.domain.model.mutable.MutableDeptDesignationRoleMap;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class DeptDesignationRoleMapBuilder implements Builder<DeptDesignationRoleMap, MutableDeptDesignationRoleMap> {
  @Override
  public void build(JsonObjectBuilder pBuilder, DeptDesignationRoleMap pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("departmentId", pReadOnly.getDepartmentId());
    pBuilder.add("employeeTypeId", pReadOnly.getEmployeeTypeId());
    pBuilder.add("designationId", pReadOnly.getDesignationId());
    pBuilder.add("roleId", pReadOnly.getRoleId());
    pBuilder.add("lastModified", pReadOnly.getLastModified() == null ? "" : pReadOnly.getLastModified());
  }

  @Override
  public void build(MutableDeptDesignationRoleMap pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
