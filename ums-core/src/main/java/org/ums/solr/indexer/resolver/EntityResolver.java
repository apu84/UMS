package org.ums.solr.indexer.resolver;

import org.ums.solr.indexer.model.Index;

public interface EntityResolver {
  String getEntityType();

  void resolve(Index pIndex);
}
