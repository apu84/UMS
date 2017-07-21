package org.ums.builder;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.formatter.DateFormat;
import org.ums.persistent.model.PersistentParameter;
import org.ums.persistent.model.PersistentSemester;

@Component
public class ParameterSettingBuilder implements Builder<ParameterSetting, MutableParameterSetting> {
  @Autowired
  DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, ParameterSetting pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    pBuilder.add("parameterId", pReadOnly.getParameter().getId());
    pBuilder.add("startDate", mDateFormat.format(pReadOnly.getStartDate()));
    pBuilder.add("endDate", mDateFormat.format(pReadOnly.getEndDate()));
    pBuilder.add("self",
        pUriInfo.getBaseUriBuilder().path("academic").path("parameterSetting").path(String.valueOf(pReadOnly.getId()))
            .build().toString());
  }

  @Override
  public void build(MutableParameterSetting pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    if(pJsonObject.containsKey("id")) {
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    }
    PersistentSemester semester = new PersistentSemester();
    semester.setId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutable.setSemester(semester);
    PersistentParameter parameter = new PersistentParameter();
    parameter.setId(pJsonObject.getString("parameterId"));
    pMutable.setParameter(parameter);
    pMutable.setStartDate(mDateFormat.parse(pJsonObject.getString("startDate")));
    pMutable.setEndDate(mDateFormat.parse(pJsonObject.getString("endDate")));
  }
}
