package org.ums.report.generator.seatPlan.support;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.ums.domain.model.dto.SeatPlanReportDto;
import org.ums.domain.model.immutable.SeatPlan;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.enums.ExamType;

import java.util.Map;

/**
 * Created by Monjur-E-Morshed on 13-May-17.
 */
public class ReportSupport {
  public static Chunk getStudentTypeChunk(Integer pExamType,
      Map<String, UGRegistrationResult> pStudentIdUgRegistrationResultMap, Chunk pStudentId,
      SeatPlanReportDto pSeatPlanInnerReport) {
    if(pExamType == ExamType.SEMESTER_FINAL.getId()) {
      if(pSeatPlanInnerReport.getStudentType().equals("RA")) {
        pStudentId = new Chunk(pSeatPlanInnerReport.getStudentId(), FontFactory.getFont(FontFactory.TIMES_BOLD));

      }
      else {
        pStudentId = new Chunk(pSeatPlanInnerReport.getStudentId(), FontFactory.getFont(FontFactory.TIMES));
      }
    }
    else {
      String mapString = (pSeatPlanInnerReport.getStudentId() + pSeatPlanInnerReport.getCourseId());
      if(pStudentIdUgRegistrationResultMap
          .get(pSeatPlanInnerReport.getStudentId() + pSeatPlanInnerReport.getCourseNo()).getType() == CourseRegType.CARRY) {
        pStudentId = new Chunk(pSeatPlanInnerReport.getStudentId(), FontFactory.getFont(FontFactory.TIMES_BOLD));
      }
      else if(pStudentIdUgRegistrationResultMap.get(
          pSeatPlanInnerReport.getStudentId() + pSeatPlanInnerReport.getCourseNo()).getType() == CourseRegType.IMPROVEMENT) {
        pStudentId = new Chunk(pSeatPlanInnerReport.getStudentId(), FontFactory.getFont(FontFactory.TIMES));
        pStudentId.setBackground(BaseColor.LIGHT_GRAY);
      }
      else if(pStudentIdUgRegistrationResultMap.get(
          pSeatPlanInnerReport.getStudentId() + pSeatPlanInnerReport.getCourseNo()).getType() == CourseRegType.SPECIAL_CARRY) {
        pStudentId = new Chunk(pSeatPlanInnerReport.getStudentId(), FontFactory.getFont(FontFactory.TIMES_BOLDITALIC));
        pStudentId.setBackground(BaseColor.LIGHT_GRAY);
      }
      else {
        pStudentId = new Chunk(pSeatPlanInnerReport.getStudentId(), FontFactory.getFont(FontFactory.TIMES));
      }
    }
    return pStudentId;
  }

  public static void addHeaderToSittingArrangementDataTable(PdfPTable pSittingArrangementTable,
      PdfPCell pTableHeaderCell, Paragraph pCellData) {
    pCellData.setAlignment(Element.ALIGN_CENTER);
    pTableHeaderCell = new PdfPCell(pCellData);
    pTableHeaderCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    pTableHeaderCell.setPaddingBottom(5);
    pTableHeaderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    pSittingArrangementTable.addCell(pTableHeaderCell);
  }

  public static PdfPTable getSittingArrangementHeader(ExamType pExamType, Font pBoldFont,
      java.util.List<SeatPlan> pSeatPlansOfTheMap, Map<Long, java.util.List<SeatPlan>> seatPlanMapByRoomNo) {
    PdfPTable headerTable = new PdfPTable(1);
    PdfPCell headerCell = new PdfPCell();
    Paragraph headerParagraph = new Paragraph("Ahsanullah University of Science and Technology", pBoldFont);
    headerParagraph.setAlignment(Element.ALIGN_CENTER);
    headerCell.addElement(headerParagraph);

    headerParagraph = new Paragraph("Office of the Controller of Examinations", pBoldFont);
    headerParagraph.setAlignment(Element.ALIGN_CENTER);
    headerParagraph.setPaddingTop(-5f);
    headerParagraph.setSpacingBefore(-5f);
    headerCell.addElement(headerParagraph);

    if(pExamType == ExamType.SEMESTER_FINAL) {
      headerParagraph =
          new Paragraph("Semester Final Examination :" + pSeatPlansOfTheMap.get(0).getSemester().getName(), pBoldFont);
      headerParagraph.setAlignment(Element.ALIGN_CENTER);
      headerParagraph.setPaddingTop(-5f);
      headerParagraph.setSpacingBefore(-5f);
      headerCell.addElement(headerParagraph);
    }
    else {
      headerParagraph =
          new Paragraph(
              "Carry/Clearance/Improvement Examination :" + pSeatPlansOfTheMap.get(0).getSemester().getName(),
              pBoldFont);
      headerParagraph.setAlignment(Element.ALIGN_CENTER);
      headerParagraph.setPaddingTop(-5f);
      headerParagraph.setSpacingBefore(-5f);
      headerCell.addElement(headerParagraph);

    }

    headerParagraph =
        new Paragraph("Sitting Arrangement ," + " Program :"
            + pSeatPlansOfTheMap.get(0).getStudent().getProgram().getShortName().replace("B.Sc in", "") + ", Year :"
            + pSeatPlansOfTheMap.get(0).getStudent().getCurrentYear() + " Semester:"
            + pSeatPlansOfTheMap.get(0).getStudent().getCurrentAcademicSemester(), pBoldFont);
    headerParagraph.setAlignment(Element.ALIGN_CENTER);
    headerParagraph.setPaddingTop(-5f);
    headerCell.addElement(headerParagraph);

    headerParagraph = new Paragraph("Total number allocated rooms: " + seatPlanMapByRoomNo.size(), pBoldFont);
    headerParagraph.setAlignment(Element.ALIGN_CENTER);
    headerParagraph.setPaddingTop(-5f);
    headerParagraph.setSpacingBefore(-5f);
    headerCell.addElement(headerParagraph);
    /*
     * headerParagraph = new Paragraph("Program :" +
     * pSeatPlansOfTheMap.get(0).getStudent().getProgram().getShortName() .replace("B.Sc in", ""),
     * pBoldFont); headerParagraph.setAlignment(Element.ALIGN_CENTER);
     * headerCell.addElement(headerParagraph); headerParagraph = new Paragraph("Year :" +
     * pSeatPlansOfTheMap.get(0).getStudent().getCurrentYear() + " Semester:" +
     * pSeatPlansOfTheMap.get(0).getStudent().getCurrentAcademicSemester(), pBoldFont);
     * headerParagraph.setAlignment(Element.ALIGN_CENTER); headerCell.addElement(headerParagraph);
     */

    headerCell.setBorder(PdfPCell.NO_BORDER);
    headerCell.setPaddingTop(-20);
    headerCell.setPaddingBottom(5.0f);
    headerCell.setSpaceCharRatio(-5);
    headerCell.setExtraParagraphSpace(-5);
    headerTable.addCell(headerCell);
    return headerTable;
  }
}
