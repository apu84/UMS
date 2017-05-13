package org.ums.report.generator.seatPlan.support;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.dto.SeatPlanReportDto;
import org.ums.domain.model.immutable.Semester;
import org.ums.manager.SeatPlanReportManager;
import org.ums.manager.SemesterManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 13-May-17.
 */
@Component
public class SeatPlanStickerReport {

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private SeatPlanReportManager mSeatPlanReportManager;

  @Autowired
  public static final String DEST = "seat_plan_report.pdf";

  public void createSeatPlanStickerReport(Integer pProgramType, Integer pSemesterId, Integer pExamType,
                                          String pExamDate, int pRoomId, OutputStream pOutputStream) throws IOException, DocumentException {

    List<SeatPlanReportDto> seatPlans =
        mSeatPlanReportManager.getSeatPlanDataForSticker(pSemesterId, pExamType, pExamDate, pRoomId);
    Semester semester = mSemesterManager.get(pSemesterId);

    Document document = new Document();
    document.addTitle("Seat Plan Attendence Sheet");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    /*
     * MyFooter event = new MyFooter(); writer.setPageEvent(event);
     */
    document.open();
    document.setPageSize(PageSize.A4);

    int totalRow;
    if (pExamType == 1) {
      totalRow = 5;
    } else {
      totalRow = 4;
    }

    Font lightFont = FontFactory.getFont(FontFactory.TIMES, 11);
    Font duetFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 13);
    Font universityNameFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 16);
    Font sponSoreFont = FontFactory.getFont(FontFactory.TIMES, 8);
    Font topSheetFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 13);
    Font boldFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
    PdfPTable masterTable = new PdfPTable(2);
    masterTable.setWidthPercentage(108);

    int rowCounter = 0;
    while (true) {
      PdfPCell masterLeftCell = new PdfPCell();
      PdfPCell masterRightCell = new PdfPCell();
      PdfPTable leftTable = new PdfPTable(1);
      PdfPTable rightTable = new PdfPTable(1);
      PdfPCell leftCell = new PdfPCell();
      PdfPCell rightCell = new PdfPCell();
      leftCell.setPaddingRight(13.0f);
      rightCell.setPaddingLeft(13.0f);
      boolean dataEnd = false;
      for (int i = 1; i <= 2; i++) {

        if (seatPlans.size() > 0) {
          SeatPlanReportDto seatPlanReportDto = seatPlans.get(0);
          if (seatPlanReportDto.getStudentId().equals("140107092")) {
            boolean foundI = true;
          }
          Paragraph paragraph = new Paragraph();
          String roomInfo = seatPlanReportDto.getRoomNo();
          paragraph.add(roomInfo);
          paragraph.setAlignment(Element.ALIGN_RIGHT);
          paragraph.setFont(boldFont);
          if (i == 1) {
            leftCell.addElement(paragraph);
          } else {
            rightCell.addElement(paragraph);
          }

          String semesterInfo;
          if (pExamType == 1) {
            semesterInfo = "Semester Final Examination, " + semester.getName();
          } else {
            semesterInfo = "Carry/Clearance/Improvement Examination, " + semester.getName();
          }

          paragraph = new Paragraph(semesterInfo, boldFont);
          paragraph.setAlignment(Element.ALIGN_CENTER);
          if (i == 1) {
            leftCell.addElement(paragraph);
          } else {
            rightCell.addElement(paragraph);
          }

          paragraph = new Paragraph("  ");
          if (i == 1) {
            leftCell.addElement(paragraph);
          } else {
            rightCell.addElement(paragraph);
          }

          String yearSemester =
              "  Year :" + seatPlanReportDto.getCurrentYear() + "           " + "            Semester: "
                  + seatPlanReportDto.getCurrentSemester();
          paragraph = new Paragraph(yearSemester, boldFont);
          if (i == 1) {
            leftCell.addElement(paragraph);
          } else {
            rightCell.addElement(paragraph);
          }

          String department = " Department :" + seatPlanReportDto.getProgramName();
          paragraph = new Paragraph(department, boldFont);
          if (i == 1) {
            leftCell.addElement(paragraph);
          } else {
            rightCell.addElement(paragraph);
          }

          paragraph = new Paragraph(" Student Id: " + seatPlanReportDto.getStudentId(), boldFont);
          if (i == 1) {
            leftCell.addElement(paragraph);
          } else {
            rightCell.addElement(paragraph);
          }

          paragraph = new Paragraph(" ");
          if (i == 1) {
            leftCell.addElement(paragraph);
          } else {
            rightCell.addElement(paragraph);
          }

          seatPlans.remove(0);

        } else {
          dataEnd = true;
          break;
        }
      }

      leftTable.addCell(leftCell);
      rightTable.addCell(rightCell);

      leftTable.setWidthPercentage(90);
      rightTable.setWidthPercentage(90);
      masterLeftCell.addElement(leftTable);
      masterRightCell.addElement(rightTable);
      masterRightCell.setPaddingLeft(7);

      masterLeftCell.setBorder(Rectangle.NO_BORDER);
      masterRightCell.setBorder(Rectangle.NO_BORDER);

      masterLeftCell.setPaddingBottom(30f);
      masterRightCell.setPaddingBottom(30f);
      masterTable.addCell(masterLeftCell);
      masterTable.addCell(masterRightCell);

      rowCounter += 1;

      if (rowCounter >= totalRow || seatPlans.size() == 0) {
        document.add(masterTable);
        rowCounter = 0;
        masterTable = new PdfPTable(2);
        masterTable.setWidthPercentage(108);
      }

      if (seatPlans.size() == 0) {
        break;
      } else {
        document.newPage();

      }

    }

    document.close();
    baos.writeTo(pOutputStream);

  }
}
