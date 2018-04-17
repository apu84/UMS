package org.ums.solr.repository.lms;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.ums.solr.repository.CustomSolrCrudRepository;
import org.ums.solr.repository.document.lms.RecordDocument;

import java.util.List;

/**
 * Created by Ifti on 15-Apr-17.
 */
public interface RecordRepository extends CustomSolrCrudRepository<RecordDocument, Long> {
  List<RecordDocument> findByCustomQuery(String searchTerm, Pageable pageable, boolean enableSort);
}
