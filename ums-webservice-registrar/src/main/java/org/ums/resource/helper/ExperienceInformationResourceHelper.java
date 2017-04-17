package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.ExperienceInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.MutableExperienceInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.registrar.ExperienceInformationManager;
import org.ums.persistent.model.registrar.PersistentExperienceInformation;
import org.ums.resource.ResourceHelper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExperienceInformationResourceHelper extends
    ResourceHelper<ExperienceInformation, MutableExperienceInformation, Integer> {

  @Autowired
  ExperienceInformationManager mExperienceInformationManager;

  @Autowired
  ExperienceInformationBuilder mExperienceInformationBuilder;

  @Transactional
  public Response saveExperienceInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    JsonArray experienceJsonArray = entries.getJsonObject(0).getJsonArray("experience");
    int sizeOfExperienceJsonArray = experienceJsonArray.size();

    List<MutableExperienceInformation> mutableExperienceInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfExperienceJsonArray; i++) {
      MutableExperienceInformation experienceInformation = new PersistentExperienceInformation();
      mExperienceInformationBuilder.build(experienceInformation, experienceJsonArray.getJsonObject(i), localCache);
      mutableExperienceInformation.add(experienceInformation);
    }
    mExperienceInformationManager.saveExperienceInformation(mutableExperienceInformation);

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<ExperienceInformation, MutableExperienceInformation, Integer> getContentManager() {
    return mExperienceInformationManager;
  }

  @Override
  protected Builder<ExperienceInformation, MutableExperienceInformation> getBuilder() {
    return mExperienceInformationBuilder;
  }

  @Override
  protected String getETag(ExperienceInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
