package org.ums.solr.repository.converter;

import java.util.List;

import org.ums.solr.repository.document.SearchDocument;

public interface Converter<S, T extends SearchDocument> {
  T convert(S pSource);

  List<T> convert(List<S> pSourceList);
}
