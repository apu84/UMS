package org.ums.accounts.resource.definitions.system.account.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.SystemAccountMap;
import org.ums.logs.DeleteLog;
import org.ums.logs.GetLog;
import org.ums.logs.PutLog;
import org.ums.persistent.model.accounts.PersistentSystemAccountMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Jun-18.
 */
@Component
@Path("/account/definition/system-account-map")
public class SystemAccountMapResource {

  @Autowired
  private SystemAccountMapResourceHelper mHelper;
  @Context
  private ResourceContext mResourceContext;

  @GET
  @Path("/all")
  @GetLog(message = "Requested for getting all system account map data")
  public List<SystemAccountMap> getAll(@Context HttpServletRequest pHttpServletRequest) {
    return mHelper.getAll();
  }

  @GET
  @Path("/id/{id}")
  @GetLog(message = "Requested for getting system account map data by id")
  public SystemAccountMap get(@Context HttpServletRequest pHttpServletRequest, @PathParam("id") String pId) {
    return mHelper.getById(Long.parseLong(pId));
  }

  @PUT
  @Path("/create-or-update")
  @PutLog(message = "Requested for creating or updating system account map data")
  public SystemAccountMap createOrUpdate(@Context HttpServletRequest pHttpServletRequest,
      PersistentSystemAccountMap pPersistentSystemAccountMap) {
    Long id = mHelper.create(pPersistentSystemAccountMap);
    return mResourceContext.getResource(SystemAccountMapResource.class).get(pHttpServletRequest, id.toString());
  }

  @DELETE
  @DeleteLog(message = "Requested for deleting data")
  public Response delete(@Context HttpServletRequest pHttpServletRequest,
      PersistentSystemAccountMap pPersistentSystemAccountMap) {
    return mHelper.delete(pPersistentSystemAccountMap);
  }

}
