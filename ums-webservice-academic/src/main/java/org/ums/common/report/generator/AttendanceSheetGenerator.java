package org.ums.common.report.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang.UnhandledException;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.dto.ClassAttendanceDto;
import org.ums.domain.model.immutable.*;
import org.ums.manager.*;
import sun.font.FileFont;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by My Pc on 05-Nov-16.
 */

@Service
public class AttendanceSheetGenerator {

  @Autowired
  private UserManager mUserManager;
  @Autowired
  private EmployeeManager mEmployeeManager;
  @Autowired
  private DepartmentManager mDepartmentManager;
  @Autowired
  private ProgramManager mProgramManager;
  @Autowired
  private CourseManager mCourseManager;
  @Autowired
  private ClassAttendanceManager mClassAttendanceManager;

  public void createAttendanceSheetReport(OutputStream pOutputStream,
                                          final Integer pSemesterId,
                                          final String pCourseId,
                                          final String pSection,
                                          final String pStudentCategory) throws Exception, IOException, DocumentException {
    Employee employee = getEmployeeInfo();
    String deptId = employee.getDepartment().getId();
    Program program = mProgramManager
        .getAll()
        .stream()
        .filter(p->{
          try{
            return p.getDepartment().equals(employee.getDepartment());
          }catch (Exception e){
            throw new UnhandledException(e);
          }
        })
        .findAny()
        .orElse(null);
    Course course = mCourseManager.get(pCourseId);
String check="E:\\check11.png";
    String cross="E:\\cross11.png";

    java.util.List<ClassAttendanceDto> dates = mClassAttendanceManager.getDateList(pSemesterId,pCourseId);
    List<ClassAttendanceDto> studentList = mClassAttendanceManager.getStudentList(pSemesterId, pCourseId, course.getCourseType(), pSection, pStudentCategory);
    Map<String,String> attendance = mClassAttendanceManager.getAttendance(pSemesterId, pCourseId);


    Font universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN,13, Font.BOLD);
    Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN,10);
    Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN,7.3f);

    Document document = new Document();
    document.addTitle("Attendance Sheet");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document,baos);
    MyFooter event = new MyFooter();
//    writer.setPageEvent(event);
    document.open();
    /*MyHeader event = new MyHeader();
    writer.setPageEvent(event);*/
    document.setPageSize(PageSize.LEGAL.rotate());
    document.setMargins(10, 20, 10, 10);
    document.newPage();
    Paragraph paragraph = new Paragraph();


    int studentCounter=0;
    while(studentList.size()!=0){
     /* float[] tableWidth = new float[53];
      tableWidth[0]=1.3f;
      tableWidth[1]=4.0f;
      tableWidth[2]=1.0f;
      for(int i=3;i<53;i++){
        tableWidth[i]=0.5f;
      }*/


      universityNameFont = new
          Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
      infoFont = new
          Font(Font.FontFamily.TIMES_ROMAN, 10);

      Phrase header = new Phrase();
      paragraph = new
          Paragraph("AHSANULLAH UNIVERSITY OF SCIENCE AND TECHNOLOGY", universityNameFont);
        paragraph.setAlignment(Element.ALIGN_CENTER); header.add(paragraph);
      document.add(paragraph);
      paragraph = new
            Paragraph("Class Register", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
      paragraph.setAlignment(Element.ALIGN_CENTER);
      document.add(paragraph);
      paragraph = new Paragraph("Name of the Course Teacher :" +
            employee.getEmployeeName(), infoFont); paragraph.setAlignment(Element.ALIGN_LEFT);
      document.add(paragraph);
//      String programShortName = "";
//      try { programShortName = program.getShortName(); }
//      catch(Exception e) { throw new UnhandledException(e); }
      paragraph = new Paragraph("PROGRAMME:  , STUDENT'S YEAR: " + course.getYear() + " , SEMESTER: " +
            course.getSemester() + " ,COURSE NO: " + course.getNo() + " , COURSE TITLE: " +
            course.getTitle(), infoFont);
      header.add(paragraph);
      document.add(paragraph);
      document.add(new Paragraph(" "));
      PdfPTable table = new PdfPTable(53);
      table.setWidthPercentage(100);

      //top header of the table
      PdfPCell headerCell = new PdfPCell();
      paragraph = new Paragraph("Student No", tableFont);
      headerCell.addElement(paragraph);
      headerCell.setColspan(2);
      headerCell.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(headerCell);

      headerCell = new PdfPCell();
      paragraph = new Paragraph("Name of the Students", tableFont);
      headerCell.addElement(paragraph);
      headerCell.setColspan(6);
      headerCell.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(headerCell);


      int dataCounter=1;

      List<ClassAttendanceDto> dateTmp = new ArrayList<>(dates);
      while(dataCounter<=45){

        if(dateTmp.size()!=0 ){
          headerCell = new PdfPCell();
          ClassAttendanceDto classAttendanceDto = dateTmp.get(0);
          paragraph = new Paragraph(classAttendanceDto.getClassDate(),tableFont);
          headerCell.addElement(paragraph);
          headerCell.setRotation(90);
          dateTmp.remove(0);
          table.addCell(headerCell);
        }else {
          headerCell = new PdfPCell();
          paragraph = new Paragraph(" ", tableFont);
          headerCell.addElement(paragraph);
          paragraph=new Paragraph(" ");
          headerCell.addElement(paragraph);
          paragraph=new Paragraph(" ");
          headerCell.addElement(paragraph);
          table.addCell(headerCell);
        }


        dataCounter+=1;
      }



      headerCell = new PdfPCell();
      paragraph = new Paragraph(" ");
      headerCell.addElement(paragraph);
      headerCell.setColspan(2);
      table.addCell(headerCell);

      headerCell = new PdfPCell();
      paragraph = new Paragraph(" ");
      headerCell.addElement(paragraph);
      headerCell.setColspan(6);
      table.addCell(headerCell);

      for(int i=1;i<=45;i++){
        headerCell = new PdfPCell(new Paragraph(""+i, tableFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(headerCell);
      }


      while(studentCounter<=24 && studentList.size()!=0){
        ClassAttendanceDto attendanceDto = studentList.get(0);

        headerCell = new PdfPCell();
        paragraph = new Paragraph(attendanceDto.getStudentId(),tableFont);
        headerCell.addElement(paragraph);
        headerCell.setColspan(2);
        table.addCell(headerCell);

        headerCell = new PdfPCell();
        paragraph = new Paragraph(attendanceDto.getStudentName(),tableFont);
        headerCell.addElement(paragraph);
        headerCell.setColspan(6);
        table.addCell(headerCell);

        dateTmp = new ArrayList<>(dates);
        for(int i=1;i<=45;i++){
          if(dateTmp.size()!=0){
            ClassAttendanceDto tmpDate = dateTmp.get(0);
            String key = tmpDate.getClassDateFormat1()+ tmpDate.getSerial()+attendanceDto.getStudentId();
            headerCell = new PdfPCell();

            dateTmp.remove(0);
            String aaa="";
            if(attendance.get(key)!=null){
              aaa=attendance.get(key);
              if(aaa.equals("1"))
              aaa=check;
              else
                aaa=cross;
            }
            headerCell = new PdfPCell(new Paragraph(aaa, tableFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

//            table.addCell(headerCell);
            table.addCell(createImageCell(aaa));
          }else{
            headerCell = new PdfPCell();
            paragraph = new Paragraph("");
            headerCell.addElement(paragraph);
            table.addCell(headerCell);
          }

        }
        studentList.remove(0);
        studentCounter+=1;
      }


      if(studentCounter>24 && studentList.size()!=0){
        document.add(table);
        document.newPage();
        studentCounter=0;
      }else{
        document.add(table);
        break;
      }


    }


    document.close();

    baos.writeTo(pOutputStream);

  }

  public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
    Image img = Image.getInstance(path);
    img.scaleAbsolute(12f, 12f);
    PdfPCell cell = new PdfPCell(img, false);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    return cell;
  }

  private Employee getEmployeeInfo() throws Exception {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    Employee employee = mEmployeeManager.getByEmployeeId(user.getEmployeeId());
    return employee;
  }

  class MyFooter extends PdfPageEventHelper {
    Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
      PdfContentByte cb = writer.getDirectContent();
      Phrase header = new Phrase("this is a header", ffont);
      ColumnText
          .showTextAligned(cb, Element.ALIGN_CENTER, header, (document.right() - document.left())
              / 2 + document.leftMargin(), document.top() + 100, 0);
    }
    /*
     * @Override public void onEndPage(PdfWriter writer, Document document) { PdfPTable table = new
     * PdfPTable(1); try { table.setWidths(new int[] {1});
     * table.getDefaultCell().setFixedHeight(20);
     * table.getDefaultCell().setBorder(Rectangle.BOTTOM);
     * table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT); table.addCell(new
     * Phrase(String.format("Page %d of", writer.getPageNumber()), ffont)); table.completeRow();
     * PdfContentByte canvas = writer.getDirectContent();
     * canvas.beginMarkedContentSequence(PdfName.ARTIFACT); table.writeSelectedRows(0, -1, 36, 30,
     * canvas); canvas.endMarkedContentSequence(); } catch(DocumentException de) { throw new
     * ExceptionConverter(de); } // Phrase footer = new Phrase("this is a footer", ffont); // //
     * ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer, // (document.right() -
     * document.left()) / 2 + document.leftMargin(), document.bottom() - 10, // 0); }
     */
  }

}
