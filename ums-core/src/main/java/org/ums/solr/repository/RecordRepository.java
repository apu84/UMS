package org.ums.solr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.ums.solr.repository.document.EmployeeDocument;
import org.ums.solr.repository.document.lms.RecordDocument;

/**
 * Created by Ifti on 15-Apr-17.
 */
public interface RecordRepository extends SolrCrudRepository<RecordDocument, String> {
  @Query("type_s:Record AND name_txt:*?0*")
  Page<RecordDocument> findByCustomQuery(String searchTerm, Pageable pageable);
}
