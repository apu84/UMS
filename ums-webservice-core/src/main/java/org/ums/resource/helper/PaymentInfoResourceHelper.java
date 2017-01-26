package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.PaymentInfoBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.PaymentInfo;
import org.ums.domain.model.mutable.MutablePaymentInfo;
import org.ums.manager.ContentManager;
import org.ums.manager.PaymentInfoManager;
import org.ums.persistent.model.PersistentPaymentInfo;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 25-Jan-17.
 */
@Component
public class PaymentInfoResourceHelper extends
    ResourceHelper<PaymentInfo, MutablePaymentInfo, Integer> {

  @Autowired
  private PaymentInfoManager mManager;

  @Autowired
  private PaymentInfoBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    JsonObject jsonObject = entries.getJsonObject(0);
    PersistentPaymentInfo paymentInfos = new PersistentPaymentInfo();
    getBuilder().build(paymentInfos, jsonObject, localCache);
    getContentManager().create(paymentInfos);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getAdmissionPaymentInfo(final String pReceiptId, final int pSemesterId,
      final UriInfo mUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    try {
      List<PaymentInfo> paymentInfos = getContentManager().getPaymentInfo(pReceiptId, pSemesterId);
      for(PaymentInfo paymentInfo : paymentInfos) {
        children.add(toJson(paymentInfo, mUriInfo, localCache));
      }
    } catch(EmptyResultDataAccessException e) {
      // do nothing
    }

    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected PaymentInfoManager getContentManager() {
    return mManager;
  }

  @Override
  protected PaymentInfoBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(PaymentInfo pReadonly) {
    return pReadonly.getLastModified();
  }
}
