package org.ums.solr.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.ums.solr.repository.document.EmployeeDocument;

public class EmployeeRepositoryImpl extends SimpleSolrRepository<EmployeeDocument, String> implements
    EmployeeRepository {

  public EmployeeRepositoryImpl(SolrTemplate solrTemplate) {
    super(solrTemplate);
  }

  @Override
  public List<EmployeeDocument> findByCustomQuery(String searchTerm, Pageable pageable) {
    Criteria conditions = createSearchConditions(searchTerm);
    SimpleQuery search = new SimpleQuery(conditions);
    search.setPageRequest(pageable);
    Page<EmployeeDocument> results = this.getSolrOperations().queryForPage(search, EmployeeDocument.class);
    return results.getContent();
  }

  private Criteria createSearchConditions(String term) {
    return new Criteria("type_s").is("Employee").and(new Criteria("name_txt").contains(term));
  }
}
