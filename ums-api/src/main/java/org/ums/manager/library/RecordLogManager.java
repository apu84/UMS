package org.ums.manager.library;

import org.ums.domain.model.immutable.library.RecordLog;
import org.ums.domain.model.mutable.library.MutableRecordLog;
import org.ums.manager.ContentManager;

import java.util.List;

public interface RecordLogManager extends ContentManager<RecordLog, MutableRecordLog, Long> {

  /*
   * List<RecordLog> getLog(Long pMfn);
   * 
   * List<RecordLog> getLog(String pEmployeeId);
   * 
   * List<RecordLog> getLog(Date pModifiedDate);
   * 
   * List<RecordLog> getLog(Long pMfn, String pEmployeeId);
   * 
   * List<RecordLog> getLog(Long pMfn, Date pModifiedDate);
   * 
   * List<RecordLog> getLog(String pEmployeeId, Date pModifiedDate);
   */

  List<RecordLog> get(String pClause);
}
