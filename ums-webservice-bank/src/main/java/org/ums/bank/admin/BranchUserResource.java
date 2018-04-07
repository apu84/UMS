package org.ums.bank.admin;

import java.util.List;

import javax.ws.rs.*;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.bank.branch.PersistentBranch;
import org.ums.bank.branch.user.BranchUser;
import org.ums.bank.branch.user.BranchUserManager;
import org.ums.bank.branch.user.PersistentBranchUser;
import org.ums.resource.Resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Component
@Path("/bank/branch/user")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class BranchUserResource extends Resource {
  @Autowired
  BranchUserManager mBranchUserManager;

  @GET
  @Path("/all")
  public List<BranchUser> getAllBranchUsers() throws Exception {
    return mBranchUserManager.getAll();
  }

  @GET
  @Path("/{id}")
  public BranchUser getBranchUser(@PathParam("id") Long pId) throws Exception {
    return mBranchUserManager.get(pId);
  }

  @POST
  @JsonDeserialize(as = PersistentBranchUser.class)
  public BranchUser createBankBranch(PersistentBranchUser pPersistentBranchUser) throws Exception {
    validate(pPersistentBranchUser);
    Long bankBranchId = mBranchUserManager.create(pPersistentBranchUser);
    return mBranchUserManager.get(bankBranchId);
  }

  @PUT
  @Path("/{id}")
  @JsonDeserialize(as = PersistentBranch.class)
  public BranchUser updateBankBranch(@PathParam("id") final Long pId, final PersistentBranchUser pPersistentBranchUser)
      throws Exception {
    validate(pPersistentBranchUser);
    pPersistentBranchUser.setId(pId);
    mBranchUserManager.update(pPersistentBranchUser);
    return mBranchUserManager.get(pId);
  }

  @DELETE
  @Path("/{id}")
  public List<BranchUser> deleteBranchUser(@PathParam("id") final Long pId) {
    BranchUser branchUser = mBranchUserManager.get(pId);
    branchUser.edit().delete();
    return mBranchUserManager.getAll();
  }

  private void validate(final BranchUser pBranchUser) {
    Validate.notEmpty(pBranchUser.getName());
    Validate.notEmpty(pBranchUser.getEmail());
    Validate.notNull(pBranchUser.getBankDesignationId());
    Validate.notNull(pBranchUser.getBranchId());
  }
}
