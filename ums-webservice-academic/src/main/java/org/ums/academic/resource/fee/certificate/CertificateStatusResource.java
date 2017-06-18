package org.ums.academic.resource.fee.certificate;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.Resource;

@Component
@Path("/certificate-status")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CertificateStatusResource extends Resource {
  @Autowired
  CertificateStatusHelper mCertificateStatusHelper;

  @GET
  @Path("/paginated")
  public JsonObject getCertificateStatus(@QueryParam("pageNumber") Integer pageNumber,
      @QueryParam("itemsPerPage") Integer itemsPerPage) throws Exception {
    return mCertificateStatusHelper.getCertificateStatus(itemsPerPage == null ? 0 : itemsPerPage,
        pageNumber == null ? 1 : pageNumber, mUriInfo);
  }

  @GET
  @Path("/filtered/{status}")
  public JsonObject getCertificateStatus(@PathParam("status") Integer status,
      @QueryParam("pageNumber") Integer pageNumber, @QueryParam("itemsPerPage") Integer itemsPerPage) throws Exception {
    return mCertificateStatusHelper.getFilteredCertificateStatus(itemsPerPage == null ? 0 : itemsPerPage,
        pageNumber == null ? 1 : pageNumber, status, mUriInfo);
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
