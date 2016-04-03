package org.ums.common.builder;


import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.mutable.MutableSemesterWithdrawal;
import org.ums.persistent.model.PersistentProgram;
import org.ums.persistent.model.PersistentSemester;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class SemesterWithdrawalBuilder implements Builder<SemesterWithdrawal,MutableSemesterWithdrawal> {

  @Override
  public void build(JsonObjectBuilder pBuilder, SemesterWithdrawal pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    pBuilder.add("id",pReadOnly.getId());
    pBuilder.add("semesterId",pReadOnly.getSemester().getId());
    pBuilder.add("programId",pReadOnly.getProgram().getId());
    pBuilder.add("cause",pReadOnly.getCause());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("semesterWithdraw").path(pReadOnly.getId().toString()).build().toString());

  }

  @Override
  public void build(MutableSemesterWithdrawal pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {
    pMutable.setId(Integer.parseInt(pJsonObject.getString("id")));
    PersistentSemester semester = new PersistentSemester();
    semester.setId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutable.setSemester(semester);
    PersistentProgram program = new PersistentProgram();
    program.setId(Integer.parseInt(pJsonObject.getString("programId")));
    pMutable.setProgram(program);
    pMutable.setCause(pJsonObject.getString("cause"));
  }
}
