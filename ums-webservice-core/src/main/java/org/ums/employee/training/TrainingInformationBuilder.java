package org.ums.employee.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.enums.registrar.TrainingCategory;
import org.ums.formatter.DateFormat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class TrainingInformationBuilder implements Builder<TrainingInformation, MutableTrainingInformation> {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, TrainingInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("trainingName", pReadOnly.getTrainingName());
    pBuilder.add("trainingInstitution", pReadOnly.getTrainingInstitute());
    pBuilder.add("trainingFrom", mDateFormat.format(pReadOnly.getTrainingFromDate()));
    pBuilder.add("trainingTo", mDateFormat.format(pReadOnly.getTrainingToDate()));
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
  }

  @Override
  public void build(MutableTrainingInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setId(!pJsonObject.getString("id").equals("") ? Long.parseLong(pJsonObject.getString("id")) : null);
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setTrainingName(pJsonObject.getString("trainingName"));
    pMutable.setTrainingInstitute(pJsonObject.getString("trainingInstitution"));
    pMutable.setTrainingFromDate(mDateFormat.parse(pJsonObject.getString("trainingFrom")));
    pMutable.setTrainingToDate(mDateFormat.parse(pJsonObject.getString("trainingTo")));
    pMutable.setTrainingDuration(pJsonObject.getInt("trainingDuration"));
    pMutable.setTrainingDurationString(pJsonObject.getString("trainingDurationString"));
    pMutable.setTrainingCategoryId(pJsonObject.getJsonObject("trainingCategory").getInt("id"));
  }
}
