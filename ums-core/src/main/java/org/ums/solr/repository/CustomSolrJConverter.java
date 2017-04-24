package org.ums.solr.repository;

import java.util.Map;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.data.solr.core.convert.SolrJConverter;

public class CustomSolrJConverter extends SolrJConverter {
  @Override
  public void write(Object source, Map sink) {
    if(source == null) {
      return;
    }

    SolrInputDocument convertedDocument = convert(source, SolrInputDocument.class);
    sink.putAll(convertedDocument);
    if(convertedDocument.getChildDocumentCount() > 0 && sink instanceof SolrInputDocument) {
      SolrInputDocument document = (SolrInputDocument) sink;
      for(SolrInputDocument childDoc : convertedDocument.getChildDocuments()) {
        document.addChildDocument(childDoc);
      }
    }
  }
}
