package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Checkout;
import org.ums.domain.model.mutable.library.MutableCheckout;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Locale;

@Component
public class CheckoutBuilder implements Builder<Checkout, MutableCheckout> {
  @Override
  public void build(JsonObjectBuilder pBuilder, Checkout pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutableCheckout pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
