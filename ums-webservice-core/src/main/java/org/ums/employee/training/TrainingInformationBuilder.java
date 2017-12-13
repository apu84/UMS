package org.ums.employee.training;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.employee.training.TrainingInformation;
import org.ums.employee.training.MutableTrainingInformation;
import org.ums.enums.registrar.TrainingCategory;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class TrainingInformationBuilder implements Builder<TrainingInformation, MutableTrainingInformation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, TrainingInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("trainingName", pReadOnly.getTrainingName());
    pBuilder.add("trainingInstitution", pReadOnly.getTrainingInstitute());
    pBuilder.add("trainingFrom", pReadOnly.getTrainingFromDate());
    pBuilder.add("trainingTo", pReadOnly.getTrainingToDate());
    pBuilder.add("trainingDuration", pReadOnly.getTrainingDuration());
    pBuilder.add("trainingDurationString", pReadOnly.getTrainingDurationString());
    if(pReadOnly.getTrainingCategoryId() == 0) {
      pBuilder.add("trainingCategory", "");
    }
    else {
      JsonObjectBuilder categoryBuilder = Json.createObjectBuilder();
      categoryBuilder.add("id", pReadOnly.getTrainingCategoryId()).add("name",
          TrainingCategory.get(pReadOnly.getTrainingCategoryId()).getLabel());
      pBuilder.add("trainingCategory", categoryBuilder);
    }
    pBuilder.add("dbAction", "");
  }

  @Override
  public void build(MutableTrainingInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable
        .setId(pJsonObject.containsKey("dbAction") ? (pJsonObject.getString("dbAction").equals("Update") || pJsonObject
            .getString("dbAction").equals("Delete")) ? Long.parseLong(pJsonObject.getString("id")) : 0 : 0);
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setTrainingName(pJsonObject.getString("trainingName"));
    pMutable.setTrainingInstitute(pJsonObject.getString("trainingInstitution"));
    pMutable.setTrainingFromDate(pJsonObject.getString("trainingFrom"));
    pMutable.setTrainingToDate(pJsonObject.getString("trainingTo"));
    pMutable.setTrainingDuration(pJsonObject.getInt("trainingDuration"));
    pMutable.setTrainingDurationString(pJsonObject.getString("trainingDurationString"));
    pMutable.setTrainingCategoryId(pJsonObject.getJsonObject("trainingCategory").getInt("id"));
  }
}
