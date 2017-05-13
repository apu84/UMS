package org.ums.report.generator.seatPlan;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.enums.ExamType;
import org.ums.report.generator.seatPlan.support.SeatChartReport;
import org.ums.report.generator.seatPlan.support.SittingArrangementReport;

import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by My Pc on 5/25/2016.
 */

@Service
public class SeatPlanReportGeneratorImpl implements SeatPlanReportGenerator {

  @Autowired
  AttendanceSheetReport mAttendanceSheetReport;

  @Autowired
  RoomWiseQuestionDistributionReport mRoomWiseQuestionDistributionReport;

  @Autowired
  SeatChartReport mSeatChartReport;

  @Autowired
  SittingArrangementReport mSittingArrangementReport;

  @Autowired
  SeatPlanStickerReport mSeatPlanStickerReport;

  @Autowired
  TopSheetReport mTopSheetReport;

  @Override
  public void createPdf(String dest, boolean noSeatPlanInfo, int pSemesterId, int groupNo, int type, String examDate,
                        OutputStream pOutputStream) throws IOException, DocumentException, WebApplicationException {

    mSeatChartReport.createPdf(dest, noSeatPlanInfo, pSemesterId, groupNo, type, examDate, pOutputStream);
  }

  @Override
  public void createSeatPlanAttendenceReport(Integer pProgramType, Integer pSemesterId, Integer pExamType,
                                             String pExamDate, OutputStream pOutputStream) throws IOException, DocumentException {

  }

  @Override
  public void createSeatPlanAttendencePdfReport(Integer pProgramType, Integer pSemesterId, Integer pExamType,
                                                String pExamDate, OutputStream pOutputStream) throws IOException, DocumentException {

    mAttendanceSheetReport.createSeatPlanAttendencePdfReport(pProgramType, pSemesterId, pExamType, pExamDate,
        pOutputStream);
  }

  @Override
  public void createSeatPlanTopSheetPdfReport(Integer pProgramType, Integer pSemesterId, Integer pExamType,
                                              String pExamDate, OutputStream pOutputStream) throws IOException, DocumentException {
    mTopSheetReport.createSeatPlanTopSheetPdfReport(pProgramType, pSemesterId, pExamType, pExamDate, pOutputStream);
  }

  @Override
  public void createSeatPlanStickerReport(Integer pProgramType, Integer pSemesterId, Integer pExamType,
                                          String pExamDate, int pRoomId, OutputStream pOutputStream) throws IOException, DocumentException {

    SeatPlanStickerReport seatPlanStickerReport = new SeatPlanStickerReport();
    mSeatPlanStickerReport.createSeatPlanStickerReport(pProgramType, pSemesterId, pExamType, pExamDate, pRoomId,
        pOutputStream);
  }

  @Override
  public void createSeatPlanSittingArrangementReport(int pSemesterId, ExamType pExamType, OutputStream pOutputStream)
      throws IOException, DocumentException {
    SittingArrangementReport sittingArrangementReport = new SittingArrangementReport();
    mSittingArrangementReport.createSeatPlanSittingArrangementReport(pSemesterId, pExamType, pOutputStream);

  }

  @Override
  public void createRoomWiseQuestionDistributionReport(int pSemesterId, ExamType pExamType, OutputStream pOutputStream)
      throws IOException, DocumentException {

    RoomWiseQuestionDistributionReport roomWiseQuestionDistributionReport = new RoomWiseQuestionDistributionReport();
    mRoomWiseQuestionDistributionReport.createRoomWiseQuestionDistributionReport(pSemesterId, pExamType, pOutputStream);
  }

}
