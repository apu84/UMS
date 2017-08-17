package org.ums.meeting;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AgendaResolutionBuilder implements Builder<AgendaResolution, MutableAgendaResolution> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AgendaResolution pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("agendaNo", pReadOnly.getAgendaNo());
    pBuilder.add("agenda", pReadOnly.getAgenda());
    pBuilder.add("resolution", pReadOnly.getResolution());
    pBuilder.add("showExpandButton", true);
  }

  @Override
  public void build(MutableAgendaResolution pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setAgendaNo(pJsonObject.getString("agendaNo"));
    pMutable.setAgenda(pJsonObject.getString("agenda"));
    pMutable.setResolution(pJsonObject.getString("resolution"));
  }
}
