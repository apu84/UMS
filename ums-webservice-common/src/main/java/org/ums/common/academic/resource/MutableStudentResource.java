package org.ums.common.academic.resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.ums.academic.model.PersistentUser;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.StudentResourceHelper;
import org.ums.domain.model.MutableRole;
import org.ums.domain.model.MutableUser;
import org.ums.domain.model.Role;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class MutableStudentResource extends Resource {
  @Autowired
  StudentResourceHelper mResourceHelper;

  @Autowired
  @Qualifier("roleManager")
  ContentManager<Role, MutableRole, Integer> mRoleManager;

  @POST
  public Response create(final JsonObject pJsonObject) throws Exception {

    Response response = mResourceHelper.post(pJsonObject, mUriInfo);

    String UPLOAD_PATH = "F:/temp/";
    try {

      String encodingPrefix = "base64,", data = pJsonObject.getString("imageData");
      int contentStartIndex = data.indexOf(encodingPrefix) + encodingPrefix.length();
      byte[] imageData = Base64.getDecoder().decode(data.substring(contentStartIndex));

      FileOutputStream fileOutputStream = new FileOutputStream(UPLOAD_PATH + pJsonObject.getString("id"));
      fileOutputStream.write(imageData);
      fileOutputStream.flush();
      fileOutputStream.close();

    } catch (IOException e) {
      throw new WebApplicationException("Error while uploading file. Please try again !!");
    }

    MutableUser studentUser = new PersistentUser();
    studentUser.setId(pJsonObject.getString("id"));
    studentUser.setPassword("testPassword".toCharArray());
    studentUser.setRole(mRoleManager.get(11));
    studentUser.setActive(true);
    studentUser.commit(false);
    return response;
  }

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response update(final @PathParam("object-id") String pObjectId,
                         final @Context Request pRequest,
                         final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
                         final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.put(pObjectId, pRequest, pIfMatchHeader, pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response delete(final @PathParam("object-id") String pObjectId) throws Exception {
    return mResourceHelper.delete(pObjectId);
  }
}
