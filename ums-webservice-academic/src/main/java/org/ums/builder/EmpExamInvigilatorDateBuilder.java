package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.EmpExamInvigilatorDate;
import org.ums.domain.model.mutable.MutableEmpExamInvigilatorDate;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
@Component
public class EmpExamInvigilatorDateBuilder implements Builder<EmpExamInvigilatorDate, MutableEmpExamInvigilatorDate> {
  @Override
  public void build(JsonObjectBuilder pBuilder, EmpExamInvigilatorDate pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId());
    }
    if(pReadOnly.getExamDate() != null) {
      pBuilder.add("examDate", pReadOnly.getExamDate());
    }
    if(pReadOnly.getAttendantId() != null) {
      pBuilder.add("attendantId", pReadOnly.getAttendantId());
    }

  }

  @Override
  public void build(MutableEmpExamInvigilatorDate pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("attendantId"))
      pMutable.setAttendantId(Long.parseLong(pJsonObject.getString("attendantId")));
    if(pJsonObject.containsKey("examDate"))
      pMutable.setExamDate(pJsonObject.getString("examDate"));
  }
}
