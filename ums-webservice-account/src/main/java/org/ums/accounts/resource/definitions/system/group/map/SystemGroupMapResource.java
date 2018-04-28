package org.ums.accounts.resource.definitions.system.group.map;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.SystemGroupMap;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;

import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Apr-18.
 */
@Component
@Path("/account/definition/system-group-map")
public class SystemGroupMapResource extends MutableSystemGroupResource {

  @Context
  private ResourceContext mResourceContext;

  @GET
  @Path("/all")
  public List<SystemGroupMap> getAllCompanyRelatedData() throws Exception {
    return mHelper.getAllSystemGroupMapByCompany();
  }

  @GET
  @Path("/id/{id}")
  public SystemGroupMap get(@PathParam("id") String pId) {
    return mHelper.get(pId);
  }

  @PUT
  @Path("/update")
  public SystemGroupMap update(MutableSystemGroupMap pMutableSystemGroupMap) {
    mHelper.update(pMutableSystemGroupMap);
    return mResourceContext.getResource(SystemGroupMapResource.class).get(pMutableSystemGroupMap.getId());
  }

  @POST
  @Path("/save")
  public SystemGroupMap save(MutableSystemGroupMap pMutableSystemGroupMap) throws Exception {
    mHelper.save(pMutableSystemGroupMap);
    return mResourceContext.getResource(SystemGroupMapResource.class).get(
        pMutableSystemGroupMap.getGroupType().getValue());
  }

}
