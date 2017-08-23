package org.ums.solr.repository.meeting;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.ums.solr.repository.document.meeting.AgendaResolutionDocument;

import java.util.List;

public interface AgendaResolutionRepository extends SolrCrudRepository<AgendaResolutionDocument, Long> {

  List<AgendaResolutionDocument> findByCustomQuery(String searchTerm, Pageable pageable);

  Long getTotalCount(String searchTerm);
}
