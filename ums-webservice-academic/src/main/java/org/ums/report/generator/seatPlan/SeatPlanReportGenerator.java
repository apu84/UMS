package org.ums.report.generator.seatPlan;

import com.itextpdf.text.DocumentException;
import org.ums.enums.ExamType;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by My Pc on 5/9/2016.
 */

public interface SeatPlanReportGenerator {
  void createPdf(String dest, boolean noSeatPlanInfo, int pSemesterId, int groupNo, int type, String examDate,
      OutputStream pOutputStream) throws IOException, DocumentException;

  void createSeatPlanAttendenceReport(Integer pProgramType, Integer pSemesterId, Integer pExamType, String pExamDate,
      OutputStream pOutputStream) throws IOException, DocumentException;

  void createSeatPlanAttendencePdfReport(Integer pProgramType, Integer pSemesterId, Integer pExamType,
      String pExamDate, OutputStream pOutputStream) throws IOException, DocumentException;

  void createSeatPlanTopSheetPdfReport(Integer pProgramType, Integer pSemesterId, Integer pExamType, String pExamDate,
      OutputStream pOutputStream) throws IOException, DocumentException;

  void createSeatPlanStickerReport(Integer pProgramType, Integer pSemesterId, Integer pExamType, String pExamDate,
      int pRoomId, OutputStream pOutputStream) throws IOException, DocumentException;

  void createSeatPlanSittingArrangementReport(int pSemesterId, ExamType pExamType, OutputStream pOutputStream)
      throws IOException, DocumentException;

  void createRoomWiseQuestionDistributionReport(int pSemesterId, ExamType pExamType, OutputStream pOutputStream)
      throws IOException, DocumentException;
}
