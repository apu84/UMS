package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AwardInformation;
import org.ums.domain.model.mutable.registrar.MutableAwardInformation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AwardInformationBuilder implements Builder<AwardInformation, MutableAwardInformation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AwardInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("awardName", pReadOnly.getAwardName());
    pBuilder.add("awardInstitute", pReadOnly.getAwardInstitute());
    pBuilder.add("awardedYear", pReadOnly.getAwardedYear());
    pBuilder.add("awardShortDescription", pReadOnly.getAwardShortDescription());
  }

  @Override
  public void build(MutableAwardInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setAwardName(pJsonObject.getString("awardName"));
    pMutable.setAwardInstitute(pJsonObject.getString("awardInstitute"));
    pMutable.setAwardedYear(pJsonObject.getString("awardedYear"));
    pMutable.setAwardShortDescription(pJsonObject.getString("awardShortDescription"));
  }
}
