package org.ums.common.report.resource;

import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.academic.resource.helper.ClassAttendanceResourceHelper;
import org.ums.common.report.generator.UgGradeSheetGenerator;
import org.ums.domain.model.dto.ClassAttendanceDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.dummy.webservice.DummyXLSGenerator;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.generator.XlsGenerator;
import org.ums.manager.ExamGradeManager;
import org.ums.resource.Resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by Ifti on 17-Jun-16.
 */
@Component
@Path("/classAttendanceReport/xls/")
@Produces({"application/vnd.ms-excel"})
public class ClassAttendanceXls extends Resource {

  @Autowired
  ClassAttendanceResourceHelper mResourceHelper;

  @Autowired
  XlsGenerator xlsGenerator;

  @GET
  @Path("/semester/{semester-id}/course/{course-id}")
  public StreamingOutput get(final @Context Request pRequest,
      final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("course-id") String pCourseId) throws Exception {
    // List<StudentGradeDto> gradeList =
    // mExamGradeManager.getAllGrades(pSemesterId, pCourseId, ExamType.get(pExamTypeId),
    // CourseType.get(pCourseType));
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {
        try {
          InputStream a =
              ClassAttendanceXls.class
                  .getResourceAsStream("/report/xls/template/ClassAttendance.xls");
          List<ClassAttendanceDto> dataList = new ArrayList<>();
          List<String> headerList = new ArrayList<>();

          List<ClassAttendanceDto> dateList =
              mResourceHelper.getDateList(pSemesterId, pCourseId, "");
          List<ClassAttendanceDto> studentList =
              mResourceHelper.getStudentList(pSemesterId, pCourseId, "");
          Map<String, String> attendanceMap =
              mResourceHelper.getAttendanceMap(pSemesterId, pCourseId, "");

          headerList.add("Student Id");
          headerList.add("Student Name");

          for(int i = 0; i < dateList.size(); i++) {
            headerList.add(dateList.get(i).getClassDate());
          }
          headerList.add("Marks");
          headerList.add("Percentage(%)");

          double totalClass = dateList.size();
          double attendedClass = 0;
          for(int i = 0; i < studentList.size(); i++) {
            attendedClass = 0;
            ClassAttendanceDto student = new ClassAttendanceDto();
            student.setStudentId(studentList.get(i).getStudentId());
            student.setStudentName(studentList.get(i).getStudentName());

            List<String> attendanceList = new ArrayList<String>();
            for(int j = 0; j < dateList.size(); j++) {
              String key =
                  dateList.get(j).getClassDateFormat1() + dateList.get(j).getSerial()
                      + studentList.get(i).getStudentId();
              attendanceList.add(attendanceMap.get(key));
              attendedClass += Integer.parseInt(attendanceMap.get(key));
            }
            double marks = (attendedClass / totalClass) * 10;
            double percentage = (attendedClass / totalClass) * 100;
            attendanceList.add(String.valueOf(Math.round(marks)));
            attendanceList.add(String.valueOf(Math.round(percentage)));
            student.setAttendances(attendanceList);
            dataList.add(student);
          }

          List<List<Object>> data = createGridData(dataList);

          // Map map = new HashMap();
          // // map.put("headers", Arrays.asList("Date 1", "Date 2", "Date 3", "Date 4"));
          // map.put("headers", dateList);
          // map.put("data", dates);

          org.jxls.common.Context context = new org.jxls.common.Context();
          context.putVar("headers", headerList);
          context.putVar("data", data);
          JxlsHelper.getInstance().processTemplate(a, output, context);
          // xlsGenerator.build(map, output, a);
          // JxlsHelper.getInstance().processGridTemplateAtCell(is, os, context,
          // "name,birthDate,payment", "Sheet2!A1");

        } catch(Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }

  private static List<List<Object>> createGridData(List<ClassAttendanceDto> students) {
    List<List<Object>> data = new ArrayList<>();
    for(ClassAttendanceDto student : students) {
      data.add(convertStudentToList(student));
    }
    return data;
  }

  private static List<Object> convertStudentToList(ClassAttendanceDto student) {
    List<Object> list = new ArrayList<>();
    list.add(student.getStudentId());
    list.add(student.getStudentName());

    List<String> attendanceList = student.getAttendances();
    for(int i = 0; i < attendanceList.size(); i++)
      list.add(attendanceList.get(i));
    return list;
  }

  // public class Student {
  // private String studentId;
  // private String studentName;
  // private List<String> attendances;
  //
  // public String getStudentId() {
  // return studentId;
  // }
  //
  // public void setStudentId(String studentId) {
  // this.studentId = studentId;
  // }
  //
  // public String getStudentName() {
  // return studentName;
  // }
  //
  // public void setStudentName(String studentName) {
  // this.studentName = studentName;
  // }
  //
  // public List<String> getAttendances() {
  // return attendances;
  // }
  //
  // public void setAttendances(List<String> attendances) {
  // this.attendances = attendances;
  // }
  // }
}
