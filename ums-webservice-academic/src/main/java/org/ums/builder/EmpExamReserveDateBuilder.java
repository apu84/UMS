package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.EmpExamReserveDate;
import org.ums.domain.model.mutable.MutableEmpExamReserveDate;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
@Component
public class EmpExamReserveDateBuilder implements Builder<EmpExamReserveDate, MutableEmpExamReserveDate> {
  @Override
  public void build(JsonObjectBuilder pBuilder, EmpExamReserveDate pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
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
  public void build(MutableEmpExamReserveDate pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("attendantId"))
      pMutable.setAttendantId(Long.parseLong(pJsonObject.getString("attendantId")));
    if(pJsonObject.containsKey("examDate"))
      pMutable.setExamDate(pJsonObject.getString("examDate"));
  }
}
