package org.ums.solr.repository;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.springframework.stereotype.Repository;
import org.ums.solr.repository.document.EmployeeDocument;

@Repository
public class EmployeeRepositoryImpl extends SimpleSolrRepository<EmployeeDocument, String> implements
    EmployeeRepository {
  @Resource
  private SolrTemplate solrTemplate;

  @Override
  public List<EmployeeDocument> findByCustomQuery(String searchTerm, Pageable pageable) {
    Criteria conditions = createSearchConditions(searchTerm);
    SimpleQuery search = new SimpleQuery(conditions);
    search.setPageRequest(pageable);
    Page<EmployeeDocument> results = solrTemplate.queryForPage(search, EmployeeDocument.class);
    return results.getContent();
  }

  private Criteria createSearchConditions(String term) {
    return new Criteria("type_s").is("Employee").and(new Criteria("name_txt").contains(term));
  }
}
