package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.AwardInformationBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AwardInformation;
import org.ums.domain.model.mutable.registrar.MutableAwardInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.registrar.AwardInformationManager;
import org.ums.persistent.model.registrar.PersistentAwardInformation;
import org.ums.resource.ResourceHelper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class AwardInformationResourceHelper extends ResourceHelper<AwardInformation, MutableAwardInformation, Integer> {

  @Autowired
  AwardInformationManager mAwardInformationManager;

  @Autowired
  AwardInformationBuilder mAwardInformationBuilder;

  @Transactional
  public Response saveAwardInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    JsonArray awardJsonArray = entries.getJsonObject(0).getJsonArray("award");
    int sizeOfAwardJsonArray = awardJsonArray.size();

    List<MutableAwardInformation> mutableAwardInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfAwardJsonArray; i++) {
      MutableAwardInformation awardInformation = new PersistentAwardInformation();
      mAwardInformationBuilder.build(awardInformation, awardJsonArray.getJsonObject(i), localCache);
      mutableAwardInformation.add(awardInformation);
    }
    mAwardInformationManager.saveAwardInformation(mutableAwardInformation);

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<AwardInformation, MutableAwardInformation, Integer> getContentManager() {
    return mAwardInformationManager;
  }

  @Override
  protected Builder<AwardInformation, MutableAwardInformation> getBuilder() {
    return mAwardInformationBuilder;
  }

  @Override
  protected String getETag(AwardInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
