package org.ums.persistent.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ExamGradeDaoDecorator;
import org.ums.domain.model.dto.*;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.CourseType;
import org.ums.enums.ExamType;
import org.ums.manager.ClassAttendanceManager;
import org.ums.util.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ifti on 29-Oct-16.
 */
public class PersistentClassAttendanceDao implements ClassAttendanceManager {

  String ATTENDANCE_QUERY = "Select Course_Id,Class_Date,Serial,Student_Id,Attendance From  "
      + "(Select Course_id,Id,to_char(Class_Date,'DDMMYYYY') class_date,serial,Teacher_Id  "
      + "From MST_CLASS_ATTENDANCE Where Semester_Id=? And Course_id=? And Section=? )tmp1  "
      + "Left Outer Join DTL_CLASS_ATTENDANCE "
      + "on tmp1.Id = DTL_CLASS_ATTENDANCE.Attendance_Id ";

  String ATTENDANCE_DATE_QUERY =
      "Select TO_Char(Class_Date,'DD MON, YY') Class_Date,To_Char(Class_Date,'DDMMYYYY') CLASS_DATE_F1,Serial,Teacher_Id ,ID,EMPLOYEE_NAME,SHORT_NAME  "
          + "From MST_CLASS_ATTENDANCE,EMPLOYEES Where Semester_Id= ? And Course_id=? And Section=? And EMPLOYEE_ID=Teacher_Id "
          + "Order by Serial desc";

  String ATTENDANCE_STUDENTS_ALL =
      "Select * From ( "
          + "Select Students.Student_Id,Full_Name From ( "
          + "Select distinct Students.Student_Id from UG_Registration_Result,Students Where  "
          + "UG_Registration_Result.Semester_Id=? and Course_Id=? and exam_type= "
          + ExamType.SEMESTER_FINAL.getId()
          + " And Students.student_id=UG_Registration_Result.student_id %s "
          + "union "
          + "Select Students.Student_Id From DTL_CLASS_ATTENDANCE,MST_CLASS_ATTENDANCE,Students Where  MST_CLASS_ATTENDANCE.id=DTL_CLASS_ATTENDANCE.ATTENDANCE_ID "
          + "And Students.Student_Id=DTL_CLASS_ATTENDANCE.Student_Id "
          + "And MST_CLASS_ATTENDANCE.Semester_Id=? and Course_id=? %s"
          + ")tmp1,Students Where tmp1.Student_Id=Students.Student_id "
          + ")tmp2 Order by Student_Id  ";

  String ATTENDANCE_STUDENTS_ENROLLED = "Select * From ( "
      + "Select Students.Student_Id,Full_Name From ( "
      + "Select distinct Students.Student_Id from UG_Registration_Result,Students Where  "
      + "UG_Registration_Result.Semester_Id=? and Course_Id=? and exam_type= "
      + ExamType.SEMESTER_FINAL.getId()
      + "And Students.student_id=UG_Registration_Result.student_id %s "
      + ")tmp1,Students Where tmp1.Student_Id=Students.Student_id )tmp2 " + "Order by Student_Id ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentClassAttendanceDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<ClassAttendanceDto> getDateList(int pSemesterId, String pCourseId, String pSection) {
    String query = ATTENDANCE_DATE_QUERY;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId, pSection},
        new AttendanceDateRowMapper());
  }

  @Override
  public List<ClassAttendanceDto> getStudentList(int pSemesterId, String pCourseId,
      CourseType courseType, String pSection, String pStudentCategory) {
    String query = "";
    if(pStudentCategory.equals("Enrolled"))
      query = ATTENDANCE_STUDENTS_ENROLLED;
    else if(pStudentCategory.equals("All"))
      query = ATTENDANCE_STUDENTS_ALL;

    if(pStudentCategory.equals("Enrolled")) {
      if(courseType == CourseType.THEORY)
        query = String.format(query, " And Theory_Section='" + pSection + "' ");
      else if(courseType == CourseType.SESSIONAL)
        query = String.format(query, " And Sessional_Section=" + pSection + "' ");
    }
    else if(pStudentCategory.equals("All")) {
      if(courseType == CourseType.THEORY)
        query =
            String.format(query, " And Theory_Section='" + pSection + "' ", " And Theory_Section='"
                + pSection + "' ");
      else if(courseType == CourseType.SESSIONAL)
        query =
            String.format(query, " And Sessional_Section=" + pSection + "' ",
                " And Sessional_Section=" + pSection + "' ");
    }

    if(pStudentCategory.equals("Enrolled"))
      return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId},
          new AttendanceStudentRowMapper());
    else if(pStudentCategory.equals("All"))
      return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId, pSemesterId,
          pCourseId}, new AttendanceStudentRowMapper());
    else
      return null;
  }

  @Override
  public Map getAttendance(int pSemesterId, String pCourseId, String pSection) {
    String query = ATTENDANCE_QUERY;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId, pSection},
        new AttendanceRowMapper());
  }

  @Override
  public int updateAttendanceMaster(String pClassDate, Integer pSerial, String pAttendanceId) {
    String query =
        "Update MST_CLASS_ATTENDANCE Set Class_Date=To_Date(?, 'DD Mon, YY'),Serial=?  Where Id=?";
    return mJdbcTemplate.update(query, pClassDate, pSerial, pAttendanceId);
  }

  @Override
  public int insertAttendanceMaster(String pId, Integer pSemesterId, String pCourseId,
      String pSection, String pClassDate, Integer pSerial, String pTeacherId) {
    String query =
        "Insert InTo MST_CLASS_ATTENDANCE(ID,SEMESTER_ID,COURSE_ID,SECTION,CLASS_DATE,SERIAL,TEACHER_ID) "
            + "Values(?,?,?,?,To_Date(?, 'DD Mon, YY'),?,?) ";
    return mJdbcTemplate.update(query, pId, pSemesterId, pCourseId, pSection, pClassDate, pSerial,
        pTeacherId);
  }

  @Override
  public boolean upsertAttendanceDtl(String id, List<ClassAttendanceDto> attendanceList) {
    batchInsertAttendanceDtl(id, attendanceList);
    return true;
  }

  public void batchInsertAttendanceDtl(String id, List<ClassAttendanceDto> attendanceList) {
    String sql =
        "merge into DTL_CLASS_ATTENDANCE "
            + "using (  "
            + "  select ? as Attendance_Id, "
            + "         ? as Student_Id, "
            + "         ? as Attendance "
            + "   from dual "
            + ")dt2 on (DTL_CLASS_ATTENDANCE.Attendance_Id = dt2.Attendance_Id And DTL_CLASS_ATTENDANCE.Student_Id = dt2.Student_Id) "
            + "when matched then  "
            + "update set DTL_CLASS_ATTENDANCE.Attendance =  dt2.Attendance "
            + "when not matched then "
            + "Insert values (dt2.Attendance_Id,dt2.Student_Id, dt2.Attendance)";

    mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        ClassAttendanceDto classAttendanceDto = attendanceList.get(i);
        ps.setString(1, id);
        ps.setString(2, classAttendanceDto.getStudentId());
        ps.setInt(3, classAttendanceDto.getAttendance());
      }

      @Override
      public int getBatchSize() {
        return attendanceList.size();
      }
    });
  }

  @Override
  public int deleteAttendanceDtl(String attendanceId) {
    String query = "Delete DTL_CLASS_ATTENDANCE Where Attendance_Id=? ";
    return mJdbcTemplate.update(query, attendanceId);
  }

  @Override
  public int deleteAttendanceMaster(String attendanceId) {
    String query = "Delete MST_CLASS_ATTENDANCE Where Id=? ";
    return mJdbcTemplate.update(query, attendanceId);
  }

  @Override
  public String getAttendanceId() {
    String query = "Select SQN_CLASS_ATTENDANCE.NextVal From Dual";
    String attendanceId =
        (String) mJdbcTemplate.queryForObject(query, new Object[] {}, String.class);
    return attendanceId;
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
      attendanceDto.setTeacherName(resultSet.getString("EMPLOYEE_NAME"));
      attendanceDto.setTeacherShortName(resultSet.getString("SHORT_NAME"));
      attendanceDto.setId(resultSet.getString("ID"));
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
