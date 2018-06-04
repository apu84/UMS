package org.ums.accounts.resource.definitions.system.account.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.SystemAccountMap;
import org.ums.persistent.model.accounts.PersistentSystemAccountMap;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Jun-18.
 */
@Component
@Path("/account/definition/system-group-map")
public class SystemAccountMapResource {

  @Autowired
  private SystemAccountMapResourceHelper mHelper;

  @GET
  public List<SystemAccountMap> getAll(@Context HttpServletRequest pHttpServletRequest) {
    return mHelper.getAll();
  }

  @PUT
  public SystemAccountMap createOrUpdate(@Context HttpServletRequest pHttpServletRequest,
      PersistentSystemAccountMap pPersistentSystemAccountMap) {
    return mHelper.create(pPersistentSystemAccountMap);
  }

  @DELETE
  public Response delete(@Context HttpServletRequest pHttpServletRequest,
      PersistentSystemAccountMap pPersistentSystemAccountMap) {
    return mHelper.delete(pPersistentSystemAccountMap);
  }

}
