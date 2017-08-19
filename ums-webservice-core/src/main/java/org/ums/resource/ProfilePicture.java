package org.ums.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import org.ums.context.AppContext;
import org.ums.integration.FileWriterGateway;
import org.ums.integration.MessageManipulator;
import org.ums.manager.BinaryContentManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.InputStream;
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
  MessageManipulator mMessageManipulator;

  /*
   * @Autowired private KafkaTemplate<String, String> mKafkaTemplate;
   */

  ApplicationContext applicationContext = AppContext.getApplicationContext();

  MessageChannel lmsChannel = applicationContext.getBean("lmsChannel", MessageChannel.class);

  @GET
  public Response get(final @Context Request pRequest) {
    String userId = "";
    Subject subject = SecurityUtils.getSubject();
    if(subject != null) {
      userId = subject.getPrincipal().toString();
    }
    InputStream imageData = null;

    /*
     * File file = new File("G:/shorna.jpg");
     * 
     * // this.mGateway.write("love.jpg", file); Message<File> messageA =
     * MessageBuilder.withPayload(file).build(); lmsChannel.send(messageA);
     */

    try {

      imageData = mGateway.read("files/user.png");
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());

      ObjectMapper mapper = new ObjectMapper();

      /* mKafkaTemplate.send("ums_logger", mapper.writeValueAsString(activityLogger)); */
    } catch(Exception fl) {
      mLogger.error(fl.getMessage());
      return Response.status(Response.Status.NOT_FOUND).build();
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
}
