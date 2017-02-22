package org.ums.indexer;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.indexer.manager.IndexManager;
import org.ums.indexer.model.Index;
import org.ums.indexer.model.MutableIndex;

public class IndexDaoDecorator extends ContentDaoDecorator<Index, MutableIndex, Long, IndexManager>
    implements IndexManager {
  @Override
  public List<Index> after(Long pId) {
    return getManager().after(pId);
  }
}
