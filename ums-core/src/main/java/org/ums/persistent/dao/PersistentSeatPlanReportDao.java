package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SeatPlanReportDaoDecorator;
import org.ums.domain.model.dto.SeatPlanReportDto;
import org.ums.enums.ExamType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by My Pc on 20-Aug-16.
 */
public class PersistentSeatPlanReportDao extends SeatPlanReportDaoDecorator {

  String SELECT_ALL_ATTENDENCE_SHEET =
      "SELECT "
          + "  seatPlan.ROOM_NO, "
          + "  examRoutine.PROGRAM_SHORT_NAME, "
          + "  examRoutine.COURSE_TITLE, "
          + "  examRoutine.COURSE_NO, "
          + "  to_char(examRoutine.EXAM_DATE, 'dd-mm-YYYY') EXAM_DATE, "
          + "  seatPlan.CURR_YEAR, "
          + "  seatPlan.CURR_SEMESTER, "
          + "  seatPlan.STUDENT_ID, "
          + "  STUDENT_RECORD.REGISTRATION_TYPE "
          + "FROM "
          + "  ( "
          + " "
          + "    SELECT "
          + "      ROOM_INFO.ROOM_NO, "
          + "      SEAT_PLAN.STUDENT_ID, "
          + "      SEAT_PLAN.SEMESTER_ID, "
          + "      STUDENTS.CURR_YEAR, "
          + "      students.CURR_SEMESTER, "
          + "      STUDENTS.PROGRAM_ID "
          + "    FROM "
          + "      SEAT_PLAN, "
          + "      STUDENTS, "
          + "      ROOM_INFO "
          + "    WHERE "
          + "      SEAT_PLAN.STUDENT_ID = STUDENTS.STUDENT_ID AND "
          + "      SEAT_PLAN.SEMESTER_ID = ? AND "
          + "      SEAT_PLAN.EXAM_TYPE = ? AND "
          + "      SEAT_PLAN.ROOM_ID = ROOM_INFO.ROOM_ID "
          + "    ORDER BY "
          + "      ROOM_INFO.ROOM_ID, STUDENTS.STUDENT_ID "
          + "  ) seatPlan, "
          + "  ( "
          + "    SELECT "
          + "      EXAM_ROUTINE.EXAM_DATE, "
          + "      EXAM_ROUTINE.EXAM_TYPE, "
          + "      MST_PROGRAM.PROGRAM_ID, "
          + "      MST_PROGRAM.PROGRAM_SHORT_NAME, "
          + "      MST_COURSE.COURSE_NO, "
          + "      MST_COURSE.COURSE_TITLE, "
          + "      MST_COURSE.COURSE_ID, "
          + "      MST_COURSE.YEAR, "
          + "      MST_COURSE.SEMESTER "
          + "    FROM EXAM_ROUTINE, "
          + "      MST_PROGRAM, "
          + "      MST_COURSE "
          + "    WHERE "
          + "      EXAM_ROUTINE.EXAM_TYPE = ? "
          + "      AND EXAM_ROUTINE.SEMESTER = ? "
          + "      AND MST_COURSE.COURSE_ID = EXAM_ROUTINE.COURSE_ID "
          + "      AND MST_PROGRAM.PROGRAM_ID = EXAM_ROUTINE.PROGRAM_ID "
          + "    ORDER BY "
          + "      EXAM_ROUTINE.EXAM_DATE, "
          + "      MST_PROGRAM.PROGRAM_ID, "
          + "      MST_COURSE.COURSE_NO "
          + "  ) examRoutine, UG_REGISTRATION_RESULT, STUDENT_RECORD "
          + "WHERE "
          + "  seatPlan.PROGRAM_ID = examRoutine.PROGRAM_ID AND "
          + "  seatPlan.CURR_YEAR = examRoutine.YEAR AND "
          + "  seatPlan.CURR_SEMESTER = examRoutine.SEMESTER AND "
          + "  UG_REGISTRATION_RESULT.COURSE_ID = examRoutine.COURSE_ID AND "
          + "  UG_REGISTRATION_RESULT.STUDENT_ID = seatPlan.STUDENT_ID AND "
          + "  UG_REGISTRATION_RESULT.SEMESTER_ID = seatPlan.SEMESTER_ID AND "
          + "  UG_REGISTRATION_RESULT.EXAM_TYPE = examRoutine.EXAM_TYPE AND "
          + "  STUDENT_RECORD.SEMESTER_ID = seatPlan.SEMESTER_ID AND "
          + "  STUDENT_RECORD.STUDENT_ID = seatPlan.STUDENT_ID "
          + "ORDER BY "
          + "  seatPlan.ROOM_NO, seatPlan.PROGRAM_ID, examRoutine.EXAM_DATE, examRoutine.COURSE_NO, seatPlan.CURR_YEAR, seatPlan.CURR_SEMESTER, to_number(seatPlan.STUDENT_ID)";

  String SELECT_ALL_ATTENDENCE_SHEET_CCI =
      "SELECT "
          + "  seatPlan.ROOM_NO, "
          + "  examRoutine.PROGRAM_SHORT_NAME, "
          + "  examRoutine.COURSE_TITLE, "
          + "  examRoutine.COURSE_NO, "
          + "  to_char(examRoutine.EXAM_DATE, 'dd-mm-YYYY') EXAM_DATE, "
          + "  examRoutine.YEAR     AS                      curr_year, "
          + "  examRoutine.SEMESTER AS                      curr_semester, "
          + "  seatPlan.STUDENT_ID, "
          + "  'R'                  AS                      registration_type "
          + "FROM "
          + "  (SELECT "
          + "     ROOM_INFO.ROOM_NO, "
          + "     SEAT_PLAN.STUDENT_ID, "
          + "     SEAT_PLAN.SEMESTER_ID, "
          + "     STUDENTS.CURR_YEAR, "
          + "     students.CURR_SEMESTER, "
          + "     STUDENTS.PROGRAM_ID "
          + "   FROM SEAT_PLAN, STUDENTS, ROOM_INFO "
          + "   WHERE SEAT_PLAN.STUDENT_ID = STUDENTS.STUDENT_ID AND SEAT_PLAN.SEMESTER_ID = ? AND "
          + "         SEAT_PLAN.EXAM_TYPE = ? AND SEAT_PLAN.ROOM_ID = ROOM_INFO.ROOM_ID "
          + "   ORDER BY "
          + "     ROOM_INFO.ROOM_ID, STUDENTS.STUDENT_ID) seatPlan, (SELECT "
          + "                                                          EXAM_ROUTINE.EXAM_DATE, "
          + "                                                          EXAM_ROUTINE.EXAM_TYPE, "
          + "                                                          MST_PROGRAM.PROGRAM_ID, "
          + "                                                          MST_PROGRAM.PROGRAM_SHORT_NAME, "
          + "                                                          MST_COURSE.COURSE_NO, "
          + "                                                          MST_COURSE.COURSE_TITLE, "
          + "                                                          MST_COURSE.COURSE_ID, "
          + "                                                          MST_COURSE.YEAR, "
          + "                                                          MST_COURSE.SEMESTER "
          + "                                                        FROM EXAM_ROUTINE, MST_PROGRAM, MST_COURSE "
          + "                                                        WHERE "
          + "                                                          EXAM_ROUTINE.EXAM_TYPE = ? AND EXAM_ROUTINE.SEMESTER = ? AND "
          + "                                                          EXAM_ROUTINE.EXAM_DATE = to_date(?, 'dd-mm-yyyy') "
          + "                                                          AND MST_COURSE.COURSE_ID = EXAM_ROUTINE.COURSE_ID "
          + "                                                          AND MST_PROGRAM.PROGRAM_ID = EXAM_ROUTINE.PROGRAM_ID "
          + "                                                        ORDER BY "
          + "                                                          EXAM_ROUTINE.EXAM_DATE, MST_PROGRAM.PROGRAM_ID, "
          + "                                                          MST_COURSE.COURSE_NO "
          + "                                                       ) examRoutine, UG_REGISTRATION_RESULT " + "WHERE "
          + "  seatPlan.PROGRAM_ID = examRoutine.PROGRAM_ID AND "
          + "  UG_REGISTRATION_RESULT.COURSE_ID = examRoutine.COURSE_ID AND "
          + "  UG_REGISTRATION_RESULT.STUDENT_ID = seatPlan.STUDENT_ID AND "
          + "  UG_REGISTRATION_RESULT.SEMESTER_ID = seatPlan.SEMESTER_ID AND "
          + "  UG_REGISTRATION_RESULT.EXAM_TYPE = examRoutine.EXAM_TYPE " + "ORDER BY "
          + "  seatPlan.ROOM_NO, seatPlan.PROGRAM_ID, examRoutine.EXAM_DATE, examRoutine.COURSE_NO, examRoutine.YEAR, "
          + "  examRoutine.SEMESTER, to_number(seatPlan.STUDENT_ID)";

  String SELECT_ALL_TOP_SHEET =
      "SELECT "
          + "  seatPlan.ROOM_NO, "
          + "  examRoutine.PROGRAM_LONG_NAME, "
          + "  examRoutine.COURSE_TITLE, "
          + "  examRoutine.COURSE_NO, "
          + "  examRoutine.COURSE_ID, "
          + "  to_char(examRoutine.EXAM_DATE, 'dd-mm-YYYY') EXAM_DATE, "
          + "  seatPlan.CURR_YEAR, "
          + "  seatPlan.CURR_SEMESTER, "
          + "  seatPlan.STUDENT_ID, "
          + "  STUDENT_RECORD.REGISTRATION_TYPE "
          + "FROM (SELECT "
          + "        ROOM_INFO.ROOM_NO, "
          + "        SEAT_PLAN.STUDENT_ID, "
          + "        SEAT_PLAN.SEMESTER_ID, "
          + "        STUDENTS.CURR_YEAR, "
          + "        students.CURR_SEMESTER, "
          + "        STUDENTS.PROGRAM_ID "
          + "      FROM SEAT_PLAN, "
          + "        STUDENTS, ROOM_INFO "
          + "      WHERE "
          + "        SEAT_PLAN.STUDENT_ID = STUDENTS.STUDENT_ID AND "
          + "        SEAT_PLAN.SEMESTER_ID = ? AND SEAT_PLAN.EXAM_TYPE = ? AND "
          + "        SEAT_PLAN.ROOM_ID = ROOM_INFO.ROOM_ID "
          + "      ORDER BY "
          + "        ROOM_INFO.ROOM_ID, STUDENTS.STUDENT_ID) seatPlan, ( "
          + "                                                            SELECT "
          + "                                                              EXAM_ROUTINE.EXAM_DATE, "
          + "                                                              EXAM_ROUTINE.EXAM_TYPE, "
          + "                                                              MST_PROGRAM.PROGRAM_ID, "
          + "                                                              MST_PROGRAM.PROGRAM_LONG_NAME, "
          + "                                                              MST_COURSE.COURSE_NO, "
          + "                                                              MST_COURSE.COURSE_ID, "
          + "                                                              MST_COURSE.COURSE_TITLE, "
          + "                                                              MST_COURSE.YEAR, "
          + "                                                              MST_COURSE.SEMESTER "
          + "                                                            FROM EXAM_ROUTINE, MST_PROGRAM, MST_COURSE "
          + "                                                            WHERE EXAM_ROUTINE.EXAM_TYPE = ? "
          + "                                                                  AND EXAM_ROUTINE.SEMESTER = ? "
          + "                                                                  AND MST_COURSE.COURSE_ID = EXAM_ROUTINE.COURSE_ID "
          + "                                                                  AND MST_PROGRAM.PROGRAM_ID = EXAM_ROUTINE.PROGRAM_ID "
          + "                                                            ORDER BY "
          + "                                                              EXAM_ROUTINE.EXAM_DATE, MST_PROGRAM.PROGRAM_ID, "
          + "                                                              MST_COURSE.COURSE_NO) examRoutine, UG_REGISTRATION_RESULT, "
          + "  STUDENT_RECORD "
          + "WHERE seatPlan.PROGRAM_ID = examRoutine.PROGRAM_ID AND "
          + "      seatPlan.CURR_YEAR = examRoutine.YEAR AND "
          + "      seatPlan.CURR_SEMESTER = examRoutine.SEMESTER AND "
          + "      UG_REGISTRATION_RESULT.COURSE_ID = examRoutine.COURSE_ID AND "
          + "      UG_REGISTRATION_RESULT.STUDENT_ID = seatPlan.STUDENT_ID AND "
          + "      UG_REGISTRATION_RESULT.SEMESTER_ID = seatPlan.SEMESTER_ID AND "
          + "      UG_REGISTRATION_RESULT.EXAM_TYPE = examRoutine.EXAM_TYPE AND "
          + "      STUDENT_RECORD.SEMESTER_ID = seatPlan.SEMESTER_ID AND "
          + "      STUDENT_RECORD.STUDENT_ID = seatPlan.STUDENT_ID "
          + "ORDER BY "
          + "  seatPlan.PROGRAM_ID, examRoutine.COURSE_NO,seatPlan.CURR_YEAR, seatPlan.CURR_SEMESTER, to_number(seatPlan.STUDENT_ID)";

  String SELECT_ALL_TOP_SHEET_CCI =
      "SELECT  "
          + "  seatPlan.ROOM_NO,  "
          + "  examRoutine.PROGRAM_LONG_NAME,  "
          + "  examRoutine.COURSE_TITLE,  "
          + "  examRoutine.COURSE_NO,  "
          + "  examRoutine.COURSE_ID,  "
          + "  to_char(examRoutine.EXAM_DATE, 'dd-mm-YYYY') EXAM_DATE,  "
          + "  seatPlan.CURR_YEAR,  "
          + "  seatPlan.CURR_SEMESTER,  "
          + "  seatPlan.STUDENT_ID,'R' REGISTRATION_TYPE  "
          + "FROM (SELECT  "
          + "        ROOM_INFO.ROOM_NO,  "
          + "        SEAT_PLAN.STUDENT_ID,  "
          + "        SEAT_PLAN.SEMESTER_ID,  "
          + "        STUDENTS.CURR_YEAR,  "
          + "        students.CURR_SEMESTER,  "
          + "        STUDENTS.PROGRAM_ID  "
          + "      FROM SEAT_PLAN,  "
          + "        STUDENTS, ROOM_INFO  "
          + "      WHERE  "
          + "        SEAT_PLAN.STUDENT_ID = STUDENTS.STUDENT_ID AND  "
          + "        SEAT_PLAN.SEMESTER_ID = ? AND SEAT_PLAN.EXAM_TYPE = ? AND  "
          + "        SEAT_PLAN.ROOM_ID = ROOM_INFO.ROOM_ID  "
          + "      ORDER BY  "
          + "        ROOM_INFO.ROOM_ID, STUDENTS.STUDENT_ID) seatPlan, (  "
          + "                                                            SELECT  "
          + "                                                              EXAM_ROUTINE.EXAM_DATE,  "
          + "                                                              EXAM_ROUTINE.EXAM_TYPE,  "
          + "                                                              MST_PROGRAM.PROGRAM_ID,  "
          + "                                                              MST_PROGRAM.PROGRAM_LONG_NAME,  "
          + "                                                              MST_COURSE.COURSE_NO,  "
          + "                                                              MST_COURSE.COURSE_ID,  "
          + "                                                              MST_COURSE.COURSE_TITLE,  "
          + "                                                              MST_COURSE.YEAR,  "
          + "                                                              MST_COURSE.SEMESTER  "
          + "                                                            FROM EXAM_ROUTINE, MST_PROGRAM, MST_COURSE  "
          + "                                                            WHERE EXAM_ROUTINE.EXAM_TYPE = ?  "
          + "                                                                  AND EXAM_ROUTINE.SEMESTER = ?  "
          + "                                                                  AND MST_COURSE.COURSE_ID = EXAM_ROUTINE.COURSE_ID  "
          + "                                                                  AND MST_PROGRAM.PROGRAM_ID = EXAM_ROUTINE.PROGRAM_ID  "
          + "                                                            ORDER BY  "
          + "                                                              EXAM_ROUTINE.EXAM_DATE, MST_PROGRAM.PROGRAM_ID,  "
          + "                                                              MST_COURSE.COURSE_NO) examRoutine, UG_REGISTRATION_RESULT  "
          + "WHERE seatPlan.PROGRAM_ID = examRoutine.PROGRAM_ID AND  "
          + "      seatPlan.CURR_YEAR = examRoutine.YEAR AND  "
          + "      seatPlan.CURR_SEMESTER = examRoutine.SEMESTER AND  "
          + "      UG_REGISTRATION_RESULT.COURSE_ID = examRoutine.COURSE_ID AND  "
          + "      UG_REGISTRATION_RESULT.STUDENT_ID = seatPlan.STUDENT_ID AND  "
          + "      UG_REGISTRATION_RESULT.SEMESTER_ID = seatPlan.SEMESTER_ID AND  "
          + "      UG_REGISTRATION_RESULT.EXAM_TYPE = examRoutine.EXAM_TYPE  "
          + "ORDER BY  "
          + "  seatPlan.PROGRAM_ID, examRoutine.COURSE_NO, seatPlan.CURR_YEAR, seatPlan.CURR_SEMESTER, to_number(seatPlan.STUDENT_ID)";

  String SELECT_ALL_ATTENDENCE_SHEET_EXAM_DATE = "SELECT " + "  seatPlan.ROOM_NO, "
      + "  examRoutine.PROGRAM_SHORT_NAME, " + "  examRoutine.COURSE_TITLE, " + "  examRoutine.COURSE_NO, "
      + "  to_char(examRoutine.EXAM_DATE, 'dd-mm-YYYY') EXAM_DATE, " + "  seatPlan.CURR_YEAR, "
      + "  seatPlan.CURR_SEMESTER, " + "  seatPlan.STUDENT_ID, " + "  STUDENT_RECORD.REGISTRATION_TYPE " + "FROM "
      + "  ( " + " " + "    SELECT " + "      ROOM_INFO.ROOM_NO, " + "      SEAT_PLAN.STUDENT_ID, "
      + "      SEAT_PLAN.SEMESTER_ID, " + "      STUDENTS.CURR_YEAR, " + "      students.CURR_SEMESTER, "
      + "      STUDENTS.PROGRAM_ID " + "    FROM " + "      SEAT_PLAN, " + "      STUDENTS, " + "      ROOM_INFO "
      + "    WHERE " + "      SEAT_PLAN.STUDENT_ID = STUDENTS.STUDENT_ID AND " + "      SEAT_PLAN.SEMESTER_ID = ? AND "
      + "      SEAT_PLAN.EXAM_TYPE = ? AND " + "      SEAT_PLAN.ROOM_ID = ROOM_INFO.ROOM_ID " + "    ORDER BY "
      + "      ROOM_INFO.ROOM_ID, STUDENTS.STUDENT_ID " + "  ) seatPlan, " + "  ( " + "    SELECT "
      + "      EXAM_ROUTINE.EXAM_DATE, " + "      EXAM_ROUTINE.EXAM_TYPE, " + "      MST_PROGRAM.PROGRAM_ID, "
      + "      MST_PROGRAM.PROGRAM_SHORT_NAME, " + "      MST_COURSE.COURSE_NO, " + "      MST_COURSE.COURSE_TITLE, "
      + "      MST_COURSE.COURSE_ID, " + "      MST_COURSE.YEAR, " + "      MST_COURSE.SEMESTER "
      + "    FROM EXAM_ROUTINE, " + "      MST_PROGRAM, " + "      MST_COURSE " + "    WHERE "
      + "      EXAM_ROUTINE.EXAM_TYPE = ? " + "      AND EXAM_ROUTINE.SEMESTER = ? AND "
      + "        EXAM_ROUTINE.EXAM_DATE=to_date(?,'dd-mm-yyyy') "
      + "      AND MST_COURSE.COURSE_ID = EXAM_ROUTINE.COURSE_ID "
      + "      AND MST_PROGRAM.PROGRAM_ID = EXAM_ROUTINE.PROGRAM_ID " + "    ORDER BY "
      + "      EXAM_ROUTINE.EXAM_DATE, " + "      MST_PROGRAM.PROGRAM_ID, " + "      MST_COURSE.COURSE_NO "
      + "  ) examRoutine, UG_REGISTRATION_RESULT, STUDENT_RECORD " + "WHERE "
      + "  seatPlan.PROGRAM_ID = examRoutine.PROGRAM_ID AND " + "  seatPlan.CURR_YEAR = examRoutine.YEAR AND "
      + "  seatPlan.CURR_SEMESTER = examRoutine.SEMESTER AND "
      + "  UG_REGISTRATION_RESULT.COURSE_ID = examRoutine.COURSE_ID AND "
      + "  UG_REGISTRATION_RESULT.STUDENT_ID = seatPlan.STUDENT_ID AND "
      + "  UG_REGISTRATION_RESULT.SEMESTER_ID = seatPlan.SEMESTER_ID AND "
      + "  UG_REGISTRATION_RESULT.EXAM_TYPE = examRoutine.EXAM_TYPE AND "
      + "  STUDENT_RECORD.SEMESTER_ID = seatPlan.SEMESTER_ID AND "
      + "  STUDENT_RECORD.STUDENT_ID = seatPlan.STUDENT_ID " + "ORDER BY "
      + "  seatPlan.ROOM_NO, seatPlan.PROGRAM_ID, examRoutine.EXAM_DATE, examRoutine.COURSE_NO, seatPlan.CURR_YEAR, "
      + "  seatPlan.CURR_SEMESTER, to_number(seatPlan.STUDENT_ID)";

  String SELECT_ALL_ATTENDENCE_SHEET_EXAM_DATE_CCI =
      "SELECT "
          + "  room_no, "
          + "  PROGRAM_SHORT_NAME, "
          + "  course_title, "
          + "  course_no, "
          + "  to_char(exam_date, 'dd-mm-yyyy') as exam_date, "
          + "  YEAR     AS curr_year, "
          + "  SEMESTER AS curr_semester, "
          + "  SEAT_PLAN.STUDENT_ID, "
          + "  'R'      AS registration_type "
          + "FROM SEAT_PLAN, ROOM_INFO, MST_PROGRAM, MST_COURSE, STUDENTS "
          + "WHERE SEAT_PLAN.SEMESTER_ID = ? AND EXAM_TYPE = 2 AND EXAM_DATE = to_date(?, 'dd-mm-yyyy') AND "
          + "      SEAT_PLAN.course_id = MST_COURSE.COURSE_ID AND SEAT_PLAN.STUDENT_ID = students.STUDENT_ID and room_info.room_id=seat_plan.room_id AND "
          + "      students.PROGRAM_ID = MST_PROGRAM.PROGRAM_ID ORDER BY SEAT_PLAN.ROOM_ID,MST_PROGRAM.PROGRAM_ID,COURSE_NO,YEAR,SEMESTER, seat_plan.student_id";

  String SELECT_ALL_TOP_SHEET_EXAM_DATE =
      "SELECT "
          + "  seatPlan.ROOM_NO, "
          + "  examRoutine.PROGRAM_LONG_NAME, "
          + "  examRoutine.COURSE_TITLE, "
          + "  examRoutine.COURSE_NO, "
          + "  examRoutine.COURSE_ID, "
          + "  to_char(examRoutine.EXAM_DATE, 'dd-mm-YYYY') EXAM_DATE, "
          + "  seatPlan.CURR_YEAR, "
          + "  seatPlan.CURR_SEMESTER, "
          + "  seatPlan.STUDENT_ID, "
          + "  STUDENT_RECORD.REGISTRATION_TYPE "
          + "FROM (SELECT "
          + "        ROOM_INFO.ROOM_NO, "
          + "        SEAT_PLAN.STUDENT_ID, "
          + "        SEAT_PLAN.SEMESTER_ID, "
          + "        STUDENTS.CURR_YEAR, "
          + "        students.CURR_SEMESTER, "
          + "        STUDENTS.PROGRAM_ID "
          + "      FROM SEAT_PLAN, "
          + "        STUDENTS, ROOM_INFO "
          + "      WHERE "
          + "        SEAT_PLAN.STUDENT_ID = STUDENTS.STUDENT_ID AND "
          + "        SEAT_PLAN.SEMESTER_ID = ? AND SEAT_PLAN.EXAM_TYPE = ? AND "
          + "        SEAT_PLAN.ROOM_ID = ROOM_INFO.ROOM_ID "
          + "      ORDER BY "
          + "        ROOM_INFO.ROOM_ID, STUDENTS.STUDENT_ID) seatPlan, ( "
          + "                                                            SELECT "
          + "                                                              EXAM_ROUTINE.EXAM_DATE, "
          + "                                                              EXAM_ROUTINE.EXAM_TYPE, "
          + "                                                              MST_PROGRAM.PROGRAM_ID, "
          + "                                                              MST_PROGRAM.PROGRAM_LONG_NAME, "
          + "                                                              MST_COURSE.COURSE_NO, "
          + "                                                              MST_COURSE.COURSE_ID, "
          + "                                                              MST_COURSE.COURSE_TITLE, "
          + "                                                              MST_COURSE.YEAR, "
          + "                                                              MST_COURSE.SEMESTER "
          + "                                                            FROM EXAM_ROUTINE, MST_PROGRAM, MST_COURSE "
          + "                                                            WHERE EXAM_ROUTINE.EXAM_TYPE = ? "
          + "                                                                  AND EXAM_ROUTINE.SEMESTER = ? "
          + "                                                                  AND Exam_routine.exam_date = to_date(?, 'DD-MM-YYYY') "
          + "                                                                  AND MST_COURSE.COURSE_ID = EXAM_ROUTINE.COURSE_ID "
          + "                                                                  AND MST_PROGRAM.PROGRAM_ID = EXAM_ROUTINE.PROGRAM_ID "
          + "                                                            ORDER BY "
          + "                                                              EXAM_ROUTINE.EXAM_DATE, MST_PROGRAM.PROGRAM_ID, "
          + "                                                              MST_COURSE.COURSE_NO) examRoutine, UG_REGISTRATION_RESULT, "
          + "  STUDENT_RECORD "
          + "WHERE seatPlan.PROGRAM_ID = examRoutine.PROGRAM_ID AND "
          + "      seatPlan.CURR_YEAR = examRoutine.YEAR AND "
          + "      seatPlan.CURR_SEMESTER = examRoutine.SEMESTER AND "
          + "      UG_REGISTRATION_RESULT.COURSE_ID = examRoutine.COURSE_ID AND "
          + "      UG_REGISTRATION_RESULT.STUDENT_ID = seatPlan.STUDENT_ID AND "
          + "      UG_REGISTRATION_RESULT.SEMESTER_ID = seatPlan.SEMESTER_ID AND "
          + "      UG_REGISTRATION_RESULT.EXAM_TYPE = examRoutine.EXAM_TYPE AND "
          + "      STUDENT_RECORD.SEMESTER_ID = seatPlan.SEMESTER_ID AND "
          + "      STUDENT_RECORD.STUDENT_ID = seatPlan.STUDENT_ID "
          + "ORDER BY "
          + "  seatPlan.PROGRAM_ID, examRoutine.COURSE_NO,seatPlan.CURR_YEAR, seatPlan.CURR_SEMESTER, to_number(seatPlan.STUDENT_ID)";

  String SELECT_ALL_TOP_SHEET_EXAM_DATE_CCI = "SELECT " + "  room_no, " + "  PROGRAM_LONG_NAME, " + "  course_title, "
      + "  course_no, " + "  SEAT_PLAN.course_id, " + "  to_char(exam_date, 'dd-mm-yyyy') AS exam_date, "
      + "  YEAR                             AS curr_year, " + "  SEMESTER                         AS curr_semester, "
      + "  SEAT_PLAN.STUDENT_ID, " + "  'R'                              AS registration_type "
      + "FROM SEAT_PLAN, ROOM_INFO, MST_PROGRAM, MST_COURSE, STUDENTS "
      + "WHERE SEAT_PLAN.SEMESTER_ID = ? AND EXAM_TYPE = 2 AND EXAM_DATE = to_date(?, 'dd-mm-yyyy') AND "
      + "      SEAT_PLAN.course_id = MST_COURSE.COURSE_ID AND SEAT_PLAN.STUDENT_ID = students.STUDENT_ID AND "
      + "      room_info.room_id = seat_plan.room_id AND " + "      students.PROGRAM_ID = MST_PROGRAM.PROGRAM_ID "
      + "ORDER BY SEAT_PLAN.ROOM_ID, MST_PROGRAM.PROGRAM_ID, COURSE_NO, YEAR, SEMESTER, seat_plan.student_id";

  String SELECT_ALL_STICKER = "SELECT " + "  seatPlans.ROOM_NO, " + "  MST_PROGRAM.PROGRAM_SHORT_NAME, "
      + "  STUDENTS.CURR_YEAR, " + "  STUDENTS.CURR_SEMESTER, " + "  STUDENTS.STUDENT_ID "
      + "FROM STUDENTS, MST_PROGRAM, ( " + "                              SELECT "
      + "                                room_no, " + "                                student_id "
      + "                              FROM SEAT_PLAN, ROOM_INFO "
      + "                              WHERE SEMESTER_ID = ? AND EXAM_TYPE = ? AND seat_plan.room_id = ? AND "
      + "                                    SEAT_PLAN.ROOM_ID = ROOM_INFO.ROOM_ID "
      + "                              ORDER BY ROOM_INFO.ROOM_ID " + "                            ) seatPlans "
      + "WHERE " + "  seatPlans.STUDENT_ID = STUDENTS.STUDENT_ID AND "
      + "  MST_PROGRAM.PROGRAM_ID = STUDENTS.PROGRAM_ID " + "ORDER BY seatPlans.ROOM_NO, "
      + "  MST_PROGRAM.PROGRAM_ID, curr_year, CURR_SEMESTER, to_number(seatPlans.STUDENT_ID)";

  public JdbcTemplate mJdbcTemplate;

  public PersistentSeatPlanReportDao(final JdbcTemplate pJdbcTemplate) {

    mJdbcTemplate = pJdbcTemplate;

  }

  @Override
  public List<SeatPlanReportDto> getSeatPlanDataForAttendenceSheet(Integer pSemesterId, Integer pExamType,
      String pExamDate) {
    String query = "";
    if(pExamDate.equals("NULL")) {
      if(pExamType == ExamType.SEMESTER_FINAL.getId())
        query = SELECT_ALL_ATTENDENCE_SHEET;
      else
        query = SELECT_ALL_ATTENDENCE_SHEET_CCI;
      return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType, pExamType, pSemesterId},
          new SeatPlanReportRowMapper());
    }
    else {
      if(pExamType == ExamType.SEMESTER_FINAL.getId())
        query = SELECT_ALL_ATTENDENCE_SHEET_EXAM_DATE;
      else
        query = SELECT_ALL_ATTENDENCE_SHEET_EXAM_DATE_CCI;
      return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamDate}, new SeatPlanReportRowMapper());
    }
  }

  @Override
  public List<SeatPlanReportDto> getSeatPlanDataForTopSheet(Integer pSemesterId, Integer pExamType, String pExamDate) {
    String query = "";
    if(pExamDate.equals("NULL")) {
      if(pExamType == ExamType.SEMESTER_FINAL.getId())
        query = SELECT_ALL_TOP_SHEET;
      else
        query = SELECT_ALL_TOP_SHEET_CCI;
      return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType, pExamType, pSemesterId},
          new SeatPlanRowMapperTopSheet());
    }
    else {
      if(pExamType == ExamType.SEMESTER_FINAL.getId())
        query = SELECT_ALL_TOP_SHEET_EXAM_DATE;
      else
        query = SELECT_ALL_TOP_SHEET_EXAM_DATE_CCI;
      return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamDate}, new SeatPlanRowMapperTopSheet());
    }
  }

  @Override
  public List<SeatPlanReportDto> getSeatPlanDataForSticker(Integer pSemesterId, Integer pExamType, String pExamDate,
      int pRoomId) {
    String query = SELECT_ALL_STICKER;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType, pRoomId}, new SeatPlanRowMapperSticker());
  }

  class SeatPlanReportRowMapper implements RowMapper<SeatPlanReportDto> {
    @Override
    public SeatPlanReportDto mapRow(ResultSet pResultSet, int pI) throws SQLException {
      SeatPlanReportDto seatPlan = new SeatPlanReportDto();
      seatPlan.setRoomNo(pResultSet.getString("room_no"));
      seatPlan.setProgramName(pResultSet.getString("program_short_name"));
      seatPlan.setCourseTitle(pResultSet.getString("course_title"));
      seatPlan.setCourseNo(pResultSet.getString("course_no"));
      seatPlan.setExamDate(pResultSet.getString("exam_date"));
      seatPlan.setCurrentYear(pResultSet.getInt("curr_year"));
      seatPlan.setCurrentSemester(pResultSet.getInt("curr_semester"));
      seatPlan.setStudentId(pResultSet.getString("student_id"));
      seatPlan.setStudentType(pResultSet.getString("registration_type"));
      return seatPlan;
    }
  }

  class SeatPlanRowMapperTopSheet implements RowMapper<SeatPlanReportDto> {
    @Override
    public SeatPlanReportDto mapRow(ResultSet pResultSet, int pI) throws SQLException {
      SeatPlanReportDto seatPlan = new SeatPlanReportDto();
      seatPlan.setRoomNo(pResultSet.getString("room_no"));
      seatPlan.setProgramName(pResultSet.getString("program_long_name"));
      seatPlan.setCourseTitle(pResultSet.getString("course_title"));
      seatPlan.setCourseNo(pResultSet.getString("course_no"));
      seatPlan.setCourseId(pResultSet.getString("course_id"));
      seatPlan.setExamDate(pResultSet.getString("exam_date"));
      seatPlan.setCurrentYear(pResultSet.getInt("curr_year"));
      seatPlan.setCurrentSemester(pResultSet.getInt("curr_semester"));
      seatPlan.setStudentId(pResultSet.getString("student_id"));
      seatPlan.setStudentType(pResultSet.getString("registration_type"));
      return seatPlan;
    }
  }

  class SeatPlanRowMapperSticker implements RowMapper<SeatPlanReportDto> {
    @Override
    public SeatPlanReportDto mapRow(ResultSet pResultSet, int pI) throws SQLException {
      SeatPlanReportDto seatPlan = new SeatPlanReportDto();
      seatPlan.setRoomNo(pResultSet.getString("room_no"));
      seatPlan.setProgramName(pResultSet.getString("program_short_name"));
      seatPlan.setCurrentYear(pResultSet.getInt("curr_year"));
      seatPlan.setCurrentSemester(pResultSet.getInt("curr_semester"));
      seatPlan.setStudentId(pResultSet.getString("student_id"));
      return seatPlan;
    }
  }

}
