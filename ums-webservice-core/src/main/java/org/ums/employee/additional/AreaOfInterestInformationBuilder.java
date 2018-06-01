package org.ums.employee.additional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
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
    pBuilder.add("employeeId", pReadOnly.getId());
    pBuilder.add("areaOfInterest", pReadOnly.getAreaOfInterest() == null ? "" : pReadOnly.getAreaOfInterest());
  }

  @Override
  public void build(MutableAreaOfInterestInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getString("employeeId"));
    pMutable.setAreaOfInterest((pJsonObject.getString("areaOfInterest")));
  }
}
