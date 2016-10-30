package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;

import javax.json.JsonObject;

/**
 * Created by Ifti on 29-Oct-16.
 */
@Component
public class ClassAttendanceBuilder {

  public void buildForUIclient(MarksSubmissionStatusDto partInfoDto, JsonObject pJsonObject)
      throws Exception {
    JsonObject course = pJsonObject.getJsonObject("courseInfo");
    partInfoDto.setCourseId(course.getString("course_id"));
    partInfoDto.setSemesterId(course.getInt("semester_id"));
    partInfoDto.setExamType(ExamType.get(course.getInt("exam_typeId")));
    partInfoDto.setTotal_part(course.getInt("total_part"));
    partInfoDto.setPart_a_total(course.getInt("part_a_total"));
    partInfoDto.setPart_b_total(course.getInt("part_b_total"));
    partInfoDto.setCourseType(CourseType.get(course.getInt("course_typeId")));
  }

}