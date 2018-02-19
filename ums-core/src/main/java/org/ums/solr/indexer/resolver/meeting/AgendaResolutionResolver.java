package org.ums.solr.indexer.resolver.meeting;

import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.manager.meeting.AgendaResolutionManager;
import org.ums.solr.indexer.model.Index;
import org.ums.solr.indexer.resolver.EntityResolver;
import org.ums.solr.repository.converter.SimpleConverter;
import org.ums.solr.repository.document.meeting.AgendaResolutionDocument;
import org.ums.solr.repository.meeting.AgendaResolutionRepository;

public class AgendaResolutionResolver implements EntityResolver {

  private AgendaResolutionManager mAgendaResolutionManager;
  private AgendaResolutionRepository mAgendaResolutionRepository;

  public AgendaResolutionResolver(AgendaResolutionManager pAgendaResolutionManager,
      AgendaResolutionRepository pAgendaResolutionRepository) {
    this.mAgendaResolutionManager = pAgendaResolutionManager;
    this.mAgendaResolutionRepository = pAgendaResolutionRepository;
  }

  @Override
  public String getEntityType() {
    return "meeting-record";
  }

  @Override
  public void resolve(Index pIndex) {
    if(!pIndex.isDeleted()) {
      AgendaResolution agendaResolution = mAgendaResolutionManager.get(Long.parseLong(pIndex.getEntityId()));
      SimpleConverter<AgendaResolution, AgendaResolutionDocument> converter =
          new SimpleConverter<>(AgendaResolution.class, AgendaResolutionDocument.class);
      AgendaResolutionDocument agendaResolutionDocument = converter.convert(agendaResolution);
      mAgendaResolutionRepository.save(agendaResolutionDocument);
    }
    else {
      mAgendaResolutionRepository.delete(Long.valueOf(pIndex.getEntityId()));
    }
  }
}
