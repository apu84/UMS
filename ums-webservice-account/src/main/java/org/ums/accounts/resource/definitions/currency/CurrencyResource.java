package org.ums.accounts.resource.definitions.currency;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
@Path("/account/definition/currency")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CurrencyResource extends MutableCurrencyResource {

  @GET
  @Path("/all")
  public List<Currency> getAll() {
    return mCurrencyResourceHelper.getContentManager().getAll();
  }
}
