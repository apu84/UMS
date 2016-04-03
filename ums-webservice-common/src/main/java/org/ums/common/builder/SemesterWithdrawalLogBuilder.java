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
    pBuilder.add("actor",pReadOnly.getActor());
    pBuilder.add("actorId",pReadOnly.getActorId());
    pBuilder.add("action",pReadOnly.getAction());
    pBuilder.add("comments",pReadOnly.getComments());
    pBuilder.add("eventDateTime",pReadOnly.getEventDateTime());
    pBuilder.add("lastModified",pReadOnly.getLastModified());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("semesterWithdrawLog").path(pReadOnly.getId().toString()).build().toString());

  }

  @Override
  public void build(MutableSemesterWithdrawalLog pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {
    pMutable.setId(Integer.parseInt(pJsonObject.getString("id")));
    PersistentSemesterWithdrawal persistentSemesterWithdrawal = new PersistentSemesterWithdrawal();
    persistentSemesterWithdrawal.setId(Integer.parseInt(pJsonObject.getString("semesterWithdrawId")));
    pMutable.setSemesterWithdrawal(persistentSemesterWithdrawal);
    pMutable.setActor(Integer.parseInt(pJsonObject.getString("actor")));
    pMutable.setActorId(pJsonObject.getString("actorId"));
    pMutable.setAction(Integer.parseInt(pJsonObject.getString("action")));
    pMutable.setComments(pJsonObject.getString("comments"));
  }
}
