package org.ums.ems.profilemanagement.experience;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.enums.registrar.ExperienceCategory;
import org.ums.formatter.DateFormat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.core.UriInfo;

@Component
public class ExperienceInformationBuilder implements Builder<ExperienceInformation, MutableExperienceInformation> {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, ExperienceInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("experienceInstitution",
        pReadOnly.getExperienceInstitute() == null ? "" : pReadOnly.getExperienceInstitute());
    pBuilder.add("experienceDesignation", pReadOnly.getDesignation() == null ? "" : pReadOnly.getDesignation());
    pBuilder.add("experienceFrom",
        pReadOnly.getExperienceFromDate() == null ? "" : mDateFormat.format(pReadOnly.getExperienceFromDate()));
    pBuilder.add("experienceTo",
        pReadOnly.getExperienceToDate() == null ? "" : mDateFormat.format(pReadOnly.getExperienceToDate()));
    pBuilder.add("experienceDuration", pReadOnly.getExperienceDuration());
    pBuilder.add("experienceDurationString",
        pReadOnly.getExperienceDurationString() == null ? "" : pReadOnly.getExperienceDurationString());
    if(pReadOnly.getExperienceCategoryId() == 0) {
      pBuilder.add("experienceCategory", JsonValue.NULL);
    }
    else {
      JsonObjectBuilder categoryBuilder = Json.createObjectBuilder();
      categoryBuilder.add("id", pReadOnly.getExperienceCategoryId()).add("name",
          ExperienceCategory.get(pReadOnly.getExperienceCategoryId()).getLabel());
      pBuilder.add("experienceCategory", categoryBuilder);
    }
  }

  @Override
  public void build(MutableExperienceInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setId(!pJsonObject.getString("id").equals("") ? Long.parseLong(pJsonObject.getString("id")) : null);
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setExperienceInstitute(pJsonObject.getString("experienceInstitution").isEmpty() ? "" : pJsonObject
        .getString("experienceInstitution"));
    pMutable.setDesignation(pJsonObject.getString("experienceDesignation").isEmpty() ? "" : pJsonObject
        .getString("experienceDesignation"));
    pMutable.setExperienceFromDate(pJsonObject.getString("experienceFrom") == null
        || pJsonObject.getString("experienceFrom").isEmpty() ? null : mDateFormat.parse(pJsonObject
        .getString("experienceFrom")));
    pMutable.setExperienceToDate(pJsonObject.getString("experienceTo") == null
        || pJsonObject.getString("experienceTo").isEmpty() ? null : mDateFormat.parse(pJsonObject
        .getString("experienceTo")));
    pMutable.setExperienceDuration(pJsonObject.getInt("experienceDuration", 0));
    pMutable.setExperienceDurationString(pJsonObject.getString("experienceDurationString").isEmpty() ? "" : pJsonObject
        .getString("experienceDurationString"));
    pMutable.setExperienceCategoryId(pJsonObject.get("experienceCategory").getValueType()
        .equals(JsonValue.ValueType.NULL) ? 0 : pJsonObject.getJsonObject("experienceCategory").getInt("id"));
  }
}
