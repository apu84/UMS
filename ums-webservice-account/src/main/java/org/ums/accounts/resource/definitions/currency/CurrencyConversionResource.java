package org.ums.accounts.resource.definitions.currency;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
@Path("/account/definition/currency/conversion")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CurrencyConversionResource extends MutableCurrencyConversionResource {

}
