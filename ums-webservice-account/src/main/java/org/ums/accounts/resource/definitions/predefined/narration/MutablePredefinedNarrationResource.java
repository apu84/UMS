package org.ums.accounts.resource.definitions.predefined.narration;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;
import org.ums.persistent.model.accounts.PersistentPredefinedNarration;

import javax.annotation.security.PermitAll;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 13-Jan-18.
 */
public class MutablePredefinedNarrationResource {
  @Autowired
  protected PredefinedNarrationResourceHelper mHelper;

  @POST
  @Path("/save")
  public List<MutablePredefinedNarration> saveAndReturnUpdatedList(List<MutablePredefinedNarration> pPersistentPredefinedNarrations, final @Context Request pRequest){
    return mHelper.createOrUpdate(pPersistentPredefinedNarrations);
  }
}
