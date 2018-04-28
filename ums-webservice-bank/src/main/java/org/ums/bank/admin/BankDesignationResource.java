package org.ums.bank.admin;

import java.util.List;

import javax.ws.rs.*;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.bank.designation.BankDesignation;
import org.ums.bank.designation.BankDesignationManager;
import org.ums.bank.designation.PersistentBankDesignation;
import org.ums.resource.Resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Component
@Path("/bank/designation")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class BankDesignationResource extends Resource {
  @Autowired
  BankDesignationManager mBankDesignationManager;

  @GET
  @Path("/all")
  public List<BankDesignation> getAllDesignations() throws Exception {
    return mBankDesignationManager.getAll();
  }

  @GET
  @Path("/{id}")
  public BankDesignation getBankDesignation(@PathParam("id") Long pId) throws Exception {
    return mBankDesignationManager.get(pId);
  }

  @POST
  @JsonDeserialize(as = PersistentBankDesignation.class)
  public BankDesignation createBankDesignation(PersistentBankDesignation pPersistentBankDesignation) throws Exception {
    Validate.notEmpty(pPersistentBankDesignation.getName());
    Long bankDesignationId = mBankDesignationManager.create(pPersistentBankDesignation);
    return mBankDesignationManager.get(bankDesignationId);
  }

  @PUT
  @Path("/{id}")
  @JsonDeserialize(as = PersistentBankDesignation.class)
  public BankDesignation updateBankDesignation(@PathParam("id") final Long pId,
      final PersistentBankDesignation pPersistentBankDesignation) throws Exception {
    Validate.notEmpty(pPersistentBankDesignation.getName());
    pPersistentBankDesignation.setId(pId);
    mBankDesignationManager.update(pPersistentBankDesignation);
    return mBankDesignationManager.get(pId);
  }

  @DELETE
  @Path("/{id}")
  public List<BankDesignation> deleteDesignation(@PathParam("id") final Long pId) {
    BankDesignation bankDesignation = mBankDesignationManager.get(pId);
    bankDesignation.edit().delete();
    return mBankDesignationManager.getAll();
  }
}
