package org.ums.solr.repository.lms;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.ums.solr.repository.document.EmployeeDocument;
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

    SimpleQuery basicSearch = new SimpleQuery(searchTerm);

    // new
    // SimpleQuery(String.format("{!parent which=\"type_s:Record AND (changedTitle_txt:%s OR acquisitionType_txt:%s)\"}",
    // "Chaneged", "Purchase", "ifti", "Apu"));

    // SimpleQuery query =
    // new
    // SimpleQuery(String.format("{!parent which=\"type_s:Record AND (changedTitle_txt:%s OR acquisitionType_txt:%s)\"}roleName_txt:*%s* OR contributorName_txt:*%s*",
    // "Chaneged", "Purchase", "ifti", "Apu"));
    basicSearch.setPageRequest(pageable);
    return search(basicSearch);

  }

  List<RecordDocument> search(SimpleQuery query) {
    Page<RecordDocument> results = this.getSolrOperations().queryForPage(query, RecordDocument.class);
    return results.getContent();
  }

  public Long getTotalCount(String searchTerm) {
    // Criteria conditions = createSearchConditions("title");
    // SimpleQuery search = new SimpleQuery(conditions);
    // return this.getSolrOperations().count(search);

    // SimpleQuery query =
    // new
    // SimpleQuery(String.format("{!parent which=\"type_s:Record\"}roleName_txt:*%s* OR contributorName_txt:*%s*",
    // "ifti", "Apu"));
    SimpleQuery query = new SimpleQuery(searchTerm);
    return this.getSolrOperations().count(query);
  }

  private Criteria createSearchConditions(String term) {
    return new Criteria("type_s").is("Record").and(new Criteria("title_txt").contains(term));
  }
}
