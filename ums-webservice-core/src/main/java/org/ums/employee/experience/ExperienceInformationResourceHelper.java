package org.ums.employee.experience;

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
public class ExperienceInformationResourceHelper extends
    ResourceHelper<ExperienceInformation, MutableExperienceInformation, Long> {

  @Autowired
  ExperienceInformationManager mManager;

  @Autowired
  ExperienceInformationBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableExperienceInformation mutableExperienceInformation = new PersistentExperienceInformation();
    mBuilder.build(mutableExperienceInformation, pJsonObject.getJsonObject("entries"), localCache);
    Long id = mManager.create(mutableExperienceInformation);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(id), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  public JsonObject get(final String pEmployeeId, final UriInfo pUriInfo) {
    if(mManager.exists(pEmployeeId)) {
      List<ExperienceInformation> experienceInformationList = mManager.get(pEmployeeId);
      return buildJsonResponse(experienceInformationList, pUriInfo);
    }
    return null;
  }

  public Response update(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableExperienceInformation mutableExperienceInformation = new PersistentExperienceInformation();
    mBuilder.build(mutableExperienceInformation, pJsonObject.getJsonObject("entries"), localCache);
    mManager.update(mutableExperienceInformation);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(mutableExperienceInformation.getId()), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  public Response delete(Long id, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableExperienceInformation mutableExperienceInformation = (MutableExperienceInformation) mManager.get(id);
    mManager.delete(mutableExperienceInformation);
    localCache.invalidate();
    return Response.noContent().build();
  }

  @Override
  protected ContentManager<ExperienceInformation, MutableExperienceInformation, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<ExperienceInformation, MutableExperienceInformation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(ExperienceInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
