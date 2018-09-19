package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.RecordLog;
import org.ums.domain.model.mutable.library.MutableRecordLog;
import org.ums.manager.library.RecordLogManager;

import java.util.List;

public class RecordLogDaoDecorator extends ContentDaoDecorator<RecordLog, MutableRecordLog, Long, RecordLogManager>
    implements RecordLogManager {
  @Override
  public List<RecordLog> get(String pClause) {
    return getManager().get(pClause);
  }
}
