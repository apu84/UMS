package org.ums.resource;

import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.PersonalInformationResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
@Path("employee/personal")
public class PersonalInformationResource extends MutablePersonalInformationResource {

  @GET
  @Path("/getPersonalInformation/employeeId/{employee-id}")
  public JsonObject getPersonalInformation(final @PathParam("employee-id") String pEmployeeId,
      final @Context Request pRequest) {
    return mResourceHelper.getPersonalInformation(pEmployeeId, mUriInfo);
  }
}
