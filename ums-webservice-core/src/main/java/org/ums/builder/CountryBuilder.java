package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.mutable.common.MutableCountry;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Ifti on 31-Jan-17.
 */
@Component
public class CountryBuilder implements Builder<Country, MutableCountry> {

  @Override
  public void build(JsonObjectBuilder pBuilder, Country pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("code", pReadOnly.getCode());
    pBuilder.add("name", pReadOnly.getName());
    pBuilder.add("lastModified", pReadOnly.getLastModified() == null ? "" : pReadOnly.getLastModified());
  }

  @Override
  public void build(MutableCountry pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    // Do Nothing
  }
}
