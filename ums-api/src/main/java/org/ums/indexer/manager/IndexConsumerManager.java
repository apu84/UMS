package org.ums.indexer.manager;

import java.util.List;

import org.ums.indexer.model.IndexConsumer;
import org.ums.indexer.model.MutableIndexConsumer;
import org.ums.manager.ContentManager;

public interface IndexConsumerManager extends
    ContentManager<IndexConsumer, MutableIndexConsumer, Long> {
  List<IndexConsumer> get(String pHost);
}
