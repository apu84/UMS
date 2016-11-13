package org.ums.common.report.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.dto.ClassAttendanceDto;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.User;
import org.ums.manager.*;
import org.ums.util.Constants;
import org.ums.util.ReportUtils;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  public void createAttendanceSheetReport(OutputStream pOutputStream, final Integer pSemesterId,
      final String pCourseId, final String pSection, final String pStudentCategory)
      throws Exception, IOException, DocumentException {
    Employee employee = getEmployeeInfo();
    Course course = mCourseManager.get(pCourseId);
    String check = "E://check11.png";
    String cross = "E://cross11.png";

    List<ClassAttendanceDto> dates = mClassAttendanceManager.getDateList(pSemesterId, pCourseId);
    List<ClassAttendanceDto> studentList =
        mClassAttendanceManager.getStudentList(pSemesterId, pCourseId, course.getCourseType(),
            pSection, pStudentCategory);
    Map<String, String> attendance = mClassAttendanceManager.getAttendance(pSemesterId, pCourseId);

    Font universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
    Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    Font infoFontUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.UNDERLINE);
    Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN, 8.5f);

    Document document = new Document();
    document.addTitle("Attendance Sheet");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    document.setPageSize(PageSize.LEGAL.rotate());
    Paragraph paragraph = new Paragraph();
    document.setMargins(10, 20, 100, 10);
    // Left, Right, Top, Bottom
    // /
    PdfPTable headerTable = new PdfPTable(1);
    Chunk chunk = null;
    headerTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
    PdfPCell cell = new PdfPCell();
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

    headerTable.setWidthPercentage(100);
    headerTable.setTotalWidth(document.getPageSize().getWidth() - 30);
    universityNameFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
    infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

    cell = ReportUtils.getCell(ReportUtils.B0_P0);
    paragraph = new Paragraph(Constants.University_AllCap, universityNameFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    headerTable.addCell(cell);

    cell = ReportUtils.getCell(ReportUtils.B0_P0);
    paragraph = new Paragraph("Class Register", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    headerTable.addCell(cell);

    cell = ReportUtils.getCell(ReportUtils.B0_P0);
    paragraph = new Paragraph("Section : ", infoFont);
    chunk = ReportUtils.getChunk(pSection, infoFontUnderline, ReportUtils.LR3S);
    paragraph.add(chunk);
    chunk = new Chunk(", Course Teacher(s) :", infoFont);
    // +pSection+", Course Teacher(s) :" + employee.getEmployeeName()
    paragraph.add(chunk);
    cell.addElement(paragraph);
    cell.setPadding(0f);
    headerTable.addCell(cell);

    cell = ReportUtils.getCell(ReportUtils.B0_P0);
    paragraph = new Paragraph("Course No: ", infoFont);
    chunk = ReportUtils.getChunk(course.getNo(), infoFontUnderline, ReportUtils.LR3S);
    paragraph.add(chunk);
    chunk = new Chunk(", Course Title :", infoFont);
    paragraph.add(chunk);
    chunk = ReportUtils.getChunk(course.getTitle(), infoFontUnderline, ReportUtils.LR3S);
    paragraph.add(chunk);
    chunk = new Chunk(", Year :", infoFont);
    paragraph.add(chunk);
    chunk = ReportUtils.getChunk(course.getYear() + "", infoFontUnderline, ReportUtils.LR3S);
    paragraph.add(chunk);
    chunk = new Chunk(", Semester :", infoFont);
    paragraph.add(chunk);
    chunk = ReportUtils.getChunk(course.getSemester() + "", infoFontUnderline, ReportUtils.LR3S);
    paragraph.add(chunk);
    cell.addElement(paragraph);
    headerTable.addCell(cell);

    // String programShortName = "";
    // try { programShortName = program.getShortName(); }
    // catch(Exception e) { throw new UnhandledException(e); }

    // /
    ClassAttendanceHeaderFooter event = new ClassAttendanceHeaderFooter(headerTable);
    writer.setPageEvent(event);
    document.open();

    PdfPTable table = new PdfPTable(54);
    table.setWidthPercentage(100);
    table.setHeaderRows(2);
    int studentCounter = 0;
    while(studentList.size() != 0) {
      // top header of the table
      PdfPCell headerCell = new PdfPCell();
      paragraph = new Paragraph("Student No", tableFont);
      headerCell.addElement(paragraph);
      headerCell.setColspan(3);
      headerCell.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(headerCell);

      headerCell = new PdfPCell();
      paragraph = new Paragraph("Name of the Students", tableFont);
      headerCell.addElement(paragraph);
      headerCell.setColspan(6);
      headerCell.setVerticalAlignment(Element.ALIGN_TOP);
      table.addCell(headerCell);

      int dataCounter = 1;

      List<ClassAttendanceDto> dateTmp = new ArrayList<>(dates);
      while(dataCounter <= 45) {

        if(dateTmp.size() != 0) {
          headerCell = new PdfPCell();
          ClassAttendanceDto classAttendanceDto = dateTmp.get(0);
          paragraph = new Paragraph(classAttendanceDto.getClassDate(), tableFont);
          headerCell.addElement(paragraph);
          headerCell.setRotation(90);
          dateTmp.remove(0);
          table.addCell(headerCell);
        }
        else {
          headerCell = new PdfPCell();
          paragraph = new Paragraph(" ", tableFont);
          headerCell.addElement(paragraph);
          paragraph = new Paragraph(" ");
          headerCell.addElement(paragraph);
          paragraph = new Paragraph(" ");
          headerCell.addElement(paragraph);
          table.addCell(headerCell);
        }

        dataCounter += 1;
      }

      headerCell = new PdfPCell();
      paragraph = new Paragraph(" ");
      headerCell.addElement(paragraph);
      headerCell.setColspan(3);
      table.addCell(headerCell);

      headerCell = new PdfPCell();
      paragraph = new Paragraph(" ");
      headerCell.addElement(paragraph);
      headerCell.setColspan(6);
      table.addCell(headerCell);

      for(int i = 1; i <= 45; i++) {
        headerCell = new PdfPCell(new Paragraph("" + i, tableFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(headerCell);
      }

      while(studentList.size() != 0) {
        ClassAttendanceDto attendanceDto = studentList.get(0);

        headerCell = new PdfPCell(new Paragraph(attendanceDto.getStudentId(), tableFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerCell.setColspan(3);
        table.addCell(headerCell);

        headerCell = new PdfPCell(new Paragraph(attendanceDto.getStudentName(), tableFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerCell.setColspan(6);
        table.addCell(headerCell);

        dateTmp = new ArrayList<>(dates);
        for(int i = 1; i <= 45; i++) {
          if(dateTmp.size() != 0) {
            ClassAttendanceDto tmpDate = dateTmp.get(0);
            String key =
                tmpDate.getClassDateFormat1() + tmpDate.getSerial() + attendanceDto.getStudentId();
            headerCell = new PdfPCell();

            dateTmp.remove(0);
            String aaa = "";
            if(attendance.get(key) != null) {
              aaa = attendance.get(key);
              if(aaa.equals("1"))
                aaa = check;
              else
                aaa = cross;
            }
            headerCell = new PdfPCell(new Paragraph(aaa, tableFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            // table.addCell(headerCell);
            table.addCell(createImageCell(aaa));
          }
          else {
            headerCell = new PdfPCell();
            paragraph = new Paragraph("");
            headerCell.addElement(paragraph);
            table.addCell(headerCell);
          }

        }
        studentList.remove(0);
        studentCounter += 1;
      }

      if(studentCounter > 24 && studentList.size() != 0) {
        document.add(table);
        document.newPage();
        studentCounter = 0;
      }
      else {
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

  class ClassAttendanceHeaderFooter extends PdfPageEventHelper {

    protected PdfPTable header;

    public ClassAttendanceHeaderFooter(PdfPTable header) {
      this.header = header;
    }

    Font ffont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);

    @Override
    public void onStartPage(PdfWriter writer, Document document) {

      header.writeSelectedRows(0, -1, 10, document.top() + 85, writer.getDirectContent());
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
