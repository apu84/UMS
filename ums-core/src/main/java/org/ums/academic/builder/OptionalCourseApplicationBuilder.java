package org.ums.academic.builder;

import org.ums.academic.model.PersistentCourse;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.readOnly.Course;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class OptionalCourseApplicationBuilder {

  public void build(List<Course> pCourseList, JsonObject pJsonObject,String courseType) throws Exception {

    JsonArray entries=pJsonObject.getJsonArray(courseType);
    MutableCourse course;
    //List<Course> courseList=new ArrayList<>(entries.size());
    for (int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      course = new PersistentCourse();
      course.setId(jsonObject.getString("id"));
      pCourseList.add(course);
    }
  }

}
