package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AbsLateComingInfo;
import org.ums.domain.model.mutable.MutableAbsLateComingInfo;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
@Component
public class AbsLateComingInfoBuilder implements Builder<AbsLateComingInfo, MutableAbsLateComingInfo> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AbsLateComingInfo pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId());
    }
    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
    }
    if(pReadOnly.getExamType() != null) {
      pBuilder.add("examType", pReadOnly.getExamType());
    }
    if(pReadOnly.getPresentType() != null) {
      pBuilder.add("presentType", pReadOnly.getPresentType());
    }
    if(pReadOnly.getPresentInfo() != null) {
      pBuilder.add("presentInfo", pReadOnly.getPresentInfo());
    }
    if(pReadOnly.getRemarks() != null) {
      pBuilder.add("remarks", pReadOnly.getRemarks());
    }
    if(pReadOnly.getEmployeeId() != null) {
      pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    }
    if(pReadOnly.getEmployeeName() != null) {
      pBuilder.add("employeeName", pReadOnly.getEmployeeName());
    }
    if(pReadOnly.getEmployeeType() != null) {
      pBuilder.add("employeeType", pReadOnly.getEmployeeType());
    }
    if(pReadOnly.getDeptId() != null) {
      pBuilder.add("deptId", pReadOnly.getDeptId());
    }
    if(pReadOnly.getDeptName() != null) {
      pBuilder.add("deptName", pReadOnly.getDeptName());
    }
    if(pReadOnly.getInvigilatorRoomId() != null) {
      pBuilder.add("invigilatorRoomId", pReadOnly.getInvigilatorRoomId());
    }
    if(pReadOnly.getInvigilatorRoomName() != null) {
      pBuilder.add("invigilatorRoomName", pReadOnly.getInvigilatorRoomName());
    }
    if(pReadOnly.getExamDate() != null) {
      pBuilder.add("examDate", pReadOnly.getExamDate());
    }
    if(pReadOnly.getArrivalTime() != null) {
      pBuilder.add("arrivalTime", pReadOnly.getArrivalTime());
    }

  }

  @Override
  public void build(MutableAbsLateComingInfo pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("semesterId"))
      pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    if(pJsonObject.containsKey("examType"))
      pMutable.setSemesterId(pJsonObject.getInt("examType"));
    if(pJsonObject.containsKey("presentType"))
      pMutable.setSemesterId(pJsonObject.getInt("presentType"));
    if(pJsonObject.containsKey("remarks"))
      pMutable.setSemesterId(pJsonObject.getInt("remarks"));
    if(pJsonObject.containsKey("employeeId"))
      pMutable.setSemesterId(pJsonObject.getInt("employeeId"));
    if(pJsonObject.containsKey("invigilatorRoomId"))
      pMutable.setSemesterId(pJsonObject.getInt("invigilatorRoomId"));
    if(pJsonObject.containsKey("examDate"))
      pMutable.setSemesterId(pJsonObject.getInt("examDate"));
    if(pJsonObject.containsKey("arrivalTime"))
      pMutable.setSemesterId(pJsonObject.getInt("arrivalTime"));
  }
}
