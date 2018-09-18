package org.ums.ems.profilemanagement.award;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class AwardInformationResourceHelper extends ResourceHelper<AwardInformation, MutableAwardInformation, Long> {

  @Autowired
  AwardInformationManager mManager;

  @Autowired
  AwardInformationBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableAwardInformation mutableAwardInformation = new PersistentAwardInformation();
    mBuilder.build(mutableAwardInformation, pJsonObject.getJsonObject("entries"), localCache);
    Long id = mManager.create(mutableAwardInformation);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(id), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  public JsonObject get(final String pEmployeeId, final UriInfo pUriInfo) {
    if(mManager.exists(pEmployeeId)) {
      List<AwardInformation> awardInformationList = mManager.get(pEmployeeId);
      return buildJsonResponse(awardInformationList, pUriInfo);
    }
    return null;
  }

  public Response update(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableAwardInformation mutableAwardInformation = new PersistentAwardInformation();
    mBuilder.build(mutableAwardInformation, pJsonObject.getJsonObject("entries"), localCache);
    mManager.update(mutableAwardInformation);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(mutableAwardInformation.getId()), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  public Response delete(Long id, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableAwardInformation mutableAwardInformation = (MutableAwardInformation) mManager.get(id);
    mManager.delete(mutableAwardInformation);
    localCache.invalidate();
    return Response.noContent().build();
  }

  @Override
  protected ContentManager<AwardInformation, MutableAwardInformation, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<AwardInformation, MutableAwardInformation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AwardInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
