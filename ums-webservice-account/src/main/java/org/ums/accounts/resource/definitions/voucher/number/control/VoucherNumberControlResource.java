package org.ums.accounts.resource.definitions.voucher.number.control;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.VoucherNumberControl;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */

@Component
@Path("account/definition/voucher-number-control")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class VoucherNumberControlResource extends MutableVoucherNumberControlResource {

  @GET
  @Path("/based-on-curr-fin-year")
  public List<VoucherNumberControl> getVoucherNumberControlBasedOnCurrentFinancialYear() {
    return mHelper.getContentManager().getByCurrentFinancialYear();
  }

}
