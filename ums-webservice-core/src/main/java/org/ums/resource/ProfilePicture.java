package org.ums.resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.integration.FileWriterGateway;
import org.ums.integration.MessagePrinter;
import org.ums.manager.BinaryContentManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Component
@Path("/profilePicture")
@Produces("image/png")
public class ProfilePicture extends Resource {

  private static final Logger mLogger = LoggerFactory.getLogger(ProfilePicture.class);

  @Autowired
  @Qualifier("fileContentManager")
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Autowired
  private FileWriterGateway mGateway;

  @Autowired
  MessagePrinter mMessagePrinter;

  @GET
  public Response get(final @Context Request pRequest) {
    String userId = "";
    Subject subject = SecurityUtils.getSubject();
    if(subject != null) {
      userId = subject.getPrincipal().toString();
    }
    InputStream imageData;

    imageData = mGateway.read("files/user.png");
    try {
    } catch(Exception fl) {
      mLogger.error(fl.getMessage());
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    return Response.ok(imageData).build();
  }
}
