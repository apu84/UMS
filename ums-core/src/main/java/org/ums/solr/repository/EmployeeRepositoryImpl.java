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
    SimpleQuery query = new SimpleQuery(conditions);
    query.setPageRequest(pageable);
    return search(query);
  }

  @Override
  public List<EmployeeDocument> findByDepartment(String searchTerm, Pageable pageable) {
    SimpleQuery query =
        new SimpleQuery(String.format("{!parent which=\"type_s:Employee\"}longName_txt:*%s* OR shortName_txt:*%s*",
            searchTerm, searchTerm));
    query.setPageRequest(pageable);
    return search(query);
  }

  List<EmployeeDocument> search(SimpleQuery query) {
    Page<EmployeeDocument> results = this.getSolrOperations().queryForPage(query, EmployeeDocument.class);
    return results.getContent();
  }

  private Criteria createSearchConditions(String term) {
    return new Criteria("type_s").is("Employee").and(new Criteria("name_txt").contains(term));
  }
}
