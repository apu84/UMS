package org.ums.accounts.resource.definitions.predefined.narration;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    return mHelper.getContentManager().getAll();
  }

}
