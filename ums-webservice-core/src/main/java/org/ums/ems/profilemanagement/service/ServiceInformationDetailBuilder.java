package org.ums.ems.profilemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.enums.common.EmploymentPeriod;
import org.ums.formatter.DateFormat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ServiceInformationDetailBuilder implements
    Builder<ServiceInformationDetail, MutableServiceInformationDetail> {

  @Autowired
  @Qualifier("genericDateFormat")
  DateFormat mDateFormat;

  @Autowired
  ServiceInformationManager mServiceInformationManager;

  private EmploymentPeriod mEmploymentPeriod;

  @Override
  public void build(JsonObjectBuilder pBuilder, ServiceInformationDetail pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    JsonObjectBuilder intervalBuilder = Json.createObjectBuilder();
    intervalBuilder.add("id", pReadOnly.getEmploymentPeriodId()).add("name",
        mEmploymentPeriod.get(pReadOnly.getEmploymentPeriodId()).getLabel());
    pBuilder.add("interval", intervalBuilder);
    pBuilder.add("startDate", mDateFormat.format(pReadOnly.getStartDate()));
    pBuilder.add("endDate", pReadOnly.getEndDate() == null ? "" : mDateFormat.format(pReadOnly.getEndDate()));
    pBuilder.add("comment", pReadOnly.getComment() == null ? "" : pReadOnly.getComment());
    pBuilder.add("serviceId", pReadOnly.getServiceId().toString());
  }

  @Override
  public void build(MutableServiceInformationDetail pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setId(!pJsonObject.getString("id").equals("") ? Long.parseLong(pJsonObject.getString("id")) : null);
    pMutable.setEmploymentPeriod(mEmploymentPeriod.get(pJsonObject.getJsonObject("interval").getInt("id")));
    pMutable.setStartDate(mDateFormat.parse(pJsonObject.getString("startDate")));
    pMutable.setEndDate(pJsonObject.containsKey("endDate") ? pJsonObject.getString("endDate").isEmpty() ? null
        : mDateFormat.parse(pJsonObject.getString("endDate")) : null);
    pMutable.setComment(pJsonObject.containsKey("comment") ? pJsonObject.getString("comment") : "");
    pMutable.setServiceId(Long.parseLong(pJsonObject.getString("serviceId")));
  }
}
