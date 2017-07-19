package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.AdditionalInformationBuilder;
import org.ums.builder.AreaOfInterestInformationBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AdditionalInformation;
import org.ums.domain.model.mutable.registrar.MutableAdditionalInformation;
import org.ums.domain.model.mutable.registrar.MutableAreaOfInterestInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.registrar.AdditionalInformationManager;
import org.ums.manager.registrar.AreaOfInterestInformationManager;
import org.ums.persistent.model.registrar.PersistentAdditionalInformation;
import org.ums.persistent.model.registrar.PersistentAreaOfInterestInformation;
import org.ums.resource.AdditionalInformationResource;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdditionalInformationResourceHelper extends
    ResourceHelper<AdditionalInformation, MutableAdditionalInformation, String> {

  @Autowired
  AdditionalInformationManager mManager;

  @Autowired
  AdditionalInformationBuilder mBuilder;

  @Autowired
  AreaOfInterestInformationManager mAreaOfInterestInformationManager;

  @Autowired
  AreaOfInterestInformationBuilder mAreaOfInterestInformationBuilder;

  public JsonObject getAdditionalInformation(final String pEmployeeId, final UriInfo pUriInfo) {
    AdditionalInformation pAdditionalInformation = new PersistentAdditionalInformation();
    try {
      pAdditionalInformation = mManager.getAdditionalInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {
    }

    return toJson(pAdditionalInformation, pUriInfo);
  }

  @Transactional
  public Response saveAdditionalInformation(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonArray additionalJsonArray = entries.getJsonObject(0).getJsonArray("additional");
    MutableAdditionalInformation mutableAdditionalInformation = new PersistentAdditionalInformation();
    mBuilder.build(mutableAdditionalInformation, additionalJsonArray.getJsonObject(0), localCache);
    // mManager.deleteAdditionalInformation(mutableAdditionalInformation);
    mManager.saveAdditionalInformation(mutableAdditionalInformation);

    List<MutableAreaOfInterestInformation> mutableAreaOfInterestInformations = new ArrayList<>();
    JsonArray aoiJsonArray = additionalJsonArray.getJsonObject(0).getJsonArray("areaOfInterestInformation");
    int sizeOfAoiJsonArray = aoiJsonArray.size();
    for(int i = 0; i < sizeOfAoiJsonArray; i++) {
      MutableAreaOfInterestInformation mutableAreaOfInterestInformation = new PersistentAreaOfInterestInformation();
      mAreaOfInterestInformationBuilder.build(mutableAreaOfInterestInformation, aoiJsonArray.getJsonObject(0),
          localCache);
      mutableAreaOfInterestInformations.add(mutableAreaOfInterestInformation);
    }
    // mAreaOfInterestInformationManager.deleteAreaOfInterestInformation(additionalJsonArray.getJsonObject(0).getString(
    // "employeeId"));
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(AdditionalInformation pAdditionalInformation, UriInfo pUriInfo) {
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    getBuilder().build(jsonObject, pAdditionalInformation, pUriInfo, localCache);
    children.add(jsonObject);
    jsonObject.add("entries", children);
    localCache.invalidate();
    return jsonObject.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<AdditionalInformation, MutableAdditionalInformation, String> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<AdditionalInformation, MutableAdditionalInformation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AdditionalInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
