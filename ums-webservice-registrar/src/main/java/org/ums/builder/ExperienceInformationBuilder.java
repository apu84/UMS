package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.MutableExperienceInformation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ExperienceInformationBuilder implements Builder<ExperienceInformation, MutableExperienceInformation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, ExperienceInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("experienceInstitution", pReadOnly.getExperienceInstitute());
    pBuilder.add("experienceDesignation", pReadOnly.getDesignation());
    pBuilder.add("experienceFrom", pReadOnly.getExperienceFromDate());
    pBuilder.add("experienceTo", pReadOnly.getExperienceToDate());
  }

  @Override
  public void build(MutableExperienceInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setExperienceInstitute(pJsonObject.getString("experienceInstitution"));
    pMutable.setDesignation(pJsonObject.getString("experienceDesignation"));
    pMutable.setExperienceFromDate(pJsonObject.getString("experienceFrom"));
    pMutable.setExperienceToDate(pJsonObject.getString("experienceTo"));
  }
}
