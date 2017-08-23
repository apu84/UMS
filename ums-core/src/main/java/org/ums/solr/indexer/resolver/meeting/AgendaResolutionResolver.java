package org.ums.solr.indexer.resolver.meeting;

import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.manager.meeting.AgendaResolutionManager;
import org.ums.solr.indexer.model.Index;
import org.ums.solr.indexer.resolver.EntityResolver;
import org.ums.solr.repository.converter.SimpleConverter;
import org.ums.solr.repository.document.meeting.AgendaResolutionDocument;
import org.ums.solr.repository.meeting.AgendaResolutionRepository;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.List;

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
      List<AgendaResolution> agendaResolution =
          mAgendaResolutionManager.getAgendaResolution(Long.valueOf(pIndex.getEntityId()));
      SimpleConverter<AgendaResolution, AgendaResolutionDocument> converter =
          new SimpleConverter<>(AgendaResolution.class, AgendaResolutionDocument.class);
      List<AgendaResolutionDocument> agendaResolutionDocument = converter.convert(agendaResolution);
      mAgendaResolutionRepository.save(agendaResolutionDocument);
    }
    else {
      mAgendaResolutionRepository.delete(Long.valueOf(pIndex.getEntityId()));
    }
  }
}
