package org.ums.accounts.resource.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.persistent.model.accounts.PersistentGroup;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 24-Dec-17.
 */
public class MutableGroupResource {
  @Autowired
  protected GroupResourceHelper mHelper;

  @POST
  @Path("/save")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<Group> saveAndReturnUpdatedGroupList(PersistentGroup pMutableGroup) {
    PersistentGroup mGroup = new PersistentGroup();
    mGroup = pMutableGroup;
    return mHelper.saveAndReturnUpdatedGroups(mGroup);
  }
}
