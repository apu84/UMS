package org.ums.manager;

import org.ums.domain.model.dto.ClassAttendanceDto;
import org.ums.domain.model.dto.StudentGradeDto;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;

import java.util.List;
import java.util.Map;

/**
 * Created by Ifti on 29-Oct-16.
 */
public interface ClassAttendanceManager {
  public List<ClassAttendanceDto> getStudentList(int semesterId, String courseId) throws Exception;

  public List<ClassAttendanceDto> getDateList(int semesterId, String courseId) throws Exception;

  public Map<String, String> getAttendance(int semesterId, String courseId) throws Exception;
}
