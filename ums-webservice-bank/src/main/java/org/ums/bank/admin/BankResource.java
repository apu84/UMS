package org.ums.bank.admin;

import java.util.List;

import javax.ws.rs.*;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.bank.Bank;
import org.ums.bank.BankManager;
import org.ums.bank.PersistentBank;
import org.ums.resource.Resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Component
@Path("/bank")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class BankResource extends Resource {
  @Autowired
  BankManager mBankManager;

  @GET
  @Path("/all")
  public List<Bank> getAllBanks() throws Exception {
    return mBankManager.getAll();
  }

  @GET
  @Path("/{id}")
  public Bank getBank(@PathParam("id") Long pId) throws Exception {
    return mBankManager.get(pId);
  }

  @POST
  @JsonDeserialize(as = PersistentBank.class)
  public Bank createBank(PersistentBank pPersistentBank) throws Exception {
    Validate.notEmpty(pPersistentBank.getName());
    Long bankId = mBankManager.create(pPersistentBank);
    return mBankManager.get(bankId);
  }

  @PUT
  @Path("/{id}")
  @JsonDeserialize(as = PersistentBank.class)
  public Bank updateBank(@PathParam("id") final Long pId, final PersistentBank pPersistentBank) throws Exception {
    Validate.notEmpty(pPersistentBank.getName());
    pPersistentBank.setId(pId);
    mBankManager.update(pPersistentBank);
    return mBankManager.get(pId);
  }
}
