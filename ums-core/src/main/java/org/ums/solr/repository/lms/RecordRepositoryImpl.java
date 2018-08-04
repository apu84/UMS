package org.ums.solr.repository.lms;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;
import org.ums.solr.repository.document.lms.RecordDocument;

import java.util.List;

/**
 * Created by Ifti on 17-Apr-17.
 */
public class RecordRepositoryImpl extends SimpleSolrRepository<RecordDocument, Long> implements RecordRepository {
  public RecordRepositoryImpl(SolrTemplate solrTemplate) {
    super(solrTemplate);
  }

  private String searchQueryTerm = "";

  @Override
  public List<RecordDocument> findByCustomQuery(String searchTerm, Pageable pageable, boolean enableSort) {

    searchQueryTerm = searchTerm;
    SimpleQuery basicSearch = new SimpleQuery(searchTerm);

    // new
    // SimpleQuery(String.format("{!parent which=\"type_s:Record AND (changedTitle_txt:%s OR acquisitionType_txt:%s)\"}",
    // "Chaneged", "Purchase", "ifti", "Apu"));

    // SimpleQuery query =
    // new
    // SimpleQuery(String.format("{!parent which=\"type_s:Record AND (changedTitle_txt:%s OR acquisitionType_txt:%s)\"}roleName_txt:*%s* OR contributorName_txt:*%s*",
    // "Chaneged", "Purchase", "ifti", "Apu"));

    basicSearch.setPageRequest(pageable);
    if(enableSort) {
      basicSearch.addSort(sort("alphaNumericTitle_s"));
    }

    return search(basicSearch);

  }

  List<RecordDocument> search(SimpleQuery query) {
    Page<RecordDocument> results = this.getSolrOperations().queryForPage(query, RecordDocument.class);
    return results.getContent();
  }

  private Criteria createSearchConditions(String term) {
    return new Criteria("type_s").is(RecordDocument.DOCUMENT_TYPE).and(new Criteria("title_t").contains(term));
  }

  @Override
  public long totalDocuments() {
    SimpleQuery query = new SimpleQuery(searchQueryTerm);
    return this.getSolrOperations().count(query);
  }

  private Sort sort(String pFiled) {
    return new Sort(Sort.Direction.ASC, pFiled);
  }
}
