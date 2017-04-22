package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.AwardInformationBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AwardInformation;
import org.ums.domain.model.mutable.registrar.MutableAwardInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.UserManager;
import org.ums.manager.registrar.AwardInformationManager;
import org.ums.persistent.model.registrar.PersistentAwardInformation;
import org.ums.resource.ResourceHelper;

import javax.json.*;
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

  @Autowired
  UserManager userManager;

  public JsonObject getAwardInformation(final UriInfo pUriInfo) {
    String userId = userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId();
    List<AwardInformation> pAwardInformation = new ArrayList<>();
    try {
      pAwardInformation = mAwardInformationManager.getEmployeeAwardInformation(userId);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(pAwardInformation, pUriInfo);
  }

  @Transactional
  public Response saveAwardInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    String userId = userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId();
    mAwardInformationManager.deleteAwardInformation(userId);

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

  private JsonObject toJson(List<AwardInformation> pAwardInformation, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AwardInformation awardInformation : pAwardInformation) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, awardInformation, pUriInfo, localCache);
      children.add(jsonObject);
    }

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
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
