package org.ums.report.generator;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.codec.Base64;
import org.pentaho.reporting.engine.classic.core.DataFactory;
import org.pentaho.reporting.engine.classic.core.MasterReport;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.DriverConnectionProvider;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.SQLReportDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.enums.ExamType;
import org.ums.enums.ProgramType;
import org.ums.manager.SeatPlanManager;
import org.ums.report.generator.AbstractReportGenerator;

import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

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
