package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.PublicationInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.PublicationInformation;
import org.ums.domain.model.mutable.registrar.MutablePublicationInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.registrar.PublicationInformationManager;
import org.ums.persistent.model.registrar.PersistentPublicationInformation;
import org.ums.resource.ResourceHelper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class PublicationInformationResourceHelper extends
    ResourceHelper<PublicationInformation, MutablePublicationInformation, Integer> {

  @Autowired
  PublicationInformationManager mPublicationInformationManager;

  @Autowired
  PublicationInformationBuilder mPublicationInformationBuilder;

  @Transactional
  public Response savePublicationInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    JsonArray publicationJsonArray = entries.getJsonObject(0).getJsonArray("publication");
    int sizeOfPublicationJsonArray = publicationJsonArray.size();

    List<MutablePublicationInformation> mutablePublicationInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfPublicationJsonArray; i++) {
      MutablePublicationInformation publicationInformation = new PersistentPublicationInformation();
      mPublicationInformationBuilder.build(publicationInformation, publicationJsonArray.getJsonObject(i), localCache);
      mutablePublicationInformation.add(publicationInformation);
    }
    mPublicationInformationManager.savePublicationInformation(mutablePublicationInformation);

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<PublicationInformation, MutablePublicationInformation, Integer> getContentManager() {
    return mPublicationInformationManager;
  }

  @Override
  protected Builder<PublicationInformation, MutablePublicationInformation> getBuilder() {
    return mPublicationInformationBuilder;
  }

  @Override
  protected String getETag(PublicationInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
