package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Attachment;
import org.ums.domain.model.mutable.common.MutableAttachment;
import org.ums.manager.common.AttachmentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Jul-17.
 */
public class AttachmentResourceHelper extends ResourceHelper<Attachment, MutableAttachment, Long> {

  @Autowired
  private AttachmentManager mAttachmentManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    List<Attachment> attachments = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    for(int i = 0; i < entries.size(); i++) {

    }
    return null;
  }

  @Override
  protected AttachmentManager getContentManager() {
    return mAttachmentManager;
  }

  @Override
  protected Builder<Attachment, MutableAttachment> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(Attachment pReadonly) {
    return pReadonly.getLastModified();
  }
}
