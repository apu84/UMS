package org.ums.common.report.generator;

import java.io.*;
import java.util.*;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Semester;
import org.ums.enums.CourseType;
import org.ums.manager.CourseManager;
import org.ums.manager.ExamGradeManager;
import org.ums.manager.SemesterManager;

import javax.ws.rs.PathParam;

/**
 * Created by Ifti on 07-Jun-16.
 */
//http://developers.itextpdf.com/examples/itext-action-second-edition/chapter-4
@Component
public class UgGradeSheetGenerator {

  @Autowired
  private ExamGradeManager examGradeManager;
  @Autowired
  private CourseManager courseManager;
  @Autowired
  private SemesterManager semesterManager;


  /**
   * Creates a PDF document.
   * @throws    DocumentException
   * @throws    IOException
   */
  public void createPdf(Integer semesterId,String courseId,Integer examTypeId,String userRole,OutputStream outputStream)
      throws DocumentException, IOException,Exception {

    //Validation goes here
    //Check
    // step 1
    Document document = new Document();
    document.setMargins(10,10,30,72);
// step 2
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos); // FileOutputStream(RESULT)

    HeaderFooter event = new HeaderFooter();
    writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
    writer.setPageEvent(event);

    // step 3

    Course course=courseManager.get(courseId);
    Semester semester=semesterManager.get(semesterId);
    List<StudentGradeDto> gradeList=examGradeManager.getAllGrades(semesterId, courseId, examTypeId, course.getCourseType());

    document.open();
    for (int i=0;i<(gradeList.size()/90);i++) {
      if(i!=0)
        document.newPage();
      PdfPTable mainTable=getMainTable();
      PdfPCell cell = new PdfPCell(getSubTableHeader(course,gradeList.size(),semester.getName()));
      cell.setPadding(0);
      mainTable.addCell(cell);

      cell = new PdfPCell(new Paragraph(""));
      cell.setBorder(Rectangle.NO_BORDER);
      mainTable.addCell(cell);

      cell = new PdfPCell(getSubTableHeader(course,gradeList.size(),semester.getName()));
      cell.setPadding(0);
      mainTable.addCell(cell);

      cell = new PdfPCell();
      cell.addElement(getGradeTable(gradeList,i*90,45,course.getCourseType()));
      cell.setPadding(0);
      cell.setBorder(Rectangle.NO_BORDER);
      mainTable.addCell(cell);

      cell = new PdfPCell(new Paragraph(""));
      cell.setBorder(Rectangle.NO_BORDER);
      mainTable.addCell(cell);

      cell = new PdfPCell();
      cell.addElement(getGradeTable(gradeList,i*90+46,45,course.getCourseType()));
      cell.setPadding(0);
      cell.setBorder(Rectangle.NO_BORDER);
      mainTable.addCell(cell);

      document.add(mainTable);


    }
    // step 4

    // step 5
    document.close();
    baos.writeTo(outputStream);
  }
  public static PdfPTable getMainTable() throws DocumentException{
    // a table with three columns
    PdfPTable table = new PdfPTable(3);
    table.setWidths(new int[]{9, 2, 9});
    table.setWidthPercentage(100);
    // cell 1: location and time

    return table;
  }

  public PdfPTable getGradeTable(java.util.List<StudentGradeDto> studentList,int startIndex,int total,CourseType courseType) throws DocumentException {
    // a table with three columns
    if(startIndex>studentList.size())
      return null;

    StudentGradeDto student;

    Font fontAUST = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);
    Font gradeSheetHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    Font nFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
    Font nuFont = new Font(Font.FontFamily.HELVETICA, 8, Font.UNDERLINE);
    Font nbFont = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

    PdfPTable table=null;

    if(courseType==CourseType.THEORY) {
      table = new PdfPTable(6);
      table.setWidths(new int[]{2, 1,1,1,1,1});
    }
    else if(courseType==CourseType.SESSIONAL) {
      table = new PdfPTable(3);
      table.setWidths(new int[]{2, 1,1});
    }


    table.setWidthPercentage(100);


    Paragraph p = new Paragraph("Student No.", nbFont);
    PdfPCell cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    if(courseType==CourseType.THEORY) {
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

  for(int i=startIndex;i<startIndex+total+1;i++){
    if(i>=studentList.size())
      break;

    student=studentList.get(i);

    p = new Paragraph(studentList.get(i).getStudentId(), nbFont);
    cell = new PdfPCell(p);
    cell.setFixedHeight(12);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    if(courseType==CourseType.THEORY) {
      p = new Paragraph(String.valueOf(student.getQuiz()), nFont);
      cell = new PdfPCell(p);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      p = new Paragraph(String.valueOf(student.getClassPerformance()), nFont);
      cell = new PdfPCell(p);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      table.addCell(cell);

      p = new Paragraph(String.valueOf(student.getPartA()+student.getPartB()), nFont);
      cell = new PdfPCell(p);
      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    }
    table.addCell(cell);

    p = new Paragraph(String.valueOf(student.getTotal()), nFont);
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
  public PdfPTable getSubTableHeader(Course course,int totalStudents,String semesterName) throws Exception{
    // a table with three columns
    Font fontAUST = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);
    Font gradeSheetHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    Font nFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
    Font nuFont = new Font(Font.FontFamily.HELVETICA, 8, Font.UNDERLINE);

    PdfPTable table = new PdfPTable(3);
    table.setWidths(new int[]{7, 5, 8});
    table.setWidthPercentage(100);

    PdfPCell pCell=new PdfPCell();
    pCell.setBorder(Rectangle.NO_BORDER);
    pCell.setColspan(3);
    pCell.setHorizontalAlignment(Element.ALIGN_CENTER);

    PdfPCell pCellB=new PdfPCell();
    pCellB.setColspan(3);
    pCellB.setHorizontalAlignment(Element.ALIGN_CENTER);

    Paragraph p=new Paragraph("AHSANULLAH UNIVERSITY OF SCIENCE AND TECHNOLOGY", fontAUST);
    PdfPCell cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p=new Paragraph("Marks/Grade Sheet ("+semesterName+")", gradeSheetHeader);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p=new Paragraph(course.getCourseType()==CourseType.THEORY?"Theoretical":"Sessional", gradeSheetHeader);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    p=new Paragraph("Examination : Final", nFont);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);


    Paragraph parag1=new Paragraph("Department : ",nFont);//This gonna be bold font
    Paragraph parag2=new Paragraph(course.getOfferedBy().getShortName(), nuFont); //This gonna be normal font
    Paragraph comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell);

    parag1=new Paragraph("Year : ",nFont);//This gonna be bold font
    parag2=new Paragraph(course.getYear()+"", nuFont); //This gonna be normal font
    comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1=new Paragraph("Semester : ",nFont);//This gonna be bold font
    parag2=new Paragraph(course.getSemester()+"", nuFont); //This gonna be normal font
    comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(2);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    table.addCell(cell);

    parag1=new Paragraph("Course No : ",nFont);//This gonna be bold font
    parag2=new Paragraph(course.getNo(), nuFont); //This gonna be normal font
    comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1=new Paragraph("Course Title : ",nFont);//This gonna be bold font
    parag2=new Paragraph(course.getTitle(), nuFont); //This gonna be normal font
    comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(2);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    table.addCell(cell);

    p=new Paragraph("(The number of students registered for the course is - "+totalStudents+")", nFont);
    cell = new PdfPCell(p);
    cell.setColspan(3);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1=new Paragraph("Credit Hour : ",nFont);//This gonna be bold font
    parag2=new Paragraph(course.getCrHr()+"", nuFont); //This gonna be normal font
    comb=new Paragraph();
    comb.add(parag1);
    comb.add(parag2);
    cell = new PdfPCell(comb);
    cell.setColspan(2);
    cell.setFixedHeight(14);
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(cell);

    parag1=new Paragraph("Full Marks : ",nFont);//This gonna be bold font
    parag2=new Paragraph("100 ", nuFont); //This gonna be normal font
    comb=new Paragraph();
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
  /** Inner class to add a header and a footer. */
  class HeaderFooter extends PdfPageEventHelper {
    int pagenumber;
    public void onStartPage(PdfWriter writer,Document document) {
      pagenumber++;
    }
    public void onEndPage(PdfWriter writer, Document document)  {
//      Rectangle rect = writer.getBoxSize("art");
//      ColumnText.showTextAligned(writer.getDirectContent(),
//          Element.ALIGN_CENTER, new Phrase(String.format("page %d", pagenumber)),
//          (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
      Font nFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);

try {
  PdfPTable table = new PdfPTable(3);
  table.setWidths(new int[]{10,10,10});
  //table.setWidthPercentage(80);
  table.setTotalWidth(523);

  Paragraph p=new Paragraph("_________________\n\nSignature of the\nScrutinizer\n\nDate ___________", nFont);
  PdfPCell pCell = new PdfPCell(p);
  pCell.setBorder(Rectangle.NO_BORDER);
  pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
  pCell.setPaddingLeft(20);
  table.addCell(pCell);

  p=new Paragraph("_________________\n\nSignature of the \nHead of the Dept./School\n\nDate ___________", nFont);
  pCell = new PdfPCell(p);
  pCell.setBorder(Rectangle.NO_BORDER);
  pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
  pCell.setPaddingLeft(60);
  table.addCell(pCell);

  p=new Paragraph("_________________\n\nSignature of the \nExaminer\n\nDate ___________", nFont);
  pCell = new PdfPCell(p);
  pCell.setBorder(Rectangle.NO_BORDER);
  pCell.setHorizontalAlignment(Element.ALIGN_LEFT);
  pCell.setPaddingLeft(80);
  table.addCell(pCell);

  table.writeSelectedRows(0, -1, 36, 64, writer.getDirectContent());


}
catch (Exception ex){
  ex.printStackTrace();
}
    }
  }

}
