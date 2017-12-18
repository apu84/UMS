package org.ums.employee.academic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class AcademicInformationResourceHelper extends
    ResourceHelper<AcademicInformation, MutableAcademicInformation, Long> {

  @Autowired
  AcademicInformationManager mManager;

  @Autowired
  AcademicInformationBuilder mBuilder;

  public JsonObject getAcademicInformation(final String pEmployeeId, final UriInfo pUriInfo) {
    List<AcademicInformation> pAcademicInformation = new ArrayList<>();
    try {
      pAcademicInformation = mManager.getEmployeeAcademicInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(pAcademicInformation, pUriInfo);
  }

  @Transactional
  public Response saveAcademicInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonArray academicJsonArray = entries.getJsonObject(0).getJsonArray("academic");
    List<MutableAcademicInformation> createMutableAcademicInformation = new ArrayList<>();
    List<MutableAcademicInformation> updateMutableAcademicInformation = new ArrayList<>();
    List<MutableAcademicInformation> deleteMutableAcademicInformation = new ArrayList<>();

    for(int i = 0; i < academicJsonArray.size(); i++) {
      MutableAcademicInformation academicInformation = new PersistentAcademicInformation();
      mBuilder.build(academicInformation, academicJsonArray.getJsonObject(i), localCache);
      if(academicJsonArray.getJsonObject(i).containsKey("dbAction")) {
        if(academicJsonArray.getJsonObject(i).getString("dbAction").equals("Create")) {
          createMutableAcademicInformation.add(academicInformation);
        }
        else if(academicJsonArray.getJsonObject(i).getString("dbAction").equals("Update")) {
          updateMutableAcademicInformation.add(academicInformation);
        }
        else if(academicJsonArray.getJsonObject(i).getString("dbAction").equals("Delete")) {
          deleteMutableAcademicInformation.add(academicInformation);
        }
      }
      else {
        Response.ResponseBuilder builder = Response.created(null);
        builder.status(Response.Status.NOT_MODIFIED);
        return builder.build();
      }
    }

    if(createMutableAcademicInformation.size() != 0) {
      mManager.saveAcademicInformation(createMutableAcademicInformation);
    }
    if(updateMutableAcademicInformation.size() != 0) {
      mManager.updateAcademicInformation(updateMutableAcademicInformation);
    }
    if(deleteMutableAcademicInformation.size() != 0) {
      mManager.deleteAcademicInformation(deleteMutableAcademicInformation);
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
  protected ContentManager<AcademicInformation, MutableAcademicInformation, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<AcademicInformation, MutableAcademicInformation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AcademicInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
