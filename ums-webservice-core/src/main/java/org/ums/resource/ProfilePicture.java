package org.ums.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.ums.context.AppContext;
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
import java.net.URI;
import java.nio.file.Files;
import java.sql.Timestamp;

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
  private UserManager mUserManager;

  // @Autowired
  // MessageManipulator mMessageManipulator;

  /*
   * @Autowired private KafkaTemplate<String, String> mKafkaTemplate;
   */

  @Autowired
  @Lazy
  private SessionFactory<FTPFile> mSessionFactory;

  ApplicationContext applicationContext = AppContext.getApplicationContext();

  /*
   * @Qualifier("photoUploadChannel") MessageChannel photoUploadChannel;
   */
  // = applicationContext.getBean("lmsChannel",
  // MessageChannel.class);

  // KafkaTemplate<String, String> mKafkaTemplate = applicationContext.getBean("kafkaTemplate",
  // KafkaTemplate.class);

  @GET
  public Response get(@Context HttpServletRequest pHttpServletRequest, @HeaderParam("user-agent") String userAgent,
      final @Context Request pRequest) {
    String userId = "";
    Subject subject = SecurityUtils.getSubject();

    userId = subject.getPrincipal().toString();
    User user = mUserManager.get(userId);
    InputStream imageData = null;
    // mKafkaTemplate.send("ums_logger", "This is from profile picture");

    try {

      imageData =
          mGateway.read("files/user-photo/"
              + (user.getPrimaryRole().getId() == 11 ? user.getId() : user.getEmployeeId()) + ".jpg");
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());

      ObjectMapper mapper = new ObjectMapper();

    } catch(Exception fl) {
      fl.printStackTrace();
      mLogger.error(fl.getMessage());
      // return Response.status(Response.Status.NOT_FOUND).build();
      try {

        imageData = mGateway.read("files/user.png");
      } catch(Exception e) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    }

    return Response.ok(imageData).build();
  }

  private String getClassName() {
    String className = this.getClass().toString();
    className.replaceAll("\\s", "");

    return this.getClass().toString();
  }

  private String getUserId() {
    return "userid:" + SecurityUtils.getSubject().getPrincipal().toString();
  }

  private String getUserRoles() {
    return "userroles:" + SecurityUtils.getSubject().getPrincipal().toString();
  }

  private String getTimeStamp() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    return "timestamp:" + timestamp.getTime();
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
      FtpRemoteFileTemplate template = new FtpRemoteFileTemplate(mSessionFactory);
      template.setRemoteDirectoryExpression(new LiteralExpression("files/lms"));
      template.setUseTemporaryFileName(false);
      template.execute(session -> {
        session.mkdir("files/");
        return session.mkdir("files/user-photo/");
      });
    template.send(messageA);
    }
    catch (Exception e){
      mLogger.error(e.getMessage());
      e.printStackTrace();
    }
    finally {
      pInputStream.deleteOnExit();
      newFile.delete();
    }
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();

  }
}
