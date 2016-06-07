package org.ums.manager;


import org.ums.domain.model.immutable.LoggerEntry;
import org.ums.domain.model.mutable.MutableLoggerEntry;

public interface LoggerEntryManager extends ContentManager<LoggerEntry, MutableLoggerEntry, Integer> {
}
