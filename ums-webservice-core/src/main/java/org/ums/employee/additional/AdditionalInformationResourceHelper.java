package org.ums.employee.additional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class AdditionalInformationResourceHelper extends
    ResourceHelper<AdditionalInformation, MutableAdditionalInformation, String> {

  @Autowired
  AdditionalInformationManager mManager;

  @Autowired
  AdditionalInformationBuilder mBuilder;

  @Override
  @Transactional
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    LocalCache localCache = new LocalCache();
    MutableAdditionalInformation mutableAdditionalInformation = new PersistentAdditionalInformation();
    mBuilder.build(mutableAdditionalInformation, pJsonObject.getJsonObject("entries"), localCache);
    mManager.delete((MutableAdditionalInformation) mManager.get(pJsonObject.getJsonObject("entries").getString(
        "employeeId")));
    mManager.create(mutableAdditionalInformation);
    localCache.invalidate();
    return Response.ok().build();
  }

  public JsonObject get(final String pEmployeeId, final UriInfo pUriInfo) {
    if(mManager.exists(pEmployeeId)) {
      AdditionalInformation additionalInformation = mManager.get(pEmployeeId);
      return toJson(additionalInformation, pUriInfo, new LocalCache());
    }
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
