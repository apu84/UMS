package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SeatPlanReportDaoDecorator;
import org.ums.domain.model.dto.SeatPlanReportDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by My Pc on 20-Aug-16.
 */
public class PersistentSeatPlanReportDao extends SeatPlanReportDaoDecorator{

  String SELECT_ALL_ATTENDENCE_SHEET="" +
      "SELECT  " +
      "  seatPlan.ROOM_NO,  " +
      "  examRoutine.PROGRAM_SHORT_NAME,  " +
      "  examRoutine.COURSE_TITLE,  " +
      "  examRoutine.COURSE_NO,  " +
      "  to_char(examRoutine.EXAM_DATE,'dd-mm-YYYY') EXAM_DATE,  " +
      "  seatPlan.CURR_YEAR ,  " +
      "  seatPlan.CURR_SEMESTER,  " +
      "  seatPlan.STUDENT_ID  " +
      "FROM  " +
      "        (  " +
      "  " +
      "      SELECT  " +
      "        ROOM_INFO.ROOM_NO,  " +
      "        SEAT_PLAN.STUDENT_ID,  " +
      "        SEAT_PLAN.SEMESTER_ID,  " +
      "        STUDENTS.CURR_YEAR,  " +
      "        students.CURR_SEMESTER,  " +
      "        STUDENTS.PROGRAM_ID  " +
      "      FROM  " +
      "        SEAT_PLAN,  " +
      "        STUDENTS,  " +
      "        ROOM_INFO  " +
      "      WHERE  " +
      "          SEAT_PLAN.STUDENT_ID = STUDENTS.STUDENT_ID AND  " +
      "          SEAT_PLAN.SEMESTER_ID = ? AND  " +
      "          SEAT_PLAN.EXAM_TYPE = ? AND  " +
      "          SEAT_PLAN.ROOM_ID = ROOM_INFO.ROOM_ID  " +
      "      ORDER BY  " +
      "        ROOM_INFO.ROOM_ID, STUDENTS.STUDENT_ID  " +
      "        ) seatPlan,  " +
      "    (  " +
      "      select  " +
      "        EXAM_ROUTINE.EXAM_DATE,  " +
      "        MST_PROGRAM.PROGRAM_ID,  " +
      "        MST_PROGRAM.PROGRAM_SHORT_NAME,  " +
      "        MST_COURSE.COURSE_NO,  " +
      "        MST_COURSE.COURSE_TITLE,  " +
      "        MST_COURSE.YEAR,  " +
      "        MST_COURSE.SEMESTER  " +
      "      from EXAM_ROUTINE,  " +
      "        MST_PROGRAM,  " +
      "        MST_COURSE  " +
      "      where  " +
      "        EXAM_ROUTINE.EXAM_TYPE=1  " +
      "        and EXAM_ROUTINE.SEMESTER=?  " +
      "        and MST_COURSE.COURSE_ID=EXAM_ROUTINE.COURSE_ID  " +
      "        and MST_PROGRAM.PROGRAM_ID=EXAM_ROUTINE.PROGRAM_ID  " +
      "      ORDER BY  " +
      "        EXAM_ROUTINE.EXAM_DATE,  " +
      "        MST_PROGRAM.PROGRAM_ID,  " +
      "        MST_COURSE.COURSE_NO  " +
      "      ) examRoutine  " +
      "WHERE  " +
      "  seatPlan.PROGRAM_ID=examRoutine.PROGRAM_ID AND  " +
      "  seatPlan.CURR_YEAR = examRoutine.YEAR AND  " +
      "  seatPlan.CURR_SEMESTER = examRoutine.SEMESTER  " +
      "ORDER BY  " +
      "  seatPlan.ROOM_NO,seatPlan.PROGRAM_ID,examRoutine.COURSE_NO, seatPlan.STUDENT_ID";


  String SELECT_ALL_ATTENDENCE_SHEET_CCI="" +
      "SELECT  " +
      "  seatPlan.ROOM_NO,  " +
      "  examRoutine.PROGRAM_SHORT_NAME,  " +
      "  examRoutine.COURSE_TITLE,  " +
      "  examRoutine.COURSE_NO,  " +
      "  to_char(examRoutine.EXAM_DATE,'dd-mm-YYYY') EXAM_DATE,  " +
      "  seatPlan.CURR_YEAR ,  " +
      "  seatPlan.CURR_SEMESTER,  " +
      "  seatPlan.STUDENT_ID  " +
      "FROM  " +
      "        (  " +
      "  " +
      "      SELECT  " +
      "        ROOM_INFO.ROOM_NO,  " +
      "        SEAT_PLAN.STUDENT_ID,  " +
      "        SEAT_PLAN.SEMESTER_ID,  " +
      "        STUDENTS.CURR_YEAR,  " +
      "        students.CURR_SEMESTER,  " +
      "        STUDENTS.PROGRAM_ID  " +
      "      FROM  " +
      "        SEAT_PLAN,  " +
      "        STUDENTS,  " +
      "        ROOM_INFO  " +
      "      WHERE  " +
      "          SEAT_PLAN.STUDENT_ID = STUDENTS.STUDENT_ID AND  " +
      "          SEAT_PLAN.SEMESTER_ID = ? AND  " +
      "          SEAT_PLAN.EXAM_TYPE = ? AND  " +
      "          SEAT_PLAN.ROOM_ID = ROOM_INFO.ROOM_ID  " +
      "      ORDER BY  " +
      "        ROOM_INFO.ROOM_ID, STUDENTS.STUDENT_ID  " +
      "        ) seatPlan,  " +
      "    (  " +
      "      select  " +
      "        EXAM_ROUTINE.EXAM_DATE,  " +
      "        MST_PROGRAM.PROGRAM_ID,  " +
      "        MST_PROGRAM.PROGRAM_SHORT_NAME,  " +
      "        MST_COURSE.COURSE_NO,  " +
      "        MST_COURSE.COURSE_TITLE,  " +
      "        MST_COURSE.YEAR,  " +
      "        MST_COURSE.SEMESTER  " +
      "      from EXAM_ROUTINE,  " +
      "        MST_PROGRAM,  " +
      "        MST_COURSE  " +
      "      where  " +
      "        EXAM_ROUTINE.EXAM_TYPE=1  " +
      "        and EXAM_ROUTINE.SEMESTER=?  " +
      "        and Exam_routine.exam_date=to_date(?,'DD-MM-YYYY')  "+
      "        and MST_COURSE.COURSE_ID=EXAM_ROUTINE.COURSE_ID  " +
      "        and MST_PROGRAM.PROGRAM_ID=EXAM_ROUTINE.PROGRAM_ID  " +
      "      ORDER BY  " +
      "        EXAM_ROUTINE.EXAM_DATE,  " +
      "        MST_PROGRAM.PROGRAM_ID,  " +
      "        MST_COURSE.COURSE_NO  " +
      "      ) examRoutine  " +
      "WHERE  " +
      "  seatPlan.PROGRAM_ID=examRoutine.PROGRAM_ID AND  " +
      "  seatPlan.CURR_YEAR = examRoutine.YEAR AND  " +
      "  seatPlan.CURR_SEMESTER = examRoutine.SEMESTER  " +
      "ORDER BY  " +
      "  seatPlan.ROOM_NO,seatPlan.PROGRAM_ID,examRoutine.COURSE_NO, seatPlan.STUDENT_ID";

  public JdbcTemplate mJdbcTemplate;

  public PersistentSeatPlanReportDao(final JdbcTemplate pJdbcTemplate){

    mJdbcTemplate = pJdbcTemplate;

  }

  @Override
  public List<SeatPlanReportDto> getSeatPlanDataForAttendenceSheet(Integer pSemesterId, Integer pExamType, String pExamDate) {
    String query="";
    if(pExamDate.equals("NULL")){
      query= SELECT_ALL_ATTENDENCE_SHEET;
      return mJdbcTemplate.query(query,new Object[]{pSemesterId,pExamType,pSemesterId},new SeatPlanReportRowMapper());
    }
    else{
      query=SELECT_ALL_ATTENDENCE_SHEET_CCI;
      return  mJdbcTemplate.query(query,new Object[]{pSemesterId,pExamDate,pSemesterId,pExamDate}, new SeatPlanReportRowMapper());
    }
  }

  class SeatPlanReportRowMapper implements RowMapper<SeatPlanReportDto>{
    @Override
    public SeatPlanReportDto mapRow(ResultSet pResultSet, int pI) throws SQLException {
      SeatPlanReportDto seatPlan = new SeatPlanReportDto();
      seatPlan.setRoomNo(pResultSet.getString("room_no"));
      seatPlan.setProgramShortName(pResultSet.getString("program_short_name"));
      seatPlan.setCourseTitle(pResultSet.getString("course_title"));
      seatPlan.setCourseNo(pResultSet.getString("course_no"));
      seatPlan.setExamDate(pResultSet.getString("exam_date"));
      seatPlan.setCurrentYear(pResultSet.getInt("curr_year"));
      seatPlan.setCurrentSemester(pResultSet.getInt("curr_semester"));
      seatPlan.setStudentId(pResultSet.getString("student_id"));
      return seatPlan;
    }
  }


}
