package org.ums.resource.leavemanagement;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

/**
 * Created by Monjur-E-Morshed on 14-May-17.
 */
public class MutableLmsApplicationResource extends Resource {

  @Autowired
  protected LmsApplicationResourceHelper mHelper;

  @POST
  @Path("/save")
  public JsonObject saveApplication(final JsonObject pJsonObject) throws Exception {
    return mHelper.saveApplication(pJsonObject, mUriInfo);
  }

  @POST
  @Path("/upload")
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  public Response uploadFile(@FormDataParam("files") File pInputStream, @FormDataParam("id") String id,
      @FormDataParam("name") String name) throws IOException {
    return mHelper.uploadFile(pInputStream, id, name, mUriInfo);
  }

}
