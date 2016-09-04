package org.ums.common.report.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.ByteArray;
import org.apache.commons.lang.UnhandledException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.AppSetting;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Teacher;
import org.ums.manager.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by My Pc on 30-Aug-16.
 */
@Service
public class ClassRoutineGeneratorImpl implements ClassRoutineGenerator {

  @Autowired
  TeacherManager mTeacherManager;

  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  DepartmentManager mDepartmentManager;

  @Autowired
  ProgramManager mProgramManager;

  @Autowired
  RoutineManager mRoutineManager;

  @Autowired
  AppSettingManager mAppSettingManager;

  @Override
  public void createClassRoutineStudentReport(OutputStream pOutputStream) throws Exception, IOException, DocumentException {

  }

  @Override
  public void createClassRoutineTeacherReport(OutputStream pOutputStream) throws Exception, IOException, DocumentException {
    String teacherId= SecurityUtils.getSecurityManager().toString();
    Teacher teacher = mTeacherManager.get(teacherId);

    Department department = mDepartmentManager.get(teacher.getDepartmentId());

    List<Semester> semesterList = mSemesterManager.getAll().stream()
                                                    .filter(pSemester -> {
                                                      try{
                                                        return pSemester.getStatus().getValue()==1;
                                                      }catch(Exception e){
                                                        throw new UnhandledException(e);
                                                      }
                                                    }).collect(Collectors.toList());

    Semester semester = semesterList.get(0);

    //AppSetting appSetting = mAppSettingManager.getAll();

    Map<String,String> appSettingMap = mAppSettingManager.getAll().stream()
                                                      .collect(Collectors.toMap(AppSetting::getParameterName,AppSetting::getParameterValue));

    Timestamp startTime = Timestamp.valueOf(appSettingMap.get("class start time"));
    Timestamp endTime = Timestamp.valueOf(appSettingMap.get("class end time"));
    int classDuration = Integer.parseInt(appSettingMap.get("class duration"));

    Document document = new Document();
    document.addTitle("Teacher's Routine");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document,baos);

    Font tableDataFont = new Font(Font.FontFamily.TIMES_ROMAN,12);

    document.open();
    document.setPageSize(PageSize.A4.rotate());

    document.newPage();

    Paragraph paragraph = new Paragraph("Ahsanullah University of Science and Technology");
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD,15));
    document.add(paragraph);

    paragraph = new Paragraph(department.getLongName().toUpperCase());
    document.add(paragraph);

    paragraph = new Paragraph("Load Schedule ("+semester.getName()+")");
    document.add(paragraph);

    paragraph = new Paragraph("TEACHER'S NAME: "+ teacher.getName().toUpperCase());
    document.add(paragraph);

    PdfPTable table = new PdfPTable(13);
    PdfPCell dayTimeCell = new PdfPCell(new Paragraph("Time/Day",tableDataFont));
    table.addCell(dayTimeCell);

    Timestamp timeInitializer = Timestamp.valueOf(appSettingMap.get("class start time"));

    while(true){
      if(timeInitializer==endTime){
        break;
      }else{
        PdfPCell cell = new PdfPCell(new Paragraph(timeInitializer.getTime()+"-"+timeInitializer.getTime()+classDuration,tableDataFont));
        timeInitializer.setTime(timeInitializer.getTime()+classDuration);
        table.addCell(cell);
      }
    }

    document.add(table);

    document.close();
    baos.writeTo(pOutputStream);

  }
}
