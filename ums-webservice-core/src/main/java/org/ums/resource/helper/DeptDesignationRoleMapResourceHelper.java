package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.DeptDesignationRoleMapBuilder;
import org.ums.domain.model.immutable.DeptDesignationRoleMap;
import org.ums.domain.model.mutable.MutableDeptDesignationRoleMap;
import org.ums.manager.ContentManager;
import org.ums.manager.DeptDesignationRoleMapManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeptDesignationRoleMapResourceHelper extends
    ResourceHelper<DeptDesignationRoleMap, MutableDeptDesignationRoleMap, Integer> {

  @Autowired
  private DeptDesignationRoleMapManager mManager;

  @Autowired
  private DeptDesignationRoleMapBuilder mBuilder;

  public JsonObject getDeptDesignationMap(final String pDeptId, final int pEmployeeTypeId, UriInfo pUriInfo) {
    List<DeptDesignationRoleMap> deptDesignationRoleMap = new ArrayList<>();
    deptDesignationRoleMap = mManager.getDeptDesignationMap(pDeptId, pEmployeeTypeId);
    return buildJsonResponse(deptDesignationRoleMap, pUriInfo);
  }

  public JsonObject getDeptDesignationMap(final String pDeptId, UriInfo pUriInfo) {
    List<DeptDesignationRoleMap> deptDesignationRoleMap = new ArrayList<>();
    deptDesignationRoleMap = mManager.getDeptDesignationMap(pDeptId);
    return buildJsonResponse(deptDesignationRoleMap, pUriInfo);
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<DeptDesignationRoleMap, MutableDeptDesignationRoleMap, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<DeptDesignationRoleMap, MutableDeptDesignationRoleMap> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(DeptDesignationRoleMap pReadonly) {
    return pReadonly.getLastModified() == null ? "" : pReadonly.getLastModified();
  }
}
