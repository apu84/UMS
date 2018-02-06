package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ApplicationCCIDaoDecorator;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.mutable.MutableApplicationCCI;
import org.ums.enums.ApplicationStatus;
import org.ums.enums.ApplicationType;
import org.ums.fee.payment.StudentPaymentDao;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentApplicationCCI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Created by My Pc on 7/14/2016.
 */

public class PersistentApplicationCCIDao extends ApplicationCCIDaoDecorator {

  String SELECT_ALL =
      "SELECT  "
          + "    a.id,  "
          + "    a.semester_id,  "
          + "    a.student_id,  "
          + "    a.course_id,  "
          + "    a.application_type,  "
          + "    to_char(a.applied_on,'DD-MM-YYYY') applied_on,  "
          + "    a.STATUS,  "
          + "  "
          + "    c.course_no,  "
          + "    c.course_title,  "
          + "    to_char(exam_routine.exam_date,  'DD-MM-YYYY')  exam_date  "
          + "FROM  application_cci  a,  mst_course  c,  exam_routine  "
          + "WHERE  "
          + "    a.course_id  =  c.course_id  AND  a.course_id  =  exam_routine.course_id  AND  exam_routine.exam_type  =  2";
  String INSERT_ONE =
      "Insert  into  APPLICATION_CCI  (ID,SEMESTER_ID,STUDENT_ID,COURSE_ID,APPLICATION_TYPE,STATUS,TRANSACTION_ID,APPLIED_ON)  values  (?,?,?,?,?,?,?,systimestamp)";
  String UPDATE_ONE =
      "update  application_cci  set  semester_id=?,  student_id=?,  course_id=?,application_type=?,applied_on=systimestamp  ";
  String DELETE_ONE = "delete  from  application_cci";

  // UpdateApliedAndApproved
  String UDAPTE_APPLIED_APPROVED = "update APPLICATION_CCI " + "set STATUS=?  where "
      + "STUDENT_ID=? AND SEMESTER_ID=? AND APPLICATION_TYPE=3 AND COURSE_ID=?";

  // Rumi-CarryApporvalQueries
  String SELECT_ALL_CA =
      "SELECT"
          + "    a.id,"
          + "    a.semester_id,"
          + "    a.student_id,"
          + "    a.course_id,"
          + "    a.application_type,"
          + "    to_char(a.applied_on,'DD-MM-YYYY') applied_on,"
          + "    a.STATUS,"
          + "    t.GRADE_LETTER                                                                GRADE,"
          + "    c.course_no,"
          + "    c.course_title,"
          + "    STUDENTS.FULL_NAME,"
          + "    STUDENTS.CURR_YEAR,"
          + "    STUDENTS.CURR_SEMESTER,"
          + "    STUDENTS.CURR_ENROLLED_SEMESTER,"
          + "    to_char(exam_routine.exam_date,  'DD-MM-YYYY')  exam_date"
          + " FROM  application_cci  a,  mst_course  c,  exam_routine,  UG_REGISTRATION_RESULT  t,  STUDENTS"
          + " WHERE "
          + "    a.course_id  =  c.course_id  AND  a.course_id  =  exam_routine.course_id  AND  exam_routine.exam_type  =  2"
          + "    AND  a.semester_id  =STUDENTS.CURR_ENROLLED_SEMESTER  AND  exam_routine.semester  =  a.semester_id  AND  t.EXAM_TYPE  =  1"
          + "    AND  t.STUDENT_ID  =  a.STUDENT_ID  AND  t.COURSE_ID  =  a.COURSE_ID  AND  a.STUDENT_ID  =  STUDENTS.STUDENT_ID AND  a.APPLICATION_TYPE=3 AND a.STATUS=2 AND c.OFFER_BY= ? ";
  // -----------SearchBystudent Id
  String SELECT_ALL_CA_SearchByStudentId =
      " SELECT "
          + "  a.id, "
          + "  a.semester_id, "
          + "  a.student_id, "
          + "  a.course_id, "
          + "  a.application_type, "
          + "  to_char(a.applied_on,'DD-MM-YYYY') applied_on, "
          + "  a.STATUS, "
          + "  t.GRADE_LETTER                                GRADE, "
          + "  c.course_no, "
          + "  c.course_title, "
          + "  STUDENTS.FULL_NAME, "
          + "  STUDENTS.CURR_YEAR, "
          + "  STUDENTS.CURR_SEMESTER, "
          + "  STUDENTS.CURR_ENROLLED_SEMESTER, "
          + "  to_char(exam_routine.exam_date, 'DD-MM-YYYY') exam_date "
          + "FROM application_cci a, mst_course c, exam_routine, UG_REGISTRATION_RESULT t, STUDENTS "
          + "WHERE "
          + "  a.course_id = c.course_id AND a.course_id = exam_routine.course_id AND exam_routine.exam_type = 2 "
          + "  AND a.semester_id = ? AND STUDENTS.CURR_ENROLLED_SEMESTER= ? AND exam_routine.semester = ? AND a.semester_id= ? AND t.EXAM_TYPE = 1 "
          + "  AND t.STUDENT_ID = ? AND t.COURSE_ID = a.COURSE_ID AND a.STUDENT_ID = ? AND STUDENTS.STUDENT_ID= ? AND  a.APPLICATION_TYPE=3 AND a.STATUS=2 AND c.OFFER_BY= ? ";

  // Rejected
  String SELECT_ALL_Reject =
      "SELECT"
          + "    a.id,"
          + "    a.semester_id,"
          + "    a.student_id,"
          + "    a.course_id,"
          + "    a.application_type,"
          + "    to_char(a.applied_on,'DD-MM-YYYY') applied_on,"
          + "    a.STATUS,"
          + "    t.GRADE_LETTER                                                                GRADE,"
          + "    c.course_no,"
          + "    c.course_title,"
          + "    STUDENTS.FULL_NAME,"
          + "    STUDENTS.CURR_YEAR,"
          + "    STUDENTS.CURR_SEMESTER,"
          + "    STUDENTS.CURR_ENROLLED_SEMESTER,"
          + "    to_char(exam_routine.exam_date,  'DD-MM-YYYY')  exam_date"
          + " FROM  application_cci  a,  mst_course  c,  exam_routine,  UG_REGISTRATION_RESULT  t,  STUDENTS"
          + " WHERE "
          + "    a.course_id  =  c.course_id  AND  a.course_id  =  exam_routine.course_id  AND  exam_routine.exam_type  =  2"
          + "    AND  a.semester_id  =STUDENTS.CURR_ENROLLED_SEMESTER  AND  exam_routine.semester  =  a.semester_id  AND  t.EXAM_TYPE  =  1"
          + "    AND  t.STUDENT_ID  =  a.STUDENT_ID  AND  t.COURSE_ID  =  a.COURSE_ID  AND  a.STUDENT_ID  =  STUDENTS.STUDENT_ID AND  a.APPLICATION_TYPE=3 AND a.STATUS=9 AND c.OFFER_BY= ? ";

  // SearchByStudentId

  String SELECT_ALL_REJECT_SearchByStudentId =
      " SELECT  "
          + "  a.id,  "
          + "  a.semester_id,  "
          + "  a.student_id,  "
          + "  a.course_id,  "
          + "  a.application_type,  "
          + "  to_char(a.applied_on,'DD-MM-YYYY') applied_on,  "
          + "  a.STATUS,  "
          + "  t.GRADE_LETTER                                GRADE,  "
          + "  c.course_no,  "
          + "  c.course_title,  "
          + "  STUDENTS.FULL_NAME,  "
          + "  STUDENTS.CURR_YEAR,  "
          + "  STUDENTS.CURR_SEMESTER,  "
          + "  STUDENTS.CURR_ENROLLED_SEMESTER,  "
          + "  to_char(exam_routine.exam_date, 'DD-MM-YYYY') exam_date  "
          + "FROM application_cci a, mst_course c, exam_routine, UG_REGISTRATION_RESULT t, STUDENTS  "
          + "WHERE  "
          + "  a.course_id = c.course_id AND a.course_id = exam_routine.course_id AND exam_routine.exam_type = 2  "
          + "  AND a.semester_id = ? AND STUDENTS.CURR_ENROLLED_SEMESTER= ? AND exam_routine.semester = ? AND a.semester_id= ? AND t.EXAM_TYPE = 1  "
          + "  AND t.STUDENT_ID = ? AND t.COURSE_ID = a.COURSE_ID AND a.STUDENT_ID = ? AND STUDENTS.STUDENT_ID= ? AND  a.APPLICATION_TYPE=3 AND a.STATUS=9 AND c.OFFER_BY= ? ";

  // /////PaymentAndApproved Query
  String SELECT_ALL_Payment_ANd_Approved =
      "SELECT"
          + "    a.id,"
          + "    a.semester_id,"
          + "    a.student_id,"
          + "    a.course_id,"
          + "    a.application_type,"
          + "    to_char(a.applied_on,'DD-MM-YYYY') applied_on,"
          + "    a.STATUS,"
          + "    t.GRADE_LETTER                                                                GRADE,"
          + "    c.course_no,"
          + "    c.course_title,"
          + "    STUDENTS.FULL_NAME,"
          + "    STUDENTS.CURR_YEAR,"
          + "    STUDENTS.CURR_SEMESTER,"
          + "    STUDENTS.CURR_ENROLLED_SEMESTER,"
          + "    to_char(exam_routine.exam_date,  'DD-MM-YYYY')  exam_date"
          + " FROM  application_cci  a,  mst_course  c,  exam_routine,  UG_REGISTRATION_RESULT  t,  STUDENTS"
          + " WHERE "
          + "    a.course_id  =  c.course_id  AND  a.course_id  =  exam_routine.course_id  AND  exam_routine.exam_type  =  2"
          + "    AND  a.semester_id  =STUDENTS.CURR_ENROLLED_SEMESTER  AND  exam_routine.semester  =  a.semester_id  AND  t.EXAM_TYPE  =  1"
          + "    AND  t.STUDENT_ID  =  a.STUDENT_ID  AND  t.COURSE_ID  =  a.COURSE_ID  AND  a.STUDENT_ID  =  STUDENTS.STUDENT_ID AND  a.APPLICATION_TYPE=3 AND (a.STATUS=7 OR a.STATUS=8) AND c.OFFER_BY= ? ";
  // Payment Approval Search

  String SELECT_ALL_Payment_ANd_Approved_SearchByStudentId =
      " SELECT   "
          + "  a.id,   "
          + "  a.semester_id,   "
          + "  a.student_id,   "
          + "  a.course_id,   "
          + "  a.application_type,   "
          + "  to_char(a.applied_on,'DD-MM-YYYY') applied_on,   "
          + "  a.STATUS,   "
          + "  t.GRADE_LETTER                                GRADE,   "
          + "  c.course_no,   "
          + "  c.course_title,   "
          + "  STUDENTS.FULL_NAME,   "
          + "  STUDENTS.CURR_YEAR,   "
          + "  STUDENTS.CURR_SEMESTER,   "
          + "  STUDENTS.CURR_ENROLLED_SEMESTER,   "
          + "  to_char(exam_routine.exam_date, 'DD-MM-YYYY') exam_date   "
          + "FROM application_cci a, mst_course c, exam_routine, UG_REGISTRATION_RESULT t, STUDENTS   "
          + "WHERE   "
          + "  a.course_id = c.course_id AND a.course_id = exam_routine.course_id AND exam_routine.exam_type = 2   "
          + "  AND a.semester_id = ? AND STUDENTS.CURR_ENROLLED_SEMESTER= ? AND exam_routine.semester = ? AND a.semester_id= ? AND t.EXAM_TYPE = 1   "
          + "  AND t.STUDENT_ID = ? AND t.COURSE_ID = a.COURSE_ID AND a.STUDENT_ID = ? AND STUDENTS.STUDENT_ID= ? AND  a.APPLICATION_TYPE=3 AND (a.STATUS=7 OR a.STATUS=8) AND c.OFFER_BY= ? ";
  // All
  String SELECT_ALL_All_Approval_Status =
      "SELECT"
          + "    a.id,"
          + "    a.semester_id,"
          + "    a.student_id,"
          + "    a.course_id,"
          + "    a.application_type,"
          + "    to_char(a.applied_on,'DD-MM-YYYY') applied_on,"
          + "    a.STATUS,"
          + "    t.GRADE_LETTER                                                                GRADE,"
          + "    c.course_no,"
          + "    c.course_title,"
          + "    STUDENTS.FULL_NAME,"
          + "    STUDENTS.CURR_YEAR,"
          + "    STUDENTS.CURR_SEMESTER,"
          + "    STUDENTS.CURR_ENROLLED_SEMESTER,"
          + "    to_char(exam_routine.exam_date,  'DD-MM-YYYY')  exam_date"
          + " FROM  application_cci  a,  mst_course  c,  exam_routine,  UG_REGISTRATION_RESULT  t,  STUDENTS"
          + " WHERE "
          + "    a.course_id  =  c.course_id  AND  a.course_id  =  exam_routine.course_id  AND  exam_routine.exam_type  =  2"
          + "    AND  a.semester_id  =STUDENTS.CURR_ENROLLED_SEMESTER  AND  exam_routine.semester  =  a.semester_id  AND  t.EXAM_TYPE  =  1"
          + "    AND  t.STUDENT_ID  =  a.STUDENT_ID  AND  t.COURSE_ID  =  a.COURSE_ID  AND  a.STUDENT_ID  =  STUDENTS.STUDENT_ID AND  a.APPLICATION_TYPE=3 AND c.OFFER_BY= ? ";
  // SEachByStudent
  String SELECT_ALL_All_Approval_Status_SearchByStudentId =
      " SELECT "
          + "  a.id, "
          + "  a.semester_id, "
          + "  a.student_id, "
          + "  a.course_id, "
          + "  a.application_type, "
          + "  to_char(a.applied_on,'DD-MM-YYYY') applied_on, "
          + "  a.STATUS, "
          + "  t.GRADE_LETTER                                GRADE, "
          + "  c.course_no, "
          + "  c.course_title, "
          + "  STUDENTS.FULL_NAME, "
          + "  STUDENTS.CURR_YEAR, "
          + "  STUDENTS.CURR_SEMESTER, "
          + "  STUDENTS.CURR_ENROLLED_SEMESTER, "
          + "  to_char(exam_routine.exam_date, 'DD-MM-YYYY') exam_date "
          + "FROM application_cci a, mst_course c, exam_routine, UG_REGISTRATION_RESULT t, STUDENTS "
          + "WHERE "
          + "  a.course_id = c.course_id AND a.course_id = exam_routine.course_id AND exam_routine.exam_type = 2 "
          + "  AND a.semester_id = ? AND STUDENTS.CURR_ENROLLED_SEMESTER= ? AND exam_routine.semester = ? AND a.semester_id= ? AND t.EXAM_TYPE = 1 "
          + "  AND t.STUDENT_ID = ? AND t.COURSE_ID = a.COURSE_ID AND a.STUDENT_ID = ? AND STUDENTS.STUDENT_ID= ? AND  a.APPLICATION_TYPE=3  AND c.OFFER_BY= ?";

  // GetAllInfo
  String SELECT_ALL_INFO =
      "SELECT  "
          + "a.STUDENT_ID ,  "
          + "a.SEMESTER_ID,  "
          + "a.COURSE_ID,  "
          + "b.COURSE_NO,  "
          + "b.COURSE_TITLE,  "
          + "a.APPLICATION_TYPE,  "
          + "to_char(a.applied_on,'DD-MM-YYYY') applied_on,  "
          + "a.STATUS  "
          + " from APPLICATION_CCI a,MST_COURSE b where a.STUDENT_ID=? AND a.SEMESTER_ID=?  AND a.COURSE_ID=b.COURSE_ID  and a.APPLICATION_TYPE=3 ";
  // GetTotalcarry
  String SELECT_TOTAL_CARRY =
      "SELECT COURSE_NO,COURSE_TITLE, YEAR, SEMESTER from MST_COURSE where COURSE_ID in(SELECT COURSE_ID "
          + "FROM UG_REGISTRATION_RESULT "
          + "WHERE UG_REGISTRATION_RESULT.Student_id = ? AND UG_REGISTRATION_RESULT.Semester_Id != ? AND "
          + "UG_REGISTRATION_RESULT.Exam_Type = 1 AND " + "UG_REGISTRATION_RESULT.GRADE_LETTER = 'F')";

  // Improvement_Limit_Calculation_Theory
  String SELECT_IMPROVEMENT_LIMIT =
      "SELECT COUNT (COURSE_ID) as improvement_limit from APPLICATION_CCI WHERE STUDENT_ID= ? and STATUS=7 and APPLICATION_TYPE=5";
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentApplicationCCIDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  public List<ApplicationCCI> getApplicationCCIForImprovementLimit(final String pStudentId) {
    String query = SELECT_IMPROVEMENT_LIMIT;
    return mJdbcTemplate.query(query, new Object[] {pStudentId}, new ApplicationCCIRowMapperForImprovementLimit());
  }

  @Override
  public List<ApplicationCCI> getTotalCarry(final String pStudentId, final Integer pSemesterId) {
    String query = SELECT_TOTAL_CARRY;
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId},
        new ApplicationCCIRowMapperForTotalCarry());
  }

  @Override
  public List<ApplicationCCI> getApplicationCarryForHeadsApproval(final String pApprovalStatus, final String empDeptId) {
    String approvalStatus = pApprovalStatus;
    String query = "";
    if(approvalStatus.equals("Waiting for head's approval")) {
      query = SELECT_ALL_CA;
    }
    else if(approvalStatus.equals("Approved By Head")) {
      query = SELECT_ALL_Payment_ANd_Approved;
    }
    else if(approvalStatus.equals("Rejected By Head")) {
      query = SELECT_ALL_Reject;
    }
    else {
      query = SELECT_ALL_All_Approval_Status;
    }
    return mJdbcTemplate.query(query, new Object[] {empDeptId}, new ApplicationCCICarryRowMapper());
  }

  public List<ApplicationCCI> getByStudentId(String pApprovalStatus, String pStudentId, Integer pSemesterId,
      String empDeptId) {
    String approvalStatus = pApprovalStatus;
    String query = "";
    if(approvalStatus.equals("Waiting for head's approval")) {
      query = SELECT_ALL_CA_SearchByStudentId;
    }
    else if(approvalStatus.equals("Approved By Head")) {
      query = SELECT_ALL_Payment_ANd_Approved_SearchByStudentId;
    }
    else if(approvalStatus.equals("Rejected By Head")) {
      query = SELECT_ALL_REJECT_SearchByStudentId;
    }
    else {
      query = SELECT_ALL_All_Approval_Status_SearchByStudentId;
    }
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pSemesterId, pSemesterId, pSemesterId, pStudentId,
        pStudentId, pStudentId, empDeptId}, new ApplicationCCICSearchByStudentIdRowMapper());
  }

  @Override
  public List<ApplicationCCI> getApplicationCarryForHeadsApprovalAndAppiled(final String pStudentId,
      final Integer pSemesterId) {
    String query = SELECT_ALL_INFO;
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId},
        new ApplicationCCIRowMapperForAppliedAndApproved());
  }

  @Override
  public List<ApplicationCCI> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new ApplicationCCIRowMapper());
  }

  @Override
    public  List<Long>  create(List<MutableApplicationCCI>  pMutableList)  {
        List<Object[]>  parameters  =  getInsertParamList(pMutableList);
        mJdbcTemplate.batchUpdate(INSERT_ONE,  parameters);
        return  parameters.stream()
                .map(paramArray  ->  (Long)  paramArray[0])
                .collect(Collectors.toCollection(ArrayList::new));
    }

  // updateBtach

  // insert for one data
  @Override
  public Long create(MutableApplicationCCI pMutable) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutable.getSemesterId(), pMutable.getStudentId(), pMutable.getCourseId(),
        pMutable.getApplicationType().getValue(), pMutable.getCCIStatus());
    return id;
  }

  @Override
  public int delete(MutableApplicationCCI pMutable) {
    return super.delete(pMutable);
  }

  @Override
  public int deleteByStudentId(String pStudentId) {
    String query = DELETE_ONE + "  where  student_id=?";
    return mJdbcTemplate.update(query, new Object[] {pStudentId});
  }

  @Override
  public int update(MutableApplicationCCI pMutable) {
    String query = DELETE_ONE + "  WHERE  STUDENT_ID=?  ";
    return mJdbcTemplate.update(query, pMutable.getStudentId());
  }

  @Override
  public int update(List<MutableApplicationCCI> pMutableList) {
    List<Object[]> parameters = getUpdateParamList(pMutableList);
    return mJdbcTemplate.batchUpdate(UDAPTE_APPLIED_APPROVED, parameters).length;
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemesterAndType(String pStudentId, int pSemesterId, int pExamType) {
    String query =
        "SELECT  "
            + "    a.id,  "
            + "    a.semester_id,  "
            + "    a.student_id,  "
            + "    a.course_id,  "
            + "    a.application_type,  "
            + "    to_char(a.applied_on,'DD-MM-YYYY') applied_on,  "
            + "    a.STATUS,  "
            + "    t.GRADE_LETTER                                                                GRADE,  "
            + "    c.course_no,  "
            + "    c.course_title,  "
            + "    to_char(exam_routine.exam_date,  'DD-MM-YYYY')  exam_date  "
            + "FROM  application_cci  a,  mst_course  c,  exam_routine,  UG_THEORY_MARKS_CURR  t  "
            + "WHERE  "
            + "    a.course_id  =  c.course_id  AND  a.course_id  =  exam_routine.course_id  AND  exam_routine.exam_type  =  2  AND  a.student_id  =  ?  "
            + "    AND  a.semester_id  =  ?  AND  exam_routine.semester  =  a.semester_id  AND  t.SEMESTER_ID  =  a.SEMESTER_ID  AND  t.EXAM_TYPE  =  1  "
            + "    AND  t.STUDENT_ID  =  a.STUDENT_ID  AND  t.COURSE_ID  =  a.COURSE_ID  ";
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId, pExamType}, new ApplicationCCIRowMapper());
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemesterForSeatPlanView(String pStudentId, Integer pSemesterId) {
    String query =
        "select  to_char(  e.EXAM_DATE,'DD-MM-YYYY')  EXAM_DATE,c.COURSE_NO,a.APPLICATION_TYPE,r.ROOM_NO,r.ROOM_ID  from  EXAM_ROUTINE  e,MST_COURSE  c,APPLICATION_CCI  a,ROOM_INFO  r,SEAT_PLAN  s    "
            + "where  a.STUDENT_ID=?  and  a.SEMESTER_ID=?  and  a.COURSE_ID=c.COURSE_ID  and    e.SEMESTER=a.SEMESTER_ID  and  e.COURSE_ID=a.COURSE_ID  and  e.EXAM_TYPE=2  and  a.STUDENT_ID=  s.STUDENT_ID    "
            + "and  s.ROOM_ID=r.ROOM_ID  and  s.EXAM_TYPE=2  and  s.STUDENT_ID=a.STUDENT_ID";
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId},
        new ApplicationCCIRowMapperForSeatPlanView());
  }

  @Override
  public List<ApplicationCCI> getBySemesterAndExamDate(Integer pSemesterId, String pExamDate) {
    // Date date = (Date) formatter.parse(pExamDate);
    // Date date = (Date) formatter.parse(pExamDate);
    // Timestamp timestamp = new Timestamp(date.getTime());
    /*
     * Date date = (Date) formatter.parse(pExamDate); String examDate = date.toString();
     */
    String query =
        "SELECT  "
            + "    e.exam_date,  "
            + "    course.course_no,  "
            + "    course.course_id,  "
            + "    course.course_title,  "
            + "    course.total_student,  "
            + "    course.year,  "
            + "    course.semester  "
            + "FROM  (  "
            + "              SELECT  "
            + "                  c.course_no,  "
            + "                  c.course_id,  "
            + "                  c.course_title,  "
            + "                  c.year,  "
            + "                  c.semester,  "
            + "                  a.semester_id,  "
            + "                  count(a.student_id)  total_student  "
            + "              FROM  application_cci  a,  mst_course  c  "
            + "              WHERE  a.semester_id  =  ?  "
            + "                          AND  a.course_id  =  c.course_id  "
            + "              GROUP  BY  c.course_no,  c.course_id,  c.course_title,  c.year,  c.semester,  a.semester_id)  course,  exam_routine  e  "
            + "WHERE  e.course_id  =  course.course_id  AND  "
            + "            e.exam_date  =  to_date(?,  'MM-DD-YYYY')  AND  e.exam_type  =  2  AND  e.semester  =  course.semester_id  "
            + "ORDER  BY  course.course_no";

    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamDate}, new ApplicationCCIRowMapperForSeatPlan());
  }

  @Override
  public List<ApplicationCCI> getBySemesterAndType(int pSemesterId, int pExamType) {
    return super.getBySemesterAndType(pSemesterId, pExamType);
  }

  @Override
  public List<ApplicationCCI> getByProgramAndSemesterAndType(int pProgramId, int pSemesterId, int pExamType) {
    return super.getByProgramAndSemesterAndType(pProgramId, pSemesterId, pExamType);
  }

  // fetching GET M
  @Override
  public List<ApplicationCCI> getByStudentIdAndSemester(String pStudentId, int pSemesterId) {
    String query =
        "SELECT    "
            + "    a.id,    "
            + "    a.semester_id,    "
            + "    a.student_id,    "
            + "    a.course_id,    "
            + "    a.application_type,    "
            + "    to_char(a.applied_on,'DD-MM-YYYY') applied_on,    "
            + "    a.STATUS,    "
            + "    t.GRADE_LETTER                                                                GRADE,    "
            + "    a.TRANSACTION_ID,    "
            + "    c.course_no,    "
            + "    c.course_title,    "
            + "  c.YEAR,      "
            + "    c.SEMESTER,    "
            + "    to_char(exam_routine.exam_date,  'DD-MM-YYYY')  exam_date    "
            + "FROM  application_cci  a,  mst_course  c,  exam_routine,  UG_REGISTRATION_RESULT  t    "
            + "WHERE    "
            + "    a.course_id  =  c.course_id  AND  a.course_id  =  exam_routine.course_id  AND  exam_routine.exam_type  =  2  AND  a.student_id  =  ?    "
            + "    AND  a.semester_id  =  ?  AND  exam_routine.semester  =  a.semester_id  AND  t.EXAM_TYPE  =  1    "
            + "    AND  t.STUDENT_ID  =  a.STUDENT_ID  AND  t.COURSE_ID  =  a.COURSE_ID";
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId}, new ApplicationCCIRowMapper());
  }

  // Insert operation
  private List<Object[]> getInsertParamList(List<MutableApplicationCCI> pMutableApplicationCCIs) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationCCI app : pMutableApplicationCCIs) {
      // String transactionId = mIdGenerator.getAlphaNumericId();
      if(app.getApplicationType().getValue() != 2) {
        params.add(new Object[] {mIdGenerator.getNumericId(), app.getSemesterId(), app.getStudentId(),
            app.getCourseId(), app.getApplicationType().getValue(), app.getCCIStatus(), app.getTransactionID()});
      }
      else {
        params.add(new Object[] {mIdGenerator.getNumericId(), app.getSemesterId(), app.getStudentId(),
            app.getCourseId(), app.getApplicationType().getValue(), app.getCCIStatus(), null});
      }

    }

    return params;
  }

  // Update information
  private List<Object[]> getUpdateParamList(List<MutableApplicationCCI> pMutableApplicationCCIs) {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationCCI app : pMutableApplicationCCIs) {
      params.add(new Object[] {app.getCCIStatus(), app.getStudent().getId(), app.getSemester().getId(),
          app.getCourse().getId()});
    }
    return params;
  }

  class ApplicationCCIRowMapper implements RowMapper<ApplicationCCI> {
    @Override
    public ApplicationCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationCCI application = new PersistentApplicationCCI();
      application.setId(pResultSet.getLong("id"));
      application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      application.setStudentId(pResultSet.getString("student_id"));
      application.setCourseId(pResultSet.getString("course_id"));
      application.setApplicationType(ApplicationType.get(pResultSet.getInt("application_type")));
      application.setApplicationDate(pResultSet.getString("applied_on"));
      application.setCourseNo(pResultSet.getString("course_no"));
      application.setCourseTitle(pResultSet.getString("course_title"));
      application.setExamDate(pResultSet.getString("exam_date"));
      application.setCCIStatus(pResultSet.getInt("status"));
      application.setGradeLetter(pResultSet.getString("grade"));
      application.setTransactionID(pResultSet.getString("TRANSACTION_ID"));
      application.setCarryYear(pResultSet.getInt("YEAR"));
      application.setCarrySemester(pResultSet.getInt("SEMESTER"));
      // application.setTransactionID(pResultSet.getString("TRANSACTION_ID"));
      // application.setFullName(pResultSet.getString("FULL_NAME"));
      // application.setCurrentEnrolledSemester(pResultSet.getInt("CURR_ENROLLED_SEMESTER"));

      // application.setApplicationStatus(ApplicationStatus.get(pResultSet.getInt("status")));
      // application.setExamDate(pResultSet.getString("exam_date"));
      // application.setTotalStudent(pResultSet.getInt("total_student"));
      return application;
    }
  }
  class ApplicationCCICSearchByStudentIdRowMapper implements RowMapper<ApplicationCCI> {
    @Override
    public ApplicationCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationCCI application = new PersistentApplicationCCI();
      application.setId(pResultSet.getLong("id"));
      application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      application.setStudentId(pResultSet.getString("student_id"));
      application.setCourseId(pResultSet.getString("course_id"));
      application.setApplicationType(ApplicationType.get(pResultSet.getInt("application_type")));
      application.setApplicationDate(pResultSet.getString("applied_on"));
      application.setCourseNo(pResultSet.getString("course_no"));
      application.setCourseTitle(pResultSet.getString("course_title"));
      application.setExamDate(pResultSet.getString("exam_date"));
      application.setCCIStatus(pResultSet.getInt("status"));
      application.setGradeLetter(pResultSet.getString("grade"));
      application.setCarryYear(pResultSet.getInt("CURR_YEAR"));
      application.setCarrySemester(pResultSet.getInt("CURR_SEMESTER"));
      application.setFullName(pResultSet.getString("FULL_NAME"));
      application.setCurrentEnrolledSemester(pResultSet.getInt("CURR_ENROLLED_SEMESTER"));

      return application;
    }
  }

  class ApplicationCCICarryRowMapper implements RowMapper<ApplicationCCI> {
    @Override
    public ApplicationCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationCCI application = new PersistentApplicationCCI();
      application.setId(pResultSet.getLong("id"));
      application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      application.setStudentId(pResultSet.getString("student_id"));
      application.setCourseId(pResultSet.getString("course_id"));
      application.setApplicationType(ApplicationType.get(pResultSet.getInt("application_type")));
      application.setApplicationDate(pResultSet.getString("applied_on"));
      application.setCourseNo(pResultSet.getString("course_no"));
      application.setCourseTitle(pResultSet.getString("course_title"));
      application.setExamDate(pResultSet.getString("exam_date"));
      application.setCCIStatus(pResultSet.getInt("status"));
      application.setGradeLetter(pResultSet.getString("grade"));
      application.setCarryYear(pResultSet.getInt("CURR_YEAR"));
      application.setCarrySemester(pResultSet.getInt("CURR_SEMESTER"));
      application.setFullName(pResultSet.getString("FULL_NAME"));
      application.setCurrentEnrolledSemester(pResultSet.getInt("CURR_ENROLLED_SEMESTER"));

      return application;
    }
  }

  class ApplicationCCIRowMapperForTotalCarry implements RowMapper<ApplicationCCI> {
    @Override
    public ApplicationCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationCCI application = new PersistentApplicationCCI();
      application.setCourseNo(pResultSet.getString("course_no"));
      application.setCourseTitle(pResultSet.getString("course_title"));
      application.setCarryYear(pResultSet.getInt("YEAR"));
      application.setCarrySemester(pResultSet.getInt("SEMESTER"));
      return application;
    }
  }
  class ApplicationCCIRowMapperForImprovementLimit implements RowMapper<ApplicationCCI> {
    @Override
    public ApplicationCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationCCI application = new PersistentApplicationCCI();
      application.setImprovementLimit(pResultSet.getInt("improvement_limit"));
      return application;
    }
  }
  //
  class ApplicationCCIRowMapperForAppliedAndApproved implements RowMapper<ApplicationCCI> {
    @Override
    public ApplicationCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationCCI application = new PersistentApplicationCCI();
      application.setStudentId(pResultSet.getString("STUDENT_ID"));
      application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      application.setCourseId(pResultSet.getString("COURSE_ID"));
      application.setCourseNo(pResultSet.getString("COURSE_NO"));
      application.setCourseTitle(pResultSet.getString("COURSE_TITLE"));
      application.setApplicationType(ApplicationType.get(pResultSet.getInt("APPLICATION_TYPE")));
      application.setApplicationDate(pResultSet.getString("APPLIED_ON"));
      application.setCCIStatus(pResultSet.getInt("STATUS"));
      return application;
    }
  }

  class ApplicationCCIRowMapperForSeatPlan implements RowMapper<ApplicationCCI> {
    @Override
    public ApplicationCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationCCI applicaton = new PersistentApplicationCCI();
      // applicaton.setId(pResultSet.getInt("id"));
      applicaton.setExamDate(pResultSet.getString("exam_date"));
      applicaton.setCourseNo(pResultSet.getString("course_no"));
      applicaton.setCourseId(pResultSet.getString("course_id"));
      applicaton.setCourseTitle(pResultSet.getString("course_title"));
      applicaton.setTotalStudent(pResultSet.getInt("total_student"));
      applicaton.setCourseYear(pResultSet.getInt("year"));
      applicaton.setCourseSemester(pResultSet.getInt("semester"));
      return applicaton;
    }
  }

  class ApplicationCCIRowMapperForSeatPlanView implements RowMapper<ApplicationCCI> {
    @Override
    public ApplicationCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationCCI applicationCCI = new PersistentApplicationCCI();
      applicationCCI.setExamDate(pResultSet.getString("EXAM_DATE"));
      applicationCCI.setCourseNo(pResultSet.getString("COURSE_NO"));
      applicationCCI.setApplicationType(ApplicationType.get(pResultSet.getInt("APPLICATION_TYPE")));
      applicationCCI.setRoomNo(pResultSet.getString("ROOM_NO"));
      applicationCCI.setRoomId(pResultSet.getInt("ROOM_ID"));
      return applicationCCI;
    }
  }

}
