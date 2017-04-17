package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.TrainingInformation;
import org.ums.domain.model.mutable.registrar.MutableTrainingInformation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class TrainingInformationBuilder implements Builder<TrainingInformation, MutableTrainingInformation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, TrainingInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("trainingName", pReadOnly.getTrainingName());
    pBuilder.add("trainingInstitution", pReadOnly.getTrainingInstitute());
    pBuilder.add("trainingFrom", pReadOnly.getTrainingFromDate());
    pBuilder.add("trainingTo", pReadOnly.getTrainingToDate());
  }

  @Override
  public void build(MutableTrainingInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getInt("employeeId"));
    pMutable.setTrainingName(pJsonObject.getString("trainingName"));
    pMutable.setTrainingInstitute(pJsonObject.getString("trainingInstitution"));
    pMutable.setTrainingFromDate(pJsonObject.getString("trainingFrom"));
    pMutable.setTrainingToDate(pJsonObject.getString("trainingTo"));
  }
}
