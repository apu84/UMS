package org.ums.accounts.resource.definitions.financial.account.year;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.FinancialAccountYear;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 28-Dec-17.
 */
@Component
@Path("/account/definition/financialAccountYear")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class FinancialAccountYearResource extends MutableFinancialAccountyearResource {

  @GET
  @Path("/all")
  public List<FinancialAccountYear> getAll() {
    return mFinancialAccountYearManager.getAll();
  }

}
