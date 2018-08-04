package org.ums.accounts.resource.definitions.predefined.narration;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.resource.Resource;
import org.ums.util.Utils;

import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 13-Jan-18.
 */
@Component
@Path("account/definition/narration")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PredefinedNarrationResource extends MutablePredefinedNarrationResource {

  @GET
  @Path("/all")
  public List<PredefinedNarration> getAll(final @Context Request pRequest) {
    return mHelper.getContentManager().getAll(Utils.getCompany());
  }

  @POST
  @Path("/save")
  public List<PredefinedNarration> saveAndReturnUpdatedList(JsonArray pPersistentPredefinedNarrations,
      final @Context Request pRequest) {
    return mHelper.createOrUpdate(pPersistentPredefinedNarrations);
  }

}
