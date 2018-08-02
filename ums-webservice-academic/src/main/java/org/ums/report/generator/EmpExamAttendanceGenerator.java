package org.ums.report.generator;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;

/**
 * Created by Monjur-E-Morshed on 7/31/2018.
 */
public interface EmpExamAttendanceGenerator {

  void createRoomMemorandum(final Integer pSemesterId, final Integer pExamType, OutputStream pOutputStream)
      throws IOException, DocumentException, ParseException;

  void createEmployeeAttendantList(final Integer pSemesterId, final Integer pExamType, final String pExamDate,
      final String pDeptId, OutputStream pOutputStream) throws IOException, DocumentException, ParseException;

  void createReserveEmployeeAttendantList(final Integer pSemesterId, final Integer pExamType, final String pExamDate,
      final String pDeptId, OutputStream pOutputStream) throws IOException, DocumentException, ParseException;

  void createStaffAttendantList(final Integer pSemesterId, final Integer pExamType, final String pExamDate,
      OutputStream pOutputStream) throws IOException, DocumentException, ParseException;
}
