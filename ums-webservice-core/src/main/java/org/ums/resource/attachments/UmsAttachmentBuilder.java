package org.ums.resource.attachments;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Attachment;
import org.ums.domain.model.mutable.common.MutableAttachment;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 20-Jul-17.
 */
@Component
public class UmsAttachmentBuilder implements Builder<Attachment, MutableAttachment> {

  @Override
  public void build(JsonObjectBuilder pBuilder, Attachment pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("type", pReadOnly.getApplicationType().getValue());
    pBuilder.add("applicationId", pReadOnly.getApplicationId());
    pBuilder.add("fileName", pReadOnly.getFileName());
    pBuilder.add("serverFileName", pReadOnly.getServerFileName());
  }

  @Override
  public void build(MutableAttachment pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
