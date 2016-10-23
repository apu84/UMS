package org.ums.resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.manager.BinaryContentManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

@Component
@Path("/profilePicture")
@Produces("image/png")
public class ProfilePicture extends Resource {

  @Autowired
  @Qualifier("fileContentManager")
  BinaryContentManager<byte[]> mBinaryContentManager;

  @GET
  public Response get(final @Context Request pRequest) throws Exception {
    String userId = "";
    Subject subject = SecurityUtils.getSubject();
    if(subject != null) {
      userId = subject.getPrincipal().toString();
    }
    byte[] imageData;
    try {
      imageData = mBinaryContentManager.get(userId, BinaryContentManager.Domain.PICTURE);
    } catch(FileNotFoundException fl) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok(new ByteArrayInputStream(imageData)).build();
  }
}
