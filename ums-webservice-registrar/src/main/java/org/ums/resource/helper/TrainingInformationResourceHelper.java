package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.TrainingInformationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.TrainingInformation;
import org.ums.domain.model.mutable.registrar.MutableTrainingInformation;
import org.ums.manager.ContentManager;
import org.ums.manager.registrar.TrainingInformationManager;
import org.ums.persistent.model.registrar.PersistentTrainingInformation;
import org.ums.resource.ResourceHelper;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class TrainingInformationResourceHelper extends
    ResourceHelper<TrainingInformation, MutableTrainingInformation, Integer> {

  @Autowired
  TrainingInformationManager mTrainingInformationManager;

  @Autowired
  TrainingInformationBuilder mTrainingInformationBuilder;

  @Transactional
  public Response saveTrainingInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    JsonArray trainingJsonArray = entries.getJsonObject(0).getJsonArray("training");
    int sizeOfTrainingJsonArray = trainingJsonArray.size();

    List<MutableTrainingInformation> mutableTrainingInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfTrainingJsonArray; i++) {
      MutableTrainingInformation trainingInformation = new PersistentTrainingInformation();
      mTrainingInformationBuilder.build(trainingInformation, trainingJsonArray.getJsonObject(i), localCache);
      mutableTrainingInformation.add(trainingInformation);
    }
    mTrainingInformationManager.saveTrainingInformation(mutableTrainingInformation);

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<TrainingInformation, MutableTrainingInformation, Integer> getContentManager() {
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
