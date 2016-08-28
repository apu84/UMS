package org.ums.common.builder;


import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.SemesterWithdrawalLog;
import org.ums.domain.model.mutable.MutableSemesterWithdrawalLog;
import org.ums.persistent.model.PersistentSemesterWithdrawal;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class SemesterWithdrawalLogBuilder implements Builder<SemesterWithdrawalLog,MutableSemesterWithdrawalLog> {

  @Override
  public void build(JsonObjectBuilder pBuilder, SemesterWithdrawalLog pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    pBuilder.add("id",pReadOnly.getId());
    pBuilder.add("semesterWithdrawId",pReadOnly.getSemesterWithdrawal().getId());
    pBuilder.add("employeeId",pReadOnly.getEmployeeId());
    pBuilder.add("action",pReadOnly.getAction());
    pBuilder.add("eventDateTime",pReadOnly.getEventDateTime());
    pBuilder.add("lastModified",pReadOnly.getLastModified());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("semesterWithdrawLog").path(pReadOnly.getId().toString()).build().toString());

  }

  @Override
  public void build(MutableSemesterWithdrawalLog pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {
    PersistentSemesterWithdrawal persistentSemesterWithdrawal = new PersistentSemesterWithdrawal();
    persistentSemesterWithdrawal.setId(pJsonObject.getInt("semesterWithdrawId"));
    pMutable.setSemesterWithdrawal(persistentSemesterWithdrawal);
    /*PersistentEmployee employee = new PersistentEmployee();
    employee.setId(pJsonObject.getString("employeeId"));*/
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setAction(pJsonObject.getInt("action"));
  }
}
