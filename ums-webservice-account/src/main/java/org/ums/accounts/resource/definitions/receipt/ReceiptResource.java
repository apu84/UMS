package org.ums.accounts.resource.definitions.receipt;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.Receipt;
import org.ums.resource.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Mar-18.
 */
@Component
@Path("account/receipt")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ReceiptResource extends MutableReceiptResource {

  @GET
  @Path("/all")
  public List<Receipt> getAllReceipts(@Context HttpServletRequest pHttpServletRequest) {
    return mHelper.getAllReceipts();
  }
}
