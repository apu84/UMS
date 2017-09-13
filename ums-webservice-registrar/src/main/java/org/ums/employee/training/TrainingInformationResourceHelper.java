package org.ums.employee.training;

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
public class TrainingInformationResourceHelper extends
    ResourceHelper<TrainingInformation, MutableTrainingInformation, Long> {

  @Autowired
  TrainingInformationManager mTrainingInformationManager;

  @Autowired
  TrainingInformationBuilder mTrainingInformationBuilder;

  public JsonObject getTrainingInformation(final String pEmployeeId, final UriInfo pUriInfo) {
    List<TrainingInformation> pTrainingInformation = new ArrayList<>();
    try {
      pTrainingInformation = mTrainingInformationManager.getEmployeeTrainingInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(pTrainingInformation, pUriInfo);
  }

  @Transactional
  public Response saveTrainingInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonArray trainingJsonArray = entries.getJsonObject(0).getJsonArray("training");
    int sizeOfTrainingJsonArray = trainingJsonArray.size();

    List<MutableTrainingInformation> createMutableTrainingInformation = new ArrayList<>();
    List<MutableTrainingInformation> updateMutableTrainingInformation = new ArrayList<>();
    List<MutableTrainingInformation> deleteMutableTrainingInformation = new ArrayList<>();

    for(int i = 0; i < sizeOfTrainingJsonArray; i++) {
      MutableTrainingInformation trainingInformation = new PersistentTrainingInformation();
      mTrainingInformationBuilder.build(trainingInformation, trainingJsonArray.getJsonObject(i), localCache);
      if(trainingJsonArray.getJsonObject(i).containsKey("dbAction")) {
        if(trainingJsonArray.getJsonObject(i).getString("dbAction").equals("Create")) {
          createMutableTrainingInformation.add(trainingInformation);
        }
        else if(trainingJsonArray.getJsonObject(i).getString("dbAction").equals("Update")) {
          updateMutableTrainingInformation.add(trainingInformation);
        }
        else if(trainingJsonArray.getJsonObject(i).getString("dbAction").equals("Delete")) {
          deleteMutableTrainingInformation.add(trainingInformation);
        }
      }
      else {
        Response.ResponseBuilder builder = Response.created(null);
        builder.status(Response.Status.NOT_MODIFIED);
        return builder.build();
      }
    }

    if(createMutableTrainingInformation.size() != 0) {
      mTrainingInformationManager.saveTrainingInformation(createMutableTrainingInformation);
    }
    if(updateMutableTrainingInformation.size() != 0) {
      mTrainingInformationManager.updateTrainingInformation(updateMutableTrainingInformation);
    }
    if(deleteMutableTrainingInformation.size() != 0) {
      mTrainingInformationManager.deleteTrainingInformation(deleteMutableTrainingInformation);
    }
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(List<TrainingInformation> pTrainingInformation, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(TrainingInformation trainingInformation : pTrainingInformation) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, trainingInformation, pUriInfo, localCache);
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
  protected ContentManager<TrainingInformation, MutableTrainingInformation, Long> getContentManager() {
    return mTrainingInformationManager;
  }

  @Override
  protected Builder<TrainingInformation, MutableTrainingInformation> getBuilder() {
    return mTrainingInformationBuilder;
  }

  @Override
  protected String getETag(TrainingInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
