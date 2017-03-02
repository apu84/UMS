package org.ums.indexer.manager;

import java.util.List;

import org.ums.indexer.model.Index;
import org.ums.indexer.model.MutableIndex;
import org.ums.manager.ContentManager;

public interface IndexManager extends ContentManager<Index, MutableIndex, Long> {
  List<Index> after(Long pId);
}
