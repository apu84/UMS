package org.ums.accounts.resource.definitions.voucher;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.Voucher;
import org.ums.resource.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
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
  public List<Voucher> getAll(final @Context HttpServletRequest pHttpServletRequest) {
    System.out.println(pHttpServletRequest.getRequestURI());
    return mVoucherManager.getAll();
  }

  @GET
  @Path("/id/{id}")
  public Voucher getVoucher(@PathParam("id") String pId) {
    return mVoucherManager.get(Long.parseLong(pId));
  }
}
