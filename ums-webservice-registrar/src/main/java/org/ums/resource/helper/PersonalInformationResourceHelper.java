package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.PersonalInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.UserManager;
import org.ums.manager.registrar.PersonalInformationManager;
import org.ums.persistent.model.registrar.PersistentPersonalInformation;
import org.ums.resource.ResourceHelper;
import sun.util.resources.cldr.lag.LocaleNames_lag;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class PersonalInformationResourceHelper extends
    ResourceHelper<PersonalInformation, MutablePersonalInformation, String> {

  @Autowired
  PersonalInformationManager mPersonalInformationManager;

  @Autowired
  PersonalInformationBuilder mPersonalInformationBuilder;

  @Autowired
  UserManager userManager;

  public JsonObject getPersonalInformation(final UriInfo pUriInfo) {
    String userId = userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId();
    PersonalInformation personalInformation = null;
    try {
      personalInformation = mPersonalInformationManager.get(userId);
    } catch(EmptyResultDataAccessException e) {
      // Do nothing
    }

    return toJson(personalInformation, pUriInfo);
  }

  @Transactional
  public Response create(JsonObject pJsonObject, UriInfo pUriInfo) {

    String userId = userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId();
    // getContentManager().delete(userId);

    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
    JsonObject personalJsonObject = entries.getJsonObject(0).getJsonObject("personal");
    mPersonalInformationBuilder.build(personalInformation, personalJsonObject, localCache);
    mPersonalInformationManager.create(personalInformation);

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response updatePersonalInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
    LocalCache localeCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonObject jsonObject = entries.getJsonObject(0);
    mPersonalInformationBuilder.build(personalInformation, jsonObject, localeCache);
    mPersonalInformationManager.update(personalInformation);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(PersonalInformation pPersonalInformation, UriInfo pUriInfo) {
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    if(pPersonalInformation.getId() != null)
      children.add(toJson(pPersonalInformation, pUriInfo, localCache));
    jsonObject.add("entries", children);
    localCache.invalidate();
    return jsonObject.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected PersonalInformationManager getContentManager() {
    return mPersonalInformationManager;
  }

  @Override
  protected Builder<PersonalInformation, MutablePersonalInformation> getBuilder() {
    return mPersonalInformationBuilder;
  }

  @Override
  protected String getETag(PersonalInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
