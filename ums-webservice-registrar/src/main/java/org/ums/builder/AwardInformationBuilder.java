package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AwardInformation;
import org.ums.domain.model.mutable.registrar.MutableAwardInformation;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AwardInformationBuilder implements Builder<AwardInformation, MutableAwardInformation> {

  @Autowired
  UserManager userManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, AwardInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("awardName", pReadOnly.getAwardName());
    if(pReadOnly.getAwardInstitute() != null) {
      pBuilder.add("awardInstitute", pReadOnly.getAwardInstitute());
    }
    else {
      pBuilder.add("awardInstitute", "");
    }
    pBuilder.add("awardedYear", pReadOnly.getAwardedYear());
    if(pReadOnly.getAwardShortDescription() != null) {
      pBuilder.add("awardShortDescription", pReadOnly.getAwardShortDescription());
    }
    else {
      pBuilder.add("awardShortDescription", "");
    }
  }

  @Override
  public void build(MutableAwardInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
    pMutable.setAwardName(pJsonObject.getString("awardName"));
    if(!pJsonObject.containsKey("awardInstitute")) {
      pMutable.setAwardInstitute("");
    }
    else {
      pMutable.setAwardInstitute(pJsonObject.getString("awardInstitute"));
    }
    pMutable.setAwardedYear(pJsonObject.getString("awardedYear"));
    if(!pJsonObject.containsKey("awardShortDescription")) {
      pMutable.setAwardShortDescription("");
    }
    else {
      pMutable.setAwardShortDescription(pJsonObject.getString("awardShortDescription"));
    }
  }
}
