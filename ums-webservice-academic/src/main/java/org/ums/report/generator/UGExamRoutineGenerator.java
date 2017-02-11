package org.ums.report.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.dto.ClassAttendanceDto;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.ExamRoutine;
import org.ums.domain.model.immutable.User;
import org.ums.manager.ExamRoutineManager;
import org.ums.manager.SemesterManager;
import org.ums.util.Constants;
import org.ums.util.ReportUtils;
import org.ums.util.UmsUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Ifti on 07-Feb-17.
 */
@Service
public class UGExamRoutineGenerator {

  @Autowired
  private ExamRoutineManager mExamRoutineManager;

  @Autowired
  private SemesterManager mSemesterManager;

  Font universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
  Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
  Font infoFontUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.UNDERLINE);
  Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN, 12f);
  Font tableCaptionFont = new Font(Font.FontFamily.TIMES_ROMAN, 11.5f, Font.BOLD);

  Font footerInfoFont = new Font(Font.FontFamily.TIMES_ROMAN, 13);
  Font footerInfoFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);

  public void createExamRoutineReport(OutputStream pOutputStream, final Integer pSemesterId,
      final Integer pExamType) throws IOException, DocumentException {

    List<ExamRoutineDto> examList = mExamRoutineManager.getExamRoutine(pSemesterId, pExamType);

    String semesterName = mSemesterManager.get(pSemesterId).getName();

    Document document = new Document();
    document.addTitle("Exam Routine");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    document.setPageSize(PageSize.A4);
    Paragraph paragraph = new Paragraph();
    document.setMargins(20, 20, 20, 20);
    // Left, Right, Top, Bottom

    UGExamRoutineGenerator.UGExamRoutineFooter event =
        new UGExamRoutineGenerator.UGExamRoutineFooter();
    writer.setPageEvent(event);

    document.open();

    PdfPTable headerTable = new PdfPTable(1);
    Chunk chunk = null;
    headerTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    headerTable.setWidthPercentage(100);
    headerTable.setTotalWidth(document.getPageSize().getWidth() - 30);
    universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
    infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

    PdfPCell cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

    cell = ReportUtils.getCell(ReportUtils.B0_P0);
    paragraph = new Paragraph("NOTIFICATION", universityNameFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    headerTable.addCell(cell);

    cell = ReportUtils.getCell(ReportUtils.B0_P0);
    paragraph =
        new Paragraph("(Schedule of Semester Final Examinations of "
            + mSemesterManager.get(pSemesterId).getName() + ")", FontFactory.getFont(
            FontFactory.TIMES_BOLD, 12));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    headerTable.addCell(cell);

    cell = ReportUtils.getCell(ReportUtils.B0_P0);
    paragraph =
        new Paragraph(
            "It is hereby notified for information of all concerned that the Semester Final Examinations of "
                + semesterName
                + " for the Undergraduate students of Ahasanullah University of Science and Technology (AUST) will be held from   "
                + UmsUtils.formatDate(examList.get(0).getExamDate(), "dd/MM/yyyy",
                    "EEEE d MMM yyyy")
                + " to "
                + UmsUtils.formatDate(examList.get(examList.size() - 1).getExamDate(),
                    "dd/MM/yyyy", "EEEE d MMM yyyy")
                + " in accordance with the following schedule.", FontFactory.getFont(
                FontFactory.TIMES, 11));
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    cell.addElement(paragraph);
    headerTable.addCell(cell);
    document.add(headerTable);

    // Create Table object, Here 6 specify the no. of columns
    PdfPTable pdfPTable = new PdfPTable(6);
    pdfPTable.setWidthPercentage(100);
    pdfPTable.setWidths(new float[] {new Float(1.4), new Float(1.8), new Float(1.2),
        new Float(1.2), new Float(1.5), 4});

    // Create cells
    PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("Date & Time", tableCaptionFont));
    pdfPCell1.setMinimumHeight(30);
    pdfPCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
    pdfPCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
    pdfPCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
    PdfPCell pdfPCell2 = new PdfPCell(new Paragraph("Program", tableCaptionFont));
    pdfPCell2.setPaddingLeft(5);
    pdfPCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
    pdfPCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
    PdfPCell pdfPCell3 = new PdfPCell(new Paragraph("Year\n(Students')", tableCaptionFont));
    pdfPCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
    pdfPCell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
    pdfPCell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
    PdfPCell pdfPCell4 = new PdfPCell(new Paragraph("Semester\n(Students')", tableCaptionFont));
    pdfPCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
    pdfPCell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
    pdfPCell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
    PdfPCell pdfPCell5 = new PdfPCell(new Paragraph("Course\nNumber", tableCaptionFont));
    pdfPCell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
    pdfPCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
    pdfPCell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
    PdfPCell pdfPCell6 = new PdfPCell(new Paragraph("Course Title", tableCaptionFont));
    pdfPCell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
    pdfPCell6.setPaddingLeft(5);
    pdfPCell6.setBackgroundColor(BaseColor.LIGHT_GRAY);

    // Add cells to table
    pdfPTable.addCell(pdfPCell1);
    pdfPTable.addCell(pdfPCell2);
    pdfPTable.addCell(pdfPCell3);
    pdfPTable.addCell(pdfPCell4);
    pdfPTable.addCell(pdfPCell5);
    pdfPTable.addCell(pdfPCell6);

    String previousExamDate = "";
    String previousProgram = "";
    ExamRoutineDto dateInfo = null;
    Map<String, List<ExamRoutineDto>> routineMap = new LinkedHashMap<>();
    ArrayList<ExamRoutineDto> courseList = new ArrayList<>();
    for(ExamRoutineDto routineDto : examList) {

      if(routineDto.getExamDate().equalsIgnoreCase(previousExamDate) || previousExamDate.equals("")) {
        dateInfo = routineDto;
        if(routineDto.getProgramName().equalsIgnoreCase(previousProgram)
            || previousProgram.equals("")) {
          courseList.add(routineDto);

        }
        else {
          // New Program Found
          // -- Add previous courses to the Map
          System.out.println(previousProgram);
          routineMap.put(previousProgram, courseList);
          courseList = new ArrayList<>();
          courseList.add(routineDto);
        }
      }
      else {
        // New Date Found
        routineMap.put(previousProgram, courseList);
        drawRow(dateInfo, routineMap, pdfPTable);
        routineMap = new HashMap<>();
        courseList = new ArrayList<>();
        courseList.add(routineDto);
      }
      previousExamDate = routineDto.getExamDate();
      previousProgram = routineDto.getProgramName();

    }
    drawRow(dateInfo, routineMap, pdfPTable);

    pdfPTable.setSpacingBefore(20);
    pdfPTable.setHeaderRows(1);
    document.add(pdfPTable);

    PdfPTable footerTable = new PdfPTable(1);
    cell = new PdfPCell();
    cell.setBorder(0);
    footerTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    footerTable.setWidthPercentage(100);
    footerTable.setTotalWidth(document.getPageSize().getWidth() - 30);

    cell = ReportUtils.getCell(ReportUtils.B0_P0);
    paragraph = new Paragraph("The examinations will ", footerInfoFont);
    chunk =
        ReportUtils.getChunk("will start from9:30 a.m and continue up to 12:30 p.m. ",
            footerInfoFontBold, ReportUtils.LR1S);
    paragraph.add(chunk);
    chunk =
        ReportUtils.getChunk(
            "everyday. Concerned examinees are asked to take their respective seats",
            footerInfoFont, ReportUtils.LR1S);
    paragraph.add(chunk);
    chunk =
        ReportUtils.getChunk("at least 15 (fifteen) minutes earlier than the scheduled time.",
            footerInfoFontBold, ReportUtils.LR1S);
    paragraph.add(chunk);
    chunk =
        ReportUtils
            .getChunk(
                "Room-wise sitting arrangement will be notified in due course for the examinees. The examinees are also asked to bring their",
                footerInfoFont, ReportUtils.LR1S);
    paragraph.add(chunk);
    chunk =
        ReportUtils
            .getChunk(
                "identity cards with valid sticker, non-programmable calculators and other accessories officially allowed",
                footerInfoFontBold, ReportUtils.LR1S);
    paragraph.add(chunk);
    chunk = ReportUtils.getChunk("in the examination halls.\n", footerInfoFont, ReportUtils.LR1S);
    paragraph.add(chunk);

    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    cell.addElement(paragraph);
    footerTable.addCell(cell);

    PdfPCell pdfPCell = new PdfPCell(new Paragraph("By Order of the Vice-Chancellor", tableFont));
    pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    pdfPCell.setVerticalAlignment(Element.ALIGN_TOP);
    pdfPCell.setPaddingRight(20);
    pdfPCell.setPaddingTop(10);
    pdfPCell.setFixedHeight(100);
    pdfPCell.setBorder(0);
    footerTable.addCell(pdfPCell);

    pdfPCell =
        new PdfPCell(
            new Paragraph(
                "______________________________\nProf. Md. Amirul Alam Khan\nController of Examinations",
                tableFont));
    pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    pdfPCell.setVerticalAlignment(Element.ALIGN_TOP);
    pdfPCell.setPaddingRight(20);
    pdfPCell.setPaddingTop(10);
    pdfPCell.setBorder(0);
    footerTable.addCell(pdfPCell);

    footerTable.setSpacingBefore(20);
    document.add(footerTable);

    document.close();

    baos.writeTo(pOutputStream);

  }

  private void drawRow(ExamRoutineDto dateInfo, Map<String, List<ExamRoutineDto>> mapList,
      PdfPTable pdfPTable) {
    String dateTime = dateInfo.getExamTime().replaceAll("to", "\nto\n");
    PdfPCell pdfPCell =
        new PdfPCell(new Paragraph(UmsUtils.formatDate(dateInfo.getExamDate(), "dd/MM/yyyy",
            "d MMM yyyy (EEEE)") + "\n\nFrom\n " + dateTime, tableFont));
    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    pdfPCell.setRowspan(getTotalRowCount(mapList));
    pdfPTable.addCell(pdfPCell);

    Iterator it = mapList.entrySet().iterator();
    while(it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      ArrayList<ExamRoutineDto> courseList = (ArrayList<ExamRoutineDto>) pair.getValue();

      pdfPCell = new PdfPCell(new Paragraph(pair.getKey().toString(), tableFont));
      pdfPCell.setRowspan(courseList.size());
      pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      pdfPCell.setPaddingLeft(5);
      pdfPTable.addCell(pdfPCell);

      for(ExamRoutineDto course : courseList) {
        pdfPCell = new PdfPCell(new Paragraph(course.getCourseYear() + "", tableFont));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPTable.addCell(pdfPCell);
        pdfPCell = new PdfPCell(new Paragraph(course.getCourseSemester() + "", tableFont));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPTable.addCell(pdfPCell);
        pdfPCell = new PdfPCell(new Paragraph(course.getCourseNumber(), tableFont));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPTable.addCell(pdfPCell);
        pdfPCell = new PdfPCell(new Paragraph(course.getCourseTitle(), tableFont));
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPCell.setMinimumHeight(20);
        pdfPCell.setPaddingLeft(5);
        pdfPTable.addCell(pdfPCell);
      }

    }

  }

  private int getTotalRowCount(Map<String, List<ExamRoutineDto>> mapList) {
    int total = 0;
    Iterator it = mapList.entrySet().iterator();
    while(it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      total += ((ArrayList<ExamRoutineDto>) pair.getValue()).size();
    }
    return total;
  }

  class UGExamRoutineFooter extends PdfPageEventHelper {

    public UGExamRoutineFooter() {}

    Font ffont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);

    @Override
    public void onStartPage(PdfWriter writer, Document document) {

    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
      PdfContentByte cb = writer.getDirectContent();
      Phrase footer = new Phrase(String.format("page %d", writer.getPageNumber()), ffont);

      ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer, document.right(),
          document.bottom() - 2, 0);
    }

  }

}
