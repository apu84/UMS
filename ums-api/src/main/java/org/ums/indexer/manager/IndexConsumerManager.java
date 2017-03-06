package org.ums.indexer.manager;

import org.ums.indexer.model.IndexConsumer;
import org.ums.indexer.model.MutableIndexConsumer;
import org.ums.manager.ContentManager;

public interface IndexConsumerManager extends
    ContentManager<IndexConsumer, MutableIndexConsumer, Long> {
  IndexConsumer get(String pHost, String pPort);

  boolean exists(String pHost, String pPort);
}
