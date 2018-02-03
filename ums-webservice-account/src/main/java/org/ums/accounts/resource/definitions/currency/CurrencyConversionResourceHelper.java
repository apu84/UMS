package org.ums.accounts.resource.definitions.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.CurrencyConversion;
import org.ums.domain.model.mutable.accounts.MutableCurrencyConversion;
import org.ums.manager.accounts.CurrencyConversionManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
public class CurrencyConversionResourceHelper extends
    ResourceHelper<CurrencyConversion, MutableCurrencyConversion, Long> {
  @Autowired
  private CurrencyConversionManager mCurrencyConversionManager;
  @Autowired
  private CurrencyConversionBuilder mCurrencyConversionBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected CurrencyConversionManager getContentManager() {
    return mCurrencyConversionManager;
  }

  @Override
  protected Builder<CurrencyConversion, MutableCurrencyConversion> getBuilder() {
    return mCurrencyConversionBuilder;
  }

  @Override
  protected String getETag(CurrencyConversion pReadonly) {
    return null;
  }
}
