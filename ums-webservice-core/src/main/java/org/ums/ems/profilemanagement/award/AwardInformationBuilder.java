package org.ums.ems.profilemanagement.award;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AwardInformationBuilder implements Builder<AwardInformation, MutableAwardInformation> {

  @Override
  public void build(JsonObjectBuilder pBuilder, AwardInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("awardName", pReadOnly.getAwardName());
    pBuilder.add("awardInstitute", pReadOnly.getAwardInstitute() == null ? "" : pReadOnly.getAwardInstitute());
    pBuilder.add("awardedYear", pReadOnly.getAwardedYear());
    pBuilder.add("awardShortDescription",
        pReadOnly.getAwardShortDescription() == null ? "" : pReadOnly.getAwardShortDescription());
  }

  @Override
  public void build(MutableAwardInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setId(!pJsonObject.getString("id").equals("") ? Long.parseLong(pJsonObject.getString("id")) : null);
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setAwardName(pJsonObject.getString("awardName"));
    pMutable
        .setAwardInstitute(pJsonObject.containsKey("awardInstitute") ? pJsonObject.getString("awardInstitute") : "");
    pMutable.setAwardedYear(pJsonObject.getInt("awardedYear"));
    pMutable.setAwardShortDescription(pJsonObject.containsKey("awardShortDescription") ? pJsonObject
        .getString("awardShortDescription") : "");
  }
}
