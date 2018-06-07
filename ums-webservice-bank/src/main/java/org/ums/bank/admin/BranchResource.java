package org.ums.bank.admin;

import java.util.List;

import javax.ws.rs.*;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.bank.branch.Branch;
import org.ums.bank.branch.BranchManager;
import org.ums.bank.branch.PersistentBranch;
import org.ums.resource.Resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Component
@Path("/bank/branch")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class BranchResource extends Resource {
  @Autowired
  BranchManager mBranchManager;

  @GET
  @Path("/{bankId}/all")
  public List<Branch> getAllBranch(@PathParam("bankId") Long pBankId) throws Exception {
    return mBranchManager.getBranchesByBank(pBankId);
  }

  @GET
  @Path("/{id}")
  public Branch getBankBranch(@PathParam("id") Long pId) throws Exception {
    return mBranchManager.get(pId);
  }

  @POST
  @JsonDeserialize(as = PersistentBranch.class)
  public Branch createBankBranch(PersistentBranch pPersistentBankBranch) throws Exception {
    validate(pPersistentBankBranch);
    Long bankBranchId = mBranchManager.create(pPersistentBankBranch);
    return mBranchManager.get(bankBranchId);
  }

  @PUT
  @Path("/{id}")
  @JsonDeserialize(as = PersistentBranch.class)
  public Branch updateBankBranch(@PathParam("id") final Long pId, final PersistentBranch pPersistentBankBranch)
      throws Exception {
    validate(pPersistentBankBranch);
    pPersistentBankBranch.setId(pId);
    mBranchManager.update(pPersistentBankBranch);
    return mBranchManager.get(pId);
  }

  @DELETE
  @Path("/{id}")
  public List<Branch> deleteBranch(@PathParam("id") final Long pId) {
    Branch bankBranch = mBranchManager.get(pId);
    bankBranch.edit().delete();
    return mBranchManager.getAll();
  }

  private void validate(final Branch pBranch) {
    Validate.notEmpty(pBranch.getName(), "Branch name is empty");
    Validate.notEmpty(pBranch.getLocation(), "Branch location is empty");
    Validate.notNull(pBranch.getBankId(), "Bank id is empty");
  }
}
