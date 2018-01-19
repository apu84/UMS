package org.ums.academic.resource.tabulation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;
import org.ums.academic.tabulation.model.TabulationEntryModel;
import org.ums.academic.tabulation.model.TabulationReportModel;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.util.Constants;
import org.ums.util.UmsUtils;

import com.itextpdf.text.*;

@Component
public class TabulationPdf {
  final Font universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
  final Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 8);
  final Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN, 8.5f);
  final Font pageNoFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);

  private TabulationReportModel mTabulationReportModel;

  public void createPdf(TabulationReportModel pReportModel, OutputStream pOutputStream) throws IOException,
      DocumentException {
    mTabulationReportModel = pReportModel;
    Document document = new Document();
    document.addTitle("Tabulation");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    document.setPageSize(PageSize.LEGAL.rotate());
    document.setMargins(10, 10, 10, 10);

    HeaderFooter event = new HeaderFooter();
    writer.setBoxSize("art", new Rectangle(20, 54, 1024, 850));
    writer.setPageEvent(event);

    document.open();
    PdfPTable contentTable = new PdfPTable(20);
    contentTable.setWidthPercentage(100);
    contentTableHeader(contentTable);
    headerSecondLine(contentTable, pReportModel);
    headerThirdLine(contentTable, pReportModel);
    entryLine(contentTable, pReportModel);
    document.add(contentTable);

    document.newPage();
    PdfPTable secondPageContentTable = new PdfPTable(12);
    secondPageContentTable.setWidthPercentage(100);
    secondPageTableHeader(secondPageContentTable);
    entryLineSecondPage(secondPageContentTable, pReportModel);
    document.add(secondPageContentTable);

    document.close();

    baos.writeTo(pOutputStream);
  }

  private void contentTableHeader(final PdfPTable contentTable) {
    PdfPCell cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    Paragraph paragraph = new Paragraph("", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(2);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("SEMESTER FINAL", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(6);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("SESSIONAL", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(5);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("CLEARANCE/ IMPROVEMENT", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(6);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);
  }

  private void headerSecondLine(final PdfPTable contentTable, final TabulationReportModel pReportModel) {
    PdfPCell cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    Paragraph paragraph = new Paragraph("", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(2);
    contentTable.addCell(cell);

    for(Course theoryCourse : pReportModel.getTheoryCourses()) {
      addCourse(contentTable, theoryCourse.getNo());
    }
    addEmptyCell(contentTable, (6 - pReportModel.getTheoryCourses().size()));

    for(Course sessionalCourse : pReportModel.getSessionalCourses()) {
      addCourse(contentTable, sessionalCourse.getNo());
    }

    addEmptyCell(contentTable, (5 - pReportModel.getSessionalCourses().size()));

    for(Course theoryCourse : pReportModel.getTheoryCourses()) {
      addCourse(contentTable, theoryCourse.getNo());
    }
    addEmptyCell(contentTable, (6 - pReportModel.getTheoryCourses().size()));
    addEmptyCell(contentTable, 1);
  }

  private void headerThirdLine(final PdfPTable contentTable, final TabulationReportModel pReportModel) {
    PdfPCell cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    Paragraph paragraph = new Paragraph("Student No.", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("Name", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    for(Course theoryCourse : pReportModel.getTheoryCourses()) {
      addCourse(contentTable, "cr.          " + theoryCourse.getCrHr());
    }
    addEmptyCell(contentTable, (6 - pReportModel.getTheoryCourses().size()));

    for(Course sessionalCourse : pReportModel.getSessionalCourses()) {
      addCourse(contentTable, "cr.          " + sessionalCourse.getCrHr());
    }

    addEmptyCell(contentTable, (5 - pReportModel.getSessionalCourses().size()));

    for(Course theoryCourse : pReportModel.getTheoryCourses()) {
      addCourse(contentTable, "cr.          " + theoryCourse.getCrHr());
    }
    addEmptyCell(contentTable, (6 - pReportModel.getTheoryCourses().size()));

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("Student No.", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);
  }

  private void entryLine(final PdfPTable contentTable, final TabulationReportModel pReportModel) {
    PdfPCell cell;
    Paragraph paragraph;
    Map<String, Double> GPA_MAP = UmsUtils.getGPAMap();
    for(TabulationEntryModel entryModel : pReportModel.getTabulationEntries()) {
      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph(entryModel.getStudent().getId(), infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph(entryModel.getStudent().getFullName(), infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      for(Course theoryCourse : pReportModel.getTheoryCourses()) {
        String gradeLetter = entryModel.getRegularCourseList().get(theoryCourse.getId()).getGradeLetter();
        double gradePoint = GPA_MAP.get(gradeLetter) * theoryCourse.getCrHr();
        addCourse(contentTable, entryModel.getRegularCourseList().get(theoryCourse.getId()).getGradeLetter()
            + "           " + gradePoint);
      }
      addEmptyCell(contentTable, (6 - pReportModel.getTheoryCourses().size()));

      for(Course sessionalCourse : pReportModel.getSessionalCourses()) {
        String gradeLetter = entryModel.getRegularCourseList().get(sessionalCourse.getId()).getGradeLetter();
        double gradePoint = GPA_MAP.get(gradeLetter) * sessionalCourse.getCrHr();
        addCourse(contentTable, gradeLetter + "         " + gradePoint);
      }

      addEmptyCell(contentTable, (5 - pReportModel.getSessionalCourses().size()));

      for(Course theoryCourse : pReportModel.getTheoryCourses()) {
        if(entryModel.getClearanceCourseList().containsKey(theoryCourse.getId())) {
          String gradeLetter = entryModel.getClearanceCourseList().get(theoryCourse.getId()).getGradeLetter();
          double gradePoint = GPA_MAP.get(gradeLetter) * theoryCourse.getCrHr();
          addCourse(contentTable, gradeLetter + "         " + gradePoint);
        }
        else {
          addEmptyCell(contentTable, 1);
        }
      }
      addEmptyCell(contentTable, (6 - pReportModel.getTheoryCourses().size()));

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph(entryModel.getStudent().getId(), infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);
    }
  }

  private void entryLineSecondPage(final PdfPTable contentTable, final TabulationReportModel pReportModel) {
    PdfPCell cell;
    Paragraph paragraph;
    Map<String, Double> GPA_MAP = UmsUtils.getGPAMap();
    for(TabulationEntryModel entryModel : pReportModel.getTabulationEntries()) {
      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph(entryModel.getStudent().getId(), infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      for(UGRegistrationResult carryCourse : entryModel.getCarryCourseList().values()) {
        String gradeLetter = carryCourse.getGradeLetter();
        double gradePoint = GPA_MAP.get(gradeLetter) * carryCourse.getCourse().getCrHr();
        addCourse(contentTable, carryCourse.getCourse().getNo() + "          " + carryCourse.getCourse().getCrHr()
            + "\n" + gradeLetter + "                     " + gradePoint);
      }
      addEmptyCell(contentTable, (4 - entryModel.getCarryCourseList().size()));

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph =
          new Paragraph(entryModel.getPresentCompletedCrHr() + "       " + entryModel.getPresentCompletedGradePoints(),
              infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      if(entryModel.getPreviousSemesterCompletedCrHr() > 0) {
        paragraph =
            new Paragraph(entryModel.getPreviousSemesterCompletedCrHr() + "       "
                + entryModel.getPreviousSemesterCompletedGradePoints(), infoFont);
      }
      else {
        paragraph = new Paragraph("", infoFont);
      }

      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph =
          new Paragraph(entryModel.getCumulativeCrHr() + "       " + entryModel.getCumulativeGradePoints(), infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph(entryModel.getGpa() + "", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph(entryModel.getCgpa() + "", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph(entryModel.getRemarks(), infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);

      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph(entryModel.getStudent().getId(), infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);
    }
  }

  private void addCourse(PdfPTable contentTable, String content) {
    PdfPCell cell;
    Paragraph paragraph;
    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(content, infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);
  }

  private void addEmptyCell(PdfPTable contentTable, int number) {
    PdfPCell cell;
    Paragraph paragraph;
    for(int i = 0; i < number; i++) {
      cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      cell.setVerticalAlignment(Element.ALIGN_CENTER);
      paragraph = new Paragraph("", infoFont);
      paragraph.setAlignment(Element.ALIGN_LEFT);
      cell.addElement(paragraph);
      cell.setColspan(1);
      contentTable.addCell(cell);
    }
  }

  private void secondPageTableHeader(PdfPTable contentTable) {
    PdfPCell cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    Paragraph paragraph = new Paragraph("Student No.", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("CARRY OVER", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(4);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("Present Grade Points", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("Previous Grade Points", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("Cumulative Grade Points", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("GPA", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("CGPA", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("REMARKS", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("Student No.", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);
  }

  private class HeaderFooter extends PdfPageEventHelper {
    int pagenumber;

    public void onStartPage(PdfWriter writer, Document document) {
      pagenumber++;
      try {
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidths(new int[] {25, 75});
        headerTable.setWidthPercentage(100);
        // headerTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        PdfPTable gpaTable = new PdfPTable(2);
        gpaTable.setWidthPercentage(100);
        gpaTable.setWidths(new int[] {65, 35});
        PdfPCell gpaCell = new PdfPCell();
        gpaCell.setColspan(2);
        gpaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        gpaCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        Paragraph paragraph = new Paragraph("Grading Scales", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        gpaCell.addElement(paragraph);
        gpaTable.addCell(gpaCell);

        PdfPTable percentageTable = new PdfPTable(3);
        percentageTable.setWidthPercentage(100);
        percentageTable.setWidths(new int[] {50, 25, 25});

        PdfPCell percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("Numerical Equivalent", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("Letter Grade", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("Grade Point", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setBorder(Rectangle.NO_BORDER);
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("80% and above", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("A+", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("4", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("75% to less than 80", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("A", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("3.75", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("70% to less than 75", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("A-", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("3.5", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("65% to less than 70", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("B+", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("3.25", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("60% to less than 65", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("B", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("3", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("55% to less than 60", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("B-", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("2.75", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("50% to less than 55", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("C+", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("2.5", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("45% to less than 50", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("C", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("2.25", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("40% to less than 45", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("D", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("2", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("Less than 40%", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("F", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        percentageTableCell = new PdfPCell();
        percentageTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        percentageTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("0", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        percentageTableCell.addElement(paragraph);
        percentageTable.addCell(percentageTableCell);

        gpaCell = new PdfPCell();
        gpaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        gpaCell.setVerticalAlignment(Element.ALIGN_TOP);
        gpaCell.setPadding(0);
        gpaCell.addElement(percentageTable);
        gpaTable.addCell(gpaCell);

        PdfPTable extraOrdinaryTable = new PdfPTable(2);
        extraOrdinaryTable.setWidthPercentage(100);
        extraOrdinaryTable.setWidths(new int[] {70, 30});

        PdfPCell extraOrdinaryTableCell = new PdfPCell();
        extraOrdinaryTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("Numerical Equivalent", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.addElement(paragraph);
        extraOrdinaryTable.addCell(extraOrdinaryTableCell);

        extraOrdinaryTableCell = new PdfPCell();
        extraOrdinaryTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("Letter Grade", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.addElement(paragraph);
        extraOrdinaryTable.addCell(extraOrdinaryTableCell);

        extraOrdinaryTableCell = new PdfPCell();
        extraOrdinaryTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("Exemption", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.addElement(paragraph);
        extraOrdinaryTable.addCell(extraOrdinaryTableCell);

        extraOrdinaryTableCell = new PdfPCell();
        extraOrdinaryTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("E", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.addElement(paragraph);
        extraOrdinaryTable.addCell(extraOrdinaryTableCell);

        extraOrdinaryTableCell = new PdfPCell();
        extraOrdinaryTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("Withheld", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.addElement(paragraph);
        extraOrdinaryTable.addCell(extraOrdinaryTableCell);

        extraOrdinaryTableCell = new PdfPCell();
        extraOrdinaryTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("W", infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        extraOrdinaryTableCell.addElement(paragraph);
        extraOrdinaryTable.addCell(extraOrdinaryTableCell);

        gpaCell = new PdfPCell();
        gpaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        gpaCell.setVerticalAlignment(Element.ALIGN_TOP);
        gpaCell.setPadding(0);
        gpaCell.addElement(extraOrdinaryTable);
        gpaTable.addCell(gpaCell);

        PdfPCell headerTableCell = new PdfPCell();
        headerTableCell.setBorder(Rectangle.NO_BORDER);
        headerTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerTableCell.addElement(gpaTable);
        headerTable.addCell(headerTableCell);

        // end of gpa table

        PdfPTable titleTable = new PdfPTable(1);
        titleTable.setWidthPercentage(100);
        titleTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell titleTableCell = new PdfPCell();
        titleTableCell.setBorder(Rectangle.NO_BORDER);
        titleTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph(Constants.University_AllCap, universityNameFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        titleTableCell.addElement(paragraph);
        titleTable.addCell(titleTableCell);

        titleTableCell = new PdfPCell();
        titleTableCell.setBorder(Rectangle.NO_BORDER);
        titleTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph =
            new Paragraph("Approved by the Government of Bangladesh and sponsored by Dhaka Ahsania Mission", tableFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        titleTableCell.addElement(paragraph);
        titleTable.addCell(titleTableCell);

        titleTableCell = new PdfPCell();
        titleTableCell.setBorder(Rectangle.NO_BORDER);
        titleTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph =
            new Paragraph("TABULATION SHEET FOR " + UmsUtils.getNumberWithSuffix(mTabulationReportModel.getYear())
                + " YEAR   " + UmsUtils.getNumberWithSuffix(mTabulationReportModel.getAcademicSemester())
                + " SEMESTER;   SESSION: " + mTabulationReportModel.getSemester().getName(), infoFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        titleTableCell.addElement(paragraph);
        titleTable.addCell(titleTableCell);

        titleTableCell = new PdfPCell();
        titleTableCell.setBorder(Rectangle.NO_BORDER);
        titleTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph = new Paragraph("Department: " + mTabulationReportModel.getDepartment().getLongName(), tableFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        titleTableCell.addElement(paragraph);
        titleTable.addCell(titleTableCell);

        headerTableCell = new PdfPCell();
        headerTableCell.setBorder(Rectangle.NO_BORDER);
        headerTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerTableCell.addElement(titleTable);
        headerTable.addCell(headerTableCell);
        document.add(headerTable);

      } catch(Exception de) {
        de.printStackTrace();
      }
    }

    public void onEndPage(PdfWriter writer, Document document) {
      Font nFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);

      PdfPTable table = new PdfPTable(3);
      table.setTotalWidth(900);

      Paragraph p = new Paragraph("Prepared by:  _________________________", nFont);
      PdfPCell pCell = new PdfPCell(p);
      pCell.setBorder(Rectangle.NO_BORDER);
      pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
      pCell.setPaddingLeft(10);
      table.addCell(pCell);

      p = new Paragraph("Checked by: _________________________", nFont);
      pCell = new PdfPCell(p);
      pCell.setBorder(Rectangle.NO_BORDER);
      pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
      pCell.setPaddingLeft(60);
      table.addCell(pCell);

      p = new Paragraph("Controller of Examinations: _________________________", nFont);
      pCell = new PdfPCell(p);
      pCell.setBorder(Rectangle.NO_BORDER);
      pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
      pCell.setPaddingLeft(80);
      table.addCell(pCell);

      Phrase footer = new Phrase(String.format("page %d", writer.getPageNumber()), pageNoFont);
      ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, footer, document.right(), document.top() + 2, 0);
      if(writer.getPageNumber() % 2 != 0) {
        Phrase continued = new Phrase("Continued on the opposite side", pageNoFont);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, continued, document.right(), document.top() - 10, 0);
      }
      table.writeSelectedRows(0, -1, 36, 65, writer.getDirectContent());
    }
  }
}
