package org.ums.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.DeptDesignationMap;
import org.ums.domain.model.mutable.MutableDeptDesignationMap;
import org.ums.resource.Resource;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("deptDesignationMap")
public class DeptDesignationMapResource extends Resource {

  @Autowired
  ResourceHelper<DeptDesignationMap, MutableDeptDesignationMap, Integer> mHelper;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }
}
