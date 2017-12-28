package org.ums.accounts.resource.definitions.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.manager.accounts.GroupManager;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 24-Dec-17.
 */
@Component
@Path("/account/definition/group")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class GroupResource extends MutableGroupResource {

  @Autowired
  GroupManager mGroupManager;

  @GET
  @Path("/all")
  public List<Group> getAll(UriInfo pUriInfo) throws Exception {
    return mGroupManager.getAll();
  }

}
