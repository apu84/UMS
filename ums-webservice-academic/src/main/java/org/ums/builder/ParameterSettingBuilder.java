package org.ums.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.formatter.DateFormat;
import org.ums.persistent.model.PersistentParameter;
import org.ums.persistent.model.PersistentSemester;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ParameterSettingBuilder implements Builder<ParameterSetting, MutableParameterSetting> {
  @Autowired
  @Qualifier("genericDateFormat")
  DateFormat mDateFormat;

  Logger mLogger = LoggerFactory.getLogger(ParameterSettingBuilder.class);

  @Override
  public void build(JsonObjectBuilder pBuilder, ParameterSetting pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    pBuilder.add("parameterId", pReadOnly.getParameter().getId());
    try {
      pBuilder.add("startDate", UmsUtils.formatDate(pReadOnly.getStartDate(), "dd-MM-yyyy"));
      pBuilder.add("endDate", UmsUtils.formatDate(pReadOnly.getEndDate(), "dd-MM-yyyy"));
      pBuilder.add("startDateJs", UmsUtils.formatDate(pReadOnly.getStartDate(), "MM-dd-yyyy"));
      pBuilder.add("endDateJs", UmsUtils.formatDate(pReadOnly.getEndDate(), "MM-dd-yyyy"));
    } catch(Exception e) {
      e.printStackTrace();
    }

    /*
     * pBuilder.add("self",
     * pUriInfo.getBaseUriBuilder().path("academic").path("parameterSetting").path
     * (String.valueOf(pReadOnly.getId())) .build().toString());
     */
  }

  @Override
  public void build(MutableParameterSetting pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    if(pJsonObject.containsKey("id")) {
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    }
    PersistentSemester semester = new PersistentSemester();
    semester.setId(pJsonObject.getInt("semesterId"));
    pMutable.setSemester(semester);
    PersistentParameter parameter = new PersistentParameter();
    parameter.setId(pJsonObject.getString("parameterId"));
    pMutable.setParameter(parameter);
    try {
      pMutable.setStartDate(UmsUtils.convertToDate((pJsonObject.getString("startDate")), "dd-MM-yyyy"));
      pMutable.setEndDate(UmsUtils.convertToDate((pJsonObject.getString("endDate")), "dd-MM-yyyy"));

    } catch(Exception e) {

    }
  }
}
