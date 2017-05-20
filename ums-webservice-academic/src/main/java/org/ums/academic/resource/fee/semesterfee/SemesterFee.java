package org.ums.academic.resource.fee.semesterfee;

import javax.json.JsonObject;
import javax.ws.rs.*;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.User;
import org.ums.manager.UserManager;
import org.ums.resource.Resource;

@Component
@Path("/semester-fee")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SemesterFee extends Resource {
  @Autowired
  SemesterFeeHelper mSemesterFeeHelper;

  @Autowired
  UserManager mUserManager;

  @GET
  @Path("/status/{semesterId}")
  public UGSemesterFee.UGSemesterFeeResponse getSemesterFeeStatus(final @PathParam("semesterId") int pSemesterId)
      throws Exception {
    return mSemesterFeeHelper.getSemesterFeeStatus(getLoggedInUser(), pSemesterId);
  }

  @GET
  @Path("/semester-installment-status/{semesterId}")
  public UGSemesterFee.UGSemesterFeeResponse getSemesterInstallmentStatus(final @PathParam("semesterId") int pSemesterId)
      throws Exception {
    return mSemesterFeeHelper.getInstallmentStatus(pSemesterId, getLoggedInUser());
  }

  @GET
  @Path("/installment-status/{semesterId}")
  public UGSemesterFee.UGSemesterFeeResponse getInstallmentStatus(final @PathParam("semesterId") int pSemesterId)
      throws Exception {
    return mSemesterFeeHelper.getInstallmentStatus(getLoggedInUser(), pSemesterId);
  }

  @GET
  @Path("/within-admission-slot/{semesterId}")
  public Boolean withInAdmissionSlot(final @PathParam("semesterId") int pSemesterId) throws Exception {
    return mSemesterFeeHelper.withInAdmissionSlot(getLoggedInUser(), pSemesterId);
  }

  @GET
  @Path("/fee/{semesterId}")
  public JsonObject getFee(final @PathParam("semesterId") int pSemesterId) throws Exception {
    return mSemesterFeeHelper.generatePayable(mSemesterFeeHelper.getFee(getLoggedInUser(), pSemesterId));
  }

  @GET
  @Path("/within-first-installment-slot/{semesterId}")
  public Boolean withInFirstInstallmentSlot(final @PathParam("semesterId") int pSemesterId) throws Exception {
    return mSemesterFeeHelper.withinFirstInstallmentSlot(getLoggedInUser(), pSemesterId);
  }

  @GET
  @Path("/within-second-installment-slot/{semesterId}")
  public Boolean withInSecondInstallmentSlot(final @PathParam("semesterId") int pSemesterId) throws Exception {
    return mSemesterFeeHelper.withinSecondInstallmentSlot(getLoggedInUser(), pSemesterId);
  }

  @GET
  @Path("/first-installment/{semesterId}")
  public JsonObject firstInstallment(final @PathParam("semesterId") int pSemesterId) throws Exception {
    return mSemesterFeeHelper.generatePayable(mSemesterFeeHelper.firstInstallment(getLoggedInUser(), pSemesterId));
  }

  @GET
  @Path("/second-installment/{semesterId}")
  public JsonObject secondInstallment(final @PathParam("semesterId") int pSemesterId) throws Exception {
    return mSemesterFeeHelper.generatePayable(mSemesterFeeHelper.secondInstallment(getLoggedInUser(), pSemesterId));
  }

  @GET
  @Path("/admission-status/{semesterId}")
  public UGSemesterFee.UGSemesterFeeResponse getAdmissionStatus(final @PathParam("semesterId") int pSemesterId)
      throws Exception {
    return mSemesterFeeHelper.getAdmissionStatus(getLoggedInUser(), pSemesterId);
  }

  @GET
  @Path("/installment-available/{semesterId}")
  public Boolean installmentAvailble(final @PathParam("semesterId") int pSemesterId) throws Exception {
    return mSemesterFeeHelper.installmentAvailable(getLoggedInUser(), pSemesterId);
  }

  @POST
  @Path("/pay/{semesterId}")
  public UGSemesterFee.UGSemesterFeeResponse pay(final @PathParam("semesterId") int pSemesterId) throws Exception {
    return mSemesterFeeHelper.pay(getLoggedInUser(), pSemesterId);
  }

  @POST
  @Path("/pay/first-installment/{semesterId}")
  public UGSemesterFee.UGSemesterFeeResponse payFirstInstallment(final @PathParam("semesterId") int pSemesterId)
      throws Exception {
    return mSemesterFeeHelper.payFirstInstallment(getLoggedInUser(), pSemesterId);
  }

  @POST
  @Path("/pay/second-installment/{semesterId}")
  public UGSemesterFee.UGSemesterFeeResponse paySecondInstallment(final @PathParam("semesterId") int pSemesterId)
      throws Exception {
    return mSemesterFeeHelper.paySecondInstallment(getLoggedInUser(), pSemesterId);
  }

  private String getLoggedInUser() {
    User user = mUserManager.get(SecurityUtils.getSubject().toString());
    if(!user.getPrimaryRole().getName().equalsIgnoreCase("student")) {
      throw new IllegalArgumentException("User is not a student");
    }
    return SecurityUtils.getSubject().toString();
  }
}
