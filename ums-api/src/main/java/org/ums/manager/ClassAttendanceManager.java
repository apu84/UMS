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
  /**
   * 
   * @param semesterId
   * @param courseId
   * @param pSection "Z" mans all section
   * @param pStudentCategory can be either "Enrolled" or "All"
   * @return
   * @throws Exception
   */
  List<ClassAttendanceDto> getStudentList(int semesterId, String courseId, CourseType pCourseType,
      String pSection, String pStudentCategory);

  List<ClassAttendanceDto> getDateList(int semesterId, String courseId, String section);

  Map<String, String> getAttendance(int semesterId, String courseId, String section);

  int deleteAttendanceDtl(String attendanceId);

  int deleteAttendanceMaster(String attendanceId);

  int updateAttendanceMaster(String pClassDate, Integer pSerial, String pAttendanceId);

  String getAttendanceId();

  int insertAttendanceMaster(String pId, Integer pSemesterId, String pCourseId, String pSection,
      String pClassDate, Integer pSerial, String pTeacherId);

  boolean upsertAttendanceDtl(String id, List<ClassAttendanceDto> attendanceList);

}
