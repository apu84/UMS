package org.ums.solr.indexer.resolver;

import org.ums.solr.indexer.model.Index;

public interface EntityResolverFactory {
  void resolve(Index pIndex);
}
