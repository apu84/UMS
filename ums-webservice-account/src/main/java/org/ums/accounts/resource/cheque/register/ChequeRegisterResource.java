package org.ums.accounts.resource.cheque.register;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.accounts.MutableChequeRegister;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

  @POST
  @Path("/transactionIdList")
  public String getChequeRegister(List<String> pTransactionIdList) throws Exception {
    List<Long> transactionIdList = pTransactionIdList.stream().map(t -> Long.parseLong(t)).collect(Collectors.toList());
    List<MutableChequeRegister> chequeRegisters = mHelper.getChequeRegisterList(transactionIdList);
    ObjectMapper mapper = new ObjectMapper();
    String jsonString = mapper.writeValueAsString(chequeRegisters);
    return jsonString;
  }
}
