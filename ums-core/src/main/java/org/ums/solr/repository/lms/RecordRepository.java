package org.ums.solr.repository.lms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.ums.solr.repository.document.EmployeeDocument;
import org.ums.solr.repository.document.lms.RecordDocument;

import java.util.List;

/**
 * Created by Ifti on 15-Apr-17.
 */
public interface RecordRepository extends SolrCrudRepository<RecordDocument, Long> {
  List<RecordDocument> findByCustomQuery(String searchTerm, Pageable pageable);
}
