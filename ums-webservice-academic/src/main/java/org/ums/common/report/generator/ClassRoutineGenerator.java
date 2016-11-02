package org.ums.common.report.generator;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by My Pc on 30-Aug-16.
 */
public interface ClassRoutineGenerator {
  void createClassRoutineStudentReport(OutputStream pOutputStream) throws Exception, IOException,
      DocumentException;

  void createClassRoutineTeacherReport(OutputStream pOutputStream) throws Exception, IOException,
      DocumentException;

  void createRoomBasedClassRoutineReport(OutputStream pOutputStream, int pSemesterId, int pRoomId)
      throws Exception, IOException, DocumentException;
}
