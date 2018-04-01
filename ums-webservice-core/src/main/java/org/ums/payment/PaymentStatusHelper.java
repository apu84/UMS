package org.ums.payment;

import org.apache.commons.lang.NotImplementedException;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.accounts.MutablePaymentStatus;
import org.ums.fee.accounts.PaymentStatus;
import org.ums.fee.accounts.PaymentStatusManager;
import org.ums.formatter.DateFormat;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;
import org.ums.resource.filter.FilterItem;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component("PaymentStatusHelper")
public class PaymentStatusHelper extends ResourceHelper<PaymentStatus, MutablePaymentStatus, Long> {
  @Autowired
  protected PaymentStatusManager mPaymentStatusManager;
  @Autowired
  @Qualifier("genericDateFormat")
  protected DateFormat mDateFormat;
  @Autowired
  @Qualifier("PaymentStatusBuilder")
  private PaymentStatusBuilder mPaymentStatusBuilder;

  private List<FilterItem> mFilterItems;

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
    return buildList(paymentStatusList, pItemsPerPage, pPageNumber, pUriInfo);
  }

  JsonObject getReceivedPayments(int pItemsPerPage, int pPageNumber, JsonObject pFilter, UriInfo pUriInfo) {
    List<PaymentStatus> paymentStatusList =
        mPaymentStatusManager.paginatedList(pItemsPerPage, pPageNumber, buildFilterQuery(pFilter));
    return buildList(paymentStatusList, pItemsPerPage, pPageNumber, pUriInfo);
  }

  private JsonObject buildList(List<PaymentStatus> paymentStatusList, int pItemsPerPage, int pPageNumber,
                               UriInfo pUriInfo) {
    LocalCache cache = new LocalCache();
    JsonArrayBuilder array = Json.createArrayBuilder();
    paymentStatusList.forEach((pPaymentStatus) -> {
      array.add(toJson(pPaymentStatus, pUriInfo, cache));
    });
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("entries", array);
    if (paymentStatusList.size() > 0) {
      addLink("next", pPageNumber, pItemsPerPage, pUriInfo, jsonObjectBuilder);
    }
    if (pPageNumber > 1) {
      addLink("previous", pPageNumber, pItemsPerPage, pUriInfo, jsonObjectBuilder);
    }
    return jsonObjectBuilder.build();
  }

  JsonArray getFilterItems() {
    if(mFilterItems == null) {
      mFilterItems = buildFilter();
    }
    return getFilterJson(mFilterItems);
  }

  private void addLink(String direction, Integer pCurrentPage, Integer itemsPerPage, UriInfo pUriInfo,
      JsonObjectBuilder pJsonObjectBuilder) {
    UriBuilder builder = new JerseyUriBuilder();
    Integer nextPage = direction.equalsIgnoreCase("next") ? pCurrentPage + 1 : pCurrentPage - 1;
    builder.path(pUriInfo.getPath()).queryParam("pageNumber", nextPage).queryParam("itemsPerPage", itemsPerPage);
    pJsonObjectBuilder.add(direction, builder.build().toString());
  }

  private List<FilterItem> buildFilter() {
    List<FilterItem> filters = new ArrayList<>();

    filters.add(new FilterItem("Start", PaymentStatusManager.FilterCriteria.RECEIVED_START.toString(),
        FilterItem.Type.DATE));
    filters
        .add(new FilterItem("End", PaymentStatusManager.FilterCriteria.RECEIVED_END.toString(), FilterItem.Type.DATE));
    filters.add(new FilterItem("Transaction Id", PaymentStatusManager.FilterCriteria.TRANSACTION_ID.toString(),
        FilterItem.Type.INPUT));
    filters
        .add(new FilterItem("Account", PaymentStatusManager.FilterCriteria.ACCOUNT.toString(), FilterItem.Type.INPUT));
    FilterItem status =
        new FilterItem("Payment Status", PaymentStatusManager.FilterCriteria.PAYMENT_STATUS.toString(),
            FilterItem.Type.SELECT);
    status.addOption(PaymentStatus.Status.VERIFIED.getLabel(), PaymentStatus.Status.VERIFIED.getId());
    status.addOption(PaymentStatus.Status.RECEIVED.getLabel(), PaymentStatus.Status.RECEIVED.getId());
    status.addOption(PaymentStatus.Status.REJECTED.getLabel(), PaymentStatus.Status.REJECTED.getId());
    filters.add(status);

    FilterItem mop =
        new FilterItem("Method of payment", PaymentStatusManager.FilterCriteria.METHOD_OF_PAYMENT.toString(),
            FilterItem.Type.SELECT);
    mop.addOption("Cash", 1);
    mop.addOption("Payorder", 2);
    mop.addOption("Cheque", 3);
    filters.add(mop);

    return filters;
  }

  @Override
  protected DateFormat getDateFormatter() {
    return mDateFormat;
  }
}
