package org.ums.indexer;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.indexer.manager.IndexConsumerManager;
import org.ums.indexer.model.IndexConsumer;
import org.ums.indexer.model.MutableIndexConsumer;

class IndexConsumerDaoDecorator extends
    ContentDaoDecorator<IndexConsumer, MutableIndexConsumer, Long, IndexConsumerManager> implements
    IndexConsumerManager {
  @Override
  public IndexConsumer get(String pHost, String pPort) {
    return getManager().get(pHost, pPort);
  }

  @Override
  public boolean exists(String pHost, String pPort) {
    return getManager().exists(pHost, pPort);
  }
}
