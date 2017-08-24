package org.ums.solr.repository.document.meeting;

import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.solr.repository.document.SearchDocument;

import java.util.List;

@SolrDocument(solrCoreName = "ums")
public class AgendaResolutionDocument implements SearchDocument<String> {

  @Id
  @Field
  private String id;

  @Field("type_s")
  private String type = "Meeting";

  @Field("agendaNo_txt")
  private List<String> agendaNo;

  @Field("agenda_txt")
  private List<String> agenda;

  @Field("resolution_txt")
  private List<String> resolution;

  @Field("scheduleId_txt")
  private List<String> scheduleId;

  public AgendaResolutionDocument() {}

  public AgendaResolutionDocument(final AgendaResolution pAgendaResolution) {
    id = pAgendaResolution.getId().toString();
    agendaNo = Lists.newArrayList(pAgendaResolution.getAgendaNo());
    agenda = Lists.newArrayList(pAgendaResolution.getAgenda());
    resolution = Lists.newArrayList(pAgendaResolution.getResolution());
    scheduleId = Lists.newArrayList(pAgendaResolution.getScheduleId().toString());
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getType() {
    return type;
  }
}
