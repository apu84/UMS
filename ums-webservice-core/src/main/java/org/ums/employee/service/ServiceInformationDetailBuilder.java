package org.ums.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.enums.common.EmploymentPeriod;
import org.ums.formatter.DateFormat;

import javax.json.*;
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
    pBuilder.add("dbAction", "");
  }

  @Override
  public void build(MutableServiceInformationDetail pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmploymentPeriod(mEmploymentPeriod.get(pJsonObject.getJsonObject("interval").getInt("id")));
    pMutable.setStartDate(mDateFormat.parse(pJsonObject.getString("startDate")));
    pMutable.setEndDate(pJsonObject.containsKey("endDate") ? pJsonObject.getString("endDate").isEmpty() ? null
        : mDateFormat.parse(pJsonObject.getString("endDate")) : null);
    pMutable.setServiceId(Long.parseLong(pJsonObject.getString("serviceId")));
  }

  public void serviceInformationDetailBuilder(MutableServiceInformationDetail pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache, Long pServiceId) {
    if(pJsonObject.getString("dbAction").equals("Update") || pJsonObject.getString("dbAction").equals("Delete")) {
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    }
    pMutable.setEmploymentPeriod(mEmploymentPeriod.get(pJsonObject.getJsonObject("interval").getInt("id")));
    pMutable.setStartDate(mDateFormat.parse(pJsonObject.getString("startDate")));
    pMutable.setEndDate(pJsonObject.containsKey("endDate") ? pJsonObject.getString("endDate").equals("") ? null
        : mDateFormat.parse(pJsonObject.getString("endDate")) : null);
    pMutable.setComment(pJsonObject.getString("comment") == null ? "" : pJsonObject.getString("comment"));
    pMutable.setServiceId(pServiceId);
  }
}
