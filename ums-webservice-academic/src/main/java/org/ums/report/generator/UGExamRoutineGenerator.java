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

  Font universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
  Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
  Font infoFontUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.UNDERLINE);
  Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f);
  Font tableCaptionFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD);

  Font footerInfoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
  Font footerInfoFontBold = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
  Font footerInfoFontBoldUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD
      | Font.UNDERLINE);

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
    document.setMargins(20, 20, 40, 70);
    // Left, Right, Top, Bottom

    PdfPTable rHeader = new PdfPTable(2);
    rHeader.setWidthPercentage(100);
    rHeader.setTotalWidth(document.getPageSize().getWidth() - 40);
    rHeader.setWidths(new float[] {new Float(1), new Float(1)});
    Chunk chunk = null;
    rHeader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

    PdfPCell cell = new PdfPCell(new Paragraph("No. : AUST/ Exam/ Fall 2016(15)", tableFont));
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    rHeader.addCell(cell);

    cell = new PdfPCell(new Paragraph("Date: 13 February 2017", tableFont));
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    rHeader.addCell(cell);

    UGExamRoutineGenerator.UGExamRoutineFooter event =
        new UGExamRoutineGenerator.UGExamRoutineFooter(rHeader);
    writer.setPageEvent(event);
    document.open();

    PdfPTable headerTable = new PdfPTable(1);
    headerTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    headerTable.setWidthPercentage(100);
    headerTable.setTotalWidth(document.getPageSize().getWidth() - 30);
    universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
    infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

    PdfPCell dummyCell = new PdfPCell(new Paragraph(""));
    dummyCell.setFixedHeight(60);
    dummyCell.setBorder(0);
    dummyCell.setHorizontalAlignment(Element.ALIGN_LEFT);
    dummyCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
    headerTable.addCell(dummyCell);

    dummyCell = ReportUtils.getCell(ReportUtils.B0_P0);
    paragraph = new Paragraph("");
    paragraph.setAlignment(Element.ALIGN_CENTER);
    dummyCell.addElement(paragraph);
    headerTable.addCell(dummyCell);

    cell = ReportUtils.getCell(ReportUtils.B0_P0);
    paragraph = new Paragraph("NOTIFICATION", universityNameFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    headerTable.addCell(cell);

    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell = ReportUtils.getCell(ReportUtils.B0_P0);
    paragraph =
        new Paragraph("(Schedule of Semester Final Examinations of "
            + mSemesterManager.get(pSemesterId).getName() + ")", FontFactory.getFont(
            FontFactory.TIMES_BOLD, 11));
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
                    "EEEE d MMMM yyyy")
                + " to "
                + UmsUtils.formatDate(examList.get(examList.size() - 1).getExamDate(),
                    "dd/MM/yyyy", "EEEE d MMMM yyyy")
                + " in accordance with the following schedule.", FontFactory.getFont(
                FontFactory.TIMES, 10));
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    cell.addElement(paragraph);
    headerTable.addCell(cell);
    headerTable.setSpacingBefore(260);
    document.add(headerTable);

    // Create Table object, Here 6 specify the no. of columns
    PdfPTable pdfPTable = new PdfPTable(6);
    pdfPTable.setWidthPercentage(100);
    pdfPTable.setWidths(new float[] {new Float(1.6), new Float(1.1), new Float(1.1), new Float(1),
        new Float(1.5), new Float(4)});

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
          routineMap.put(previousProgram, courseList);
          courseList = new ArrayList<>();
          courseList.add(routineDto);
        }
      }
      else {
        // New Date Found
        routineMap.put(previousProgram, courseList);
        drawRow(dateInfo, routineMap, pdfPTable);
        routineMap = new LinkedHashMap<>();
        courseList = new ArrayList<>();
        courseList.add(routineDto);
      }
      previousExamDate = routineDto.getExamDate();
      previousProgram = routineDto.getProgramName();

    }
    routineMap.put(previousProgram, courseList);
    drawRow(dateInfo, routineMap, pdfPTable);

    pdfPTable.setSpacingBefore(20);
    pdfPTable.setHeaderRows(1);
    document.add(pdfPTable);

    PdfPTable footerTable = new PdfPTable(2);
    cell = new PdfPCell();
    cell.setBorder(0);
    footerTable.setWidthPercentage(100);
    rHeader.setWidths(new float[] {new Float(3.5), new Float(1)});
    footerTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    footerTable.setTotalWidth(document.getPageSize().getWidth() - 30);

    cell = ReportUtils.getCell(ReportUtils.B0_P0);
    cell.setColspan(2);
    paragraph = new Paragraph("The examinations will ", footerInfoFont);
    chunk =
        ReportUtils.getChunk("will start from 9:30 a.m and continue up to 12:30 p.m. ",
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

    cell = new PdfPCell();
    cell.setBorder(0);
    footerTable.addCell(cell);

    PdfPCell pdfPCell = new PdfPCell(new Paragraph("By Order of the Vice-Chancellor, ", tableFont));
    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    pdfPCell.setVerticalAlignment(Element.ALIGN_TOP);
    pdfPCell.setPaddingTop(10);
    pdfPCell.setFixedHeight(40);
    pdfPCell.setBorder(0);
    footerTable.addCell(pdfPCell);

    cell = new PdfPCell();
    cell.setBorder(0);
    footerTable.addCell(cell);

    pdfPCell = new PdfPCell(new Paragraph("___________________________________", tableFont));
    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    pdfPCell.setVerticalAlignment(Element.ALIGN_TOP);
    pdfPCell.setBorder(0);
    footerTable.addCell(pdfPCell);

    cell = new PdfPCell();
    cell.setBorder(0);
    footerTable.addCell(cell);
    pdfPCell =
        new PdfPCell(new Paragraph(
            "Dr. Md. Ismail Chowdhury\nController of Examinations(In-charge)", tableFont));
    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    pdfPCell.setVerticalAlignment(Element.ALIGN_TOP);
    pdfPCell.setBorder(0);
    footerTable.addCell(pdfPCell);

    footerTable.setSpacingBefore(20);
    document.add(footerTable);

    /** Copy to Section **/
    PdfPTable copyToTable = new PdfPTable(1);
    cell = new PdfPCell();
    copyToTable.setWidthPercentage(100);
    copyToTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

    pdfPCell = new PdfPCell(new Paragraph("Copy to:", footerInfoFontBoldUnderline));
    pdfPCell.setFixedHeight(30);
    pdfPCell.setBorder(0);
    copyToTable.addCell(pdfPCell);

    pdfPCell =
        new PdfPCell(new Paragraph("1. Heads of the Department/ School/ Offices, AUST", tableFont));
    pdfPCell.setFixedHeight(25);
    pdfPCell.setBorder(0);
    copyToTable.addCell(pdfPCell);

    pdfPCell = new PdfPCell(new Paragraph("2. APS to Vice-Chancellor, AUST ", tableFont));
    pdfPCell.setFixedHeight(25);
    pdfPCell.setBorder(0);
    copyToTable.addCell(pdfPCell);

    pdfPCell = new PdfPCell(new Paragraph("3. APS to Treasurer, AUST ", tableFont));
    pdfPCell.setFixedHeight(25);
    pdfPCell.setBorder(0);
    copyToTable.addCell(pdfPCell);

    pdfPCell = new PdfPCell(new Paragraph("4. Notice Boards, AUST ", tableFont));
    pdfPCell.setFixedHeight(25);
    pdfPCell.setBorder(0);
    copyToTable.addCell(pdfPCell);

    pdfPCell = new PdfPCell(new Paragraph("5. University Website, AUST ", tableFont));
    pdfPCell.setFixedHeight(25);
    pdfPCell.setBorder(0);
    copyToTable.addCell(pdfPCell);
    /** End of Copy To **/

    rHeader.setSpacingBefore(40);
    document.add(rHeader);
    copyToTable.setSpacingBefore(15);
    document.add(copyToTable);

    document.close();
    baos.writeTo(pOutputStream);
  }

  private void drawRow(ExamRoutineDto dateInfo, Map<String, List<ExamRoutineDto>> mapList,
      PdfPTable pdfPTable) {
    String dateTime = dateInfo.getExamTime().replaceAll("to", "\nto\n");
    PdfPCell pdfPCell =
        new PdfPCell(new Paragraph(UmsUtils.formatDate(dateInfo.getExamDate(), "dd/MM/yyyy",
            "d MMMM yyyy (EEEE)") + "\n\nFrom\n " + dateTime, tableFont));
    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    pdfPCell.setRowspan(getTotalRowCount(mapList));
    // pdfPCell.setBorderWidthBottom(new Float(1));
    pdfPCell.setPaddingTop(3);
    pdfPCell.setPaddingBottom(3);
    pdfPTable.addCell(pdfPCell);

    int programCount = 1;
    Iterator it = mapList.entrySet().iterator();
    while(it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      List<ExamRoutineDto> courseList = (ArrayList<ExamRoutineDto>) pair.getValue();

      pdfPCell = new PdfPCell(new Paragraph(pair.getKey().toString(), tableFont));
      pdfPCell.setRowspan(courseList.size());
      pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      pdfPCell.setPaddingLeft(5);
      // if(programCount == mapList.size())
      // pdfPCell.setBorderWidthBottom(new Float(1));
      pdfPTable.addCell(pdfPCell);

      int courseCount = 1;
      for(ExamRoutineDto course : courseList) {
        pdfPCell = new PdfPCell(new Paragraph(course.getCourseYear() + "", tableFont));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        // if(programCount == mapList.size() && courseCount == courseList.size())
        // pdfPCell.setBorderWidthBottom(new Float(1));
        pdfPTable.addCell(pdfPCell);
        pdfPCell = new PdfPCell(new Paragraph(course.getCourseSemester() + "", tableFont));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        // if(programCount == mapList.size() && courseCount == courseList.size())
        // pdfPCell.setBorderWidthBottom(new Float(1));
        pdfPTable.addCell(pdfPCell);
        pdfPCell = new PdfPCell(new Paragraph(course.getCourseNumber(), tableFont));
        pdfPCell.setPaddingLeft(5);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        // if(programCount == mapList.size() && courseCount == courseList.size())
        // pdfPCell.setBorderWidthBottom(new Float(1));
        pdfPTable.addCell(pdfPCell);
        pdfPCell = new PdfPCell(new Paragraph(course.getCourseTitle(), tableFont));
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        // if(programCount == mapList.size() && courseCount == courseList.size())
        // pdfPCell.setBorderWidthBottom(new Float(1));
        pdfPCell.setMinimumHeight(18);
        pdfPCell.setPaddingLeft(5);
        pdfPTable.addCell(pdfPCell);

        courseCount++;
      }
      programCount++;
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

    protected PdfPTable header;

    public UGExamRoutineFooter(PdfPTable header) {
      this.header = header;
    }

    Font ffont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
      if(writer.getPageNumber() == 1)
        header.writeSelectedRows(0, -1, 20, document.top() - 40, writer.getDirectContent());
      else
        header.writeSelectedRows(0, -1, 20, document.top() + 22, writer.getDirectContent());
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
      PdfContentByte cb = writer.getDirectContent();
      Phrase footer = new Phrase(String.format("page %d", writer.getPageNumber()), ffont);

      ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer, document.right(),
          document.bottom() - 30, 0);
    }

  }

}
