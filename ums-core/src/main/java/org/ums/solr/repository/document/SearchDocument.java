package org.ums.solr.repository.document;

public interface SearchDocument<I> {
  I getId();

  String getType();
}
