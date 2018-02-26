package org.ums.accounts.resource.cheque.register;

import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Monjur-E-Morshed on 26-Feb-18.
 */

@Component
@Path("account/cheque")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class ChequeRegisterResource extends MutableChequeRegisterResource {

}
