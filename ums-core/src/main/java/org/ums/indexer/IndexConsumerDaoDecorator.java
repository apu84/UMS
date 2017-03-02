package org.ums.indexer;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.indexer.manager.IndexConsumerManager;
import org.ums.indexer.model.IndexConsumer;
import org.ums.indexer.model.MutableIndexConsumer;

class IndexConsumerDaoDecorator extends
    ContentDaoDecorator<IndexConsumer, MutableIndexConsumer, Long, IndexConsumerManager> implements
    IndexConsumerManager {
  @Override
  public List<IndexConsumer> get(String pHost) {
    return getManager().get(pHost);
  }
}
