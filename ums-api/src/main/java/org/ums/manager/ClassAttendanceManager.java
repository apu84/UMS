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
  public List<ClassAttendanceDto> getStudentList(int semesterId, String courseId,
      CourseType pCourseType, String pSection, String pStudentCategory) throws Exception;

  public List<ClassAttendanceDto> getDateList(int semesterId, String courseId) throws Exception;

  public Map<String, String> getAttendance(int semesterId, String courseId) throws Exception;

  public int deleteAttendanceDtl(String attendanceId) throws Exception;

  public int deleteAttendanceMaster(String attendanceId) throws Exception;

  public int updateAttendanceMaster(String pClassDate, Integer pSerial, String pAttendanceId)
      throws Exception;

  public String getAttendanceId() throws Exception;

  public int insertAttendanceMaster(String pId, Integer pSemesterId, String pCourseId,
      String pSection, String pClassDate, Integer pSerial, String pTeacherId) throws Exception;

  public boolean upsertAttendanceDtl(String id, List<ClassAttendanceDto> attendanceList)
      throws Exception;

}
