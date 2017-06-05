package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.ExperienceInformation;
import org.ums.domain.model.mutable.registrar.MutableExperienceInformation;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ExperienceInformationBuilder implements Builder<ExperienceInformation, MutableExperienceInformation> {

  @Autowired
  UserManager userManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, ExperienceInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("experienceInstitution", pReadOnly.getExperienceInstitute());
    pBuilder.add("experienceDesignation", pReadOnly.getDesignation());
    pBuilder.add("experienceFrom", pReadOnly.getExperienceFromDate());
    pBuilder.add("experienceTo", pReadOnly.getExperienceToDate());
  }

  @Override
  public void build(MutableExperienceInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    pMutable.setExperienceInstitute(pJsonObject.getString("experienceInstitution"));
    pMutable.setDesignation(pJsonObject.getString("experienceDesignation"));
    pMutable.setExperienceFromDate(pJsonObject.getString("experienceFrom"));
    pMutable.setExperienceToDate(pJsonObject.getString("experienceTo"));
  }
}
