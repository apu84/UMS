package org.ums.resource.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.logs.GetLog;
import org.ums.logs.PostLog;
import org.ums.resource.Resource;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

/**
 * Created by Monjur-E-Morshed on 03-Jul-18.
 */
@Component
@Path("/company")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class CompanyResource extends Resource {
  @Autowired
  private CompanyResourceHelper mCompanyResourceHelper;

  @GET
  @Path("/id/{id}")
  @GetLog(message = "Requested for company")
  public JsonObject getCompany(@Context HttpServletRequest pHttpServletRequest, @PathParam("id") String pId) {
    return mCompanyResourceHelper.getCompany(pId, mUriInfo);
  }

  @GET
  @Path("/all")
  @GetLog(message = "Requested for all company")
  public JsonArray getAllCompany(@Context HttpServletRequest pHttpServletRequest) {
    return mCompanyResourceHelper.getAllCompany(mUriInfo);
  }

  @POST
  @PostLog(message = "Requested for saving new Company")
  public JsonObject saveCompany(@Context HttpServletRequest pHttpServletRequest, JsonObject pJsonObject)
      throws Exception {
    return mCompanyResourceHelper.save(pJsonObject, mUriInfo);
  }

  @PUT
  @PostLog(message = "Requested for updating company")
  public JsonObject updateCompany(@Context HttpServletRequest pHttpServletRequest, JsonObject pJsonObject)
      throws Exception {
    return mCompanyResourceHelper.put(pJsonObject, mUriInfo);
  }
}
