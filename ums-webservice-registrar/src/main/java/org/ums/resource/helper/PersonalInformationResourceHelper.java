package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.PersonalInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.PersonalInformation;
import org.ums.domain.model.mutable.registrar.MutablePersonalInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.registrar.PersonalInformationManager;
import org.ums.persistent.model.registrar.PersistentPersonalInformation;
import org.ums.resource.ResourceHelper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonalInformationResourceHelper extends
    ResourceHelper<PersonalInformation, MutablePersonalInformation, Integer> {

  @Autowired
  PersonalInformationManager mPersonalInformationManager;

  @Autowired
  PersonalInformationBuilder mPersonalInformationBuilder;

  @Transactional
  public Response savePersonalInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
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

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<PersonalInformation, MutablePersonalInformation, Integer> getContentManager() {
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
