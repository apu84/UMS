package org.ums.academic.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.academic.resources.helper.LibraryResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class MutableLibraryResource extends Resource {
  @Autowired
  LibraryResourceHelper mLibraryResourceHelper;

  @POST
  public Response createBook(final JsonObject pJsonObject) {
    return mLibraryResourceHelper.post(pJsonObject, mUriInfo);
  }

  @DELETE
  @Path("/delete/book/{bookName}/author/{authorName}")
  public Response deleteByBookAndAuthor(final @PathParam("bookName") String pbookName,
      final @PathParam("authorName") String pAuthorName) {
    return mLibraryResourceHelper.deleteByBookAndAuthor(pbookName.toString(),
        pAuthorName.toString());
  }
}
