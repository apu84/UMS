package org.ums.solr.repository.meeting;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.ums.solr.repository.document.meeting.AgendaResolutionDocument;

import java.util.List;

public class AgendaResolutionRepositoryImpl extends SimpleSolrRepository<AgendaResolutionDocument, Long> implements
    AgendaResolutionRepository {
  public AgendaResolutionRepositoryImpl(SolrTemplate solrTemplate) {
    super(solrTemplate);
  }

  @Override
  public List<AgendaResolutionDocument> findByCustomQuery(String searchTerm, Pageable pageable) {
    return null;
  }

  @Override
  public Long getTotalCount(String searchTerm) {
    return null;
  }
}
