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
    pBuilder.add("agendaEditor", pReadOnly.getAgendaEditor());
    pBuilder.add("resolution", pReadOnly.getResolution() == null ? "" : pReadOnly.getResolution());
    pBuilder.add("resolutionEditor", pReadOnly.getResolutionEditor());
    pBuilder.add("scheduleId", pReadOnly.getScheduleId().toString());
    pBuilder.add("showExpandButton", true);
  }

  @Override
  public void build(MutableAgendaResolution pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.containsKey("id") ? pJsonObject.getString("id").equals("") ? null : Long.parseLong(pJsonObject.getString("id")) : null);
    pMutable.setAgendaNo(pJsonObject.containsKey("agendaNo") ? pJsonObject.getString("agendaNo") : null);
    pMutable.setAgenda(pJsonObject.containsKey("agenda") ? pJsonObject.getString("agenda") : null);
    pMutable.setAgendaEditor(pJsonObject.getString("agendaEditor"));
    pMutable.setResolution(pJsonObject.containsKey("resolution") ? pJsonObject.getString("resolution") : null);
    pMutable.setResolutionEditor(pJsonObject.getString("resolutionEditor"));
    pMutable.setScheduleId(Long.parseLong(pJsonObject.getString("scheduleId")));
  }
}
