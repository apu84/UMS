package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.library.RecordLog;
import org.ums.domain.model.mutable.library.MutableRecordLog;

public class MutableRecordLogResource extends Resource {

  @Autowired
  private ResourceHelper<RecordLog, MutableRecordLog, Long> mHelper;
}
