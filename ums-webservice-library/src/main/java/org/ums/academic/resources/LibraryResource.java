package org.ums.academic.resources;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.manager.LibraryManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by kawsu on 12/5/2016.
 */

@Component
@Path("library")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class LibraryResource extends MutableLibraryResource {

  private static Logger mLogger = org.slf4j.LoggerFactory.getLogger(LibraryResource.class);

  @Autowired
  LibraryManager mManager;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mLibraryResourceHelper.getAllLibraryBooks(mUriInfo);
  }

  @GET
  @Path("/libraryBook/{bookName}")
  public JsonObject getLibraryBooks(final @Context Request pRequest,
      final @PathParam("bookName") String pBook) throws Exception {
    return mLibraryResourceHelper.getTheLibraryBooks(pBook, mUriInfo);
  }
}
