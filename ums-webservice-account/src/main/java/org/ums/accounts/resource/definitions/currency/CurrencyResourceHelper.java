package org.ums.accounts.resource.definitions.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.mutable.accounts.MutableCurrency;
import org.ums.manager.accounts.CurrencyManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
public class CurrencyResourceHelper extends ResourceHelper<Currency, MutableCurrency, Long> {
  @Autowired
  private CurrencyBuilder mCurrencyBuilder;
  @Autowired
  private CurrencyManager mCurrencyManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected CurrencyManager getContentManager() {
    return mCurrencyManager;
  }

  @Override
  protected Builder<Currency, MutableCurrency> getBuilder() {
    return mCurrencyBuilder;
  }

  @Override
  protected String getETag(Currency pReadonly) {
    return null;
  }
}
