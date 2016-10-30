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

  public int deleteAttendanceDtl(Integer attendanceId) throws Exception;

  public int deleteAttendanceMaster(Integer attendanceId) throws Exception;

  public int updateAttendanceMaster(ClassAttendanceDto classAttendanceDto) throws Exception;
  public int insertAttendanceMaster(Integer pId,Integer pSemesterId, String pCourseId, String pSection, String pClassDate,Integer pSerial,String pTeacherId) throws Exception;
  public boolean upsertAttendanceDtl(Integer id, List<ClassAttendanceDto> attendanceList) throws Exception;



}
