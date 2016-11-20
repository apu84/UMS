package org.ums.common.report.generator;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.util.Constants;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public abstract class AbstractResultGenerator {
  private static final Logger mLogger = LoggerFactory.getLogger(AbstractResultGenerator.class);

  final Font universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
  final Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
  final Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN, 8.5f);

  private Department mCurrentDepartment;
  private Integer mCurrentYear;
  private Integer mCurrentAcademicSemester;
  private Semester mCurrentSemester;

  protected abstract List<StudentRecord> getStudentList(int pProgramId, int pSemesterId)
      throws Exception;

  protected abstract PdfPTable contentTableHeader();

  protected abstract void contentRow(StudentRecord pStudentRecord, PdfPTable pContentTable)
      throws Exception;

  protected abstract String getReportTitle();

  private void initialize(StudentRecord pStudentRecord) throws Exception {
    mCurrentYear = pStudentRecord.getYear();
    mCurrentAcademicSemester = pStudentRecord.getAcademicSemester();
    mCurrentSemester = pStudentRecord.getSemester();
    mCurrentDepartment = pStudentRecord.getStudent().getDepartment();
  }

  public void createPdf(Integer pProgramId, Integer pSemesterId, OutputStream pOutputStream)
      throws Exception {
    java.util.List<StudentRecord> studentRecordList = getStudentList(pProgramId, pSemesterId);
    if(studentRecordList.size() > 0) {
      initialize(studentRecordList.get(0));
    }
    else {
      throw new IllegalArgumentException("No. of students is zero");
    }

    Document document = new Document();
    document.addTitle(getReportTitle());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    document.setPageSize(PageSize.A4);
    document.setMargins(10, 10, 50, 10);

    writer.setPageEvent(new HeaderFooter());
    document.open();

    PdfPTable contentTable = contentTableHeader();
    contentTable.setHeaderRows(1);

    for(StudentRecord studentRecord : studentRecordList) {
      if(mCurrentYear.intValue() != studentRecord.getYear()
          || mCurrentAcademicSemester.intValue() != studentRecord.getAcademicSemester()) {
        document.add(contentTable);
        mCurrentYear = studentRecord.getYear();
        mCurrentAcademicSemester = studentRecord.getAcademicSemester();
        document.newPage();
        contentTable = contentTableHeader();
        contentTable.setHeaderRows(1);
      }
      contentRow(studentRecord, contentTable);
    }
    document.add(contentTable);
    document.close();
    baos.writeTo(pOutputStream);
  }

  private class HeaderFooter extends PdfPageEventHelper {
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

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
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
        paragraph = new Paragraph(getReportTitle(), infoFont);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(paragraph);
        headerSecondRow.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("SESSION: " + mCurrentSemester.getName(), infoFont);
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(paragraph);
        headerSecondRow.addCell(cell);

        headerSecondRow.addCell(cell);
        headerTable.addCell(headerSecondRow);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("DEPARTMENT: " + mCurrentDepartment.getLongName(), infoFont);
        paragraph.setAlignment(Element.ALIGN_LEFT);
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
        paragraph.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(paragraph);
        headerFourthRow.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("SEMESTER: " + mCurrentAcademicSemester, infoFont);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(paragraph);
        headerFourthRow.addCell(cell);

        headerTable.addCell(headerFourthRow);
        headerTable.setSpacingAfter(20F);
        document.add(headerTable);

      } catch(Exception de) {
        mLogger.error("Exception while adding page header for promotion list report", de);
      }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {

      PdfContentByte cb = writer.getDirectContent();
      Phrase footer =
          new Phrase(String.format("page %d of %d", writer.getPageNumber(),
              document.getPageNumber()), pageNoFont);

      ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer, document.right(),
          document.bottom() - 2, 0);
    }
  }
}
