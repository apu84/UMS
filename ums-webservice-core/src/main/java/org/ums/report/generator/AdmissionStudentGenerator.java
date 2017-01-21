package org.ums.report.generator;

import org.springframework.stereotype.Component;

import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 20-Dec-16.
 */
public interface AdmissionStudentGenerator {
  void createABlankTaletalkDataFormatFile(OutputStream pOutputStream, final int pSemesterId)
      throws Exception;

  void createABlankMeritListUploadFormatFile(OutputStream pOutputStream, final int pSemesterId)
      throws Exception;
}
