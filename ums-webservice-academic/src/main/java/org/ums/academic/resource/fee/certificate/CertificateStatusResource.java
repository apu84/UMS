package org.ums.academic.resource.fee.certificate;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.fee.FeeTypeManager;
import org.ums.resource.Resource;

@Component
@Path("/certificate-status")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CertificateStatusResource extends Resource {
  @Autowired
  CertificateStatusHelper mCertificateStatusHelper;
  @Autowired
  FeeTypeManager mFeeTypeManager;

  private int mDefaultNoOfItems = 20;

  @POST
  @Path("/paginated")
  public JsonObject getCertificateStatus(@QueryParam("pageNumber") Integer pageNumber,
      @QueryParam("itemsPerPage") Integer itemsPerPage, @QueryParam("feeType") Integer feeType, JsonObject pJsonObject)
      throws Exception {
    return mCertificateStatusHelper.getFilteredCertificateStatus(itemsPerPage == null ? mDefaultNoOfItems
        : itemsPerPage, pageNumber == null ? 1 : pageNumber, feeType == null ? null : mFeeTypeManager.get(feeType),
        pJsonObject, mUriInfo);
  }

  @GET
  @Path("/filters")
  public JsonArray getFilters() throws Exception {
    return mCertificateStatusHelper.getFilterItems();
  }

  @GET
  public JsonObject getCertificateStatus() throws Exception {
    return mCertificateStatusHelper.getCertificateStatus(getLoggedInUserId(), mUriInfo);
  }

  @PUT
  public Response updateCertificateStatus(JsonObject pJsonObject) throws Exception {
    return mCertificateStatusHelper.updateCertificateStatus(pJsonObject, mUriInfo);
  }
}
