package org.ums.academic.resource.helper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.json.*;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.ClassAttendanceBuilder;
import org.ums.exceptions.ValidationException;
import org.ums.manager.CourseTeacherManager;
import org.ums.report.generator.AttendanceSheetGenerator;
import org.ums.domain.model.dto.ClassAttendanceDto;
import org.ums.domain.model.immutable.Course;
import org.ums.statistics.TextLogger;
import org.ums.usermanagement.user.User;
import org.ums.manager.ClassAttendanceManager;
import org.ums.manager.CourseManager;
import org.ums.usermanagement.user.UserManager;
import org.ums.services.UserService;

import com.itextpdf.text.DocumentException;

/**
 * Created by Ifti on 29-Oct-16.
 */
@Component
public class ClassAttendanceResourceHelper {
  private static final Logger mLogger = org.slf4j.LoggerFactory.getLogger(ClassAttendanceResourceHelper.class);
  @Autowired
  private ClassAttendanceManager mManager;

  @Autowired
  private ClassAttendanceBuilder mBuilder;

  @Autowired
  private AttendanceSheetGenerator mSheetGenerator;

  @Autowired
  private CourseManager mCourseManager;

  @Autowired
  private UserService mUserService;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  private CourseTeacherManager courseTeacherManager;

  public ClassAttendanceManager getContentManager() {
    return mManager;
  }

  public ClassAttendanceBuilder getBuilder() {
    return mBuilder;
  }

  public List<ClassAttendanceDto> getDateList(final Integer pSemesterId, final String pCourseId, final String pSection) {
    return getContentManager().getDateList(pSemesterId, pCourseId, pSection);
  }

  public List<ClassAttendanceDto> getStudentList(final Integer pSemesterId, final String pCourseId,
      final String pSection, String pStudentCategory) {
    Course course = mCourseManager.get(pCourseId);
    return getContentManager().getStudentList(pSemesterId, pCourseId, course.getCourseType(), pSection,
        pStudentCategory);
  }

  public Map<String, String> getAttendanceMap(final Integer pSemesterId, final String pCourseId, final String pSection) {
    return getContentManager().getAttendance(pSemesterId, pCourseId, pSection);
  }

  public JsonObject getClassAttendance(final Integer pSemesterId, final String pCourseId, final String pSection,
      final String pStudentCategory) {

    List<ClassAttendanceDto> dateList = getDateList(pSemesterId, pCourseId, pSection);
    List<ClassAttendanceDto> studentList = getStudentList(pSemesterId, pCourseId, pSection, pStudentCategory);
    Map<String, String> attendanceList = getAttendanceMap(pSemesterId, pCourseId, pSection);

    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    JsonArray jsonArray;
    JsonArrayBuilder jsonArrayBuilder = factory.createArrayBuilder();
    JsonObjectBuilder jsonObjectBuilder;
    int index = 0;

    jsonObjectBuilder = factory.createObjectBuilder();

    // GOI = Global Operation Icon
    jsonObjectBuilder.add("sId", "").add("sName", "GOI");
    for(ClassAttendanceDto date : dateList) {
      // if(index == 0) {
      // jsonObjectBuilder.add("sId", "").add("sName", "OR");
      // }
      // index = index + 1;
      // date.getTeacherId();

      String userId = mUserService.getUser();
      User user = mUserManager.get(userId);
      String employeeId = user.getEmployeeId();

      if(employeeId.equals(date.getTeacherId()))
        jsonObjectBuilder.add("date" + date.getClassDateFormat1() + date.getSerial(), "I");
      else
        jsonObjectBuilder
            .add("date" + date.getClassDateFormat1() + date.getSerial(), "T-" + date.getTeacherShortName());
    }
    // asfasdfasdfasdf a dsf asdf
    jsonArrayBuilder.add(jsonObjectBuilder);
    for(ClassAttendanceDto student : studentList) {
      jsonObjectBuilder = factory.createObjectBuilder();
      index = 0;
      jsonObjectBuilder.add("sId", student.getStudentId()).add("sName", student.getStudentName());
      for(ClassAttendanceDto date : dateList) {
        // if(index == 0) {
        // jsonObjectBuilder.add("sId", student.getStudentId()).add("sName",
        // student.getStudentName());
        // }
        index = index + 1;

        String key = date.getClassDateFormat1() + "" + date.getSerial() + "" + student.getStudentId();
        if(attendanceList.containsKey(key))
          jsonObjectBuilder.add("date" + date.getClassDateFormat1() + date.getSerial(),
              attendanceList.get(key).equals("1") ? "Y" : "N");
        else
          jsonObjectBuilder.add("date" + date.getClassDateFormat1() + date.getSerial(), "N");

      }
      jsonArrayBuilder.add(jsonObjectBuilder);
    }
    jsonArray = jsonArrayBuilder.build();
    System.out.println(jsonArray.toString());
    objectBuilder.add("attendance", jsonArray);

    jsonObjectBuilder = factory.createObjectBuilder();
    jsonObjectBuilder.add("data", "sId").add("title", "Student Id").add("readOnly", true).add("date", "")
        .add("serial", 0);
    jsonArrayBuilder.add(jsonObjectBuilder);
    jsonObjectBuilder.add("data", "sName").add("title", "Student Name").add("readOnly", true).add("date", "")
        .add("serial", 0);
    jsonArrayBuilder.add(jsonObjectBuilder);

    for(ClassAttendanceDto date : dateList) {
      jsonObjectBuilder.add("data", "date" + date.getClassDateFormat1() + date.getSerial()).add("id", date.getId())
          .add("title", date.getClassDate() + "&nbsp;<span class=\"badge badge-info\">" + date.getSerial() + "</span>")
          .add("readOnly", true).add("date", date.getClassDate()).add("serial", date.getSerial());
      jsonArrayBuilder.add(jsonObjectBuilder);
    }
    jsonArray = jsonArrayBuilder.build();
    System.out.println(jsonArray.toString());
    objectBuilder.add("columns", jsonArray);

    return objectBuilder.build();
  }

  @Transactional
  public Response saveNewAttendance(final JsonObject pJsonObject) {
    List<ClassAttendanceDto> attendanceList = getBuilder().getAttendanceList(pJsonObject);

    Integer semesterId = pJsonObject.getInt("semester");
    String courseId = pJsonObject.getString("course");
    String section = pJsonObject.getString("section");
    String classDate = pJsonObject.getString("classDate");
    Integer serial = pJsonObject.getInt("serial");
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    validateCourseForAttendance(semesterId, user.getEmployeeId(), courseId, section);

    String attendanceId = getContentManager().getAttendanceId();
    getContentManager().insertAttendanceMaster(attendanceId, semesterId, courseId, section, classDate, serial,
        user.getEmployeeId());
    getContentManager().upsertAttendanceDtl(attendanceId, attendanceList);

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    builder.entity(attendanceId);
    return builder.build();
  }

  @Transactional
  public Response updateClassAttendance(final JsonObject pJsonObject) {

    Integer semesterId = pJsonObject.getInt("semester");
    String courseId = pJsonObject.getString("course");
    String section = pJsonObject.getString("section");
    String classDate = pJsonObject.getString("classDate");
    Integer serial = pJsonObject.getInt("serial");
    String attendanceId = pJsonObject.getString("id");

    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    validateCourseForAttendance(semesterId, user.getEmployeeId(), courseId, section);

    List<ClassAttendanceDto> attendanceList = getBuilder().getAttendanceList(pJsonObject);
    getContentManager().updateAttendanceMaster(classDate, serial, attendanceId);
    getContentManager().upsertAttendanceDtl(attendanceId, attendanceList);

    return Response.noContent().build();
  }

  @Transactional(rollbackFor = Exception.class)
  public Response deleteClassAttendance(final String attendanceId) {
    ClassAttendanceDto attendanceMst = getContentManager().getAttendanceInfo(Long.valueOf(attendanceId));
    User user = mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString());
    validateCourseForAttendance(attendanceMst.getSemesterId(), user.getEmployeeId(), attendanceMst.getCourseId(),
        attendanceMst.getSection());

    getContentManager().deleteAttendanceMaster(attendanceId);
    getContentManager().deleteAttendanceDtl(attendanceId);

    return Response.noContent().build();
  }

  public void getAttendanceSheetReport(final OutputStream pOutputStream, final int pSemesterId, final String pCourseId,
      final String pSection, final String pStudentCategory) throws DocumentException, IOException {
    mSheetGenerator.createAttendanceSheetReport(pOutputStream, pSemesterId, pCourseId, pSection, pStudentCategory);
  }

  public void validateCourseForAttendance(final Integer pSemesterId, final String pTeacherId, final String pCourseId,
      final String pSectionId) {
    try {
      courseTeacherManager.getAssignedCourse(pSemesterId, pTeacherId, pCourseId, pSectionId);
    } catch(Exception ex) {
      mLogger
          .debug(
              "[{}]: Unauthorized access detected for class attendance. SemesterId: {}, TeacherId: {}, CourseId: {}, Section: {}",
              SecurityUtils.getSubject().getPrincipal().toString(), pSemesterId, pTeacherId, pCourseId, pSectionId);
      throw new ValidationException("Unauthorized access detected");
    }
  }
}
