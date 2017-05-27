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
  @Path("/getPublicationInformation")
  public JsonObject getPublicationInformation(final @Context Request pRequest) throws Exception {
    return mPublicationInformationResourceHelper.getPublicationInformation(mUriInfo);
  }

  // @GET
  // @Path("/getPublicationInformation/pageNumber/{page-number}/ipp/{itemPerPage}")
  // public JsonObject getPaginatedPublicationInformation(final @Context Request pRequest,
  // final @PathParam("page-number") int pPageNumber, final @PathParam("itemPerPage") int
  // pItemPerPage)
  // throws Exception {
  // return mPublicationInformationResourceHelper
  // .getPaginatedPublicationInformation(pPageNumber, pItemPerPage, mUriInfo);
  // }

  @GET
  @Path("/getPublicationInformation/{employee-id}/{status}")
  public JsonObject getPublicationInformation(final @PathParam("employee-id") String pEmployeeId,
      final @PathParam("status") String pStatus, final @Context Request pRequest) throws Exception {
    return mPublicationInformationResourceHelper.getPublicationInformation(pEmployeeId, pStatus, mUriInfo);
  }

  @GET
  @Path("/getTeachersList/{status}")
  public JsonObject getTeachersList(final @PathParam("status") String pStatus) {
    return mPublicationInformationResourceHelper.getTeachersList(pStatus, mUriInfo);
  }

  // @GET
  // @Path("/getPublication/ipp/{item-per-page}/page/{page}/order/{order}")
  // public JsonObject getPublicationForPagination(final @Context Request pRequest, final
  // @PathParam("item-per-page") int pItemPerPage, final @PathParam("page") int pPage,
  // final @PathParam("order") String pOrder, final @QueryParam("filter") String pFilter) throws
  // Exception{
  // return mPublicationInformationResourceHelper.getPublicationForPagination(pPage, pItemPerPage,
  // pFilter, mUriInfo);
  // }

}
