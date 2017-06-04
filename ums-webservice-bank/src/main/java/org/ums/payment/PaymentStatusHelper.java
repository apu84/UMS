package org.ums.payment;

import java.util.ArrayList;
import java.util.List;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.accounts.MutablePaymentStatus;
import org.ums.fee.accounts.PaymentStatus;
import org.ums.fee.accounts.PaymentStatusManager;
import org.ums.fee.accounts.PersistentPaymentStatus;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

@Component
public class PaymentStatusHelper extends ResourceHelper<PaymentStatus, MutablePaymentStatus, Long> {
  @Autowired
  PaymentStatusManager mPaymentStatusManager;
  @Autowired
  PaymentStatusBuilder mPaymentStatusBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<PaymentStatus, MutablePaymentStatus, Long> getContentManager() {
    return mPaymentStatusManager;
  }

  @Override
  protected Builder<PaymentStatus, MutablePaymentStatus> getBuilder() {
    return mPaymentStatusBuilder;
  }

  @Override
  protected String getETag(PaymentStatus pReadonly) {
    return pReadonly.getLastModified();
  }

  JsonObject getReceivedPayments(int pItemsPerPage, int pPageNumber, UriInfo pUriInfo) {
    List<PaymentStatus> paymentStatusList = mPaymentStatusManager.paginatedList(pItemsPerPage, pPageNumber);
    LocalCache cache = new LocalCache();
    JsonArrayBuilder array = Json.createArrayBuilder();
    paymentStatusList.forEach((pPaymentStatus) -> {
      array.add(toJson(pPaymentStatus, pUriInfo, cache));
    });
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("entries", array);
    addLink("next", pPageNumber, pItemsPerPage, pUriInfo, jsonObjectBuilder);
    if(pPageNumber > 1) {
      addLink("previous", pPageNumber, pItemsPerPage, pUriInfo, jsonObjectBuilder);
    }
    return jsonObjectBuilder.build();
  }

  @Transactional
  public Response updatePaymentStatus(JsonObject pJsonObject) {
    Validate.notEmpty(pJsonObject);
    Validate.notEmpty(pJsonObject.getJsonArray("entries"));
    JsonArray entries = pJsonObject.getJsonArray("entries");
    List<MutablePaymentStatus> paymentStatusList = new ArrayList<>();
    for(JsonValue entry : entries) {
      MutablePaymentStatus paymentStatus = new PersistentPaymentStatus();
      getBuilder().build(paymentStatus, (JsonObject) entry, null);
      PaymentStatus latestPayment = mPaymentStatusManager.get(paymentStatus.getId());
      Validate.isTrue(paymentStatus.getLastModified().equals(latestPayment.getLastModified()));
      Validate.isTrue(!latestPayment.isPaymentComplete());
      paymentStatus.setPaymentComplete(true);
      paymentStatusList.add(paymentStatus);
    }
    mPaymentStatusManager.update(paymentStatusList);
    return Response.ok().build();
  }

  private void addLink(String direction, Integer pCurrentPage, Integer itemsPerPage, UriInfo pUriInfo,
      JsonObjectBuilder pJsonObjectBuilder) {
    UriBuilder builder = pUriInfo.getBaseUriBuilder();
    Integer nextPage = direction.equalsIgnoreCase("next") ? pCurrentPage + 1 : pCurrentPage - 1;
    builder.path(pUriInfo.getPath()).queryParam("page", nextPage).queryParam("itemsPerPage", itemsPerPage);
    pJsonObjectBuilder.add(direction, builder.build().toString());
  }
}
