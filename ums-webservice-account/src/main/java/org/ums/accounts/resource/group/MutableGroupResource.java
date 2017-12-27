package org.ums.accounts.resource.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableGroup;
import org.ums.persistent.model.accounts.PersistentGroup;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
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

  @POST
  @Path("/delete")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<Group> deleteAndReturnUpdatedGroupList(PersistentGroup persistentGroup) {
    PersistentGroup mGroup = new PersistentGroup();
    mGroup = persistentGroup;
    return mHelper.deleteAndReturnUpdatedGroups(mGroup);
  }

  @POST
  @Path("/saveAll")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<Group> saveListAndReturnUpdatedGroupList(List<PersistentGroup> pPersistentGroups) {
    List<MutableGroup> groups = new ArrayList<>(pPersistentGroups);
    return mHelper.saveGroupListAndReturnUpdatedList(groups);
  }
}
