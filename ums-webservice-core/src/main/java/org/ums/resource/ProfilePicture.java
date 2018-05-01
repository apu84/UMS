package org.ums.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.scenario.effect.ImageData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.ums.integration.FileWriterGateway;
import org.ums.manager.BinaryContentManager;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Date;

@Component
@Path("/profilePicture")
@Produces("image/png")
public class ProfilePicture extends Resource {

  private static final Logger mLogger = LoggerFactory.getLogger(ProfilePicture.class);

  @Autowired
  @Qualifier("fileContentManager")
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Value("${user.default.image}")
  private String mDefaultImage;

  @Autowired
  private FileWriterGateway mGateway;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  private SessionFactory<FTPFile> ftpSessionFactory;

  @GET
  @Path("/{image-id}")
  /**
   * For each login attempt, we call this endpoint twice which is not good.
   * We should try to make only one call. Two call mean two rest call with two ftp file read operation.
   */
  public Response get(@Context HttpServletRequest pHttpServletRequest, final @PathParam("image-id") String pImageId) {
    String photoId = "";
    String userId = null;
    System.out.println(new Date());
    if(pImageId.equals("0")) {
      userId = SecurityUtils.getSubject().getPrincipal().toString();
      User user = mUserManager.get(userId);
      photoId =
          user.getPrimaryRole().getId() == 11 || user.getId().equals("sadmin") ? user.getId() : user.getEmployeeId();
    }
    else {
      photoId = pImageId;
    }
    InputStream imageData;

    try {
      imageData = mGateway.read("files/user-photo/" + (photoId + ".jpg"));
    } catch(Exception e) {
      if(e instanceof NoRouteToHostException || e.getCause().getMessage().equals("Read timed out")) {
        mLogger.error("Failed to connect with ftp server", e);
        imageData = getDefaultImage();
        if(imageData == null)
          return Response.status(Response.Status.NOT_FOUND).build();
        mLogger.info("[" + userId + "]: Using default user photo.", e);
      }
      else {
        try {
          mLogger.error("[" + userId + "] :" + photoId + ".jpg image not found", e);
          imageData = mGateway.read("files/user.png");
          mLogger.info("[" + userId + "]: Using default user photo from ftp.", e);
        } catch(Exception e1) {
          imageData = getDefaultImage();
          if(imageData == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        }
      }
    }
    return Response.ok(imageData).build();
  }

  private InputStream getDefaultImage() {
    try {
      File initialFile = new File(mDefaultImage);
      return FileUtils.openInputStream(initialFile);
    } catch(Exception e2) {
      return null;
    }
  }

  @POST
  @Path("/upload")
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  public Response uploadFile(@FormDataParam("files") File pInputStream, @FormDataParam("id") String id,
                             @FormDataParam("name") String name) throws IOException {

    File newFile = new File(pInputStream.getParent(), id + ".jpg");
    Files.move(pInputStream.toPath(), newFile.toPath());

    Message<File> messageA = MessageBuilder.withPayload(newFile).build();

    try {
      FtpRemoteFileTemplate template = new FtpRemoteFileTemplate(ftpSessionFactory);
      template.setRemoteDirectoryExpression(new LiteralExpression("files/user-photo"));
      template.setUseTemporaryFileName(false);
      template.execute(session -> {
        session.mkdir("files/");
        return session.mkdir("files/user-photo/");
      });
      template.send(messageA);
    } catch (Exception e) {
      mLogger.error(e.getMessage());
      e.printStackTrace();
    } finally {
      pInputStream.deleteOnExit();
      newFile.delete();
    }
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();

  }
}
