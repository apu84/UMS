package org.ums.solr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.ums.solr.repository.document.EmployeeDocument;

public interface EmployeeRepository extends SolrCrudRepository<EmployeeDocument, String> {
  @Query("type_s:Employee AND name_txt:*?0*")
  Page<EmployeeDocument> findByCustomQuery(String searchTerm, Pageable pageable);
}
