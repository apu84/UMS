package org.ums.resource;

import org.springframework.stereotype.Component;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

@Component
@Path("employee/publication")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class PublicationInformationResource extends MutablePublicationInformationResource {

  @GET
  @Path("/getPublicationInformation/employeeId/{employee-id}")
  public JsonObject getPublicationInformation(final @PathParam("employee-id") String pEmployeeId,
      final @Context Request pRequest) throws Exception {
    return mPublicationInformationResourceHelper.getPublicationInformation(pEmployeeId, mUriInfo);
  }

  @GET
  @Path("/getPublicationInformation/{employee-id}/{status}")
  public JsonObject getPublicationInformation(final @PathParam("employee-id") String pEmployeeId,
      final @PathParam("status") String pStatus, final @Context Request pRequest) throws Exception {
    return mPublicationInformationResourceHelper.getPublicationInformation(pEmployeeId, pStatus, mUriInfo);
  }

  @GET
  @Path("/getPublicationInformation/employeeId/{employee-id}/publicationStatus/{status}/pageNumber/{page}/ipp/{item-per-page}")
  public JsonObject getPublicationForPagination(final @PathParam("employee-id") String pEmployeeId,
      final @PathParam("status") String pPublicationStatus, final @Context Request pRequest,
      final @PathParam("item-per-page") int pItemPerPage, final @PathParam("page") int pPage) throws Exception {
    return mPublicationInformationResourceHelper.getPublicationWithPagination(pEmployeeId, pPublicationStatus, pPage,
        pItemPerPage, mUriInfo);
  }

  @GET
  @Path("/getPublicationInformation/employeeId/{employee-id}/pageNumber/{page}/ipp/{item-per-page}")
  public JsonObject getPublicationForPagination(final @PathParam("employee-id") String pEmployeeId,
      final @Context Request pRequest, final @PathParam("item-per-page") int pItemPerPage,
      final @PathParam("page") int pPage) throws Exception {
    return mPublicationInformationResourceHelper.getPublicationWithPagination(pEmployeeId, pPage, pItemPerPage,
        mUriInfo);
  }

}
