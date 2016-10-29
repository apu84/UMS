package org.ums.persistent.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ExamGradeDaoDecorator;
import org.ums.domain.model.dto.ClassAttendanceDto;
import org.ums.domain.model.dto.CourseTeacherDto;
import org.ums.domain.model.dto.GradeChartDataDto;
import org.ums.domain.model.dto.MarksSubmissionStatusDto;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.manager.ClassAttendanceManager;
import org.ums.util.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 29-Oct-16.
 */
public class PersistentClassAttendanceDao implements ClassAttendanceManager {

  String ATTENDANCE_QUERY = "Select Course_Id,Class_Date,Serial,Student_Id,Attendance From  "
      + "(Select Course_id,Id,to_char(Class_Date,'DDMMYYYY') class_date,serial,Teacher_Id  "
      + "From MST_CLASS_ATTENDANCE Where Semester_Id=? And Course_id=? )tmp1  "
      + "Left Outer Join DTL_CLASS_ATTENDANCE "
      + "on tmp1.Id = DTL_CLASS_ATTENDANCE.Attendance_Id ";

  String ATTENDANCE_ALL = ATTENDANCE_QUERY + " Order by StudentId";

  String ATTENDANCE_ONLY_REGISTERED = "Select * From ( " + ATTENDANCE_QUERY
      + ")test1,UG_Registration_Result reg " + "Where test1.student_id=reg.student_id "
      + "And test1.course_id=reg.course_id " + "Order by StudentId";

  String ATTENDANCE_DATE_QUERY =
      "Select TO_Char(Class_Date,'DD MON, YY') Class_Date,To_Char(Class_Date,'DDMMYYYY') CLASS_DATE_F1,Serial,Teacher_Id  "
          + "From MST_CLASS_ATTENDANCE Where Semester_Id= ? And Course_id=? "
          + "Order by Class_Date,Serial ";

  String ATTENDANCE_STUDENTS =
      "Select Distinct STUDENTS.Student_Id,STUDENTS.FULL_NAME From DTL_CLASS_ATTENDANCE,STUDENTS Where Attendance_Id in ( "
          + "Select Id From MST_CLASS_ATTENDANCE Where Semester_Id=? And Course_id=? "
          + ")  "
          + "And DTL_CLASS_ATTENDANCE.STUDENT_ID=STUDENTS.STUDENT_ID "
          + "%s "
          + "Order by STUDENTS.Student_Id ";

  String ATTENDANCE_STUDENTS_ALL = String.format(ATTENDANCE_STUDENTS, "");

  String ATTENDANCE_STUDENTS_REGISTERED =
      String
          .format(
              ATTENDANCE_STUDENTS,
              " And STUDENTS.Student_Id in (Select Student_Id From UG_REGISTRATION_RESULT WHERE Semester_Id=? And Course_id=? ) ");

  private JdbcTemplate mJdbcTemplate;

  public PersistentClassAttendanceDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<ClassAttendanceDto> getDateList(int pSemesterId, String pCourseId) throws Exception {
    String query = ATTENDANCE_DATE_QUERY;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId},
        new AttendanceDateRowMapper());
  }

  @Override
  public List<ClassAttendanceDto> getStudentList(int pSemesterId, String pCourseId)
      throws Exception {
    String query = ATTENDANCE_STUDENTS_ALL;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId},
        new AttendanceStudentRowMapper());
  }

  @Override
  public Map getAttendance(int pSemesterId, String pCourseId) throws Exception {
    String query = ATTENDANCE_QUERY;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId},
        new AttendanceRowMapper());
  }

  class AttendanceStudentRowMapper implements RowMapper<ClassAttendanceDto> {
    @Override
    public ClassAttendanceDto mapRow(ResultSet resultSet, int i) throws SQLException {
      ClassAttendanceDto attendanceDto = new ClassAttendanceDto();
      attendanceDto.setStudentId(resultSet.getString("STUDENT_ID"));
      attendanceDto.setStudentName(resultSet.getString("FULL_NAME"));
      AtomicReference<ClassAttendanceDto> atomicReference = new AtomicReference<>(attendanceDto);
      return atomicReference.get();
    }
  }

  class AttendanceDateRowMapper implements RowMapper<ClassAttendanceDto> {
    @Override
    public ClassAttendanceDto mapRow(ResultSet resultSet, int i) throws SQLException {
      ClassAttendanceDto attendanceDto = new ClassAttendanceDto();
      attendanceDto.setClassDate(resultSet.getString("CLASS_DATE"));
      attendanceDto.setClassDateFormat1(resultSet.getString("CLASS_DATE_F1"));
      attendanceDto.setSerial(resultSet.getInt("SERIAL"));
      attendanceDto.setTeacherId(resultSet.getString("TEACHER_ID"));
      AtomicReference<ClassAttendanceDto> atomicReference = new AtomicReference<>(attendanceDto);
      return atomicReference.get();
    }
  }

  class AttendanceRowMapper implements ResultSetExtractor<Map> {
    @Override
    public Map extractData(ResultSet rs) throws SQLException, DataAccessException {
      HashMap<String, String> mapRet = new HashMap<String, String>();
      String key = "";
      while(rs.next()) {
        key = rs.getString("CLASS_DATE") + rs.getString("SERIAL") + rs.getString("STUDENT_ID");
        mapRet.put(key, rs.getString("ATTENDANCE"));
      }
      return mapRet;
    }

  }
}
