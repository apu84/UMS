package org.ums.resource.attachments;

import org.apache.commons.io.IOUtils;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
  public StreamingOutput get(@PathParam("attachment-id") String pAttachmentId) throws Exception {

    return new StreamingOutput() {

      @Override
      public void write(OutputStream pOutputStream) throws IOException, WebApplicationException {

        Attachment attachment = mAttachmentManager.get(Long.parseLong(pAttachmentId));
        String fileName = attachment.getServerFileName();
        InputStream inputStream = mGateWay.read(attachment.getServerFileName());
        IOUtils.copy(inputStream, pOutputStream);

      }
    };

  }
}
