package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.EmpExamAttendance;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmpExamAttendance;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 7/27/2018.
 */
@Component
public class EmpExamAttendanceBuilder implements Builder<EmpExamAttendance, MutableEmpExamAttendance> {
  @Override
  public void build(JsonObjectBuilder pBuilder, EmpExamAttendance pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if(pReadOnly.getId() != null) {
      pBuilder.add("id", pReadOnly.getId().toString());
    }
    if(pReadOnly.getSemesterId() != null) {
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
    }
    if(pReadOnly.getExamType() != null) {
      pBuilder.add("examType", pReadOnly.getExamType());
    }
    if(pReadOnly.getRoomInCharge() != null) {
      pBuilder.add("roomInCharge", pReadOnly.getRoomInCharge());
      pBuilder.add("roomInChargeStatus", pReadOnly.getRoomInCharge() == 1 ? " (Room In Charge)" : " ");
    }
    if(pReadOnly.getEmployeeId() != null) {
      pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    }
    if(pReadOnly.getEmployeeType() != null) {
      pBuilder.add("employeeType", pReadOnly.getEmployeeType());
    }
    if(pReadOnly.getEmployeeId() != null) {
      Employee employee = pReadOnly.getEmployees();
      pBuilder.add("employeeFullName", employee.getPersonalInformation().getName());
      pBuilder.add("employeeType", employee.getEmployeeType());
    }
    if(pReadOnly.getDepartmentId() != null) {
      pBuilder.add("deptId", pReadOnly.getDepartmentId());
    }
    if(pReadOnly.getDesignationId() != null) {
      pBuilder.add("designationId", pReadOnly.getDesignationId());
    }
    if(pReadOnly.getDepartmentId() != null) {
      Department department = pReadOnly.getDepartment();
      pBuilder.add("departmentId", department.getId());
      pBuilder.add("department",
          pUriInfo.getBaseUriBuilder().path("academic").path("department").path(String.valueOf(department.getId()))
              .build().toString());
      pBuilder.add("departmentName", department.getLongName());
      pBuilder.add("departmentShortName", department.getShortName());
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
    if(pReadOnly.getInvigilatorDate() != null) {
      pBuilder.add("invigilatorDate", pReadOnly.getInvigilatorDate());
    }
    if(pReadOnly.getReserveDate() != null) {
      pBuilder.add("reserveDate", pReadOnly.getReserveDate());
    }
  }

  @Override
  public void build(MutableEmpExamAttendance pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    if(pJsonObject.containsKey("semesterId"))
      pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    if(pJsonObject.containsKey("examType"))
      pMutable.setExamType(pJsonObject.getInt("examType"));
    if(pJsonObject.containsKey("employeeId"))
      pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    if(pJsonObject.containsKey("invigilatorRoomId"))
      pMutable.setInvigilatorRoomId(Long.parseLong(pJsonObject.getString("invigilatorRoomId")));
    if(pJsonObject.containsKey("examDate"))
      pMutable.setExamDate(pJsonObject.getString("examDate"));
    if(pJsonObject.containsKey("invigilatorDate"))
      pMutable.setInvigilatorDate(pJsonObject.getString("invigilatorDate"));
    if(pJsonObject.containsKey("reserveDate"))
      pMutable.setReserveDate(pJsonObject.getString("reserveDate"));
    if(pJsonObject.containsKey("roomInCharge"))
      pMutable.setRoomInCharge(pJsonObject.getInt("roomInCharge"));
  }
}
