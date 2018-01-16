package org.ums.persistent.dao;

import org.apache.commons.lang.WordUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.util.StringUtils;
import org.ums.decorator.ExamGradeDaoDecorator;
import org.ums.domain.model.dto.*;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.enums.*;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentExamGradeDao extends ExamGradeDaoDecorator {
  String UPDATE_ALL = "UPDATE MARKS_SUBMISSION_STATUS_CURR SET Status = ? , LAST_SUBMISSION_DATE_PREP= ?, "
      + "LAST_SUBMISSION_DATE_SCR=?, LAST_SUBMISSION_DATE_HEAD=?,  LAST_MODIFIED =  " + getLastModifiedSql()
      + " Where Course_Id=? and Semester_Id=? and Exam_Type=?";

  public int update(MutableMarksSubmissionStatus pMutable) {
    String query = UPDATE_ALL;

    return mJdbcTemplate.update(query, pMutable.getStatus().getId(), pMutable.getLastSubmissionDatePrep(),
        pMutable.getLastSubmissionDateScr(), pMutable.getLastSubmissionDateHead(), pMutable.getCourseId(),
        pMutable.getSemesterId(), pMutable.getExamType().getId());
    /*
     * List<MutableMarksSubmissionStatus> abc = new ArrayList<>(); abc.add(pMutable); int[] a =
     * mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
     * 
     * @Override public void setValues(PreparedStatement ps, int i) throws SQLException {
     * MutableMarksSubmissionStatus aa = abc.get(i); ps.setInt(1, aa.getSemesterId());
     * ps.setString(2, aa.getCourseId()); ps.setInt(3, aa.getStatus().getId()); ps.setInt(4,
     * aa.getExamType().getId()); ps.setString(5, null); ps.setString(6, null); ps.setDate(7, null);
     * ps.setInt(8, aa.getTotalPart()); ps.setInt(9, aa.getPartATotal()); ps.setInt(10,
     * aa.getPartBTotal()); ps.setInt(11, aa.getYear()); ps.setInt(12, aa.getAcademicSemester());
     * ps.setLong(13, aa.getId()); }
     * 
     * @Override public int getBatchSize() { return abc.size(); } });
     * 
     * return a.length;
     */
  }

  String SELECT_THEORY_MARKS =
      "Select UG_THEORY_MARKS.*,FULL_NAME From UG_THEORY_MARKS,STUDENTS  Where UG_THEORY_MARKS.STUDENT_ID=STUDENTS.STUDENT_ID AND UG_THEORY_MARKS.Semester_Id=? and Course_Id=? and Exam_Type=? ";
  String SELECT_SESSIONAL_MARKS =
      "Select UG_SESSIONAL_MARKS.*,FULL_NAME From UG_SESSIONAL_MARKS,STUDENTS  Where UG_SESSIONAL_MARKS.STUDENT_ID=STUDENTS.STUDENT_ID AND UG_SESSIONAL_MARKS.Semester_Id=? and Course_Id=? and Exam_Type=? ";

  String SELECT_PART_INFO =
      "Select MARKS_SUBMISSION_STATUS.*, LAST_SUBMISSION_DATE_PREP,LAST_SUBMISSION_DATE_SCR,LAST_SUBMISSION_DATE_HEAD, "
          + " COURSE_TYPE,COURSE_TITLE,COURSE_NO,CRHR,SHORT_NAME,LONG_NAME,SEMESTER_NAME,PROGRAM_SHORT_NAME,PROGRAM_LONG_NAME  "
          + "From MARKS_SUBMISSION_STATUS,MST_COURSE,COURSE_SYLLABUS_MAP,MST_SYLLABUS,MST_PROGRAM,MST_DEPT_OFFICE,MST_SEMESTER "
          + " Where MST_COURSE.Course_Id=MARKS_SUBMISSION_STATUS.Course_Id  "
          + "AND MST_COURSE.Course_Id = COURSE_SYLLABUS_MAP.Course_Id "
          + "AND COURSE_SYLLABUS_MAP.SYLLABUS_ID = MST_SYLLABUS.SYLLABUS_ID "
          + " And MST_SYLLABUS.PROGRAM_ID=MST_PROGRAM.PROGRAM_ID "
          + " And MST_PROGRAM.DEPT_ID=MST_DEPT_OFFICE.DEPT_ID "
          + " And MST_SEMESTER.SEMESTER_ID=MARKS_SUBMISSION_STATUS.SEMESTER_ID "
          + " And MARKS_SUBMISSION_STATUS.Semester_Id=? and MARKS_SUBMISSION_STATUS.Course_Id=? and Exam_Type=?  ";

  String UPDATE_PART_INFO =
      "Update MARKS_SUBMISSION_STATUS_CURR Set TOTAL_PART=?,PART_A_TOTAL=?,PART_B_TOTAL=? Where SEMESTER_ID=? and COURSE_ID=? and EXAM_TYPE=? and Status=0";
  String UPDATE_MARKS_SUBMISSION_STATUS =
      "Update MARKS_SUBMISSION_STATUS_CURR Set STATUS=? Where SEMESTER_ID=? and COURSE_ID=? and EXAM_TYPE=? ";

  String UPDATE_THEORY_MARKS =
      "Update  UG_THEORY_MARKS_CURR Set Quiz=?,Class_Performance=?,Part_A=?,Part_A_Addi_Info=?,Part_B=?,Part_B_Addi_Info=?,Total=?,Grade_Letter=?,Status=? "
          + " Where Semester_Id=? And Course_Id=? and Exam_Type=? and Student_Id=? and status in (0,1)";
  // None, Submit grades are allowed to update
  String UPDATE_THEORY_MARKS_RECHECK =
      "Update  UG_THEORY_MARKS_CURR Set Quiz=?,Class_Performance=?,Part_A=?,Part_A_Addi_Info=?,Part_B=?,Part_B_Addi_Info=?, Total=?,Grade_Letter=?,Status=? "
          + " Where Semester_Id=? And Course_Id=? and Exam_Type=? and Student_Id=? and recheck_status=1";

  String UPDATE_SESSIONAL_MARKS = "Update  UG_SESSIONAL_MARKS_CURR Set Total=?,Grade_Letter=?,Status=? "
      + " Where Semester_Id=? And Course_Id=? and Exam_Type=? and Student_Id=? and status in (0,1)";
  // None, Submit grades are allowed to update
  String UPDATE_SESSIONAL_MARKS_RECHECK = "Update  UG_SESSIONAL_MARKS_CURR Set Total=?,Grade_Letter=?,Status=? "
      + " Where Semester_Id=? And Course_Id=? and Exam_Type=? and Student_Id=? and recheck_status=1";

  String SELECT_GRADE_SUBMISSION_TABLE_TEACHER =
      "Select tmp5.*,Status,Exam_Type, LAST_SUBMISSION_DATE_PREP,LAST_SUBMISSION_DATE_SCR,LAST_SUBMISSION_DATE_HEAD From ( "
          + "Select tmp4.*,MVIEW_TEACHERS.TEACHER_NAME Scrutinizer_Name,getCourseTeacher(semester_id,course_id) Course_Teachers From "
          + "( "
          + "Select tmp3.*,MVIEW_TEACHERS.TEACHER_NAME Preparer_name From  "
          + "( "
          + "Select tmp2.*,Mst_Course.Course_Title,Program_Short_Name,MST_COURSE.COURSE_NO,Year,Semester from ( "
          + "Select semester_id,course_id,preparer preparer_id,scrutinizer scrutinizer_id From PREPARER_SCRUTINIZER Where Semester_Id=? And (Preparer=? or Scrutinizer=?) "
          + "Union "
          + "Select tmp1.semester_id,tmp1.course_id,preparer preparer_id,scrutinizer scrutinizer_id from ( "
          + "Select Semester_Id,Course_Id From COURSE_TEACHER Where Semester_Id=? and Teacher_Id=?)tmp1,PREPARER_SCRUTINIZER "
          + "Where tmp1.semester_id=PREPARER_SCRUTINIZER.semester_id(+) "
          + "and tmp1.course_id=PREPARER_SCRUTINIZER.course_id(+))tmp2,Mst_Course,Course_Syllabus_Map,Mst_Syllabus,Mst_Program "
          + "Where tmp2.Course_id=Mst_Course.Course_id " + "And Mst_Course.Course_Id=Course_Syllabus_Map.Course_id "
          + "And Course_Syllabus_Map.Syllabus_Id=Mst_Syllabus.SYLLABUS_ID "
          + "And MST_PROGRAM.PROGRAM_ID=MST_SYLLABUS.PROGRAM_ID " + ")tmp3,MVIEW_TEACHERS "
          + "Where tmp3.preparer_id=MVIEW_TEACHERS.teacher_id (+))tmp4,MVIEW_TEACHERS "
          + "Where tmp4.scrutinizer_id=MVIEW_TEACHERS.teacher_id (+) " + ")tmp5, Marks_Submission_Status "
          + "Where tmp5.course_id=MARKS_SUBMISSION_STATUS.COURSE_ID(+) "
          + "And tmp5.SEMESTER_ID=MARKS_SUBMISSION_STATUS.SEMESTER_ID(+)  " + "And Exam_Type=?";

  String SELECT_GRADE_SUBMISSION_TABLE_HEAD =
      "Select LAST_SUBMISSION_DATE_PREP,LAST_SUBMISSION_DATE_SCR,LAST_SUBMISSION_DATE_HEAD, Ms_Status.Semester_Id,Exam_Type,Mst_Course.Course_Id,Course_No,Course_Title ,CrHr,Course_Type,Course_Category,Offer_By,Ms_Status.Year,Ms_Status.Semester,COURSE_SYLLABUS_MAP.Syllabus_Id, "
          + "getCourseTeacher(Ms_Status.semester_id,Mst_Course.course_id) Course_Teachers, "
          + "MST_PROGRAM.PROGRAM_SHORT_NAME,getPreparerScrutinizer(Ms_Status.Semester_Id,Mst_Course.Course_Id,'P') PREPARER_NAME,0 preparer_id,0 scrutinizer_id, "
          + "getPreparerScrutinizer(Ms_Status.Semester_Id,Mst_Course.Course_Id,'S') SCRUTINIZER_NAME,STATUS  "
          + "From MARKS_SUBMISSION_STATUS Ms_Status,Mst_Course,COURSE_SYLLABUS_MAP,MST_SYLLABUS,MST_PROGRAM "
          + "Where Ms_Status.Semester_Id=? And Exam_Type=? "
          // + "And MST_PROGRAM.PROGRAM_ID =? " // No need. We will show all the courses either
          // offered by or offered to the current department
          + "And Ms_Status.Course_Id=Mst_Course.Course_Id "
          + "And Mst_Course.Course_Id=COURSE_SYLLABUS_MAP.Course_Id "
          + "AND MST_SYLLABUS.SYLLABUS_ID = COURSE_SYLLABUS_MAP.SYLLABUS_ID "
          + "And MST_SYLLABUS.PROGRAM_ID=MST_PROGRAM.PROGRAM_ID "
          + "And Offer_By in "
          + "(Select dept_id From MVIEW_Teachers  where Teacher_Id =?) ";

  String SELECT_GRADE_SUBMISSION_TABLE_CoE =
      "Select LAST_SUBMISSION_DATE_PREP,LAST_SUBMISSION_DATE_SCR,LAST_SUBMISSION_DATE_HEAD, Ms_Status.Semester_Id,Exam_Type,Mst_Course.Course_Id,Course_No,Course_Title ,CrHr,Course_Type,Course_Category,Offer_By,Ms_Status.Year,Ms_Status.Semester,COURSE_SYLLABUS_MAP.Syllabus_Id, "
          + "getCourseTeacher(Ms_Status.semester_id,Mst_Course.course_id) Course_Teachers, "
          + "MST_PROGRAM.PROGRAM_SHORT_NAME,getPreparerScrutinizer(Ms_Status.Semester_Id,Mst_Course.Course_Id,'P') PREPARER_NAME, "
          + "getPreparerScrutinizer(Ms_Status.Semester_Id,Mst_Course.Course_Id,'S') SCRUTINIZER_NAME, 0 preparer_id,0 scrutinizer_id, STATUS  "
          + "From MARKS_SUBMISSION_STATUS Ms_Status,Mst_Course,COURSE_SYLLABUS_MAP,MST_SYLLABUS,MST_PROGRAM "
          + "Where Ms_Status.Semester_Id=? And Exam_Type=? "
          + "And Ms_Status.Course_Id=Mst_Course.Course_Id "
          + "And Mst_Course.Course_Id=COURSE_SYLLABUS_MAP.Course_Id "
          + "AND MST_SYLLABUS.SYLLABUS_ID = COURSE_SYLLABUS_MAP.SYLLABUS_ID "
          + "And MST_SYLLABUS.PROGRAM_ID=MST_PROGRAM.PROGRAM_ID";

  String UPDATE_STATUS_SAVE_RECHECK =
      "Update %s Set RECHECK_STATUS=?  Where SEMESTER_ID=? And COURSE_ID=? And EXAM_TYPE=? And STUDENT_ID=? and "
          + " Status in (select regexp_substr(?,'[^,]+', 1, level) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";

  String UPDATE_STATUS_SAVE_APPROVE =
      "Update %s Set RECHECK_STATUS=?,STATUS=?  Where SEMESTER_ID=? And COURSE_ID=? And EXAM_TYPE=? And STUDENT_ID=? and "
          + " Status in  (select regexp_substr(?,'[^,]+', 1, level) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";

  String UPDATE_STATUS_RECHECK_RECHECK =
      "Update %s Set RECHECK_STATUS=?,STATUS=?   Where SEMESTER_ID=? And COURSE_ID=? And EXAM_TYPE=? And STUDENT_ID=? and "
          + " Status in (select regexp_substr(?,'[^,]+', 1, level) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";
  String UPDATE_STATUS_RECHECK_APPROVE =
      "Update %s Set RECHECK_STATUS=?,STATUS=?   Where SEMESTER_ID=? And COURSE_ID=? And EXAM_TYPE=? And STUDENT_ID=? and  "
          + " Status in (select regexp_substr(?,'[^,]+', 1, level) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";

  String UPDATE_STATUS_APPROVE =
      "Update %s Set RECHECK_STATUS=?, STATUS=?  Where SEMESTER_ID=? And COURSE_ID=? And EXAM_TYPE=? And STUDENT_ID=? and  "
          + " Status in (select regexp_substr(?,'[^,]+', 1, level) from dual connect by regexp_substr(?, '[^,]+', 1, level) is not null)";

  String CHECK_TEACHER_ROLE =
      "Select * From  "
          + "( "
          + "Select 'preparer' Role,1 Serial From PREPARER_SCRUTINIZER Where Preparer=? And Semester_Id=? and Course_Id=?  "
          + "Union "
          + "Select 'scrutinizer' Role,2 Serial From PREPARER_SCRUTINIZER Where Scrutinizer=? And Semester_Id=? and Course_Id=? "
          + "Union "
          + "Select 'courseteacher' Role,3 Role From Course_Teacher Where Teacher_Id =? and Semester_Id=? and Course_Id=? "
          + ")tmp1 Order by Serial";

  String CHECK_HEAD_ROLE =
      "Select 'head' Role, 1 Serial From ADDITIONAL_ROLE_PERMISSIONS Where User_Id=? and Role_Id=22";

  String CHECK_COE_ROLE = "Select 'coe' Role, 1 Serial From USERS Where User_Id=? and Role_Id=71 " + "Union "
      + "Select 'CoE' Role, 2 Serial From ADDITIONAL_ROLE_PERMISSIONS Where User_Id=?  And Role_Id=71";

  String CHECK_VC_ROLE = "Select 'vc' Role, 1 Serial From USERS Where User_Id=? and Role_Id=99 " + "Union "
      + "Select 'vc' Role, 2 Serial From ADDITIONAL_ROLE_PERMISSIONS Where User_Id=?  And Role_Id=99";

  String CHART_DATA =
      "Select Grade_Letter,sum(Total) Total, max(Color) Color From   "
          + "(  "
          + "select Grade_Letter,Count(nvl(Total,0)) Total,'' Color From %s Where Semester_Id=? And Course_Id=? and Exam_Type=? Group by Grade_Letter  "
          + "Union  "
          + "Select 'A+' Grade_Letter, 0 Total, '#FF0F00' Color From Dual  "
          + "Union  "
          + "Select 'A' Grade_Letter, 0 Total, '#FF6600' Color From Dual  "
          + "Union  "
          + "Select 'A-' Grade_Letter, 0 Total, '#FF9E01' Color From Dual  "
          + "Union  "
          + "Select 'B+' Grade_Letter, 0 Total, '#FCD202' Color From Dual  "
          + "Union  "
          + "Select 'B' Grade_Letter, 0 Total, '#F8FF01' Color From Dual  "
          + "Union  "
          + "Select 'B-' Grade_Letter, 0 Total, '#B0DE09' Color From Dual  "
          + "Union  "
          + "Select 'C+' Grade_Letter, 0 Total, '#04D215' Color From Dual  "
          + "Union  "
          + "Select 'C' Grade_Letter, 0 Total, '#0D8ECF' Color From Dual  "
          + "Union  "
          + "Select 'D' Grade_Letter, 0 Total, '#0D52D1' Color From Dual  "
          + "Union  "
          + "Select 'F' Grade_Letter, 0 Total, '#2A0CD0' Color From Dual  "
          + ")Tmp Group by Grade_Letter Order by Decode(Grade_Letter,'A+',1,'A',2,'A-',3,'B+',4,'B',5,'B-',6,'C+',7,'C',8,'D',9,'F',10)  ";

  String SELECT_EXAM_GRADE_DEAD_LINE_THEORY_BY_DATE =
      " SELECT "
          + "  to_char(EXAM_ROUTINE.EXAM_DATE, 'dd-mm-yyyy') Exam_date, "
          + "  MST_PROGRAM.PROGRAM_SHORT_NAME, "
          + "  MST_COURSE.COURSE_ID, "
          + "  MST_COURSE.COURSE_NO, "
          + "  MST_COURSE.COURSE_TITLE, "
          + "  MST_COURSE.CRHR, "
          + "  ugRegistrationResult.total_students, "
          + "  marksSubmissionStatus.ID, "
          + "  last_submission_date_prep, "
          + "  LAST_SUBMISSION_DATE_SCR, "
          + "  LAST_SUBMISSION_DATE_HEAD, LAST_SUBMISSION_DATE_COE "
          + "FROM EXAM_ROUTINE, MST_PROGRAM, "
          + "  MST_COURSE, (SELECT "
          + "                 COURSE_ID, "
          + "                 count(COURSE_ID) total_students "
          + "               FROM UG_REGISTRATION_RESULT "
          + "               WHERE SEMESTER_ID = ? AND EXAM_TYPE = ? "
          + "               GROUP BY COURSE_ID) ugRegistrationResult, (SELECT "
          + "                                                            ID, "
          + "                                                            SEMESTER_ID, "
          + "                                                            COURSE_ID, "
          + "                                                            last_submission_date_prep, "
          + "                                                            LAST_SUBMISSION_DATE_SCR, "
          + "                                                            LAST_SUBMISSION_DATE_HEAD,LAST_SUBMISSION_DATE_COE "
          + "                                                          FROM MARKS_SUBMISSION_STATUS "
          + "                                                          WHERE EXAM_TYPE = ?) marksSubmissionStatus "
          + "WHERE EXAM_ROUTINE.EXAM_DATE = to_date(?, 'dd-mm-yyyy') AND "
          + "      MST_PROGRAM.PROGRAM_ID = EXAM_ROUTINE.PROGRAM_ID AND "
          + "      MST_COURSE.COURSE_ID = EXAM_ROUTINE.COURSE_ID AND MST_COURSE.OFFER_BY = ? AND "
          + "      EXAM_ROUTINE.COURSE_ID = ugRegistrationResult.COURSE_ID AND "
          + "      exam_routine.SEMESTER = ? AND exam_routine.exam_type = ? AND "
          + "      marksSubmissionStatus.SEMESTER_ID = EXAM_ROUTINE.SEMESTER AND "
          + "      marksSubmissionStatus.COURSE_ID = EXAM_ROUTINE.COURSE_ID";

  String SELECT_EXAM_GRADE_DEAD_LINE_THEORY_ALL =
      "SELECT "
          + "  to_char(EXAM_ROUTINE.EXAM_DATE, 'dd-mm-yyyy') Exam_date, "
          + "  MST_PROGRAM.PROGRAM_SHORT_NAME, "
          + "  MST_COURSE.COURSE_ID, "
          + "  MST_COURSE.COURSE_NO, "
          + "  MST_COURSE.COURSE_TITLE, "
          + "  MST_COURSE.CRHR, "
          + "  ugRegistrationResult.total_students, "
          + "  marksSubmissionStatus.ID, "
          + "  last_submission_date_prep, "
          + "  LAST_SUBMISSION_DATE_SCR, "
          + "  LAST_SUBMISSION_DATE_HEAD, "
          + "  LAST_SUBMISSION_DATE_coe "
          + "FROM EXAM_ROUTINE, MST_PROGRAM, "
          + "  MST_COURSE, (SELECT "
          + "                 COURSE_ID, "
          + "                 count(COURSE_ID) total_students "
          + "               FROM UG_REGISTRATION_RESULT "
          + "               WHERE SEMESTER_ID = ? "
          + "               GROUP BY COURSE_ID) ugRegistrationResult, (SELECT "
          + "                                                            ID, "
          + "                                                            SEMESTER_ID, "
          + "                                                            COURSE_ID, "
          + "                                                            last_submission_date_prep, "
          + "                                                            LAST_SUBMISSION_DATE_SCR, "
          + "                                                            LAST_SUBMISSION_DATE_HEAD, "
          + "                                                            LAST_SUBMISSION_DATE_COE "
          + "                                                          FROM MARKS_SUBMISSION_STATUS) marksSubmissionStatus "
          + "WHERE MST_PROGRAM.PROGRAM_ID = EXAM_ROUTINE.PROGRAM_ID AND "
          + "      MST_COURSE.COURSE_ID = EXAM_ROUTINE.COURSE_ID AND MST_COURSE.OFFER_BY = ? AND "
          + "      EXAM_ROUTINE.COURSE_ID = ugRegistrationResult.COURSE_ID AND "
          + "      exam_routine.SEMESTER = ? AND exam_routine.exam_type = ? AND "
          + "      marksSubmissionStatus.SEMESTER_ID = EXAM_ROUTINE.SEMESTER AND "
          + "      marksSubmissionStatus.COURSE_ID = EXAM_ROUTINE.COURSE_ID AND mst_course.crhr != 0 "
          + "ORDER BY EXAM_ROUTINE.EXAM_DATE";

  String SELECT_ALL_EXAMGRADE_DEADLINE_SESSIONAL =
      "SELECT  "
          + "  MST_PROGRAM.PROGRAM_SHORT_NAME,  "
          + "  MST_COURSE.COURSE_ID,  "
          + "  MST_COURSE.COURSE_NO,  "
          + "  MST_COURSE.COURSE_TITLE,  "
          + "  MST_COURSE.CRHR,  "
          + "  ugRegistrationResult.COURSE_ID,  "
          + "  ugRegistrationResult.total_students,  "
          + "  MARKS_SUBMISSION_STATUS.ID,  "
          + "  MARKS_SUBMISSION_STATUS.LAST_SUBMISSION_DATE_prep,  "
          + "  MARKS_SUBMISSION_STATUS.LAST_SUBMISSION_DATE_scr,  "
          + "  MARKS_SUBMISSION_STATUS.LAST_SUBMISSION_DATE_head, MARKS_SUBMISSION_STATUS.LAST_SUBMISSION_DATE_COE  "
          + "FROM  "
          + "  (SELECT  "
          + "     UG_SESSIONAL_MARKS.COURSE_ID,  "
          + "     count(UG_SESSIONAL_MARKS.COURSE_ID) total_students,  "
          + "     UG_SESSIONAL_MARKS.EXAM_TYPE,  "
          + "     UG_SESSIONAL_MARKS.SEMESTER_ID  "
          + "   FROM UG_SESSIONAL_MARKS, MST_COURSE, MST_SYLLABUS, COURSE_SYLLABUS_MAP  "
          + "   WHERE UG_SESSIONAL_MARKS.SEMESTER_ID = ? AND EXAM_TYPE = ? AND  "
          + "         UG_SESSIONAL_MARKS.COURSE_ID = MST_COURSE.COURSE_ID AND MST_COURSE.OFFER_BY = ? AND  "
          + "         MST_COURSE.COURSE_TYPE = 2 AND MST_SYLLABUS.SYLLABUS_ID = COURSE_SYLLABUS_MAP.SYLLABUS_ID AND  "
          + "         COURSE_SYLLABUS_MAP.COURSE_ID = MST_COURSE.COURSE_ID  "
          + "   GROUP BY UG_SESSIONAL_MARKS.COURSE_ID, UG_SESSIONAL_MARKS.EXAM_TYPE,  "
          + "     UG_SESSIONAL_MARKS.SEMESTER_ID) ugRegistrationResult, MARKS_SUBMISSION_STATUS,  "
          + "  MST_COURSE, MST_PROGRAM, MST_SYLLABUS, COURSE_SYLLABUS_MAP  "
          + "WHERE ugRegistrationResult.SEMESTER_ID = MARKS_SUBMISSION_STATUS.SEMESTER_ID  "
          + "      AND ugRegistrationResult.COURSE_ID = MARKS_SUBMISSION_STATUS.COURSE_ID AND  "
          + "      MST_COURSE.COURSE_ID = ugRegistrationResult.COURSE_ID AND MST_COURSE.CRHR != 0 AND  "
          + "      MST_COURSE.COURSE_ID = COURSE_SYLLABUS_MAP.COURSE_ID AND  "
          + "      COURSE_SYLLABUS_MAP.SYLLABUS_ID = MST_SYLLABUS.SYLLABUS_ID AND MST_SYLLABUS.PROGRAM_ID = MST_PROGRAM.PROGRAM_ID  "
          + "ORDER BY MST_COURSE.YEAR, MST_COURSE.SEMESTER";

  String INSERT_THEORY_LOG =
      "Insert InTo UG_THEORY_MARKS_LOG_CURR(USER_ID,ROLE, SEMESTER_ID,COURSE_ID, STUDENT_ID, EXAM_TYPE, QUIZ, CLASS_PERFORMANCE, "
          + "PART_A, PART_B, TOTAL, GRADE_LETTER, STATUS,RECHECK_STATUS) " + "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

  String INSERT_SESSIONAL_LOG = "Insert InTo UG_SESSIONAL_MARKS_LOG_CURR(USER_ID,ROLE,SEMESTER_ID, COURSE_ID,  "
      + "STUDENT_ID, EXAM_TYPE, TOTAL, GRADE_LETTER,STATUS, RECHECK_STATUS)  " + "Values(?,?,?,?,?,?,?,?,?,?) ";

  String INSERT_MARKS_SUBMISSION_STATUS_LOG =
      "Insert Into MARKS_SUBMISSION_SLOG_CURR(USER_ID, ROLE,SEMESTER_ID, COURSE_ID, EXAM_TYPE,STATUS) "
          + " Values(?,?,?,?,?,?) ";

  String THEORY_SUBMIT_COUNT =
      "Select Count(Student_Id) From UG_THEORY_MARKS Where Semester_Id=? and Course_Id=? and Exam_Type=? and Status in (0,1) ";
  String SESSIONAL_SUBMIT_COUNT =
      "Select Count(Student_Id) From UG_SESSIONAL_MARKS Where Semester_Id=? and Course_Id=? and Exam_Type=? and Status in (0,1)";

  String SELECT_MARKS_SUBMISSION_STATUS_LOG = "  SELECT MARKS_SUBMISSION_SLOG.User_Id, "
      + "         EMPLOYEES.EMPLOYEE_NAME, ROLE, " + "         TO_CHAR (MARKS_SUBMISSION_SLOG.Inserted_On, "
      + "                  'DD-MM-YYYY HH:MI:SS AM') " + "            Inserted_On, "
      + "         MARKS_SUBMISSION_SLOG.Status " + "    FROM MARKS_SUBMISSION_SLOG, EMPLOYEES, USERS "
      + "    WHERE     MARKS_SUBMISSION_SLOG.User_Id = Users.User_Id "
      + "         AND USERS.EMPLOYEE_ID = EMPLOYEES.EMPLOYEE_ID " + "         AND Semester_Id = ?"
      + "         AND Course_Id = ? " + "         AND Exam_Type = ? " + "ORDER BY Inserted_On desc";

  String SELECT_THEORY_LOG =
      "Select UG_THEORY_MARKS_LOG.User_Id,Employee_Name, ROLE,  "
          + "TO_CHAR (UG_THEORY_MARKS_LOG.Inserted_On,'DD-MM-YYYY HH:MI:SS AM')  Inserted_On, Quiz,Class_Performance,Part_A,Part_B, "
          + "Total,Grade_Letter,UG_THEORY_MARKS_LOG.Status,Recheck_Status  "
          + "From EMPLOYEES,USERS,UG_THEORY_MARKS_LOG " + "Where EMPLOYEES.EMPLOYEE_ID=USERS.EMPLOYEE_ID "
          + "And UG_THEORY_MARKS_LOG.USER_ID=USERS.USER_ID " + "And Semester_Id=? " + "And Course_Id=?"
          + "And Exam_Type=? " + "And Student_Id=? " + "Order by Inserted_On desc";

  String SELECT_SESSIONAL_LOG = "";

  String USER_ROLE_QUERY =
      "Select User_Id,'Preparer' Role From Users Where Employee_Id in ( "
          + "Select Preparer From Preparer_Scrutinizer Where Semester_Id=? and  Course_Id=?) "
          + " and Status=1 "
          + "Union "
          + "Select User_Id,'Scrutinizer' Role From Users Where Employee_Id in ( "
          + "Select Scrutinizer From Preparer_Scrutinizer Where Semester_Id=? and  Course_Id=?) "
          + "and Status=1  "
          + "Union "
          + "select user_id,'Head' Role from ADDITIONAL_ROLE_PERMISSIONS where role_id=22 And Sysdate<=Valid_To And Sysdate>=Valid_From "
          + "Union "
          + "select user_id,'CoE' Role from ADDITIONAL_ROLE_PERMISSIONS where role_id=71 And Sysdate<=Valid_To And Sysdate>=Valid_From "
          + "Union " + "Select User_Id,'CoE' Role From Users,EMPLOYEES "
          + "Where USERS.EMPLOYEE_ID=EMPLOYEES.EMPLOYEE_ID " + "And EMPLOYEES.STATUS=1 And USERS.STATUS=1 "
          + "And Designation = 202 " + "Union " + "Select user_id,'VC' Role From Users Where Role_Id=99 and Status=1 ";

  String MARKS_SUBMISSION_STAT = "Select tmp1.dept_id,short_name,program_id,PROGRAM_SHORT_NAME,  "
      + "marksSubmissionStat(?, nvl(program_id,0),tmp1.dept_id,?,?,'Self','Total') Total_Offered_To_Self, "
      + "marksSubmissionStat(?, nvl(program_id,0),tmp1.dept_id,?,?,'Self','Accepted') Accepted_Offered_To_Self, "
      + "marksSubmissionStat(?, nvl(program_id,0),tmp1.dept_id,?,?,'Others','Total') Total_Offered_to_Other, "
      + "marksSubmissionStat(?, nvl(program_id,0),tmp1.dept_id,?,?,'Others','Accepted') Accepted_Offered_to_Other, "
      + "marksSubmissionStat(?, nvl(program_id,0),tmp1.dept_id,?,?,'All','Total') Total_Offered, "
      + "marksSubmissionStat(?, nvl(program_id,0),tmp1.dept_id,?,?,'All','Accepted') Total_Accepted "
      + "From (select dept_id,short_name from mst_dept_office where type=1 )tmp1, " + "mst_program  "
      + "Where tmp1.dept_id =  MST_PROGRAM.DEPT_ID(+)   " + "Order by Dept_Id ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentExamGradeDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public JdbcTemplate getmJdbcTemplate() {
    return mJdbcTemplate;
  }

  @Override
  public int checkSize(Integer pSemesterId, ExamType pExamType, String pExamDate) {
    String query =
        "" + "select count(*) from MARKS_SUBMISSION_STATUS,  " + "  (  " + "      select course_id,SEMESTER  "
            + "      from EXAM_ROUTINE  " + "      where semester=?  "
            + "      and exam_date=to_date(?,'dd-mm-yyyy')  " + "    and EXAM_TYPE=?  " + "  " + "  ) examRoutine  "
            + "where MARKS_SUBMISSION_STATUS.COURSE_ID = examRoutine.COURSE_ID  "
            + "  and MARKS_SUBMISSION_STATUS.SEMESTER_ID=examRoutine.SEMESTER";
    return mJdbcTemplate.queryForObject(query, Integer.class, pSemesterId, pExamDate, pExamType.getId());
  }

  /**
   * 
   * @param pSemesterId
   * @param pExamType
   * @param pExamDate
   * @return
   * 
   *         At the first stage, there is no data of the specific semester. But, for showing the
   *         client about the grade submission deadline, data needed to be shown. So, before
   *         showing, first, all the relevant data are inserted into the table by course_id and then
   *         is returned to the client.
   */
  @Override
  public int createGradeSubmissionStatus(Integer pSemesterId, ExamType pExamType, String pExamDate) {
    String query =
        "" + "insert into MARKS_SUBMISSION_STATUS_CURR (SEMESTER_ID,COURSE_ID,EXAM_TYPE,STATUS)  " + "    select  "
            + "      SEMESTER,  " + "      COURSE_ID,  " + "      EXAM_TYPE,  " + "      0 as STATUS "
            + "    FROM EXAM_ROUTINE  " + "    where EXAM_TYPE=?  " + "    and SEMESTER=?  "
            + "    and EXAM_DATE=TO_DATE(?,'dd-mm-yyyy')";
    return mJdbcTemplate.update(query, pExamType.getId(), pSemesterId, pExamDate);
  }

  @Override
  public List<MarksSubmissionStatusDto> getGradeSubmissionDeadLine(Integer pSemesterId, ExamType pExamType,
      String pExamDate, String pOfferedDeptId, CourseType pCourseType) {

    String query = "";

    if(pCourseType == CourseType.SESSIONAL) {
      query = SELECT_ALL_EXAMGRADE_DEADLINE_SESSIONAL;
      return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType.getId(), pOfferedDeptId},
          new GradeSubmissionDeadlineRowMapperSessional());
    }
    else {
      if(pExamDate.equals("null")) {
        query = SELECT_EXAM_GRADE_DEAD_LINE_THEORY_ALL;
        return mJdbcTemplate.query(query, new Object[] {pSemesterId, pOfferedDeptId, pSemesterId, pExamType.getId()},
            new GradeSubmissionDeadlineRowMapperTheory());
      }
      else {
        query = SELECT_EXAM_GRADE_DEAD_LINE_THEORY_BY_DATE;
        return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType.getId(), pExamType.getId(), pExamDate,
            pOfferedDeptId, pSemesterId, pExamType.getId()}, new GradeSubmissionDeadlineRowMapperTheory());

      }
    }
  }

  @Override
  public List<StudentGradeDto> getAllGrades(int pSemesterId, String pCourseId, ExamType pExamType, CourseType courseType) {

    String query = "";
    if(courseType == CourseType.THEORY) {
      query =
          SELECT_THEORY_MARKS
              + " Order by decode(Reg_Type,1,1,2,2,3,3,4,4,5,5,6,6),UG_THEORY_MARKS.Student_Id,UG_THEORY_MARKS.Status  ";
    }
    else if(courseType == CourseType.SESSIONAL || courseType == CourseType.THESIS_PROJECT) {
      query =
          SELECT_SESSIONAL_MARKS
              + " Order by decode(Reg_Type,1,1,2,2,3,3,4,4,5,5,6,6),UG_SESSIONAL_MARKS.Student_Id,UG_SESSIONAL_MARKS.Status  ";
    }

    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId, pExamType.getId()},
        new StudentMarksRowMapper(courseType));
  }

  @Override
  public List<GradeChartDataDto> getChartData(int pSemesterId, String pCourseId, ExamType pExamType,
      CourseType courseType) {
    String query = getQuery(CHART_DATA, courseType, false);
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pCourseId, pExamType.getId()},
        new ChartDataRowMapper());
  }

  private String getQuery(String orgQuery, CourseType courseType, boolean isCurrent) {
    String query = "";
    if(courseType == CourseType.THEORY) {
      query = String.format(orgQuery, "UG_THEORY_MARKS" + (isCurrent ? "_CURR" : ""));
    }
    else if(courseType == CourseType.SESSIONAL || courseType == CourseType.THESIS_PROJECT) {
      query = String.format(orgQuery, "UG_SESSIONAL_MARKS" + (isCurrent ? "_CURR" : ""));
    }
    return query;
  }

  @Override
  public MarksSubmissionStatusDto getMarksSubmissionStatus(int pSemesterId, String pCourseId, ExamType pExamType) {
    String query = SELECT_PART_INFO;
    return mJdbcTemplate.queryForObject(query, new Object[] {pSemesterId, pCourseId, pExamType.getId()},
        new MarksSubmissionStatusRowMapper());
  }

  @Override
  public List<MarksSubmissionStatusDto> getMarksSubmissionStatus(Integer pSemesterId, Integer pExamType,
      Integer pProgramId, Integer year, Integer semester, String teacherId, String deptId, String userRole,
      Integer status, String pCourseNo) {
    String query = "";
    if(userRole.equals("T")) { // Teacher
      query = SELECT_GRADE_SUBMISSION_TABLE_TEACHER;
      return mJdbcTemplate.query(query, new Object[] {pSemesterId, teacherId, teacherId, pSemesterId, teacherId,
          pExamType}, new MarksSubmissionStatusTableRowMapper());
    }
    else if(userRole.equals("H")) { // Head
      query = SELECT_GRADE_SUBMISSION_TABLE_HEAD;
      if(!StringUtils.isEmpty(pCourseNo)) {
        query += "  And MST_COURSE.COURSE_NO=UPPER('" + pCourseNo + "') ";
      }
      else {
        if(status != -1) {
          query += "  And Ms_Status.Status=" + status;
        }
        if(year != 0) {
          query += "  And Ms_Status.Year=" + year + " And Ms_Status.Semester=" + semester;
        }
      }
      return mJdbcTemplate.query(query,
      // new Object[] {pSemesterId, pExamType, pProgramId, teacherId},
          new Object[] {pSemesterId, pExamType, teacherId}, new MarksSubmissionStatusTableRowMapper());
    }
    else if(userRole.equals("C")) { // CoE
      query = SELECT_GRADE_SUBMISSION_TABLE_CoE;

      if(!StringUtils.isEmpty(pCourseNo)) {
        query += "  And MST_COURSE.COURSE_NO=UPPER('" + pCourseNo + "') ";
      }
      else {
        if(status != -1) {
          query += "  And Ms_Status.Status=" + status;
        }
        if(year != 0) {
          query += "  And Ms_Status.Year=" + year + " And Ms_Status.Semester=" + semester;
        }
        if(!deptId.equalsIgnoreCase("NA")) {
          query += " AND MST_PROGRAM.PROGRAM_ID =  " + pProgramId;
        }
      }
      return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType},
          new MarksSubmissionStatusTableRowMapper());
    }
    else if(userRole.equals("V")) { // CoE
      query = SELECT_GRADE_SUBMISSION_TABLE_CoE;
      if(status != -1) {
        query += "  And Ms_Status.Status=" + status;
      }
      if(year != 0) {
        query += "  And Ms_Status.Year=" + year + " And Ms_Status.Semester=" + semester;
      }
      if(!deptId.equalsIgnoreCase("NA")) {
        query += " AND MST_PROGRAM.PROGRAM_ID =  " + pProgramId;
      }

      return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType},
          new MarksSubmissionStatusTableRowMapper());
    }
    return null;
  }

  @Override
  public List<String> getRoleForTeacher(String pTeacherId, int pSemesterId, String pCourseId) {
    String query = CHECK_TEACHER_ROLE;
    return mJdbcTemplate.query(query, new Object[] {pTeacherId, pSemesterId, pCourseId, pTeacherId, pSemesterId,
        pCourseId, pTeacherId, pSemesterId, pCourseId}, new RoleRowMapper());
  }

  @Override
  public List<String> getRoleForHead(String pUserId) {
    String query = CHECK_HEAD_ROLE;
    return mJdbcTemplate.query(query, new Object[] {pUserId}, new RoleRowMapper());
  }

  @Override
  public List<String> getRoleForCoE(String pUserId) {
    String query = CHECK_COE_ROLE;
    return mJdbcTemplate.query(query, new Object[] {pUserId, pUserId}, new RoleRowMapper());
  }

  @Override
  public List<String> getRoleForVC(String pUserId) {
    String query = CHECK_VC_ROLE;
    return mJdbcTemplate.query(query, new Object[] {pUserId, pUserId}, new RoleRowMapper());
  }

  class ChartDataRowMapper implements RowMapper<GradeChartDataDto> {
    @Override
    public GradeChartDataDto mapRow(ResultSet resultSet, int i) throws SQLException {
      GradeChartDataDto data = new GradeChartDataDto();
      data.setGradeLetter(resultSet.getString("GRADE_LETTER"));
      data.setTotal(resultSet.getInt("TOTAL"));
      data.setColor(resultSet.getString("COLOR"));
      AtomicReference<GradeChartDataDto> atomicReference = new AtomicReference<>(data);
      return atomicReference.get();
    }
  }

  class StudentMarksRowMapper implements RowMapper<StudentGradeDto> {
    CourseType courseType;

    public StudentMarksRowMapper(CourseType courseType) {
      this.courseType = courseType;
    }

    @Override
    public StudentGradeDto mapRow(ResultSet resultSet, int i) throws SQLException {
      StudentGradeDto marks = new StudentGradeDto();
      marks.setStudentId(resultSet.getString("STUDENT_ID"));
      marks.setStudentName(resultSet.getString("FULL_NAME"));

      if(courseType == CourseType.THEORY) {
        Double quiz = resultSet.getDouble("QUIZ");
        marks.setQuiz(resultSet.wasNull() ? null : quiz);

        Double classPerformance = resultSet.getDouble("CLASS_PERFORMANCE");
        marks.setClassPerformance(resultSet.wasNull() ? null : classPerformance);

        marks.setPartAAddiInfo(resultSet.getString("PART_A_ADDI_INFO"));
        Double partA = resultSet.getDouble("PART_A");
        marks.setPartA(resultSet.wasNull() ? null : partA);

        marks.setPartBAddiInfo(resultSet.getString("PART_B_ADDI_INFO"));
        Double partB = resultSet.getDouble("PART_B");
        marks.setPartB(resultSet.wasNull() ? null : partB);
      }
      Double total = resultSet.getDouble("TOTAL");
      marks.setTotal(resultSet.wasNull() ? null : total);

      marks.setGradeLetter(resultSet.getString("GRADE_LETTER"));

      marks.setStatus(StudentMarksSubmissionStatus.values()[resultSet.getInt("STATUS")]);
      marks.setStatusId(resultSet.getInt("STATUS"));
      marks.setRecheckStatusId(resultSet.getInt("RECHECK_STATUS"));
      marks.setRecheckStatus(RecheckStatus.values()[resultSet.getInt("RECHECK_STATUS")]);

      marks.setRegType(resultSet.getInt("REG_TYPE"));

      AtomicReference<StudentGradeDto> atomicReference = new AtomicReference<>(marks);
      return atomicReference.get();
    }
  }

  @Override
  public boolean saveGradeSheet(MarksSubmissionStatus actualStatus, List<StudentGradeDto> pGradeList) {
    batchUpdateGrade(actualStatus.getStatus(), actualStatus.getSemesterId(), actualStatus.getCourseId(),
        actualStatus.getExamType(), actualStatus.getCourse().getCourseType(), pGradeList);
    return true;
  }

  public void batchUpdateGrade(CourseMarksSubmissionStatus courseMarksSubmissionStatus, int pSemesterId,
      String pCourseId, ExamType pExamType, CourseType courseType, List<StudentGradeDto> pGradeList) {
    String sql = "";
    if(courseType == CourseType.THEORY) {
      if(courseMarksSubmissionStatus == CourseMarksSubmissionStatus.NOT_SUBMITTED)
        sql = UPDATE_THEORY_MARKS;
      else
        sql = UPDATE_THEORY_MARKS_RECHECK;
    }
    else if(courseType == CourseType.SESSIONAL || courseType == CourseType.THESIS_PROJECT) {
      if(courseMarksSubmissionStatus == CourseMarksSubmissionStatus.NOT_SUBMITTED)
        sql = UPDATE_SESSIONAL_MARKS;
      else
        sql = UPDATE_SESSIONAL_MARKS_RECHECK;
    }

    mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        StudentGradeDto gradeDto = pGradeList.get(i);
        if(courseType == CourseType.THEORY) {
          if(gradeDto.getQuiz() == null || gradeDto.getQuiz() == -1) {
            ps.setNull(1, Types.NULL);
          }
          else {
            ps.setDouble(1, gradeDto.getQuiz());
          }

          if(gradeDto.getClassPerformance() == null || gradeDto.getClassPerformance() == -1) {
            ps.setNull(2, Types.NULL);
          }
          else {
            ps.setDouble(2, gradeDto.getClassPerformance());
          }

          if(gradeDto.getPartA() == null || gradeDto.getPartA() == -1) {
            ps.setNull(3, Types.NULL);
          }
          else {
            ps.setDouble(3, gradeDto.getPartA());
          }
          ps.setString(4, gradeDto.getPartAAddiInfo());

          if(gradeDto.getPartB() == null || gradeDto.getPartB() == -1) {
            ps.setNull(5, Types.NULL);
          }
          else {
            ps.setDouble(5, gradeDto.getPartB());
          }
          ps.setString(6, gradeDto.getPartBAddiInfo());

          if(gradeDto.getTotal() == null || gradeDto.getTotal() == -1) {
            ps.setNull(7, Types.NULL);
          }
          else {
            ps.setDouble(7, gradeDto.getTotal());
          }

          ps.setString(8, gradeDto.getGradeLetter());
          ps.setInt(9, gradeDto.getStatusId());

          ps.setInt(10, pSemesterId);
          ps.setString(11, pCourseId);
          ps.setInt(12, pExamType.getId());
          ps.setString(13, gradeDto.getStudentId());
        }
        if(courseType == CourseType.SESSIONAL || courseType == CourseType.THESIS_PROJECT) {
          if(gradeDto.getTotal() == null || gradeDto.getTotal() == -1) {
            ps.setNull(1, Types.NULL);
          }
          else {
            ps.setDouble(1, gradeDto.getTotal());
          }

          ps.setString(2, gradeDto.getGradeLetter());
          ps.setInt(3, gradeDto.getStatusId());

          ps.setInt(4, pSemesterId);
          ps.setString(5, pCourseId);
          ps.setInt(6, pExamType.getId());
          ps.setString(7, gradeDto.getStudentId());
        }
      }

      @Override
      public int getBatchSize() {
        return pGradeList.size();
      }

    });
  }

  @Override
  public boolean updateGradeStatus_Save(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList) {
    batchUpdateGradeStatus_Save(actualStatus, recheckList, approveList);
    return true;
  }

  private void batchUpdateGradeStatus_Save(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList) {

    String query = getQuery(UPDATE_STATUS_SAVE_RECHECK, actualStatus.getCourse().getCourseType(), true);
    mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        StudentGradeDto gradeDto = recheckList.get(i);
        ps.setInt(1, gradeDto.getRecheckStatus().getId());
        ps.setInt(2, actualStatus.getSemesterId());
        ps.setString(3, actualStatus.getCourseId());
        ps.setInt(4, actualStatus.getExamType().getId());
        ps.setString(5, gradeDto.getStudentId());
        ps.setString(6, gradeDto.getPreviousStatusString());
        ps.setString(7, gradeDto.getPreviousStatusString());
      }

      @Override
      public int getBatchSize() {
        return recheckList.size();
      }

    });

    query = getQuery(UPDATE_STATUS_SAVE_APPROVE, actualStatus.getCourse().getCourseType(), true);
    mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        StudentGradeDto gradeDto = approveList.get(i);
        setParams(ps, gradeDto, actualStatus);
      }

      @Override
      public int getBatchSize() {
        return approveList.size();
      }

    });
  }

  private void setParams(PreparedStatement ps, StudentGradeDto gradeDto, MarksSubmissionStatus pMarksSubmissionStatus)
      throws SQLException {
    ps.setInt(1, gradeDto.getRecheckStatus().getId());
    ps.setInt(2, gradeDto.getStatus().getId());
    ps.setInt(3, pMarksSubmissionStatus.getSemesterId());
    ps.setString(4, pMarksSubmissionStatus.getCourseId());
    ps.setInt(5, pMarksSubmissionStatus.getExamType().getId());
    ps.setString(6, gradeDto.getStudentId());
    ps.setString(7, gradeDto.getPreviousStatusString());
    ps.setString(8, gradeDto.getPreviousStatusString());
  }

  @Override
  public boolean updateGradeStatus_Recheck(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList) {
    batchUpdateGradeStatus_Recheck(actualStatus, recheckList, approveList);
    return true;
  }

  @Override
  public int rejectRecheckRequest(MarksSubmissionStatus pMarksSubmissionStatus) {
    String query = "UPDATE %s SET RECHECK_STATUS= ? WHERE SEMESTER_ID = ? AND COURSE_ID = ? AND EXAM_TYPE = ? ";
    query = String.format(query, getTableName(pMarksSubmissionStatus.getCourse().getCourseType()));
    return mJdbcTemplate.update(query, RecheckStatus.RECHECK_FALSE.getId(), pMarksSubmissionStatus.getSemesterId(),
        pMarksSubmissionStatus.getCourseId(), pMarksSubmissionStatus.getExamType().getId());

  }

  @Override
  public int approveRecheckRequest(MarksSubmissionStatus pMarksSubmissionStatus) {
    String query =
        "UPDATE %s SET STATUS = ? WHERE SEMESTER_ID = ? AND COURSE_ID = ? AND EXAM_TYPE = ? AND STATUS = ? "
            + "AND RECHECK_STATUS= ?";
    query = String.format(query, getTableName(pMarksSubmissionStatus.getCourse().getCourseType()));
    return mJdbcTemplate.update(query, StudentMarksSubmissionStatus.NONE.getId(), pMarksSubmissionStatus
        .getSemesterId(), pMarksSubmissionStatus.getCourseId(), pMarksSubmissionStatus.getExamType().getId(),
        StudentMarksSubmissionStatus.ACCEPTED.getId(), RecheckStatus.RECHECK_TRUE.getId());
  }

  private String getTableName(CourseType courseType) {
    if(courseType == CourseType.THEORY)
      return "UG_THEORY_MARKS_CURR";
    else
      return "UG_SESSIONAL_MARKS_CURR";
  }

  private int batchUpdateGradeStatus_Recheck(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList) {

    int total;
    int[] recheckTotal;
    int[] approveTotal;
    String query = getQuery(UPDATE_STATUS_RECHECK_RECHECK, actualStatus.getCourse().getCourseType(), true);
    recheckTotal = mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        StudentGradeDto gradeDto = recheckList.get(i);
        setParams(ps, gradeDto, actualStatus);
      }

      @Override
      public int getBatchSize() {
        return recheckList.size();
      }

    });

    query = getQuery(UPDATE_STATUS_RECHECK_APPROVE, actualStatus.getCourse().getCourseType(), true);
    approveTotal = mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        StudentGradeDto gradeDto = approveList.get(i);
        setParams(ps, gradeDto, actualStatus);
      }

      @Override
      public int getBatchSize() {
        return approveList.size();
      }

    });
    total = recheckTotal.length + approveTotal.length;
    return total;
  }

  @Override
  public boolean updateGradeStatus_Approve(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList) {
    batchUpdateGradeStatus_Approve(actualStatus, recheckList, approveList);
    return true;
  }

  private void batchUpdateGradeStatus_Approve(MarksSubmissionStatus actualStatus, List<StudentGradeDto> recheckList,
      List<StudentGradeDto> approveList) {
    String query = getQuery(UPDATE_STATUS_APPROVE, actualStatus.getCourse().getCourseType(), true);
    mJdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        StudentGradeDto gradeDto = approveList.get(i);
        ps.setInt(1, RecheckStatus.RECHECK_FALSE.getId());
        ps.setInt(2, gradeDto.getStatus().getId());
        ps.setInt(3, actualStatus.getSemesterId());
        ps.setString(4, actualStatus.getCourseId());
        ps.setInt(5, actualStatus.getExamType().getId());
        ps.setString(6, gradeDto.getStudentId());
        ps.setString(7, gradeDto.getPreviousStatusString());
        ps.setString(8, gradeDto.getPreviousStatusString());
      }

      @Override
      public int getBatchSize() {
        return approveList.size();
      }

    });
  }

  private List<Object[]> getParamListForGradeSubmissionStatusCreator(
      List<MarksSubmissionStatusDto> pMarksSubmissionStatusDtos) {
    List<Object[]> params = new ArrayList<>();
    for(MarksSubmissionStatusDto app : pMarksSubmissionStatusDtos) {
      params.add(new Object[] {app.getExamDate(), app.getSemesterId(), app.getExamType().getId(), app.getCourseId()});
    }
    return params;
  }

  private List<Object[]> getParamListForDeadlineUpdate(List<MarksSubmissionStatusDto> pMarksSubmissionStatusDtos) {
    List<Object[]> params = new ArrayList<>();
    for(MarksSubmissionStatusDto app : pMarksSubmissionStatusDtos) {
      params.add(new Object[] {app.getLastSubmissionDatePrep(), app.getLastSubmissionDateScr(),
          app.getLastSubmissionDateHead(), app.getSemesterId(), app.getCourseId(), app.getExamType().getId()});
    }
    return params;
  }

  class GradeSubmissionDeadlineRowMapperSessional implements RowMapper<MarksSubmissionStatusDto> {
    @Override
    public MarksSubmissionStatusDto mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MarksSubmissionStatusDto submissionStatusDto = new MarksSubmissionStatusDto();
      rowMapperSetter(pResultSet, submissionStatusDto);
      return submissionStatusDto;
    }
  }

  class GradeSubmissionDeadlineRowMapperTheory implements RowMapper<MarksSubmissionStatusDto> {
    @Override
    public MarksSubmissionStatusDto mapRow(ResultSet pResultSet, int pI) throws SQLException {
      MarksSubmissionStatusDto submissionStatusDto = new MarksSubmissionStatusDto();

      submissionStatusDto.setExamDate(pResultSet.getString("exam_date"));
      rowMapperSetter(pResultSet, submissionStatusDto);
      return submissionStatusDto;
    }
  }

  private void rowMapperSetter(ResultSet pResultSet, MarksSubmissionStatusDto pSubmissionStatusDto) throws SQLException {
    pSubmissionStatusDto.setProgramShortName(pResultSet.getString("program_short_name"));
    pSubmissionStatusDto.setCourseId(pResultSet.getString("course_id"));
    pSubmissionStatusDto.setCourseNo(pResultSet.getString("course_no"));
    pSubmissionStatusDto.setCourseTitle(pResultSet.getString("course_title"));
    pSubmissionStatusDto.setCourseCreditHour(pResultSet.getInt("crhr"));
    pSubmissionStatusDto.setTotalStudents(pResultSet.getInt("total_students"));
    pSubmissionStatusDto.setLastSubmissionDatePrep(pResultSet.getDate("LAST_SUBMISSION_DATE_PREP"));
    pSubmissionStatusDto.setLastSubmissionDateScr(pResultSet.getDate("LAST_SUBMISSION_DATE_SCR"));
    pSubmissionStatusDto.setLastSubmissionDateHead(pResultSet.getDate("LAST_SUBMISSION_DATE_HEAD"));
    pSubmissionStatusDto.setLastSubmissionDateCoe(pResultSet.getDate("LAST_SUBMISSION_DATE_COE"));
    pSubmissionStatusDto.setId(pResultSet.getInt("id"));
  }

  class MarksSubmissionStatusRowMapper implements RowMapper<MarksSubmissionStatusDto> {
    @Override
    public MarksSubmissionStatusDto mapRow(ResultSet resultSet, int i) throws SQLException {
      MarksSubmissionStatusDto statusDto = new MarksSubmissionStatusDto();

      statusDto.setSemesterId(resultSet.getInt("SEMESTER_ID"));
      statusDto.setTotal_part(resultSet.getInt("TOTAL_PART") == 0 ? 2 : resultSet.getInt("TOTAL_PART"));
      statusDto.setPart_a_total(resultSet.getInt("PART_A_TOTAL"));
      statusDto.setPart_b_total(resultSet.getInt("PART_B_TOTAL"));
      statusDto.setStatusId(resultSet.getInt("STATUS"));
      statusDto.setStatus(CourseMarksSubmissionStatus.values()[resultSet.getInt("STATUS")]);
      statusDto.setStatusName(CourseMarksSubmissionStatus.values()[resultSet.getInt("STATUS")].getLabel());

      statusDto.setCourseType(CourseType.get(resultSet.getInt("COURSE_TYPE")));
      statusDto.setExamType(ExamType.get(resultSet.getInt("Exam_Type")));

      statusDto.setCourseTypeId(resultSet.getInt("COURSE_TYPE"));
      statusDto.setExamTypeId(resultSet.getInt("Exam_Type"));

      statusDto.setCourseTypeName(CourseType.get(resultSet.getInt("COURSE_TYPE")).getLabel());
      statusDto.setExamTypeName(ExamType.get(resultSet.getInt("Exam_Type")).getLabel());

      statusDto.setCourseTitle(resultSet.getString("COURSE_TITLE"));
      statusDto.setCourseId(resultSet.getString("COURSE_ID"));
      statusDto.setCourseNo(resultSet.getString("COURSE_NO"));
      statusDto.setDeptSchoolShortName(resultSet.getString("SHORT_NAME"));
      statusDto.setDeptSchoolLongName(resultSet.getString("LONG_NAME"));
      statusDto.setSemesterName(resultSet.getString("SEMESTER_NAME"));
      statusDto.setcRhR(resultSet.getFloat("CRHR"));
      statusDto.setLastSubmissionDatePrep(resultSet.getDate("LAST_SUBMISSION_DATE_PREP"));
      statusDto.setLastSubmissionDateScr(resultSet.getDate("LAST_SUBMISSION_DATE_SCR"));
      statusDto.setLastSubmissionDateHead(resultSet.getDate("LAST_SUBMISSION_DATE_HEAD"));
      statusDto.setProgramShortName(resultSet.getString("PROGRAM_SHORT_NAME"));
      statusDto.setProgramLongName(resultSet.getString("PROGRAM_LONG_NAME"));
      AtomicReference<MarksSubmissionStatusDto> atomicReference = new AtomicReference<>(statusDto);
      return atomicReference.get();
    }

  }
  class MarksSubmissionStatusTableRowMapper implements RowMapper<MarksSubmissionStatusDto> {
    @Override
    public MarksSubmissionStatusDto mapRow(ResultSet resultSet, int i) throws SQLException {
      MarksSubmissionStatusDto statusDto = new MarksSubmissionStatusDto();

      statusDto.setCourseId(resultSet.getString("COURSE_ID"));
      statusDto.setCourseNo(resultSet.getString("COURSE_NO"));
      statusDto.setCourseTitle(resultSet.getString("COURSE_TITLE"));
      statusDto.setYear(resultSet.getInt("YEAR"));
      statusDto.setSemester(resultSet.getInt("SEMESTER"));
      statusDto.setSemesterId(resultSet.getInt("SEMESTER_ID"));

      statusDto.setExamType(ExamType.get(resultSet.getInt("Exam_Type")));
      statusDto.setExamTypeId(resultSet.getInt("Exam_Type"));
      statusDto.setExamTypeName(ExamType.get(resultSet.getInt("Exam_Type")).getLabel());

      statusDto.setPreparerId(resultSet.getString("PREPARER_ID"));
      statusDto.setScrutinizerId(resultSet.getString("SCRUTINIZER_ID"));
      statusDto.setPreparerName(resultSet.getString("PREPARER_NAME"));
      statusDto.setScrutinizerName(resultSet.getString("SCRUTINIZER_NAME"));

      statusDto.setOfferedTo(resultSet.getString("PROGRAM_SHORT_NAME").replaceAll("BSC in ", ""));
      statusDto.setStatus(CourseMarksSubmissionStatus.get(resultSet.getInt("STATUS")));
      statusDto.setStatusId(resultSet.getInt("STATUS"));
      statusDto.setStatusName(CourseMarksSubmissionStatus.values()[resultSet.getInt("STATUS")].getLabel());

      statusDto.setLastSubmissionDatePrep(resultSet.getDate("LAST_SUBMISSION_DATE_PREP"));
      statusDto.setLastSubmissionDateScr(resultSet.getDate("LAST_SUBMISSION_DATE_SCR"));
      statusDto.setLastSubmissionDateHead(resultSet.getDate("LAST_SUBMISSION_DATE_HEAD"));


      String courseTeachers = resultSet.getString("Course_Teachers");
      ArrayList<CourseTeacherDto> teacherList = new ArrayList();
      if(courseTeachers != null && !courseTeachers.equalsIgnoreCase("")) {
        statusDto.setCourseTeacherStr(courseTeachers.replaceAll("#", ", "));
        String courseTeacherArr[] = courseTeachers.split("#");

        for(int t = 0; t < courseTeacherArr.length; t++) {
          CourseTeacherDto teacher = new CourseTeacherDto();
          teacher.setTeacher_name(courseTeacherArr[t]);
          teacherList.add(teacher);
        }
      }
      statusDto.setCourseTeacherList(teacherList);

      AtomicReference<MarksSubmissionStatusDto> atomicReference = new AtomicReference<>(statusDto);
      return atomicReference.get();
    }
  }
  class RoleRowMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {
      String a = resultSet.getString("ROLE");

      AtomicReference<String> atomicReference = new AtomicReference<>(a);
      return atomicReference.get();
    }
  }

  @Override
  public boolean insertGradeLog(String userId, String pRole, MarksSubmissionStatus actualStatus,
      CourseMarksSubmissionStatus nextStatus, List<StudentGradeDto> pGradeList) {
    batchInsertGradeLog(userId, pRole, actualStatus, nextStatus, pGradeList);
    return true;
  }

  private void batchInsertGradeLog(String userId, String pRole, MarksSubmissionStatus actualStatus,
      CourseMarksSubmissionStatus nextStatus, List<StudentGradeDto> pGradeList) {
    String sql = "";
    if(actualStatus.getCourse().getCourseType() == CourseType.THEORY) {
      sql = INSERT_THEORY_LOG;
    }
    else if(actualStatus.getCourse().getCourseType() == CourseType.SESSIONAL
        || actualStatus.getCourse().getCourseType() == CourseType.THESIS_PROJECT) {
      sql = INSERT_SESSIONAL_LOG;
    }

    mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        StudentGradeDto gradeDto = pGradeList.get(i);
        ps.setString(1, userId);
        ps.setString(2, pRole);
        ps.setInt(3, actualStatus.getSemesterId());
        ps.setString(4, actualStatus.getCourseId());
        ps.setString(5, gradeDto.getStudentId());
        ps.setInt(6, actualStatus.getExamType().getId());

        try {
          if(actualStatus.getCourse().getCourseType() == CourseType.THEORY) {
            if(actualStatus.getStatus() == CourseMarksSubmissionStatus.NOT_SUBMITTED
                || actualStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER
                || actualStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD
                || actualStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE) {
              if(gradeDto.getQuiz() == null)
                ps.setNull(7, Types.NULL);
              else
                ps.setDouble(7, gradeDto.getQuiz());

              if(gradeDto.getClassPerformance() == null)
                ps.setNull(8, Types.NULL);
              else
                ps.setDouble(8, gradeDto.getClassPerformance());

              ps.setDouble(9, gradeDto.getPartA());
              if(gradeDto.getPartB() == null)
                ps.setNull(10, Types.NULL);
              else
                ps.setDouble(10, gradeDto.getPartB());
              ps.setDouble(11, gradeDto.getTotal());
              ps.setString(12, gradeDto.getGradeLetter());
              ps.setInt(13, nextStatus.getId());
              ps.setInt(14, gradeDto.getRecheckStatusId());
            }
            else {
              ps.setNull(7, Types.NULL);
              ps.setNull(8, Types.NULL);
              ps.setNull(9, Types.NULL);
              ps.setNull(10, Types.NULL);
              ps.setNull(11, Types.NULL);
              ps.setNull(12, Types.NULL);
              ps.setInt(13, nextStatus.getId());
              ps.setInt(14, gradeDto.getRecheckStatusId());
            }
          }
          if(actualStatus.getCourse().getCourseType() == CourseType.SESSIONAL
              || actualStatus.getCourse().getCourseType() == CourseType.THESIS_PROJECT) {
            if(actualStatus.getStatus() == CourseMarksSubmissionStatus.NOT_SUBMITTED
                || actualStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_SCRUTINIZER
                || actualStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_HEAD
                || actualStatus.getStatus() == CourseMarksSubmissionStatus.REQUESTED_FOR_RECHECK_BY_COE) {
              ps.setDouble(7, gradeDto.getTotal());
              ps.setString(8, gradeDto.getGradeLetter());
              ps.setInt(9, nextStatus.getId());
              ps.setInt(10, gradeDto.getRecheckStatusId());
            }
            else {
              ps.setNull(7, Types.NULL);
              ps.setNull(8, Types.NULL);
              ps.setInt(9, nextStatus.getId());
              ps.setInt(10, gradeDto.getRecheckStatusId());
            }
          }
        } catch(Exception e) {
          throw new SQLException("Exception while inserting grade info");
        }
      }

      @Override
      public int getBatchSize() {
        return pGradeList.size();
      }
    });
  }

  @Override
  public int insertMarksSubmissionStatusLog(String userId, String userRole, MarksSubmissionStatus actualStatus,
      CourseMarksSubmissionStatus status) {
    return mJdbcTemplate.update(INSERT_MARKS_SUBMISSION_STATUS_LOG, userId, userRole, actualStatus.getSemesterId(),
        actualStatus.getCourseId(), actualStatus.getExamType().getId(), status.getId());
  }

  @Override
  public int getTotalStudentCount(MarksSubmissionStatus actualStatus) {
    String sql = "";
    if(actualStatus.getStatus() == CourseMarksSubmissionStatus.NOT_SUBMITTED) {
      if(actualStatus.getCourse().getCourseType() == CourseType.THEORY) {
        sql = THEORY_SUBMIT_COUNT + " and recheck_status=0";
      }
      else if(actualStatus.getCourse().getCourseType() == CourseType.SESSIONAL
          || actualStatus.getCourse().getCourseType() == CourseType.THESIS_PROJECT) {
        sql = SESSIONAL_SUBMIT_COUNT + " and recheck_status=0";
      }
    }
    else {
      if(actualStatus.getCourse().getCourseType() == CourseType.THEORY) {
        sql = THEORY_SUBMIT_COUNT + " and recheck_status=1";
      }
      else if(actualStatus.getCourse().getCourseType() == CourseType.SESSIONAL
          || actualStatus.getCourse().getCourseType() == CourseType.THESIS_PROJECT) {
        sql = SESSIONAL_SUBMIT_COUNT + " and recheck_status=1";
      }
    }

    return mJdbcTemplate.queryForObject(sql, Integer.class, actualStatus.getSemesterId(), actualStatus.getCourseId(),
        actualStatus.getExamType().getId());
  }

  @Override
  public List<MarksSubmissionStatusLogDto> getMarksSubmissionLogs(Integer pSemesterId, String pCourseId,
      Integer pExamType) {
    return mJdbcTemplate.query(SELECT_MARKS_SUBMISSION_STATUS_LOG, new Object[] {pSemesterId, pCourseId, pExamType},
        new MarksSubmissionStatusLogRowMapper());
  }

  class MarksSubmissionStatusLogRowMapper implements RowMapper<MarksSubmissionStatusLogDto> {
    @Override
    public MarksSubmissionStatusLogDto mapRow(ResultSet resultSet, int i) throws SQLException {
      MarksSubmissionStatusLogDto log = new MarksSubmissionStatusLogDto();

      log.setUserId(resultSet.getString("USER_ID"));
      log.setUserName(resultSet.getString("EMPLOYEE_NAME"));
      log.setRoleName(resultSet.getString("ROLE"));
      log.setInsertedOn(resultSet.getString("INSERTED_ON"));
      log.setStatus(CourseMarksSubmissionStatus.values()[resultSet.getInt("STATUS")]);
      log.setStatusName(CourseMarksSubmissionStatus.values()[resultSet.getInt("STATUS")].getLabel());
      AtomicReference<MarksSubmissionStatusLogDto> atomicReference = new AtomicReference<>(log);
      return atomicReference.get();
    }
  }

  @Override
  public List<MarksLogDto> getMarksLogs(final Integer pSemesterId, final String pCourseId, final ExamType pExamType,
      final String pStudentId, final CourseType pCourseType) {
    String sql = "";
    if(pCourseType == CourseType.THEORY)
      sql = SELECT_THEORY_LOG;
    else if(pCourseType == CourseType.SESSIONAL || pCourseType == CourseType.THESIS_PROJECT)
      sql = SELECT_SESSIONAL_LOG;

    return mJdbcTemplate.query(sql, new Object[] {pSemesterId, pCourseId, pExamType.getId(), pStudentId},
        new MarksStatusLogRowMapper());
  }

  class MarksStatusLogRowMapper implements RowMapper<MarksLogDto> {
    @Override
    public MarksLogDto mapRow(ResultSet resultSet, int i) throws SQLException {
      MarksLogDto log = new MarksLogDto();
      Double nullValue = null;
      log.setUserId(resultSet.getString("USER_ID"));
      log.setRoleName(WordUtils.capitalize(resultSet.getString("ROLE")));
      log.setUserName(resultSet.getString("EMPLOYEE_NAME"));
      log.setInsertedOn(resultSet.getString("INSERTED_ON"));
      log.setQuiz(resultSet.getDouble("QUIZ"));
      if(resultSet.wasNull()) {
        log.setQuiz(nullValue);
      }
      log.setClassPerformance(resultSet.getDouble("CLASS_PERFORMANCE"));
      if(resultSet.wasNull()) {
        log.setClassPerformance(nullValue);
      }
      log.setPartA(resultSet.getDouble("PART_A"));
      if(resultSet.wasNull()) {
        log.setPartA(nullValue);
      }
      log.setPartB(resultSet.getDouble("PART_B"));
      if(resultSet.wasNull()) {
        log.setPartB(nullValue);
      }
      log.setTotal(resultSet.getDouble("TOTAL"));
      if(resultSet.wasNull()) {
        log.setTotal(nullValue);
      }
      log.setGradeLetter(resultSet.getString("GRADE_LETTER"));
      log.setStatus(CourseMarksSubmissionStatus.values()[resultSet.getInt("STATUS")]);
      log.setStatusName(CourseMarksSubmissionStatus.values()[resultSet.getInt("STATUS")].getLabel());
      log.setRecheckStatus(resultSet.getInt("RECHECK_STATUS"));
      AtomicReference<MarksLogDto> atomicReference = new AtomicReference<>(log);
      return atomicReference.get();
    }
  }

  @Override
  public Map getUserRoleList(Integer pSemesterId, String pCourseId) {
    return mJdbcTemplate.query(USER_ROLE_QUERY, new Object[] {pSemesterId, pCourseId, pSemesterId, pCourseId},
        new UserRoleRowExtractor());
  }

  class UserRoleRowExtractor implements ResultSetExtractor<Map> {

    @Override
    public Map extractData(ResultSet rs) throws SQLException, DataAccessException {
      HashMap<String, String> mapRet = new HashMap<String, String>();
      while(rs.next()) {
        mapRet.put(rs.getString("ROLE"), rs.getString("USER_ID"));
      }
      return mapRet;
    }
  }

  @Override
  public List<MarksSubmissionStatDto> getMarksSubmissionStat(Integer pProgramType, Integer pSemesterId, String pDeptId,
      Integer pExamType, String pStatus) throws Exception {
    String query = MARKS_SUBMISSION_STAT;
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType, pStatus, pSemesterId, pExamType, pStatus,
        pSemesterId, pExamType, pStatus, pSemesterId, pExamType, pStatus, pSemesterId, pExamType, pStatus, pSemesterId,
        pExamType, pStatus}, new MarksSubmissionStatRow());
  }

  public int transferUGGradesToPrivateDB(String pCourseId, int pSemesterId, int pExamType) {

    List<SqlParameter> parameters = Arrays.asList(
        new SqlParameter(Types.VARCHAR),new SqlParameter(Types.INTEGER),new SqlParameter(Types.INTEGER),
        new SqlOutParameter("oRespCode", Types.INTEGER), new SqlOutParameter("oRespMsg", Types.VARCHAR));

    Map<String, Object> t = mJdbcTemplate.call(new CallableStatementCreator() {
      @Override
      public CallableStatement createCallableStatement(Connection con) throws SQLException {
        CallableStatement callableStatement = con.prepareCall("{call Transfer_UG_Grades (?, ?, ?, ?, ?)}");
        callableStatement.setString(1, pCourseId);
        callableStatement.setInt(2, pSemesterId);
        callableStatement.setInt(3, pExamType);
        callableStatement.registerOutParameter(4, Types.INTEGER);
        callableStatement.registerOutParameter(5, Types.VARCHAR);
        return callableStatement;
      }
    }, parameters);

    t.forEach((k, v) -> System.out.println((k + ":" + v)));

    return (Integer)t.get("oRespCode");
  }

  public int transferUGGradesToPublicDB(String pCourseId, int pSemesterId,int pCourseType, int pExamType, String pStudents, String pCause, String pActor) {

    List<SqlParameter> parameters = Arrays.asList(
        new SqlParameter(Types.VARCHAR),new SqlParameter(Types.INTEGER),new SqlParameter(Types.INTEGER),
        new SqlParameter(Types.INTEGER),new SqlParameter(Types.VARCHAR),new SqlParameter(Types.VARCHAR),
        new SqlParameter(Types.VARCHAR),
        new SqlOutParameter("oRespCode", Types.INTEGER), new SqlOutParameter("oRespMsg", Types.VARCHAR));

    Map<String, Object> t = mJdbcTemplate.call(new CallableStatementCreator() {
      @Override
      public CallableStatement createCallableStatement(Connection con) throws SQLException {
        CallableStatement callableStatement = con.prepareCall("{call DB_IUMS_PRIVATE.Transfer_UG_Grades (?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        callableStatement.setString(1, pCourseId);
        callableStatement.setInt(2, pSemesterId);
        callableStatement.setInt(3, pCourseType);
        callableStatement.setInt(4, pExamType);
        callableStatement.setString(5, pStudents);
        callableStatement.setString(6, pCause);
        callableStatement.setString(7, pActor);
        callableStatement.registerOutParameter(8, Types.INTEGER);
        callableStatement.registerOutParameter(9, Types.VARCHAR);
        return callableStatement;
      }
    }, parameters);

    t.forEach((k, v) -> System.out.println((k + ":" + v)));

    return (Integer)t.get("oRespCode");
  }

  class MarksSubmissionStatRow implements RowMapper<MarksSubmissionStatDto> {
    @Override
    public MarksSubmissionStatDto mapRow(ResultSet resultSet, int i) throws SQLException {
      MarksSubmissionStatDto data = new MarksSubmissionStatDto();

      data.setDeptId(resultSet.getString("DEPT_ID"));
      data.setDeptName(resultSet.getString("SHORT_NAME"));
      data.setProgramId(resultSet.getInt("PROGRAM_ID"));
      data.setProgramName(resultSet.getString("PROGRAM_SHORT_NAME"));
      data.setTotalOfferedSelf(resultSet.getInt("TOTAL_OFFERED_TO_SELF"));
      data.setTotalAcceptedSelf(resultSet.getInt("ACCEPTED_OFFERED_TO_SELF"));
      data.setTotalOfferedOthers(resultSet.getInt("TOTAL_OFFERED_TO_OTHER"));
      data.setTotalAcceptedOthers(resultSet.getInt("ACCEPTED_OFFERED_TO_OTHER"));
      data.setTotalOffered(resultSet.getInt("TOTAL_OFFERED"));
      data.setTotalAccepted(resultSet.getInt("TOTAL_ACCEPTED"));

      AtomicReference<MarksSubmissionStatDto> atomicReference = new AtomicReference<>(data);
      return atomicReference.get();

    }
  }
}
