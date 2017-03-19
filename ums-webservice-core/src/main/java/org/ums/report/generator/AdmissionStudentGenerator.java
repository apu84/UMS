package org.ums.report.generator;

import org.springframework.stereotype.Component;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;

import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 20-Dec-16.
 */
public interface AdmissionStudentGenerator {
  void createABlankTaletalkDataFormatFile(OutputStream pOutputStream, final int pSemesterId) throws Exception;

  void createABlankMeritListUploadFormatFile(OutputStream pOutputStream, final int pSemesterId) throws Exception;

  void createBlankMigrationListUploadFormatFile(OutputStream pOutputStream) throws Exception;

  void createMeritListXlsFile(final int pSemesterId, final ProgramType pProgramType, final QuotaType pQuotaType,
      String pUnit, OutputStream pOutputStream) throws Exception;
}
