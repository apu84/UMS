package org.ums.builder;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.mutable.MutableCompany;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 12-Feb-18.
 */
@Component
public class CompanyBuilder implements Builder<Company, MutableCompany> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Company pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    throw new NotImplementedException();
  }

  public void builder(JsonObjectBuilder pBuilder, Company pCompany) {
    pBuilder.add("id", pCompany.getId());
    pBuilder.add("name", pCompany.getName());
    pBuilder.add("shortName", pCompany.getShortName());
  }

  @Override
  public void build(MutableCompany pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    throw new NotImplementedException();
  }

  public void build(MutableCompany pMutableCompany, JsonObject pJsonObject) {
    if(pJsonObject.containsKey("id"))
      pMutableCompany.setId(pJsonObject.getString("id"));
    if(pJsonObject.containsKey("name"))
      pMutableCompany.setName(pJsonObject.getString("name"));
    if(pJsonObject.containsKey("shortName"))
      pMutableCompany.setShortName(pJsonObject.getString("shortName"));
  }
}
