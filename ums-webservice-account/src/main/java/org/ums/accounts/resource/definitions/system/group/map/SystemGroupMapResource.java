package org.ums.accounts.resource.definitions.system.group.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.SystemGroupMap;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;
import org.ums.logs.GetLog;
import org.ums.logs.PostLog;
import org.ums.logs.PutLog;
import org.ums.persistent.model.accounts.PersistentSystemGroupMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Apr-18.
 */
@Component
@Path("/account/definition/system-group-map")
public class SystemGroupMapResource {
  @Autowired
  protected SystemGroupMapResourceHelper mHelper;

  @Context
  private ResourceContext mResourceContext;

  @GET
  @Path("/all")
  @GetLog(message = "Getting all company related System Group Map Information")
  public List<SystemGroupMap> getAllCompanyRelatedData(@Context HttpServletRequest httpServletRequest) throws Exception {
    return mHelper.getAllSystemGroupMapByCompany();
  }

  @GET
  @Path("/id/{id}")
  @GetLog(message = "Getting System Group Map Information by Id")
  public SystemGroupMap get(@Context HttpServletRequest httpServletRequest, @PathParam("id") String pId) {
    return mHelper.get(pId);
  }

  @PUT
  @Path("/update")
  @PutLog(message = "Updating System Group Map Data")
  public SystemGroupMap update(@Context HttpServletRequest httpServletRequest,
      PersistentSystemGroupMap pMutableSystemGroupMap) {
    mHelper.update(pMutableSystemGroupMap);
    return mResourceContext.getResource(SystemGroupMapResource.class).get(httpServletRequest,
        pMutableSystemGroupMap.getId());
  }

  @POST
  @Path("/save")
  @PostLog(message = "Creating a row in System Group Map")
  public SystemGroupMap save(@Context HttpServletRequest httpServletRequest,
      PersistentSystemGroupMap pMutableSystemGroupMap) throws Exception {
    MutableSystemGroupMap systemGroupMap = mHelper.save(pMutableSystemGroupMap);
    return mResourceContext.getResource(SystemGroupMapResource.class).get(httpServletRequest, systemGroupMap.getId());
  }

}
