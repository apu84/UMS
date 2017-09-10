package org.ums.solr.repository.meeting;

import javafx.beans.property.SimpleListProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.solr.repository.document.meeting.AgendaResolutionDocument;

import java.util.List;

public class AgendaResolutionRepositoryImpl extends SimpleSolrRepository<AgendaResolutionDocument, Long> implements
    AgendaResolutionRepository {
  public AgendaResolutionRepositoryImpl(SolrTemplate solrTemplate) {
    super(solrTemplate);
  }

  @Override
  public List<AgendaResolutionDocument> findByCustomQuery(String searchTerm, Pageable pageable) {
    SimpleQuery basicSearch = new SimpleQuery(searchTerm);
    basicSearch.setPageRequest(pageable);
    return search(basicSearch);
  }

  List<AgendaResolutionDocument> search(SimpleQuery query) {
    Page<AgendaResolutionDocument> results =
        this.getSolrOperations().queryForPage(query, AgendaResolutionDocument.class);
    return results.getContent();
  }

  @Override
  public Long getTotalCount(String searchTerm) {
    SimpleQuery query = new SimpleQuery(searchTerm);
    return this.getSolrOperations().count(query);
  }
}
