package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.PersonalInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.UserManager;
import org.ums.manager.registrar.PersonalInformationManager;
import org.ums.persistent.model.registrar.PersistentPersonalInformation;
import org.ums.resource.ResourceHelper;

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

  public JsonObject getPersonalInformation(final String pEmployeeId, final UriInfo pUriInfo) {
    PersistentPersonalInformation personalInformation = new PersistentPersonalInformation();
    try {
      PersonalInformation pPersonalInformation =
          mPersonalInformationManager.getEmployeePersonalInformation(pEmployeeId);
      personalInformation = (PersistentPersonalInformation) pPersonalInformation;
    } catch(EmptyResultDataAccessException e) {
      // Do nothing
    }

    return toJson((PersonalInformation) personalInformation, pUriInfo);
  }

  @Transactional
  public Response savePersonalInformation(JsonObject pJsonObject, UriInfo pUriInfo) {

    mPersonalInformationManager.deletePersonalInformation(userManager.get(
        SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());

    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    MutablePersonalInformation personalInformation = new PersistentPersonalInformation();
    JsonObject personalJsonObject = entries.getJsonObject(0).getJsonObject("personal");
    mPersonalInformationBuilder.build(personalInformation, personalJsonObject, localCache);
    mPersonalInformationManager.savePersonalInformation(personalInformation);

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(PersonalInformation pPersonalInformation, UriInfo pUriInfo) {
    JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    if(pPersonalInformation.getEmployeeId() != null)
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
