package org.ums.accounts.resource.cheque.register;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.accounts.ChequeRegister;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 26-Feb-18.
 */

@Component
@Path("account/cheque")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ChequeRegisterResource extends MutableChequeRegisterResource {

  @GET
  @Path("/transactionIdList")
  public List<ChequeRegister> getChequeRegister(@QueryParam("transactionIdList") List<String> pTransactionIdList) {
    List<Long> transactionIdList = pTransactionIdList.stream().map(t -> Long.parseLong(t)).collect(Collectors.toList());
    return mHelper.getChequeRegisterList(transactionIdList);
  }
}
