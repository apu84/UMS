package org.ums.common.academic.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resources.helper.LibraryResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by kawsu on 12/5/2016.
 */
public class MutableLibraryResource extends Resource {
  @Autowired
  LibraryResourceHelper mLibraryResourceHelper;

  @POST
  public Response createBook(final JsonObject pJsonObject) {
    return mLibraryResourceHelper.post(pJsonObject, mUriInfo);
  }
}
