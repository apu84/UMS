package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AreaOfInterestInformation;
import org.ums.domain.model.mutable.registrar.MutableAreaOfInterestInformation;
import org.ums.manager.AreaOfInterestManager;
import org.ums.usermanagement.user.UserManager;

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
    pBuilder.add("areaOfInterestId", pReadOnly.getAreaOfInterestId());
  }

  @Override
  public void build(MutableAreaOfInterestInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setAreaOfInterest(mAreaOfInterestManager.get(pJsonObject.getInt("id")));
  }
}
