package org.ums.ems.profilemanagement.training;

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
public class TrainingInformationResourceHelper extends
    ResourceHelper<TrainingInformation, MutableTrainingInformation, Long> {

  @Autowired
  TrainingInformationManager mManager;

  @Autowired
  TrainingInformationBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableTrainingInformation mutableTrainingInformation = new PersistentTrainingInformation();
    mBuilder.build(mutableTrainingInformation, pJsonObject.getJsonObject("entries"), localCache);
    Long id = mManager.create(mutableTrainingInformation);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(id), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  public JsonObject get(final String pEmployeeId, final UriInfo pUriInfo) {
    if(mManager.exists(pEmployeeId)) {
      List<TrainingInformation> trainingInformationList = mManager.get(pEmployeeId);
      return buildJsonResponse(trainingInformationList, pUriInfo);
    }
    return null;
  }

  public Response update(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableTrainingInformation mutableTrainingInformation = new PersistentTrainingInformation();
    mBuilder.build(mutableTrainingInformation, pJsonObject.getJsonObject("entries"), localCache);
    mManager.update(mutableTrainingInformation);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(mutableTrainingInformation.getId()), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  public Response delete(Long id, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableTrainingInformation mutableTrainingInformation = (MutableTrainingInformation) mManager.get(id);
    mManager.delete(mutableTrainingInformation);
    localCache.invalidate();
    return Response.noContent().build();
  }

  @Override
  protected ContentManager<TrainingInformation, MutableTrainingInformation, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<TrainingInformation, MutableTrainingInformation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(TrainingInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
