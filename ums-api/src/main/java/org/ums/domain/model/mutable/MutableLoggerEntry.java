package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.LoggerEntry;

import java.util.Date;

public interface MutableLoggerEntry extends LoggerEntry, Mutable, MutableIdentifier<Integer> {
  void setSql(final String pSql);

  void setUserName(final String pUserName);

  void setExecutionTime(final long pExecutionTime);

  void setTimestamp(final Date pTimestamp);
}
