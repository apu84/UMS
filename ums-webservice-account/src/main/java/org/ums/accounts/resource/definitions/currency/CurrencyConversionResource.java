package org.ums.accounts.resource.definitions.currency;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.CurrencyConversion;
import org.ums.persistent.model.accounts.PersistentCurrencyConversion;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
@Path("/account/definition/currency/conversion")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CurrencyConversionResource extends MutableCurrencyConversionResource {

  @GET
  @Path("/all")
  public List<CurrencyConversion> getAll() throws Exception {
    List<CurrencyConversion> currencyConversions = mCurrencyConversionResourceHelper.getContentManager().getAll();
    return mCurrencyConversionResourceHelper.getContentManager().getAll();
  }

}
