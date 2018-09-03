package org.ums.ems.profilemanagement.training;

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
import javax.json.JsonValue;
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
    pBuilder.add("trainingName", pReadOnly.getTrainingName() == null ? "" : pReadOnly.getTrainingName());
    pBuilder.add("trainingInstitution", pReadOnly.getTrainingInstitute() == null ? "" : pReadOnly.getTrainingInstitute());
    pBuilder.add("trainingFrom", pReadOnly.getTrainingFromDate() == null ? "" :mDateFormat.format(pReadOnly.getTrainingFromDate()));
    pBuilder.add("trainingTo", pReadOnly.getTrainingFromDate() == null ? "" : mDateFormat.format(pReadOnly.getTrainingToDate()));
    pBuilder.add("trainingDuration", pReadOnly.getTrainingDuration());
    pBuilder.add("trainingDurationString", pReadOnly.getTrainingDurationString() == null ? "" : pReadOnly.getTrainingDurationString());
    if(pReadOnly.getTrainingCategoryId() == 0) {
      pBuilder.add("trainingCategory", JsonValue.NULL);
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
    pMutable.setTrainingName(pJsonObject.getString("trainingName") == null ? "" : pJsonObject.getString("trainingName"));
    pMutable.setTrainingInstitute(pJsonObject.getString("trainingInstitution") == null ? "" : pJsonObject.getString("trainingInstitution"));
    pMutable.setTrainingFromDate(pJsonObject.getString("trainingFrom") == null || pJsonObject.getString("trainingFrom").isEmpty() ? null : mDateFormat.parse(pJsonObject.getString("trainingFrom")));
    pMutable.setTrainingToDate(pJsonObject.getString("trainingTo") == null || pJsonObject.getString("trainingFrom").isEmpty() ? null : mDateFormat.parse(pJsonObject.getString("trainingTo")));
    pMutable.setTrainingDuration(pJsonObject.getInt("trainingDuration", 0));
    pMutable.setTrainingDurationString(pJsonObject.getString("trainingDurationString") == null ? "" : pJsonObject.getString("trainingDurationString"));
    pMutable.setTrainingCategoryId(pJsonObject.get("trainingCategory").getValueType().equals(JsonValue.ValueType.NULL) ? 0 : pJsonObject.getJsonObject("trainingCategory").getInt("id"));
  }
}
