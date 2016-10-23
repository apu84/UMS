package org.ums.persistent.dao;

import com.google.common.collect.Lists;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.StudentDaoDecorator;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.persistent.model.PersistentStudent;
import org.ums.persistent.model.PersistentTeacher;
import org.ums.util.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentStudentDao extends StudentDaoDecorator {
  static String SELECT_ALL = "SELECT" + "  STUDENT_ID," + "  FULL_NAME," + "  DEPT_ID,"
      + "  SEMESTER_ID," + "  FATHER_NAME," + "  MOTHER_NAME," + "  BIRTH_DATE," + "  GENDER,"
      + "  PRESENT_ADDRESS," + "  PERMANENT_ADDRESS," + "  MOBILE_NUMBER," + "  PHONE_NUMBER,"
      + "  BLOOD_GROUP," + "  EMAIL_ADDRESS," + "  GUARDIAN_NAME," + "  GUARDIAN_MOBILE,"
      + "  GUARDIAN_PHONE," + "  GUARDIAN_EMAIL," + "  PROGRAM_ID," + "  LAST_MODIFIED,"
      + "  ENROLLMENT_TYPE," + "  CURR_YEAR," + "  CURR_SEMESTER," + "  CURR_ENROLLED_SEMESTER,"
      + "  THEORY_SECTION," + "  SESSIONAL_SECTION," + "  ADVISER," + "  STATUS"
      + "  FROM STUDENTS ";

  static String UPDATE_ALL = "UPDATE STUDENTS SET" + "  FULL_NAME = ?," + "  DEPT_ID = ?,"
      + "  SEMESTER_ID = ?," + "  FATHER_NAME = ?," + "  MOTHER_NAME = ?,"
      + "  BIRTH_DATE = TO_DATE(?, '"
      + Constants.DATE_FORMAT
      + "'),"
      + "  GENDER = ?,"
      + "  PRESENT_ADDRESS = ?,"
      + "  PERMANENT_ADDRESS = ?,"
      + "  MOBILE_NUMBER = ?,"
      + "  PHONE_NUMBER = ?,"
      + "  BLOOD_GROUP = ?,"
      + "  EMAIL_ADDRESS = ?,"
      + "  GUARDIAN_NAME = ?,"
      + "  GUARDIAN_MOBILE = ?,"
      + "  GUARDIAN_PHONE = ?,"
      + "  GUARDIAN_EMAIL = ?,"
      + "  LAST_MODIFIED = "
      + getLastModifiedSql()
      + ","
      + "  ENROLLMENT_TYPE = ?,"
      + "  CURR_YEAR = ?,"
      + "  CURR_SEMESTER = ?, "
      + "  CURR_ENROLLED_SEMESTER = ?"
      + "  THEORY_SECTION = ?," + "  SESSIONAL_SECTION = ?";

  static String DELETE_ALL = "DELETE FROM STUDENTS";
  static String CREATE_ALL = "INSERT INTO STUDENTS(" + "  STUDENT_ID," + "  FULL_NAME,"
      + "  DEPT_ID," + "  SEMESTER_ID," + "  FATHER_NAME," + "  MOTHER_NAME," + "  BIRTH_DATE,"
      + "  GENDER," + "  PRESENT_ADDRESS," + "  PERMANENT_ADDRESS," + "  MOBILE_NUMBER,"
      + "  PHONE_NUMBER," + "  BLOOD_GROUP," + "  EMAIL_ADDRESS," + "  GUARDIAN_NAME,"
      + "  GUARDIAN_MOBILE," + "  GUARDIAN_PHONE," + "  GUARDIAN_EMAIL," + "  PROGRAM_ID,"
      + "  LAST_MODIFIED," + "  ENROLLMENT_TYPE," + "  CURR_YEAR, " + "  CURR_SEMESTER,"
      + "  CURR_ENROLLED_SEMESTER" + "  THEORY_SECTION," + "  SESSIONAL_SECTION"
      + ") VALUES (?,?,?,?,?,?,TO_DATE(?, '" + Constants.DATE_FORMAT
      + "'),?,?,?,?,?,?,?,?,?,?,?,?," + getLastModifiedSql() + ",?, ?, ?, ?, ?,?)";

  private JdbcTemplate mJdbcTemplate;

  private DateFormat mDateFormat;

  public PersistentStudentDao(final JdbcTemplate pJdbcTemplate, final DateFormat pDateFormat) {
    mJdbcTemplate = pJdbcTemplate;
    mDateFormat = pDateFormat;
  }

  @Override
  public int update(MutableStudent pMutable) throws Exception {
    String query = UPDATE_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.update(query, getUpdateParamArray(Lists.newArrayList(pMutable)).get(0));
  }

  @Override
  public int delete(MutableStudent pMutable) throws Exception {
    String query = DELETE_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int create(MutableStudent pMutable) throws Exception {
    return mJdbcTemplate.update(CREATE_ALL, pMutable.getId(), pMutable.getFullName(),
        pMutable.getDepartmentId(), pMutable.getSemesterId(), pMutable.getFatherName(),
        pMutable.getMotherName(), mDateFormat.format(pMutable.getDateOfBirth()),
        pMutable.getGender(), pMutable.getPresentAddress(), pMutable.getPermanentAddress(),
        pMutable.getMobileNo(), pMutable.getPhoneNo(), pMutable.getBloodGroup(),
        pMutable.getEmail(), pMutable.getGuardianName(), pMutable.getGuardianMobileNo(),
        pMutable.getGuardianPhoneNo(), pMutable.getGuardianEmail(), pMutable.getProgramId(),
        pMutable.getEnrollmentType().getValue(), pMutable.getCurrentYear(),
        pMutable.getCurrentAcademicSemester(), pMutable.getCurrentEnrolledSemester().getId(),
        pMutable.getTheorySection(), pMutable.getSessionalSection());
  }

  /*
   * This get(pId) method can be used for getting student's info while student log in with his or
   * her password. No separate rest call is needed ,like : /academic/student/11001, The information
   * can be fetched with the following method, by passing the studentId vai login methodology.
   */

  @Override
  public Student get(String pId) throws Exception {
    String query = SELECT_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new StudentRowMapper());
  }

  @Override
  public List<Student> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new StudentRowMapper());
  }

  @Override
  public List<Student> getActiveStudents() {
    String query = SELECT_ALL + " where status=1";
    return mJdbcTemplate.query(query, new StudentRowMapper());
  }

  @Override
  public int update(List<MutableStudent> pStudentList) throws Exception {
    String query = UPDATE_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamArray(pStudentList)).length;
  }

  @Override
  public int updateStudentsAdviser(List<MutableStudent> pStudents) throws Exception {
    String query = "UPDATE STUDENTS SET ADVISER=? WHERE STUDENT_ID=?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamArrayForAdviser(pStudents)).length;
  }

  private List<Object[]> getUpdateParamArrayForAdviser(List<MutableStudent> pStudents) {
    List<Object[]> params = new ArrayList<>();

    for(Student student : pStudents) {
      params.add(new Object[] {student.getAdviser().getId(), student.getId()});

    }

    return params;
  }

  private List<Object[]> getUpdateParamArray(List<MutableStudent> pStudents) {
    List<Object[]> params = new ArrayList<>();
    for(Student student : pStudents) {
      params.add(new Object[] {student.getFullName(), student.getDepartmentId(),
          student.getSemesterId(), student.getFatherName(), student.getMotherName(),
          mDateFormat.format(student.getDateOfBirth()), student.getGender(),
          student.getPresentAddress(), student.getPermanentAddress(), student.getMobileNo(),
          student.getPhoneNo(), student.getBloodGroup(), student.getEmail(),
          student.getGuardianName(), student.getGuardianMobileNo(), student.getGuardianPhoneNo(),
          student.getGuardianEmail(), student.getEnrollmentType().getValue(),
          student.getCurrentYear(), student.getCurrentAcademicSemester(),
          student.getCurrentEnrolledSemester().getId(), student.getTheorySection(),
          student.getSessionalSection(), student.getId()});
    }

    return params;
  }

  @Override
  public List<Student> getStudentListFromStudentsString(String pStudents) throws Exception {
    String query =
        "Select * From Students where Student_Id in ( "
            + "select regexp_substr(?,'[^,]+', 1, level)  " + "from dual  " + "connect by  "
            + "regexp_substr(?, '[^,]+', 1, level)  " + "is not null  " + ") ";
    return mJdbcTemplate.query(query, new Object[] {pStudents, pStudents}, new StudentRowMapper());
  }

  @Override
  public List<Student> getActiveStudentsByAdviser(String pTeacherId) {
    String query = SELECT_ALL + " where adviser=? and status=1";
    return mJdbcTemplate.query(query, new Object[] {pTeacherId}, new StudentRowMapper());
  }

  @Override
  public List<Student> getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(String pCourseId,
      int pSemesterId) {
    String query2 =
        " select s.student_id,p.program_short_name,s.year,s.semester,application_type,s.program_id from STUDENTS s,mst_program p,(  "
            + "         select course_no,student_id,application_type from exam_routine r,mst_course c,        "
            + "         (select distinct(course_id),student_id,application_type from application_cci where semester_id=? ) a          "
            + "         where exam_type=2 and exam_date = to_date(?,'MM-DD-YYYY') and r.course_id=c.course_id and a.course_id=c.course_id order by c.course_no,a.student_id) a        "
            + "         where a.student_id=s.student_id and s.program_id=p.program_id";
    return mJdbcTemplate.query(query2, new Object[] {pSemesterId, pCourseId},
        new SpStudentRowMapperForCCI());
  }

  @Override
  public List<Student> getStudentBySemesterIdAndExamDateForCCI(Integer pSemesterId, String pExamDate) {
    String query =
        "select distinct(s.student_id),p.program_short_name,s.year,s.semester,a.application_type,p.program_id from sp_student s,mst_program p, "
            + "(select distinct(application_cci.course_id),student_id,application_type,exam_date from application_cci,exam_routine where application_cci.course_id=exam_routine.course_id and exam_routine.exam_type=2 and exam_date=to_date(?,'MM-DD-YYYY') and semester_id=? )a  "
            + "where a.student_id=s.student_id and s.program_id=p.program_id  order by  p.program_short_name,s.student_id";

    String query2 =
        " select s.student_id,p.program_short_name,s.CURRENT_YEAR,s.CURRENT_SEMESTER,application_type,s.program_id from STUDENTS s,mst_program p,( "
            + "         select course_no,student_id,application_type from exam_routine r,mst_course c,       "
            + "         (select distinct(course_id),student_id,application_type from application_cci where semester_id=? ) a         "
            + "         where exam_type=2 and exam_date = to_date(?,'MM-DD-YYYY') and r.course_id=c.course_id and a.course_id=c.course_id order by c.course_no,a.student_id) a       "
            + "         where a.student_id=s.student_id and s.program_id=p.program_id";

    return mJdbcTemplate.query(query2, new Object[] {pSemesterId, pExamDate},
        new SpStudentRowMapperForCCI2());

  }

  class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableStudent student = new PersistentStudent();
      student.setId(rs.getString("STUDENT_ID"));
      student.setFullName(rs.getString("FULL_NAME"));
      student.setDepartmentId(rs.getString("DEPT_ID"));
      student.setSemesterId(rs.getInt("SEMESTER_ID"));
      student.setFatherName(rs.getString("FATHER_NAME"));
      student.setMotherName(rs.getString("MOTHER_NAME"));
      student.setDateOfBirth(rs.getDate("BIRTH_DATE"));
      student.setGender(rs.getString("GENDER"));
      student.setPresentAddress(rs.getString("PRESENT_ADDRESS"));
      student.setPermanentAddress(rs.getString("PERMANENT_ADDRESS"));
      student.setMobileNo(rs.getString("MOBILE_NUMBER"));
      student.setPhoneNo(rs.getString("PHONE_NUMBER"));
      student.setBloodGroup(rs.getString("BLOOD_GROUP"));
      student.setEmail(rs.getString("EMAIL_ADDRESS"));
      student.setGuardianName(rs.getString("GUARDIAN_NAME"));
      student.setGuardianMobileNo(rs.getString("GUARDIAN_MOBILE"));
      student.setGuardianPhoneNo(rs.getString("GUARDIAN_PHONE"));
      student.setGuardianEmail(rs.getString("GUARDIAN_EMAIL"));
      student.setProgramId(rs.getInt("PROGRAM_ID"));
      student.setLastModified(rs.getString("LAST_MODIFIED"));
      if(rs.getObject("ENROLLMENT_TYPE") != null) {
        student.setEnrollmentType(Student.EnrollmentType.get(rs.getInt("ENROLLMENT_TYPE")));
      }
      if(rs.getObject("CURR_YEAR") != null) {
        student.setCurrentYear(rs.getInt("CURR_YEAR"));
      }
      if(rs.getObject("CURR_SEMESTER") != null) {
        student.setCurrentAcademicSemester(rs.getInt("CURR_SEMESTER"));
      }
      if(rs.getObject("CURR_ENROLLED_SEMESTER") != null) {
        student.setCurrentEnrolledSemesterId(rs.getInt("CURR_ENROLLED_SEMESTER"));
      }
      student.setTheorySection(rs.getString("THEORY_SECTION"));
      student.setSessionalSection(rs.getString("SESSIONAL_SECTION"));

      String teacherId = rs.getString("ADVISER");
      PersistentTeacher teacher = new PersistentTeacher();
      teacher.setId(teacherId);
      student.setAdviser(teacher);
      student.setStatus(rs.getInt("STATUS"));
      AtomicReference<Student> atomicReference = new AtomicReference<>(student);
      return atomicReference.get();
    }
  }

  class SpStudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentStudent spStudent = new PersistentStudent();
      spStudent.setId(pResultSet.getString("STUDENT_ID"));
      spStudent.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      spStudent.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      spStudent.setCurrentYear(pResultSet.getInt("YEAR"));
      spStudent.setCurrentAcademicSemester(pResultSet.getInt("SEMESTER"));
      // spStudent.setStatus(pResultSet.getInt("ACTIVE"));
      spStudent.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return spStudent;
    }
  }

  class SpStudentRowMapperForCCI implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentStudent student = new PersistentStudent();
      student.setId(pResultSet.getString("student_id"));
      student.setProgramShortName(pResultSet.getString("program_short_name"));
      student.setCurrentYear(pResultSet.getInt("year"));
      student.setCurrentAcademicSemester(pResultSet.getInt("semester"));
      student.setApplicationType(pResultSet.getInt("application_type"));
      return student;
    }
  }
  class SpStudentRowMapperForCCI2 implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentStudent student = new PersistentStudent();
      student.setId(pResultSet.getString("student_id"));
      student.setProgramShortName(pResultSet.getString("program_short_name"));
      student.setCurrentYear(pResultSet.getInt("year"));
      student.setCurrentAcademicSemester(pResultSet.getInt("semester"));
      student.setApplicationType(pResultSet.getInt("application_type"));
      student.setProgramId(pResultSet.getInt("program_id"));
      return student;
    }
  }
}
