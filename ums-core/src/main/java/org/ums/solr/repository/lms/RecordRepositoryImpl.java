package org.ums.solr.repository.lms;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.springframework.stereotype.Repository;
import org.ums.solr.repository.document.EmployeeDocument;
import org.ums.solr.repository.document.lms.RecordDocument;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Ifti on 17-Apr-17.
 */
@Repository
public class RecordRepositoryImpl extends SimpleSolrRepository<RecordDocument, Long> implements RecordRepository {

  @Resource
  private SolrTemplate solrTemplate;

  @Override
  public List<RecordDocument> findByCustomQuery(String searchTerm, Pageable pageable) {
    Criteria conditions = createSearchConditions(searchTerm);
    SimpleQuery search = new SimpleQuery(conditions);
    search.setPageRequest(pageable);
    Page<RecordDocument> results = solrTemplate.queryForPage(search, RecordDocument.class);
    return results.getContent();
  }

  private Criteria createSearchConditions(String term) {
    return new Criteria("type_s").is("Record").and(new Criteria("title_txt").contains(term));
  }
}
