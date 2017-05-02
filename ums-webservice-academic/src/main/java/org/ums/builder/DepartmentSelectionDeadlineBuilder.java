package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.DepartmentSelectionDeadline;
import org.ums.domain.model.mutable.MutableDepartmentSelectionDeadline;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 27-Apr-17.
 */
@Component
public class DepartmentSelectionDeadlineBuilder implements
    Builder<DepartmentSelectionDeadline, MutableDepartmentSelectionDeadline> {
  @Override
  public void build(JsonObjectBuilder pBuilder, DepartmentSelectionDeadline pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    if(pReadOnly.getId() != 0)
      pBuilder.add("id", pReadOnly.getId());
    if(pReadOnly.getSemester() != null) {
      pBuilder.add("semesterId", pReadOnly.getSemester().getId());
      pBuilder.add("semesterName", pReadOnly.getSemester().getName());
    }
    if(pReadOnly.getUnit() != null)
      pBuilder.add("unit", pReadOnly.getUnit());
    if(pReadOnly.getQuota() != null)
      pBuilder.add("quota", pReadOnly.getQuota());
    if(pReadOnly.getFromMeritSerialNumber() != 0)
      pBuilder.add("fromMeritSerialNumber", pReadOnly.getFromMeritSerialNumber());
    if(pReadOnly.getToMeritSerialNumber() != 0)
      pBuilder.add("toMeritSerialNumber", pReadOnly.getToMeritSerialNumber());
    if(pReadOnly.getDeadline() != null)
      pBuilder.add("deadline", UmsUtils.formatDate(pReadOnly.getDeadline(), "DD/MM/YYYY"));
  }

  @Override
  public void build(MutableDepartmentSelectionDeadline pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(pJsonObject.getInt("id"));
    if(pJsonObject.containsKey("semesterId"))
      pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    if(pJsonObject.containsKey("unit"))
      pMutable.setUnit(pJsonObject.getString("unit"));
    if(pJsonObject.containsKey("quota"))
      pMutable.setQuota(pJsonObject.getString("quota"));
    if(pJsonObject.containsKey("fromMeritSerialNumber"))
      pMutable.setFromMeritSerialNumber(pJsonObject.getInt("fromMeritSerialNumber"));
    if(pJsonObject.containsKey("toMeritSerialNumber"))
      pMutable.setToMeritSerialNumber(pJsonObject.getInt("toMeritSerialNumber"));
    if(pJsonObject.containsKey("deadline"))
      pMutable.setDeadline(UmsUtils.convertToDate(pJsonObject.getString("deadline"), "dd/MM/YYYY"));
  }
}
