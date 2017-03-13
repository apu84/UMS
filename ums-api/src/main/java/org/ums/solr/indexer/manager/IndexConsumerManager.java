package org.ums.solr.indexer.manager;

import org.ums.solr.indexer.model.IndexConsumer;
import org.ums.solr.indexer.model.MutableIndexConsumer;
import org.ums.manager.ContentManager;

public interface IndexConsumerManager extends
    ContentManager<IndexConsumer, MutableIndexConsumer, Long> {
  IndexConsumer get(String pHost, String pPort);

  boolean exists(String pHost, String pPort);
}
