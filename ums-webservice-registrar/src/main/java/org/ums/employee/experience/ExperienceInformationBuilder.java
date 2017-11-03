package org.ums.employee.experience;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.employee.experience.ExperienceInformation;
import org.ums.employee.experience.MutableExperienceInformation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ExperienceInformationBuilder implements Builder<ExperienceInformation, MutableExperienceInformation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, ExperienceInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("experienceInstitution", pReadOnly.getExperienceInstitute());
    pBuilder.add("experienceDesignation", pReadOnly.getDesignation());
    pBuilder.add("experienceFrom", pReadOnly.getExperienceFromDate());
    pBuilder.add("experienceTo", pReadOnly.getExperienceToDate());
    pBuilder.add("experienceDuration", pReadOnly.getExperienceDuration());
    pBuilder.add("experienceDurationString", pReadOnly.getExperienceDurationString());
    pBuilder.add("dbAction", "");
  }

  @Override
  public void build(MutableExperienceInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable
        .setId(pJsonObject.containsKey("dbAction") ? (pJsonObject.getString("dbAction").equals("Update") || pJsonObject
            .getString("dbAction").equals("Delete")) ? Long.parseLong(pJsonObject.getString("id")) : 0 : 0);
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setExperienceInstitute(pJsonObject.getString("experienceInstitution"));
    pMutable.setDesignation(pJsonObject.getString("experienceDesignation"));
    pMutable.setExperienceFromDate(pJsonObject.getString("experienceFrom"));
    pMutable.setExperienceToDate(pJsonObject.getString("experienceTo"));
    pMutable.setExperienceDuration(pJsonObject.getInt("experienceDuration"));
    pMutable.setExperienceDurationString(pJsonObject.getString("experienceDurationString"));
  }
}
