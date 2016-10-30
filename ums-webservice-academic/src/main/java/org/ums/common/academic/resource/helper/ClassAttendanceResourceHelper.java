package org.ums.common.academic.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.builder.ClassAttendanceBuilder;
import org.ums.common.builder.ExamGradeBuilder;
import org.ums.domain.model.dto.ClassAttendanceDto;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.ExamType;
import org.ums.manager.ClassAttendanceManager;
import org.ums.manager.ExamGradeManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.UserManager;
import org.ums.services.academic.GradeSubmissionService;

import javax.json.*;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

/**
 * Created by Ifti on 29-Oct-16.
 */
@Component
public class ClassAttendanceResourceHelper {

  @Autowired
  private ClassAttendanceManager mManager;

  @Autowired
  private ClassAttendanceBuilder mBuilder;

  @Autowired
  private UserManager mUserManager;

  public ClassAttendanceManager getContentManager() {
    return mManager;
  }

  public ClassAttendanceBuilder getBuilder() {
    return mBuilder;
  }

  public JsonObject getClassAttendance(final Integer pSemesterId, final String pCourseId)
      throws Exception {

    List<ClassAttendanceDto> dateList = getContentManager().getDateList(pSemesterId, pCourseId);
    List<ClassAttendanceDto> studentList =
        getContentManager().getStudentList(pSemesterId, pCourseId);
    Map<String, String> attendanceList = getContentManager().getAttendance(pSemesterId, pCourseId);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    JsonArray jsonArray;
    JsonArrayBuilder jsonArrayBuilder = factory.createArrayBuilder();
    JsonObjectBuilder jsonObjectBuilder;
    int index = 0;

    jsonObjectBuilder = factory.createObjectBuilder();
    for(ClassAttendanceDto date : dateList) {
      if(index == 0) {
        jsonObjectBuilder.add("sId", "").add("sName", "");
      }
      index = index + 1;
      jsonObjectBuilder.add("date" + date.getClassDateFormat1(), "I");
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

        String key =
            date.getClassDateFormat1() + "" + date.getSerial() + "" + student.getStudentId();
        if(attendanceList.containsKey(key))
          jsonObjectBuilder.add("date" + date.getClassDateFormat1(), attendanceList.get(key)
              .equals("1") ? "Y" : "N");
        else
          jsonObjectBuilder.add("date" + date.getClassDateFormat1(), "N");

      }
      jsonArrayBuilder.add(jsonObjectBuilder);
    }
    jsonArray = jsonArrayBuilder.build();
    System.out.println(jsonArray.toString());
    objectBuilder.add("attendance", jsonArray);

    jsonObjectBuilder = factory.createObjectBuilder();
    jsonObjectBuilder.add("data", "sId").add("title", "Student Id").add("readOnly", true)
        .add("date", "").add("serial", 0);
    jsonArrayBuilder.add(jsonObjectBuilder);
    jsonObjectBuilder.add("data", "sName").add("title", "Student Name").add("readOnly", true)
        .add("date", "").add("serial", 0);
    jsonArrayBuilder.add(jsonObjectBuilder);

    for(ClassAttendanceDto date : dateList) {
      jsonObjectBuilder
          .add("data", "date" + date.getClassDateFormat1())
          .add(
              "title",
              date.getClassDate() + "&nbsp;<span class=\"badge badge-info\">" + date.getSerial()
                  + "</span>").add("readOnly", true).add("date", date.getClassDate())
          .add("serial", date.getSerial());
      jsonArrayBuilder.add(jsonObjectBuilder);
    }
    jsonArray = jsonArrayBuilder.build();
    System.out.println(jsonArray.toString());
    objectBuilder.add("columns", jsonArray);

    // jsonArrayBuilder=factory.createArrayBuilder();

    // JsonObject jsonObject = jsonObject.put("aoColumnDefs",(Object)jsonArray);

    return objectBuilder.build();
  }
}
