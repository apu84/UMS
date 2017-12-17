package org.ums.employee.additional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.employee.additional.AreaOfInterestInformation;
import org.ums.employee.additional.MutableAdditionalInformation;
import org.ums.employee.additional.MutableAreaOfInterestInformation;
import org.ums.manager.AreaOfInterestManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AreaOfInterestInformationBuilder implements
    Builder<AreaOfInterestInformation, MutableAreaOfInterestInformation> {

  @Autowired
  AreaOfInterestManager mAreaOfInterestManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, AreaOfInterestInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getAreaOfInterestId());
    pBuilder.add("name", mAreaOfInterestManager.get(pReadOnly.getAreaOfInterestId()).getAreaOfInterest());
  }

  @Override
  public void build(MutableAreaOfInterestInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setAreaOfInterest(mAreaOfInterestManager.get(pJsonObject.getInt("id")));
  }

  public void aoiBuilder(MutableAreaOfInterestInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache,
      MutableAdditionalInformation mutableAdditionalInformation) {
    pMutable.setEmployeeId(mutableAdditionalInformation.getId());
    pMutable.setAreaOfInterest(mAreaOfInterestManager.get(pJsonObject.getInt("id")));
  }
}
