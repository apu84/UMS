package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.AreaOfInterestInformationBuilder;
import org.ums.builder.PersonalInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AreaOfInterestInformation;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutableAreaOfInterestInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.registrar.AreaOfInterestInformationManager;
import org.ums.manager.registrar.PersonalInformationManager;
import org.ums.persistent.model.registrar.PersistentAreaOfInterestInformation;
import org.ums.persistent.model.registrar.PersistentPersonalInformation;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
      personalInformation = mManager.get(pEmployeeId);
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
    mManager.update(personalInformation);
    localeCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  @Transactional
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    MutablePersonalInformation mutablePersonalInformation = new PersistentPersonalInformation();
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonObject personalJsonObject = entries.getJsonObject(0).getJsonObject("personal");
    getBuilder().build(mutablePersonalInformation, personalJsonObject, localCache);
    mutablePersonalInformation.create();
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
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
