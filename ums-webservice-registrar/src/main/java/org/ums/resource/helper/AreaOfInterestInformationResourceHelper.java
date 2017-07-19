package org.ums.resource.helper;

import netscape.javascript.JSObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.AreaOfInterestInformationBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AreaOfInterestInformation;
import org.ums.domain.model.mutable.registrar.MutableAreaOfInterestInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.registrar.AreaOfInterestInformationManager;
import org.ums.persistent.model.registrar.PersistentAreaOfInterestInformation;
import org.ums.resource.MutableAreaOfInterestInformationResource;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.UserManager;
import sun.security.provider.certpath.OCSPResponse;

import javax.json.*;
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

  public JsonObject getAreaOfInterestInformation(final String pEmployeeId, final UriInfo pUriInfo) {
    List<AreaOfInterestInformation> pAreaOfInterestInformation = new ArrayList<>();
    try {
      pAreaOfInterestInformation = mManager.getAreaOfInterestInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {
    }
    return toJson(pAreaOfInterestInformation, pUriInfo);
  }

  @Transactional
  public Response saveAreaOfInterestInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonArray areaOfInterestJsonArray = entries.getJsonObject(0).getJsonArray("areaOfInterest");
    int sizeOfAreaOfInterestJsonArray = areaOfInterestJsonArray.size();
    List<MutableAreaOfInterestInformation> pMutableAreaOfInterestInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfAreaOfInterestJsonArray; i++) {
      MutableAreaOfInterestInformation mutableAreaOfInterestInformation = new PersistentAreaOfInterestInformation();
      mBuilder.build(mutableAreaOfInterestInformation, areaOfInterestJsonArray.getJsonObject(i), localCache);
      pMutableAreaOfInterestInformation.add(mutableAreaOfInterestInformation);
    }
    mManager.saveAreaOfInterestInformation(pMutableAreaOfInterestInformation);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(List<AreaOfInterestInformation> pAreaOfInterestInformation, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AreaOfInterestInformation areaOfInterestInformation : pAreaOfInterestInformation) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, areaOfInterestInformation, pUriInfo, localCache);
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
