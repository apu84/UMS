package org.ums.report.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.enums.CourseRegType;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.manager.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Ifti on 07-Jun-16.
 */
// http://developers.itextpdf.com/examples/itext-action-second-edition/chapter-4
@Component
public class UgGradeSheetGenerator {

  @Autowired
  private ExamGradeManager examGradeManager;
  @Autowired
  private CourseManager courseManager;
  @Autowired
  private SemesterManager semesterManager;
  @Autowired
  private ProgramManager programManager;

  private CourseRegType prevCourseRegType = CourseRegType.REGULAR;

  /**
   * Creates a PDF document.
   * 
   * @throws DocumentException
   * @throws IOException
   */
  public void createPdf(Integer semesterId, String courseId, ExamType examType, String userRole,
      OutputStream outputStream) throws DocumentException, IOException, Exception {

    // Validation goes here
    // Check
    // step 1
    Document document = new Document();
    document.setMargins(60, 10, 30, 72);
    // step 2
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos); // FileOutputStream(RESULT)

    HeaderFooter event = new HeaderFooter();
    writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
    writer.setPageEvent(event);

    // step 3

    Course course = courseManager.get(courseId);
    Semester semester = semesterManager.get(semesterId);
    Integer offeredToProgramId = courseManager.getOfferedToProgram(semesterId, courseId);
    Program offeredToProgram = programManager.get(offeredToProgramId);
    String offeredToProgramName = offeredToProgram.getShortName();
    List<StudentGradeDto> gradeList =
        examGradeManager.getAllGrades(semesterId, courseId, examType, course.getCourseType());

    document.open();
    double totalPage = Math.ceil(Float.valueOf(gradeList.size()) / 80);
    for(int i = 0; i < totalPage || (gradeList.size() < 80 && i == 0); i++) {
      if(i != 0)
        document.newPage();

      PdfPTable firstColumnGradeTable = getGradeTable(gradeList, i * 80, 40, course.getCourseType(), examType);
      PdfPTable secondColumnGradeTable = getGradeTable(gradeList, i * 80 + 40, 40, course.getCourseType(), examType);

      PdfPTable mainTable = getMainTable(secondColumnGradeTable == null ? 1 : 3);
      PdfPCell cell =
          new PdfPCell(getSubTableHeader(course, gradeList.size(), semester.getName(), offeredToProgram, examType));
      cell.setPadding(0);
      mainTable.addCell(cell);

      cell = new PdfPCell(new Paragraph(""));
      cell.setBorder(Rectangle.NO_BORDER);
      mainTable.addCell(cell);

      if(secondColumnGradeTable != null) {
        cell =
            new PdfPCell(getSubTableHeader(course, gradeList.size(), semester.getName(), offeredToProgram, examType));
        cell.setPadding(0);
        mainTable.addCell(cell);
      }

      cell = new PdfPCell();
      cell.addElement(firstColumnGradeTable);
      cell.setPadding(0);
      cell.setBorder(Rectangle.NO_BORDER);
      mainTable.addCell(cell);

      if(secondColumnGradeTable != null) {

        cell = new PdfPCell(new Paragraph(""));
        cell.setBorder(Rectangle.NO_BORDER);
        mainTable.addCell(cell);

        cell = new PdfPCell();
        cell.addElement(secondColumnGradeTable);
        cell.setPadding(0);
        cell.setBorder(Rectangle.NO_BORDER);
        mainTable.addCell(cell);
      }

      document.add(mainTable);

    }
    // step 4

    // step 5
    document.close();
    baos.writeTo(outputStream);
  }

  public static PdfPTable getMainTable(int totalGradeColumn) throws DocumentException {
    // a table with three columns
    PdfPTable table = new PdfPTable(totalGradeColumn);
    if(totalGradeColumn == 3) {
      table.setWidths(new int[] {9, 1, 9});
      table.setWidthPercentage(100);
    }
    else {
      table.setWidths(new int[] {1});
      table.setWidthPercentage(45);
    }

    // cell 1: location and times

    return table;
  }

  public PdfPTable getGradeTable(java.util.List<StudentGradeDto> studentList, int startIndex, int total,
      CourseType courseType, ExamType examType) throws DocumentException {

    NumberFormat nf = new DecimalFormat("##.#");
    // a table with three columns
    if(startIndex > studentList.size())
      return null;

    StudentGradeDto student;

    Font fontAUST = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);
    Font gradeSheetHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    Font nFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
    Font nuFont = new Font(Font.FontFamily.HELVETICA, 8, Font.UNDERLINE);
    Font nbFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

    PdfPTable table = null;

    if(courseType == CourseType.THEORY) {
      table = new PdfPTable(6);
      table.setWidths(new float[] {new Float(1.5), 1, 1, 1, 1, 1});
    }
    else if(courseType == CourseType.SESSIONAL) {
      table = new PdfPTable(3);
      table.setWidths(new int[] {2, 1, 1});
    }

    table.setWidthPercentage(100);

    Paragraph p = new Paragraph("Student No.", nbFont);
    PdfPCell cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    if(courseType == CourseType.THEORY) {
      p = new Paragraph("Quiz\n(20).", nbFont);
      cell = new PdfPCell(p);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      p = new Paragraph("Class \nPerf.\n(10).", nbFont);
      cell = new PdfPCell(p);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      p = new Paragraph("Final\n(70)", nbFont);
      cell = new PdfPCell(p);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);
    }

    p = new Paragraph("Total\n(100)", nbFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph("Letter\nGrade", nbFont);
    cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    int endIndex = startIndex + total;
    for(int i = startIndex; i < endIndex; i++) {
      if(i >= studentList.size())
        break;

      student = studentList.get(i);

      if(prevCourseRegType != null && prevCourseRegType != student.getRegType()
          && examType.getId() != ExamType.SEMESTER_FINAL.getId()) {
        p = new Paragraph(student.getRegType().getLabel(), gradeSheetHeader);
        cell = new PdfPCell(p);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(6);
        cell.setFixedHeight(16);
        table.addCell(cell);

      }
      prevCourseRegType = student.getRegType();

      p = new Paragraph(studentList.get(i).getStudentId(), nbFont);
      cell = new PdfPCell(p);
      cell.setFixedHeight(new Float(13.5));
      cell.setHorizontalAlignment(Element.ALIGN_LEFT);
      table.addCell(cell);
      if(courseType == CourseType.THEORY) {
        p = new Paragraph(student.getQuiz() == null ? "" : String.valueOf(nf.format(student.getQuiz())), nFont);
        cell = new PdfPCell(p);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        p =
            new Paragraph(student.getClassPerformance() == null ? "" : String.valueOf(nf.format(student
                .getClassPerformance())), nFont);
        cell = new PdfPCell(p);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        BigDecimal tmpTotal = null;

        if(student.getPartA() != null && student.getPartB() != null) {
          tmpTotal =
              (student.getPartA() == null ? new BigDecimal(0) : BigDecimal.valueOf(student.getPartA()).setScale(1,
                  RoundingMode.HALF_UP)).add(student.getPartB() == null ? new BigDecimal(0) : BigDecimal.valueOf(
                  student.getPartB()).setScale(1, RoundingMode.HALF_UP));
        }
        else {
          tmpTotal =
              student.getPartA() == null ? null : BigDecimal.valueOf(student.getPartA()).setScale(1,
                  RoundingMode.HALF_UP);
        }

        String finalTotalMarks = "";

        if((student.getPartAAddiInfo() != null && (student.getPartAAddiInfo().equals("Abs") || student
            .getPartAAddiInfo().equals("Rep")))
            || (student.getPartBAddiInfo() != null && (student.getPartBAddiInfo().equals("Abs") || student
                .getPartBAddiInfo().equals("Rep")))) {
          finalTotalMarks = student.getPartAAddiInfo();
        }
        else {
          finalTotalMarks = String.valueOf(tmpTotal == null ? "" : String.valueOf(nf.format(tmpTotal)));
        }

        p = new Paragraph(finalTotalMarks, nFont);
        cell = new PdfPCell(p);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
      }

      p = new Paragraph(student.getTotal() == null ? "" : String.valueOf(Math.round(student.getTotal())), nFont);
      cell = new PdfPCell(p);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      p = new Paragraph(student.getGradeLetter(), nFont);
      cell = new PdfPCell(p);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);
    }
    return table;
  }

  public PdfPTable getSubTableHeader(Course course, int totalStudents, String semesterName, Program program,
      ExamType examType) throws DocumentException {
    // a table with three columns
    Font fontAUST = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);
    Font gradeSheetHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    Font nFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
    Font nuFont = new Font(Font.FontFamily.HELVETICA, 8, Font.UNDERLINE);

    PdfPTable table = new PdfPTable(3);
    table.setWidths(new int[] {7, 3, 7});
    table.setWidthPercentage(100);

    PdfPCell pCell = new PdfPCell();
    pCell.setBorder(Rectangle.NO_BORDER);
    pCell.setColspan(3);
    pCell.setHorizontalAlignment(Element.ALIGN_CENTER);

    PdfPCell pCellB = new PdfPCell();
    pCellB.setColspan(3);
    pCellB.setHorizontalAlignment(Element.ALIGN_CENTER);

    Paragraph p = new Paragraph("AHSANULLAH UNIVERSITY OF SCIENCE AND TECHNOLOGY", fontAUST);
    PdfPCell cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph("Marks/Grade Sheet (" + semesterName + ")", gradeSheetHeader);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p = new Paragraph(course.getCourseType() == CourseType.THEORY ? "Theoretical" : "Sessional", gradeSheetHeader);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p =
        new Paragraph("Examination : "
            + (examType == ExamType.SEMESTER_FINAL ? "Final" : "Improvement/Clearance/Carryover"), nFont);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    Paragraph parag1 = new Paragraph("Department : ", nFont);// This gonna be bold font
    Paragraph parag2 = new Paragraph(program.getDepartment().getShortName(), nuFont);
    Paragraph parag3 = new Paragraph(",  Program : ", nFont);// This gonna be bold font
    Paragraph parag4 = new Paragraph(program.getShortName(), nuFont);

    Paragraph comb = new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    comb.add(parag3);
    comb.add(parag4);
    cell = new PdfPCell(comb);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    parag1 = new Paragraph("Year : ", nFont);// This gonna be bold font
    parag2 = new Paragraph(course.getYear() + "", nuFont); // This gonna be normal font
    comb = new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1 = new Paragraph("Semester : ", nFont);// This gonna be bold font
    parag2 = new Paragraph(course.getSemester() + "", nuFont); // This gonna be normal font
    comb = new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(2);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    table.addCell(cell);

    parag1 = new Paragraph("Course No : ", nFont);// This gonna be bold font
    parag2 = new Paragraph(course.getNo(), nuFont); // This gonna be normal font
    comb = new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1 = new Paragraph("Course Title : ", nFont);// This gonna be bold font
    parag2 = new Paragraph(course.getTitle(), nuFont); // This gonna be normal font
    comb = new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    p = new Paragraph("(The number of students registered for the course is - " + totalStudents + ")", nFont);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1 = new Paragraph("Credit Hour : ", nFont);// This gonna be bold font
    parag2 = new Paragraph(course.getCrHr() + "", nuFont); // This gonna be normal font
    comb = new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(2);
    cell.setFixedHeight(14);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1 = new Paragraph("Full Marks : ", nFont);// This gonna be bold font
    parag2 = new Paragraph("100 ", nuFont); // This gonna be normal font
    comb = new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(2);
    cell.setFixedHeight(14);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    table.addCell(cell);
    return table;
  }

  /**
   * Inner class to add a header and a footer.
   */
  class HeaderFooter extends PdfPageEventHelper {
    int pagenumber;

    public void onStartPage(PdfWriter writer, Document document) {
      pagenumber++;
    }

    public void onEndPage(PdfWriter writer, Document document) {
      // Rectangle rect = writer.getBoxSize("art");
      // ColumnText.showTextAligned(writer.getDirectContent(),
      // Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),
      // (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
      Font nFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);

      try {
        PdfPTable table = new PdfPTable(3);
        table.setWidths(new int[] {10, 10, 10});
        // table.setWidthPercentage(80);
        table.setTotalWidth(523);

        Paragraph p = new Paragraph("___________________\n\nSignature of the\nScrutinizer\n\nDate ___________", nFont);
        PdfPCell pCell = new PdfPCell(p);
        pCell.setBorder(Rectangle.NO_BORDER);
        pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        pCell.setPaddingLeft(20);
        table.addCell(pCell);

        p =
            new Paragraph("___________________\n\nSignature of the \nHead of the Dept./School\n\nDate ___________",
                nFont);
        pCell = new PdfPCell(p);
        pCell.setBorder(Rectangle.NO_BORDER);
        pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        pCell.setPaddingLeft(60);
        table.addCell(pCell);

        p = new Paragraph("___________________\n\nSignature of the \nExaminer\n\nDate ___________", nFont);
        pCell = new PdfPCell(p);
        pCell.setBorder(Rectangle.NO_BORDER);
        pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        pCell.setPaddingLeft(80);
        table.addCell(pCell);

        table.writeSelectedRows(0, -1, 36, 65, writer.getDirectContent());

      } catch(Exception ex) {
        ex.printStackTrace();
      }
    }
  }

}
