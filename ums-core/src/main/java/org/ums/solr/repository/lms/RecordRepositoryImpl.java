package org.ums.solr.repository.lms;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.ums.solr.repository.document.lms.RecordDocument;

/**
 * Created by Ifti on 17-Apr-17.
 */
public class RecordRepositoryImpl extends SimpleSolrRepository<RecordDocument, Long> implements RecordRepository {
  public RecordRepositoryImpl(SolrTemplate solrTemplate) {
    super(solrTemplate);
  }

  @Override
  public List<RecordDocument> findByCustomQuery(String searchTerm, Pageable pageable) {
    Criteria conditions = createSearchConditions("title");
    SimpleQuery search = new SimpleQuery(conditions);
    search.setPageRequest(pageable);
    Page<RecordDocument> results = this.getSolrOperations().queryForPage(search, RecordDocument.class);
    return results.getContent();
  }

  public Long getTotalCount(String searchTerm) {
    Criteria conditions = createSearchConditions("title");
    SimpleQuery search = new SimpleQuery(conditions);
    return this.getSolrOperations().count(search);
  }

  private Criteria createSearchConditions(String term) {
    return new Criteria("type_s").is("Record").and(new Criteria("title_txt").contains(term));
  }
}
