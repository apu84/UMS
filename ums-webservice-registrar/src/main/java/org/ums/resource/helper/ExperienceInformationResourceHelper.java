package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
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

  public JsonObject getExperienceInformation(final String pEmployeeId, final UriInfo pUriInfo) {
    List<ExperienceInformation> pExperienceInformation = new ArrayList<>();
    try {
      pExperienceInformation = mExperienceInformationManager.getEmployeeExperienceInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(pExperienceInformation, pUriInfo);
  }

  @Transactional
  public Response saveExperienceInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonArray experienceJsonArray = entries.getJsonObject(0).getJsonArray("experience");
    int sizeOfExperienceJsonArray = experienceJsonArray.size();

    List<MutableExperienceInformation> createMutableExperienceInformation = new ArrayList<>();
    List<MutableExperienceInformation> updateMutableExperienceInformation = new ArrayList<>();
    List<MutableExperienceInformation> deleteMutableExperienceInformation = new ArrayList<>();

    for(int i = 0; i < sizeOfExperienceJsonArray; i++) {
      MutableExperienceInformation experienceInformation = new PersistentExperienceInformation();
      mExperienceInformationBuilder.build(experienceInformation, experienceJsonArray.getJsonObject(i), localCache);
      if(experienceJsonArray.getJsonObject(i).containsKey("dbAction")) {
        if(experienceJsonArray.getJsonObject(i).getString("dbAction").equals("Create")) {
          createMutableExperienceInformation.add(experienceInformation);
        }
        else if(experienceJsonArray.getJsonObject(i).getString("dbAction").equals("Update")) {
          updateMutableExperienceInformation.add(experienceInformation);
        }
      }
      else {
        deleteMutableExperienceInformation.add(experienceInformation);
      }
    }

    if(createMutableExperienceInformation.size() != 0) {
      mExperienceInformationManager.saveExperienceInformation(createMutableExperienceInformation);
    }
    if(updateMutableExperienceInformation.size() != 0) {
      mExperienceInformationManager.updateExperienceInformation(updateMutableExperienceInformation);
    }
    if(deleteMutableExperienceInformation.size() != 0) {
      mExperienceInformationManager.deleteExperienceInformation(deleteMutableExperienceInformation);
    }
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(List<ExperienceInformation> pExperienceInformation, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(ExperienceInformation experienceInformation : pExperienceInformation) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, experienceInformation, pUriInfo, localCache);
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
