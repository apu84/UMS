package org.ums.employee.personal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class PersonalInformationResourceHelper extends
    ResourceHelper<PersonalInformation, MutablePersonalInformation, String> {

  @Autowired
  private PersonalInformationManager mManager;

  @Autowired
  private PersonalInformationBuilder mBuilder;

  public JsonObject getPersonalInformation(final String pEmployeeId, final UriInfo pUriInfo) {
    PersonalInformation personalInformation = new PersistentPersonalInformation();
    try {
      personalInformation = mManager.getPersonalInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {
    }
    return buildJson(personalInformation, pUriInfo);
  }

  public Response updatePersonalInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
    LocalCache localeCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonObject jsonObject = entries.getJsonObject(0).getJsonObject("personal");
    mBuilder.build(personalInformation, jsonObject, localeCache);
    mManager.updatePersonalInformation(personalInformation);
    localeCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response savePersonalInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
    LocalCache localeCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonObject jsonObject = entries.getJsonObject(0).getJsonObject("personal");
    mBuilder.build(personalInformation, jsonObject, localeCache);
    mManager.savePersonalInformation(personalInformation);
    localeCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  private JsonObject buildJson(PersonalInformation pPersonalInformation, UriInfo pUriInfo) {
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    if(pPersonalInformation.getId() != null) {
      children.add(toJson(pPersonalInformation, pUriInfo, localCache));
    }
    jsonObject.add("entries", children);
    localCache.invalidate();
    return jsonObject.build();
  }

  @Override
  protected PersonalInformationManager getContentManager() {
    return mManager;
  }

  @Override
  protected PersonalInformationBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(PersonalInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
