package org.ums.resource.company;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
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
  public void build(JsonObjectBuilder pBuilder, Company pCompany, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pCompany.getId());
    pBuilder.add("name", pCompany.getName());
    pBuilder.add("shortName", pCompany.getShortName());
    pBuilder.add("address", pCompany.getAddress());
  }

  public void builder(JsonObjectBuilder pBuilder, Company pCompany) {
    pBuilder.add("id", pCompany.getId());
    pBuilder.add("name", pCompany.getName());
    pBuilder.add("shortName", pCompany.getShortName());
    pBuilder.add("address", pCompany.getAddress());
  }

  @Override
  public void build(MutableCompany pMutableCompany, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutableCompany.setId(pJsonObject.getString("id"));
    pMutableCompany.setName(pJsonObject.getString("name"));
    pMutableCompany.setShortName(pJsonObject.getString("shortName"));
    pMutableCompany.setAddress(pJsonObject.getString("address"));
  }

  public void build(MutableCompany pMutableCompany, JsonObject pJsonObject) {
    if(pJsonObject.containsKey("id"))
      pMutableCompany.setId(pJsonObject.getString("id"));
      pMutableCompany.setName(pJsonObject.getString("name"));
      pMutableCompany.setShortName(pJsonObject.getString("shortName"));
      pMutableCompany.setAddress(pJsonObject.getString("address"));
  }
}
