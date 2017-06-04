package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.AcademicInformationBuilder;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.UserManager;
import org.ums.manager.registrar.AcademicInformationManager;
import org.ums.persistent.model.registrar.PersistentAcademicInformation;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class AcademicInformationResourceHelper extends
    ResourceHelper<AcademicInformation, MutableAcademicInformation, Integer> {

  @Autowired
  AcademicInformationManager mAcademicInformationManager;

  @Autowired
  AcademicInformationBuilder mAcademicInformationBuilder;

  @Autowired
  UserManager userManager;

  public JsonObject getAcademicInformation(final UriInfo pUriInfo) {
    String userId = userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId();
    List<AcademicInformation> pAcademicInformation = new ArrayList<>();
    try {
      pAcademicInformation = mAcademicInformationManager.getEmployeeAcademicInformation(userId);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(pAcademicInformation, pUriInfo);
  }

  @Transactional
  public Response saveAcademicInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonArray academicJsonArray = entries.getJsonObject(0).getJsonArray("academic");
    int sizeOfAcademicJsonArray = academicJsonArray.size();

    List<MutableAcademicInformation> createMutableAcademicInformation = new ArrayList<>();
    List<MutableAcademicInformation> updateMutableAcademicInformation = new ArrayList<>();
    List<MutableAcademicInformation> deleteMutableAcademicInformation = new ArrayList<>();

    for(int i = 0; i < sizeOfAcademicJsonArray; i++) {
      MutableAcademicInformation academicInformation = new PersistentAcademicInformation();
      mAcademicInformationBuilder.build(academicInformation, academicJsonArray.getJsonObject(i), localCache);
      if(academicJsonArray.getJsonObject(i).containsKey("dbAction")) {
        if(academicJsonArray.getJsonObject(i).getString("dbAction").equals("Create")) {
          createMutableAcademicInformation.add(academicInformation);
        }
        else if(academicJsonArray.getJsonObject(i).getString("dbAction").equals("Update")) {
          updateMutableAcademicInformation.add(academicInformation);
        }
      }
      else {
        deleteMutableAcademicInformation.add(academicInformation);
      }
    }

    if(createMutableAcademicInformation.size() != 0) {
      mAcademicInformationManager.saveAcademicInformation(createMutableAcademicInformation);
    }
    if(updateMutableAcademicInformation.size() != 0) {
      mAcademicInformationManager.updateAcademicInformation(updateMutableAcademicInformation);
    }
    if(deleteMutableAcademicInformation.size() != 0) {
      mAcademicInformationManager.deleteAcademicInformation(deleteMutableAcademicInformation);
    }
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(List<AcademicInformation> pAcademicInformation, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AcademicInformation academicInformation : pAcademicInformation) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, academicInformation, pUriInfo, localCache);
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
  protected ContentManager<AcademicInformation, MutableAcademicInformation, Integer> getContentManager() {
    return mAcademicInformationManager;
  }

  @Override
  protected Builder<AcademicInformation, MutableAcademicInformation> getBuilder() {
    return mAcademicInformationBuilder;
  }

  @Override
  protected String getETag(AcademicInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
