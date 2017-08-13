package org.ums.resource.attachments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Attachment;
import org.ums.domain.model.mutable.common.MutableAttachment;
import org.ums.enums.ApplicationType;
import org.ums.manager.common.AttachmentManager;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Jul-17.
 */
@Component
public class AttachmentResourceHelper extends ResourceHelper<Attachment, MutableAttachment, Long> {

  @Autowired
  AttachmentManager mAttachmentManager;

  @Autowired
  UmsAttachmentBuilder attachmentBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    List<Attachment> attachments = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    for(int i = 0; i < entries.size(); i++) {

    }
    return null;
  }

  public JsonObject getAttachment(ApplicationType pApplicationType, String pApplicationId, final UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    List<Attachment> attachments = getContentManager().getAttachments(pApplicationType, pApplicationId);

    for(Attachment attachment : attachments) {
      JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
      getBuilder().build(jsonObjectBuilder, attachment, pUriInfo, localCache);
      children.add(jsonObjectBuilder);
    }

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected AttachmentManager getContentManager() {
    return mAttachmentManager;
  }

  @Override
  protected Builder<Attachment, MutableAttachment> getBuilder() {
    return attachmentBuilder;
  }

  @Override
  protected String getETag(Attachment pReadonly) {
    return pReadonly.getLastModified();
  }
}
