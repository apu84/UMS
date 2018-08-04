package org.ums.meeting;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
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
    pBuilder.add("plainAgenda", pReadOnly.getPlainAgenda());
    pBuilder.add("resolution", pReadOnly.getResolution() == null ? "" : pReadOnly.getResolution());
    pBuilder.add("resolutionEditor", pReadOnly.getResolutionEditor());
    pBuilder.add("plainResolution", pReadOnly.getPlainResolution());
    pBuilder.add("scheduleId", pReadOnly.getScheduleId().toString());
    pBuilder.add("showExpandButton", true);
  }

  @Override
  public void build(MutableAgendaResolution pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.containsKey("id") ? pJsonObject.getString("id").equals("") ? null : Long
        .parseLong(pJsonObject.getString("id")) : null);
    pMutable.setAgendaNo(pJsonObject.containsKey("agendaNo") ? pJsonObject.getString("agendaNo") : null);
    pMutable.setAgenda(pJsonObject.containsKey("agenda") ? pJsonObject.getString("agenda") : null);
    pMutable.setAgendaEditor(pJsonObject.getString("agendaEditor"));
    pMutable.setResolution(pJsonObject.containsKey("resolution") ? pJsonObject.getString("resolution") : null);
    pMutable.setResolutionEditor(pJsonObject.getString("resolutionEditor"));
    pMutable.setScheduleId(Long.parseLong(pJsonObject.getString("scheduleId")));

    if(pMutable.getAgenda() != null && pMutable.getAgendaEditor().contains("Y")) {
      pMutable.setPlainAgenda(Jsoup.clean(pJsonObject.getString("agenda"), Whitelist.none()).replaceAll("&nbsp;", ""));
    }
    else {
      pMutable.setPlainAgenda(pJsonObject.getString("agenda"));
    }

    if(pMutable.getResolution() != null && pMutable.getResolutionEditor().contains("Y")) {
      pMutable.setPlainResolution(Jsoup.clean(pJsonObject.getString("resolution"), Whitelist.none()).replaceAll(
          "&nbsp;", ""));
    }
    else {
      pMutable.setPlainResolution(pJsonObject.getString("resolution"));
    }
  }
}
