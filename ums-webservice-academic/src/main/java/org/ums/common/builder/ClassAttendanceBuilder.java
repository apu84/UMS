package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.domain.model.dto.ClassAttendanceDto;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.enums.StudentMarksSubmissionStatus;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

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

  public List<ClassAttendanceDto> getAttendanceList(JsonObject pJsonObject) throws Exception {

    JsonArray entries = pJsonObject.getJsonArray("attendanceList");
    List<ClassAttendanceDto> attendanceList = new ArrayList<>(entries.size());
    for(int i = 0; i < entries.size(); i++) {
      JsonObject jsonObject = entries.getJsonObject(i);
      ClassAttendanceDto attendanceDto = new ClassAttendanceDto();
      attendanceDto.setStudentId(jsonObject.getString("studentId"));
      attendanceDto.setAttendance(jsonObject.getInt("attendance"));
      attendanceList.add(attendanceDto);
    }
    return attendanceList;
  }
  // var complete_json = {};
  // complete_json["attendanceList"] = attendanceList;
  // complete_json["date"] = $("#date_"+row+column).val();
  // complete_json["serial"] = $("#serial_"+row+column).val();
  // return complete_json;

}
