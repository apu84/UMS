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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("deptDesignationMap")
public class DeptDesignationMapResource extends Resource {

  @Autowired
  DeptDesignationMapResourceHelper mHelper;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }

  @GET
  @Path("/dept/{dept-id}/employeeType/{employee-type}")
  public JsonObject getDeptDesignationMap(final @Context Request pRequest, final @PathParam("dept-id") String pDeptId,
      final @PathParam("employee-type") int pEmployeeTypeId) throws Exception {
    return mHelper.getDeptDesignationMap(pDeptId, pEmployeeTypeId, mUriInfo);
  }
}
