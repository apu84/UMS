package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.CheckoutBuilder;
import org.ums.domain.model.immutable.library.Checkout;
import org.ums.domain.model.mutable.library.MutableCheckout;
import org.ums.manager.ContentManager;
import org.ums.manager.library.CheckoutManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class CheckoutResourceHelper extends ResourceHelper<Checkout, MutableCheckout, Long> {

  @Autowired
  CheckoutManager mManager;

  @Autowired
  CheckoutBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<Checkout, MutableCheckout, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Checkout, MutableCheckout> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Checkout pReadonly) {
    return pReadonly.getLastModified();
  }
}
