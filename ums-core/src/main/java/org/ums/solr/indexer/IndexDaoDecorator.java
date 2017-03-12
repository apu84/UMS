package org.ums.solr.indexer;

import java.util.Date;
import java.util.List;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.solr.indexer.manager.IndexManager;
import org.ums.solr.indexer.model.Index;
import org.ums.solr.indexer.model.MutableIndex;

public class IndexDaoDecorator extends ContentDaoDecorator<Index, MutableIndex, Long, IndexManager>
    implements IndexManager {
  @Override
  public List<Index> after(Date pDate) {
    return getManager().after(pDate);
  }
}
