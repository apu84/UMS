package org.ums.accounts.resource.definitions.currency;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.mutable.accounts.MutableCurrency;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
public class CurrencyBuilder implements Builder<Currency, MutableCurrency> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Currency pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableCurrency pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
