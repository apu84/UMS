package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.ums.builder.PersonalInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.registrar.PersonalInformationManager;
import org.ums.persistent.model.registrar.PersistentPersonalInformation;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Component
public class PersonalInformationResourceHelper extends
    ResourceHelper<PersonalInformation, MutablePersonalInformation, String> {

  @Autowired
  private PersonalInformationManager mManager;

  @Autowired
  private PersonalInformationBuilder mBuilder;

  @Autowired
  private UserManager userManager;

  public JsonObject getPersonalInformation(final UriInfo pUriInfo) {
    String userId = userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId();
    PersonalInformation personalInformation = new PersistentPersonalInformation();
    try {
      personalInformation = mManager.get(userId);
    } catch(EmptyResultDataAccessException e) {
      // Do nothing
    }

    return buildJson(personalInformation, pUriInfo);
  }

  // @Transactional
  // public Response create(JsonObject pJsonObject, UriInfo pUriInfo) {
  //
  // String userId =
  // userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId();
  // // getContentManager().delete(userId);
  //
  // LocalCache localCache = new LocalCache();
  // JsonArray entries = pJsonObject.getJsonArray("entries");
  //
  // MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
  // JsonObject personalJsonObject = entries.getJsonObject(0).getJsonObject("personal");
  // mBuilder.build(personalInformation, personalJsonObject, localCache);
  // mManager.create(personalInformation);
  //
  // Response.ResponseBuilder builder = Response.created(null);
  // builder.status(Response.Status.CREATED);
  // return builder.build();
  // }

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
