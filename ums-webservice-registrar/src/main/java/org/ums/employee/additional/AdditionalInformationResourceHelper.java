package org.ums.employee.additional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.manager.ContentManager;
import org.ums.manager.registrar.AdditionalInformationManager;
import org.ums.manager.registrar.AreaOfInterestInformationManager;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
    JsonObject additionalJsonObject = entries.getJsonObject(0).getJsonObject("additional");
    MutableAdditionalInformation mutableAdditionalInformation = new PersistentAdditionalInformation();
    mBuilder.build(mutableAdditionalInformation, additionalJsonObject, localCache);
    mManager.deleteAdditionalInformation(mutableAdditionalInformation);
    mManager.saveAdditionalInformation(mutableAdditionalInformation);

    mAreaOfInterestInformationManager.deleteAreaOfInterestInformation(mutableAdditionalInformation.getId());
    List<MutableAreaOfInterestInformation> mutableAreaOfInterestInformations = new ArrayList<>();
    JsonArray aoiJsonArray = additionalJsonObject.getJsonArray("areaOfInterestInformation");
    int sizeOfAoiJsonArray = aoiJsonArray.size();
    if(sizeOfAoiJsonArray > 0) {
      for(int i = 0; i < sizeOfAoiJsonArray; i++) {
        MutableAreaOfInterestInformation mutableAreaOfInterestInformation = new PersistentAreaOfInterestInformation();
        mAreaOfInterestInformationBuilder.aoiBuilder(mutableAreaOfInterestInformation, aoiJsonArray.getJsonObject(i),
            localCache, mutableAdditionalInformation);
        mutableAreaOfInterestInformations.add(mutableAreaOfInterestInformation);
      }
      mAreaOfInterestInformationManager.saveAreaOfInterestInformation(mutableAreaOfInterestInformations);
    }
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
