package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.persistent.model.PersistentParameter;
import org.ums.persistent.model.PersistentSemester;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ParameterSettingBuilder implements Builder<ParameterSetting, MutableParameterSetting> {
  @Override
  public void build(JsonObjectBuilder pBuilder, ParameterSetting pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    pBuilder.add("parameterId", pReadOnly.getParameter().getId());
    pBuilder.add("startDate", pReadOnly.getStartDate());
    pBuilder.add("endDate", pReadOnly.getEndDate());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("parameterSetting")
        .path(String.valueOf(pReadOnly.getId())).build().toString());
  }

  @Override
  public void build(MutableParameterSetting pMutable, JsonObject pJsonObject, LocalCache pLocalCache)
      throws Exception {

    if(pJsonObject.getString("id") != null) {
      pMutable.setId(pJsonObject.getString("id"));
    }
    PersistentSemester semester = new PersistentSemester();
    semester.setId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutable.setSemester(semester);
    PersistentParameter parameter = new PersistentParameter();
    parameter.setId(pJsonObject.getString("parameterId"));
    pMutable.setParameter(parameter);
    pMutable.setStartDate(pJsonObject.getString("startDate"));
    pMutable.setEndDate(pJsonObject.getString("endDate"));
  }
}
