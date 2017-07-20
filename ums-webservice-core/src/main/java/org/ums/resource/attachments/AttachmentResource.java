package org.ums.resource.attachments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.common.Attachment;
import org.ums.enums.ApplicationType;
import org.ums.integration.FileWriterGateway;
import org.ums.manager.common.AttachmentManager;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by Monjur-E-Morshed on 20-Jul-17.
 */
@Component
@Path("/attachment")
public class AttachmentResource extends Resource {

  @Autowired
  AttachmentResourceHelper mHelper;

  @Autowired
  private FileWriterGateway mGateWay;

  @Autowired
  AttachmentManager mAttachmentManager;

  @GET
  @Path("/applicationType/{application-type}/applicationId/{application-id}")
  public JsonObject getAttachments(@PathParam("application-type") String pApplicationType,
      @PathParam("application-id") String pApplicationId) {
    return mHelper.getAttachment(ApplicationType.get(Integer.parseInt(pApplicationType)), pApplicationId, mUriInfo);
  }

  @GET
  @Path("/downloadFile/attachmentId/{attachment-id}")
  public Response get(@PathParam("attachment-id") String pAttachmentId) throws Exception {

    Attachment attachment = mAttachmentManager.get(Long.parseLong(pAttachmentId));
    String fileName = attachment.getServerFileName();
    InputStream inputStream = mGateWay.read(attachment.getServerFileName());
    return Response.ok().type("application/pdf").entity(inputStream).build();
  }
}
