package org.ums.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.domain.model.mutable.MutableDeptDesignationMap;
import org.ums.manager.ContentManager;
import org.ums.manager.DeptDesignationMapManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class DeptDesignationMapResourceHelper extends
    ResourceHelper<DeptDesignationMap, MutableDeptDesignationMap, Integer> {

  @Autowired
  private DeptDesignationMapManager mManager;

  @Autowired
  private DeptDesignationMapBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<DeptDesignationMap, MutableDeptDesignationMap, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<DeptDesignationMap, MutableDeptDesignationMap> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(DeptDesignationMap pReadonly) {
    return pReadonly.getLastModified() == null ? "" : pReadonly.getLastModified();
  }
}
