package org.ums.solr.repository.document.meeting;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.solr.repository.document.SearchDocument;

@SolrDocument(solrCoreName = "ums")
public class AgendaResolutionDocument implements SearchDocument<String> {

  @Id
  @Field
  private String id;

  @Field("type_s")
  private String type = "Meeting";

  @Field("agendaNo_txt")
  private String agendaNo;

  @Field("agenda_txt")
  private String agenda;

  @Field("resolution_txt")
  private String resolution;

  public AgendaResolutionDocument() {}

  public AgendaResolutionDocument(final AgendaResolution pAgendaResolution) {
    id = pAgendaResolution.getId().toString();
    agendaNo = pAgendaResolution.getAgendaNo();
    agenda = pAgendaResolution.getAgenda();
    resolution = pAgendaResolution.getResolution();
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
