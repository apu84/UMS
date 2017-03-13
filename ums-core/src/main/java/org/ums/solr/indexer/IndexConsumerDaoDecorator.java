package org.ums.solr.indexer;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.solr.indexer.manager.IndexConsumerManager;
import org.ums.solr.indexer.model.IndexConsumer;
import org.ums.solr.indexer.model.MutableIndexConsumer;

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
