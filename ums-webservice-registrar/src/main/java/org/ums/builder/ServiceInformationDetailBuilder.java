package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.ServiceInformationDetail;
import org.ums.domain.model.mutable.registrar.MutableServiceInformationDetail;
import org.ums.enums.common.EmploymentPeriod;
import org.ums.formatter.DateFormat;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ServiceInformationDetailBuilder implements
    Builder<ServiceInformationDetail, MutableServiceInformationDetail> {

  @Autowired
  DateFormat mDateFormat;

  private EmploymentPeriod mEmploymentPeriod;

  @Override
  public void build(JsonObjectBuilder pBuilder, ServiceInformationDetail pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("intervalId", pReadOnly.getEmploymentPeriodId());
    pBuilder.add("startDate", mDateFormat.format(pReadOnly.getStartDate()));
    pBuilder.add("endDate", pReadOnly.getEndDate() == null ? "" : mDateFormat.format(pReadOnly.getEndDate()));
    pBuilder.add("serviceId", pReadOnly.getServiceId());
  }

  @Override
  public void build(MutableServiceInformationDetail pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmploymentPeriod(mEmploymentPeriod.get(pJsonObject.getJsonObject("interval").getInt("id")));
    pMutable.setStartDate(mDateFormat.parse(pJsonObject.getString("startDate")));
    pMutable
        .setEndDate(pJsonObject.containsKey("endDate") ? mDateFormat.parse(pJsonObject.getString("endDate")) : null);
    pMutable.setServiceId(pJsonObject.getInt("serviceId"));
  }
}
