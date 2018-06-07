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
import java.util.ArrayList;
import java.util.List;

@Component
public class AreaOfInterestInformationResourceHelper extends
    ResourceHelper<AreaOfInterestInformation, MutableAreaOfInterestInformation, String> {

  @Autowired
  AreaOfInterestInformationManager mManager;

  @Autowired
  AreaOfInterestInformationBuilder mBuilder;

  @Override
  @Transactional
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    LocalCache localCache = new LocalCache();
    List<MutableAreaOfInterestInformation> mutableAreaOfInterestInformationList = new ArrayList<>();
    if(pJsonObject.getJsonArray("entries").size() > 0) {
      List<AreaOfInterestInformation> areaOfInterestInformations =
          mManager.getAll(pJsonObject.getJsonArray("entries").getJsonObject(0).getString("employeeId"));
      mManager.delete((MutableAreaOfInterestInformation) areaOfInterestInformations.get(0));
      for(int i = 0; i < pJsonObject.getJsonArray("entries").size(); i++) {
        MutableAreaOfInterestInformation mutableAreaOfInterestInformation = new PersistentAreaOfInterestInformation();
        mBuilder.build(mutableAreaOfInterestInformation, pJsonObject.getJsonArray("entries").getJsonObject(i),
            localCache);
        mutableAreaOfInterestInformationList.add(mutableAreaOfInterestInformation);
      }
      mManager.create(mutableAreaOfInterestInformationList);
    }
    localCache.invalidate();
    return Response.ok().build();
  }

  public JsonObject get(final String pEmployeeId, final UriInfo pUriInfo) {
    if(mManager.exists(pEmployeeId)) {
      List<AreaOfInterestInformation> areaOfInterestInformationList = mManager.getAll(pEmployeeId);
      return buildJsonResponse(areaOfInterestInformationList, pUriInfo);
    }
    return null;
  }

  @Override
  protected ContentManager<AreaOfInterestInformation, MutableAreaOfInterestInformation, String> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<AreaOfInterestInformation, MutableAreaOfInterestInformation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AreaOfInterestInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
