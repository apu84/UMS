package org.ums.resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.UserGuideResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;

/**
 * Created by Ifti on 13-Dec-16.
 */
@Component
@Path("/userGuide")
@Produces(Resource.MIME_TYPE_JSON)
public class UserGuideResource extends Resource {

  @Autowired
  UserGuideResourceHelper mUserGuideResourceHelper;

  @GET
  public JsonObject getUserGuides(final @Context Request pRequest) {
    return mUserGuideResourceHelper.getUserGuides(SecurityUtils.getSubject().getPrincipal().toString(), mUriInfo);
  }

  @GET
  @Path("/html/{navigationId}")
  public JsonObject getUserGuide(final @Context Request pRequest, final @PathParam("navigationId") Integer pNavigationId) {
    return mUserGuideResourceHelper.getUserGuide(pNavigationId);
  }

  @GET
  @Produces({"application/pdf"})
  @Path("/pdf/{navigationId}")
  public StreamingOutput getUserGuidePdf(final @Context Request pRequest,
      final @PathParam("navigationId") String pNavigationId) {
    // To Do: User wise manual access validation need to be done
    return output -> {
      File toBeCopied = new File("F:\\IUMS-Manual\\" + pNavigationId + ".pdf");
      try {
        java.nio.file.Path path = toBeCopied.toPath();
        Files.copy(path, output);
        output.flush();
      } catch(Exception e) {
        throw new WebApplicationException(e);
      }
    };
  }
}
