package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.persistent.model.PersistentCourse;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.immutable.Course;
import org.ums.persistent.model.PersistentStudent;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.List;

@Component
public class OptionalCourseApplicationBuilder {

  public void build(List<Course> pCourseList, JsonObject pJsonObject, String courseType) {

    JsonArray entries = pJsonObject.getJsonArray(courseType);
    MutableCourse course;
    // List<Course> courseList=new ArrayList<>(entries.size());
    for(int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      course = new PersistentCourse();
      course.setId(jsonObject.getString("id"));
      pCourseList.add(course);
    }
  }

  public void buildStudent(List<String> pStudentId, JsonObject pJsonObject, String operationType) {
    JsonArray entries = pJsonObject.getJsonArray(operationType);
    String studentId = "";
    for(int i = 0; i < entries.size(); i++) {
      if(operationType.equalsIgnoreCase("students"))
        studentId = entries.getString(i);
      else {
        JsonObject jsonObject = entries.getJsonObject(i);
        studentId = jsonObject.getString("studentId");
      }
      pStudentId.add(studentId);
    }
  }

  public void buildCourseId(StringBuilder pCourseId, JsonObject pJsonObject, String operationType) {
    JsonArray entries = pJsonObject.getJsonArray(operationType);
    for(int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      pCourseId.append(jsonObject.getString("courseId"));
    }
  }

  public void buildCourseId(List<String> pCourseList, JsonObject pJsonObject) {
    JsonArray entries = pJsonObject.getJsonArray("courseList");
    for(int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      pCourseList.add(jsonObject.getString("id"));
    }
  }

}
