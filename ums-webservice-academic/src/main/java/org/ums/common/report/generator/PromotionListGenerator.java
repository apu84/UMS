package org.ums.common.report.generator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.manager.StudentRecordManager;
import org.ums.util.Constants;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Component
public class PromotionListGenerator {
  @Autowired
  StudentRecordManager mStudentRecordManager;

  private static final Logger mLogger = LoggerFactory.getLogger(PromotionListGenerator.class);

  final Font universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
  final Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
  final Font infoFontUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.UNDERLINE);
  final Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN, 8.5f);

  private Department mCurrentDepartment;
  private Integer mCurrentYear;
  private Integer mCurrentAcademicSemester;
  private Semester mCurrentSemester;

  public void createPdf(Integer pProgramId, Integer pSemesterId, OutputStream outputStream)
      throws DocumentException, IOException, Exception {

    java.util.List<StudentRecord> studentRecordList =
        getPromotedStudentList(mStudentRecordManager.getStudentRecords(pProgramId, pSemesterId));

    if(studentRecordList.size() > 0) {
      initialize(studentRecordList.get(0));
    }
    else {
      throw new IllegalArgumentException("No. of students for promotion list is zero");
    }

    Document document = new Document();
    document.addTitle("Promotion list");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    document.setPageSize(PageSize.A4);
    document.setMargins(10, 10, 100, 10);

    writer.setPageEvent(new HeaderFooter());
    document.open();

    PdfPTable contentTable = contentTable();

    for(StudentRecord studentRecord : studentRecordList) {
      if(mCurrentYear.intValue() != studentRecord.getYear()
          || mCurrentAcademicSemester.intValue() != studentRecord.getAcademicSemester()) {
        mCurrentYear = studentRecord.getYear();
        mCurrentAcademicSemester = studentRecord.getAcademicSemester();
        document.add(contentTable);
        document.newPage();
        contentTable = contentTable();
      }
      contentCell(studentRecord.getStudentId(), studentRecord.getStudent().getFullName(),
          studentRecord.getGPA().toString(), studentRecord.getCGPA().toString(), contentTable);
    }
    document.add(contentTable);
  }

  private java.util.List<StudentRecord> getPromotedStudentList(
      java.util.List<StudentRecord> pStudentRecordList) {
    return pStudentRecordList.stream()
        .filter(pResult -> pResult.getStatus() == StudentRecord.Status.PASSED)
        .collect(Collectors.toList());
  }

  private void initialize(StudentRecord pStudentRecord) throws Exception {
    mCurrentYear = pStudentRecord.getYear();
    mCurrentAcademicSemester = pStudentRecord.getAcademicSemester();
    mCurrentSemester = pStudentRecord.getSemester();
  }

  private PdfPTable contentTable() {
    PdfPTable contentTable = new PdfPTable(4);

    PdfPCell cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    Paragraph paragraph = new Paragraph("Student No.", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("Student Name", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("GPA", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("CGPA", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    contentTable.addCell(cell);

    return contentTable;
  }

  private void contentCell(String pStudentId, String pName, String pGPA, String pCGPA,
      PdfPTable pContentTable) {
    PdfPCell cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    Paragraph paragraph = new Paragraph(pStudentId, tableFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    pContentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph(pName, tableFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    pContentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph(pGPA, tableFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    pContentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph(pCGPA, tableFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    pContentTable.addCell(cell);
  }

  class HeaderFooter extends PdfPageEventHelper {
    int pagenumber;
    final Font pageNoFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
      pagenumber++;
      try {
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        headerTable.setWidthPercentage(100);

        cell = new PdfPCell();
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        Paragraph paragraph = new Paragraph(Constants.University_AllCap, universityNameFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        headerTable.addCell(cell);

        PdfPTable headerSecondRow = new PdfPTable(2);
        headerSecondRow.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        headerSecondRow.setWidthPercentage(100);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("PROMOTION LIST", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("SESSION: " + mCurrentSemester.getName(), infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);

        headerSecondRow.addCell(cell);
        headerTable.addCell(headerSecondRow);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("DEPARTMENT: " + mCurrentDepartment.getLongName(), infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        headerTable.addCell(cell);

        PdfPTable headerFourthRow = new PdfPTable(2);
        headerFourthRow.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        headerFourthRow.setWidthPercentage(100);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("YEAR: " + mCurrentYear, infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("SEMESTER: " + mCurrentAcademicSemester, infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);

        headerFourthRow.addCell(cell);
        headerTable.addCell(headerFourthRow);

        document.add(headerTable);

      } catch(Exception de) {
        mLogger.error("Exception while adding page header for promotion list report", de);
      }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {

      PdfContentByte cb = writer.getDirectContent();
      Phrase footer = new Phrase(
          String.format("page %d of %d", writer.getPageNumber(), document.getPageNumber()),
          pageNoFont);

      ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer, document.right(),
          document.bottom() - 2, 0);
    }
  }
}
