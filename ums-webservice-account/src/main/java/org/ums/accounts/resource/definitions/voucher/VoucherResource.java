package org.ums.accounts.resource.definitions.voucher;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.Voucher;
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
@Path("account/definition/voucher")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class VoucherResource extends MutableVoucherResource {

  @GET
  @Path("/all")
  List<Voucher> getAll() {
    return mVoucherManager.getAll();
  }
}
