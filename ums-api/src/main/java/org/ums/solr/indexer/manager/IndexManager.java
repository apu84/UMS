package org.ums.solr.indexer.manager;

import java.util.Date;
import java.util.List;

import org.ums.solr.indexer.model.Index;
import org.ums.solr.indexer.model.MutableIndex;
import org.ums.manager.ContentManager;

public interface IndexManager extends ContentManager<Index, MutableIndex, Long> {
  List<Index> after(Date pDate);
}
