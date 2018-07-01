package org.ums.academic.resource.tabulation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.tabulation.model.TabulationEntryModel;
import org.ums.academic.tabulation.model.TabulationReportModel;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.enums.CourseRegType;
import org.ums.enums.CourseType;
import org.ums.manager.CourseManager;
import org.ums.util.Constants;
import org.ums.util.UmsUtils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Component
public class TabulationPdf {
  final Font universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
  final Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 7);
  final Font innerTableFont = new Font(Font.FontFamily.TIMES_ROMAN, 6);
  final Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f);
  final Font pageNoFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);
  final static int NUMBER_OF_ENTRIES_PER_PAGE = 15;

  private TabulationReportModel mTabulationReportModel;

  @Autowired
  private CourseManager mCourseManager;

  public void createPdf(TabulationReportModel pReportModel, OutputStream pOutputStream)
      throws IOException, DocumentException {
    mTabulationReportModel = pReportModel;
    Document document = new Document();
    document.addTitle("Tabulation");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    document.setPageSize(PageSize.LEGAL.rotate());
    document.setMargins(40, 40, 10, 10);

    HeaderFooter event = new HeaderFooter();
    writer.setBoxSize("art", new Rectangle(20, 54, 1024, 850));
    writer.setPageEvent(event);

    document.open();
    int totalPageRequired = totalPageRequired(pReportModel.getTabulationEntries().size());
    for(int i = 1; i <= totalPageRequired; i++) {
      PdfPTable contentTable = new PdfPTable(20);
      contentTable
          .setWidths(new float[] {4f, 7f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f, 4f});
      contentTable.setWidthPercentage(100);
      contentTableHeader(contentTable);
      headerSecondLine(contentTable, pReportModel);
      headerThirdLine(contentTable, pReportModel);
      for(int j = startIndex(i); j < totalNumberOfEntriesToBeDrawn(i,
          pReportModel.getTabulationEntries().size()); j++) {
        entryLineFirstPage(contentTable, pReportModel, pReportModel.getTabulationEntries().get(j));
      }
      document.add(contentTable);

      document.newPage();
      PdfPTable secondPageContentTable = new PdfPTable(12);
      secondPageContentTable.setWidths(new int[] {5, 9, 9, 9, 9, 8, 7, 7, 5, 5, 22, 5});
      secondPageContentTable.setWidthPercentage(100);
      secondPageTableHeader(secondPageContentTable);
      for(int j = startIndex(i); j < totalNumberOfEntriesToBeDrawn(i,
          pReportModel.getTabulationEntries().size()); j++) {
        entryLineSecondPage(secondPageContentTable, pReportModel, pReportModel.getTabulationEntries().get(j));
      }
      document.add(secondPageContentTable);
      if(i != totalPageRequired) {
        document.newPage();
      }
    }

    document.close();

    baos.writeTo(pOutputStream);
  }

  private int startIndex(final int pPageNumber) {
    return pPageNumber == 1 ? 0 : (pPageNumber * NUMBER_OF_ENTRIES_PER_PAGE) - NUMBER_OF_ENTRIES_PER_PAGE;
  }

  private int totalNumberOfEntriesToBeDrawn(final int pPageNumber, final int pTotal) {
    return pPageNumber * NUMBER_OF_ENTRIES_PER_PAGE > pTotal ? pTotal : pPageNumber * NUMBER_OF_ENTRIES_PER_PAGE;
  }

  private int totalPageRequired(final int pTotalEntries) {
    return pTotalEntries / NUMBER_OF_ENTRIES_PER_PAGE + (pTotalEntries % NUMBER_OF_ENTRIES_PER_PAGE > 0 ? 1 : 0);
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
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("SEMESTER FINAL", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setColspan(6);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("SESSIONAL", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setColspan(5);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("CLEARANCE/ IMPROVEMENT", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
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
      addInnerTable(contentTable, theoryCourse.getNo());
    }
    addEmptyCell(contentTable, (6 - pReportModel.getTheoryCourses().size()));

    for(Course sessionalCourse : pReportModel.getSessionalCourses()) {
      addInnerTable(contentTable, sessionalCourse.getNo());
    }

    addEmptyCell(contentTable, (5 - pReportModel.getSessionalCourses().size()));

    for(Course theoryCourse : pReportModel.getTheoryCourses()) {
      addInnerTable(contentTable, theoryCourse.getNo());
    }
    addEmptyCell(contentTable, (6 - pReportModel.getTheoryCourses().size()));
    addEmptyCell(contentTable, 1);
  }

  private void headerThirdLine(final PdfPTable contentTable, final TabulationReportModel pReportModel)
      throws DocumentException {
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
      addInnerTable(contentTable, "cr.", theoryCourse.getCrHr() + "");
    }
    addEmptyCell(contentTable, (6 - pReportModel.getTheoryCourses().size()));

    for(Course sessionalCourse : pReportModel.getSessionalCourses()) {
      addInnerTable(contentTable, "cr.", sessionalCourse.getCrHr() + "");
    }

    addEmptyCell(contentTable, (5 - pReportModel.getSessionalCourses().size()));

    for(Course theoryCourse : pReportModel.getTheoryCourses()) {
      addInnerTable(contentTable, "cr.", theoryCourse.getCrHr() + "");
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

  private void entryLineFirstPage(final PdfPTable contentTable, final TabulationReportModel pReportModel,
      final TabulationEntryModel entryModel) throws DocumentException {
    PdfPCell cell;
    Paragraph paragraph;
    Map<String, Double> GPA_MAP = UmsUtils.getGPAMap();

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
      addInnerTable(contentTable, entryModel.getRegularCourseList().get(theoryCourse.getId()).getGradeLetter(),
          gradePoint + "");
    }
    List<Course> optionalTheoryCourse = getOptionalCourses(pReportModel.getTheoryCourses(),
        entryModel.getRegularCourseList(), CourseType.THEORY, CourseRegType.REGULAR);
    for(Course theoryCourse : optionalTheoryCourse) {
      String gradeLetter = entryModel.getRegularCourseList().get(theoryCourse.getId()).getGradeLetter();
      double gradePoint = GPA_MAP.get(gradeLetter) * theoryCourse.getCrHr();
      addInnerTable(contentTable, theoryCourse.getNo(), theoryCourse.getCrHr() + "",
          entryModel.getRegularCourseList().get(theoryCourse.getId()).getGradeLetter(), gradePoint + "");
    }
    addEmptyCell(contentTable, (6 - (pReportModel.getTheoryCourses().size() + optionalTheoryCourse.size())));

    for(Course sessionalCourse : pReportModel.getSessionalCourses()) {
      String gradeLetter = entryModel.getRegularCourseList().get(sessionalCourse.getId()).getGradeLetter();
      double gradePoint = GPA_MAP.get(gradeLetter) * sessionalCourse.getCrHr();
      addInnerTable(contentTable, gradeLetter, gradePoint + "");
    }

    List<Course> optionalSessionalCourse = getOptionalCourses(pReportModel.getSessionalCourses(),
        entryModel.getRegularCourseList(), CourseType.SESSIONAL, CourseRegType.REGULAR);
    for(Course sessionalCourse : optionalSessionalCourse) {
      String gradeLetter = entryModel.getRegularCourseList().get(sessionalCourse.getId()).getGradeLetter();
      double gradePoint = GPA_MAP.get(gradeLetter) * sessionalCourse.getCrHr();
      addInnerTable(contentTable, sessionalCourse.getNo(), sessionalCourse.getCrHr() + "",
          entryModel.getRegularCourseList().get(sessionalCourse.getId()).getGradeLetter(), gradePoint + "");
    }
    addEmptyCell(contentTable, (5 - (pReportModel.getSessionalCourses().size() + optionalSessionalCourse.size())));

    for(Course theoryCourse : pReportModel.getTheoryCourses()) {
      if(entryModel.getClearanceCourseList().containsKey(theoryCourse.getId())) {
        String gradeLetter = entryModel.getClearanceCourseList().get(theoryCourse.getId()).getGradeLetter();
        double gradePoint = GPA_MAP.get(gradeLetter) * theoryCourse.getCrHr();
        addInnerTable(contentTable, gradeLetter, gradePoint + "");
      }
      else {
        addEmptyCell(contentTable, 1);
      }
    }

    List<Course> optionalTheoryClearanceCourse = getOptionalCourses(pReportModel.getTheoryCourses(),
        entryModel.getClearanceCourseList(), CourseType.THEORY, CourseRegType.CLEARANCE);

    for(Course theoryCourse : optionalTheoryClearanceCourse) {
      if(entryModel.getClearanceCourseList().containsKey(theoryCourse.getId())) {
        String gradeLetter = entryModel.getClearanceCourseList().get(theoryCourse.getId()).getGradeLetter();
        double gradePoint = GPA_MAP.get(gradeLetter) * theoryCourse.getCrHr();
        addInnerTable(contentTable, theoryCourse.getNo(), theoryCourse.getCrHr() + "", gradeLetter, gradePoint + "");
      }
    }

    addEmptyCell(contentTable, (6 - (pReportModel.getTheoryCourses().size() + optionalTheoryClearanceCourse.size())));

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(entryModel.getStudent().getId(), infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);
  }

  private List<Course> getOptionalCourses(List<Course> pRegularCourses,
      Map<String, UGRegistrationResult> studentCourseMap, CourseType pCourseType, CourseRegType pCourseRegType) {
    Map<String, Course> courseMap =
        pRegularCourses.stream().collect(Collectors.toMap(Course::getId, Function.identity()));
    return studentCourseMap.values().stream()
        .filter((result) -> !courseMap.containsKey(result.getCourseId()) && result.getType() == pCourseRegType)
        .map((result) -> mCourseManager.get(result.getCourseId())).collect(Collectors.toList()).stream()
        .filter((course) -> course.getCourseType() == pCourseType).collect(Collectors.toList());
  }

  private void entryLineSecondPage(final PdfPTable contentTable, final TabulationReportModel pReportModel,
      TabulationEntryModel entryModel) throws DocumentException {
    PdfPCell cell;
    Paragraph paragraph;
    Map<String, Double> GPA_MAP = UmsUtils.getGPAMap();

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
      addInnerTable(contentTable, carryCourse.getCourse().getNo(), carryCourse.getCourse().getCrHr() + "",
          gradeLetter + "", gradePoint + "");
    }
    addEmptyCell(contentTable, (4 - entryModel.getCarryCourseList().size()));
    addInnerTable(contentTable, entryModel.getPresentCompletedCrHr() + "",
        entryModel.getPresentCompletedGradePoints() + "");

    if(entryModel.getPreviousSemesterCompletedCrHr() > 0) {
      addInnerTable(contentTable, entryModel.getPreviousSemesterCompletedCrHr() + "",
          entryModel.getPreviousSemesterCompletedGradePoints() + "");
    }
    else {
      addInnerTable(contentTable, "", "");
    }

    addInnerTable(contentTable, entryModel.getCumulativeCrHr() + "", entryModel.getCumulativeGradePoints() + "");

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    String gpaString = Double.toString(entryModel.getGpa());
    paragraph = new Paragraph(gpaString.substring(0, gpaString.length() > 10 ? 10 : gpaString.length()), infoFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    String cgpaString = Double.toString(entryModel.getCgpa());
    paragraph = new Paragraph(cgpaString.substring(0, cgpaString.length() > 10 ? 10 : cgpaString.length()), infoFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
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

  private void addInnerTable(PdfPTable contentTable, String content) {
    PdfPCell cell;
    Paragraph paragraph;
    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(content, infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    contentTable.addCell(cell);
  }

  private void addInnerTable(PdfPTable contentTable, String leftPart, String rightPart) throws DocumentException {
    PdfPCell cell;
    Paragraph paragraph;
    PdfPTable table = new PdfPTable(2);
    table.setWidths(new int[] {40, 60});
    table.setWidthPercentage(100);
    // table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(leftPart, infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    table.addCell(cell);
    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(rightPart, infoFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell.addElement(paragraph);
    table.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    cell.addElement(table);
    cell.setPadding(0);
    contentTable.addCell(cell);
  }

  private void addInnerTable(PdfPTable contentTable, String cell1, String cell2, String cell3, String cell4)
      throws DocumentException {
    PdfPCell cell;
    Paragraph paragraph;
    PdfPTable table1 = new PdfPTable(2);
    table1.setWidths(new int[] {65, 35});
    table1.setWidthPercentage(100);
    // table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(cell1, innerTableFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    table1.addCell(cell);
    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(cell2, innerTableFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell.addElement(paragraph);
    table1.addCell(cell);

    PdfPTable table2 = new PdfPTable(2);
    table2.setWidths(new int[] {35, 65});
    table2.setWidthPercentage(100);
    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(cell3, innerTableFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    table2.addCell(cell);
    cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(cell4, innerTableFont);
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cell.addElement(paragraph);
    table2.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    cell.addElement(table1);
    cell.addElement(table2);
    cell.setPadding(0);
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
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("CARRY OVER", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setColspan(4);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("Present Grade Points", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("Previous Grade Points", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("Cumulative Grade Points", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("GPA", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("CGPA", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("REMARKS", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    paragraph = new Paragraph("Student No.", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setColspan(1);
    contentTable.addCell(cell);
  }

  private class HeaderFooter extends PdfPageEventHelper {
    int pagenumber;

    public void onStartPage(PdfWriter writer, Document document) {
      pagenumber++;
      try {
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(100);

        PdfPCell headerTableCell = new PdfPCell();
        headerTableCell.setBorder(Rectangle.NO_BORDER);
        headerTableCell.setPaddingLeft(0);
        headerTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerTable.addCell(headerTableCell);

        PdfPTable titleTable = new PdfPTable(1);
        titleTable.setWidthPercentage(100);
        titleTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        PdfPCell titleTableCell = new PdfPCell();
        titleTableCell.setBorder(Rectangle.NO_BORDER);
        titleTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        Paragraph paragraph = new Paragraph(Constants.University_AllCap, universityNameFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        titleTableCell.addElement(paragraph);
        titleTable.addCell(titleTableCell);

        titleTableCell = new PdfPCell();
        titleTableCell.setBorder(Rectangle.NO_BORDER);
        titleTableCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        paragraph =
            new Paragraph("Approved by the Government of Bangladesh and sponsored by Dhaka Ahsania Mission", infoFont);
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
                + " SEMESTER;   SESSION: " + mTabulationReportModel.getSemester().getName(), tableFont);
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
      ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, footer, document.right(),
          document.top() - 10, 0);
      if(writer.getPageNumber() % 2 != 0) {
        Phrase continued = new Phrase("Continued on the opposite side", pageNoFont);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, continued, document.right(),
            document.top() - 20, 0);
      }
      table.writeSelectedRows(0, -1, 36, 35, writer.getDirectContent());
    }

    private PdfPCell getGPATableCell(String pContent) {
      PdfPCell cell = new PdfPCell();
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      Paragraph paragraph = new Paragraph(pContent, infoFont);
      paragraph.setAlignment(Element.ALIGN_CENTER);
      cell.addElement(paragraph);
      return cell;
    }

    private PdfPCell getGPATableCell(String pContent, int pBorder) {
      PdfPCell cell = getGPATableCell(pContent);
      cell.setBorder(pBorder);
      cell.setPadding(0.7f);
      return cell;
    }
  }
}
