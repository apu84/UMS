package org.ums.employee.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MutablePublicationInformationResource extends Resource {

  @Autowired
  PublicationInformationResourceHelper mPublicationInformationResourceHelper;

  @POST
  @Path("/save")
  public Response saveServiceInformation(final JsonObject pJsonObject) throws InterruptedException, ExecutionException,
      IOException {
    return mPublicationInformationResourceHelper.savePublicationInformation(pJsonObject, mUriInfo);
  }

  @PUT
  @Path("/update/publicationStatus")
  public Response updatePublicationStatus(final JsonObject pJsonObject) {
    return mPublicationInformationResourceHelper.updatePublicationStatus(pJsonObject, mUriInfo);
  }
}
