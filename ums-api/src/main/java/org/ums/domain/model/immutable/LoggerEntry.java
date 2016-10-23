package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.mutable.MutableLoggerEntry;

import java.io.Serializable;
import java.util.Date;

public interface LoggerEntry extends Serializable, Identifier<Integer>,
    EditType<MutableLoggerEntry> {
  String getSql();

  String getUserName();

  long getExecutionTime();

  Date getTimestamp();
}
