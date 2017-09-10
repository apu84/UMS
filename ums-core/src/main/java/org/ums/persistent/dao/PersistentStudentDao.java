package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.zookeeper.Op;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.StudentDaoDecorator;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.enums.StudentStatus;
import org.ums.persistent.model.PersistentStudent;
import org.ums.persistent.model.PersistentTeacher;
import org.ums.util.Constants;

import com.google.common.collect.Lists;

public class PersistentStudentDao extends StudentDaoDecorator {
  static String SELECT_ALL = "SELECT" + "  STUDENT_ID," + "  FULL_NAME," + "  DEPT_ID," + "  SEMESTER_ID,"
      + "  FATHER_NAME," + "  MOTHER_NAME," + "  BIRTH_DATE," + "  GENDER," + "  PRESENT_ADDRESS,"
      + "  PERMANENT_ADDRESS," + "  MOBILE_NUMBER," + "  PHONE_NUMBER," + "  BLOOD_GROUP," + "  EMAIL_ADDRESS,"
      + "  GUARDIAN_NAME," + "  GUARDIAN_MOBILE," + "  GUARDIAN_PHONE," + "  GUARDIAN_EMAIL," + "  PROGRAM_ID,"
      + "  LAST_MODIFIED," + "  ENROLLMENT_TYPE," + "  CURR_YEAR," + "  CURR_SEMESTER," + "  CURR_ENROLLED_SEMESTER,"
      + "  THEORY_SECTION," + "  SESSIONAL_SECTION," + "  ADVISER," + "  STATUS" + "  FROM STUDENTS ";

  static String UPDATE_ALL = "UPDATE STUDENTS SET" + "  FULL_NAME = ?," + "  DEPT_ID = ?," + "  SEMESTER_ID = ?,"
      + "  FATHER_NAME = ?," + "  MOTHER_NAME = ?," + "  BIRTH_DATE = TO_DATE(?, '"
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
      + "  CURR_SEMESTER = ?, " + "  CURR_ENROLLED_SEMESTER = ?" + "  THEORY_SECTION = ?," + "  SESSIONAL_SECTION = ?";

  static String DELETE_ALL = "DELETE FROM STUDENTS";
  static String CREATE_ALL = "INSERT INTO STUDENTS(" + "  STUDENT_ID," + "  FULL_NAME," + "  DEPT_ID,"
      + "  SEMESTER_ID," + "  FATHER_NAME," + "  MOTHER_NAME," + "  BIRTH_DATE," + "  GENDER," + "  PRESENT_ADDRESS,"
      + "  PERMANENT_ADDRESS," + "  MOBILE_NUMBER," + "  PHONE_NUMBER," + "  BLOOD_GROUP," + "  EMAIL_ADDRESS,"
      + "  GUARDIAN_NAME," + "  GUARDIAN_MOBILE," + "  GUARDIAN_PHONE," + "  GUARDIAN_EMAIL," + "  PROGRAM_ID,"
      + "  LAST_MODIFIED," + "  ENROLLMENT_TYPE," + "  CURR_YEAR, " + "  CURR_SEMESTER," + "  CURR_ENROLLED_SEMESTER"
      + "  THEORY_SECTION," + "  SESSIONAL_SECTION" + ") VALUES (?,?,?,?,?,?,TO_DATE(?, '" + Constants.DATE_FORMAT
      + "'),?,?,?,?,?,?,?,?,?,?,?,?," + getLastModifiedSql() + ",?, ?, ?, ?, ?,?)";

  private JdbcTemplate mJdbcTemplate;

  public PersistentStudentDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int update(MutableStudent pMutable) {
    String query = UPDATE_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.update(query, getUpdateParamArray(Lists.newArrayList(pMutable)).get(0));
  }

  @Override
  public int delete(MutableStudent pMutable) {
    String query = DELETE_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int getSize(int pSemesterId, int pProgramId) {
    String query = "select count(*) from students where semester_id=? and program_id=?";
    return mJdbcTemplate.queryForObject(query, Integer.class, pSemesterId, pProgramId);
  }

  @Override
  public String create(MutableStudent pMutable) {
    String query =
        "INSERT INTO STUDENTS (STUDENT_ID, FULL_NAME, DEPT_ID, SEMESTER_ID, FATHER_NAME, MOTHER_NAME, BIRTH_DATE, curr_year,curr_semester, curr_enrolled_semester,enrollment_type, program_id,status,gender, last_modified) "
            + " VALUES (?, ?, ?, ?, ?, ?, to_date(?,'dd/mm/yy'),?,?,?,?,?,1,?," + getLastModifiedSql() + ")";

    DateFormat df = new SimpleDateFormat("dd/mm/yy");

    String birthDate = df.format(pMutable.getDateOfBirth());
    mJdbcTemplate.update(query, pMutable.getId(), pMutable.getFullName(), pMutable.getDepartment().getId(), pMutable
        .getSemester().getId(), pMutable.getFatherName(), pMutable.getMotherName(), birthDate, pMutable
        .getCurrentYear(), pMutable.getCurrentAcademicSemester(), pMutable.getCurrentEnrolledSemesterId(), 1, pMutable
        .getProgram().getId(), pMutable.getGender());
    return pMutable.getId();
  }

  /*
   * This get(pId) method can be used for getting student's info while student log in with his or
   * her password. No separate rest call is needed ,like : /academic/student/11001, The information
   * can be fetched with the following method, by passing the studentId vai login methodology.
   */

  @Override
  public Student get(String pId) {
    String query = SELECT_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new StudentRowMapper());
  }

  @Override
  public List<Student> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new StudentRowMapper());
  }

  @Override
  public List<Student> getActiveStudents() {
    String query = SELECT_ALL + " where status=1";
    return mJdbcTemplate.query(query, new StudentRowMapper());
  }

  @Override
  public List<Student> getRegisteredStudents(int pSemesterId, int pExamType) {
    String query =
        "SELECT " + "  STUDENTS.STUDENT_ID, " + "  FULL_NAME, " + "  DEPT_ID, " + "  STUDENTS.SEMESTER_ID, "
            + "  FATHER_NAME, " + "  MOTHER_NAME, " + "  BIRTH_DATE, " + "  GENDER, " + "  PRESENT_ADDRESS, "
            + "  PERMANENT_ADDRESS, " + "  MOBILE_NUMBER, " + "  PHONE_NUMBER, " + "  BLOOD_GROUP, "
            + "  EMAIL_ADDRESS, " + "  GUARDIAN_NAME, " + "  GUARDIAN_MOBILE, " + "  GUARDIAN_PHONE, "
            + "  GUARDIAN_EMAIL, " + "  STUDENTS.PROGRAM_ID, " + "  STUDENTS.LAST_MODIFIED, " + "  ENROLLMENT_TYPE, "
            + "  CURR_YEAR, " + "  CURR_SEMESTER, " + "  CURR_ENROLLED_SEMESTER, " + "  THEORY_SECTION, "
            + "  SESSIONAL_SECTION, " + "  ADVISER, " + "  STATUS " + "FROM STUDENTS "
            + "WHERE STUDENT_ID IN (SELECT DISTINCT STUDENT_ID " + "                     FROM UG_REGISTRATION_RESULT "
            + "                     WHERE STUDENTS.SEMESTER_ID = ? AND EXAM_TYPE = ?) "
            + "ORDER BY PROGRAM_ID, STUDENT_ID, CURR_YEAR, CURR_SEMESTER";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType}, new StudentRowMapper());
  }

  @Override
  public List<Student> getRegisteredStudents(int pGroupNo, int pSemesterId, int pExamType) {
    String query =
        "SELECT "
            + "  STUDENTS.STUDENT_ID, "
            + "  FULL_NAME, "
            + "  DEPT_ID, "
            + "  STUDENTS.SEMESTER_ID, "
            + "  FATHER_NAME, "
            + "  MOTHER_NAME, "
            + "  BIRTH_DATE, "
            + "  GENDER, "
            + "  PRESENT_ADDRESS, "
            + "  PERMANENT_ADDRESS, "
            + "  MOBILE_NUMBER, "
            + "  PHONE_NUMBER, "
            + "  BLOOD_GROUP, "
            + "  EMAIL_ADDRESS, "
            + "  GUARDIAN_NAME, "
            + "  GUARDIAN_MOBILE, "
            + "  GUARDIAN_PHONE, "
            + "  GUARDIAN_EMAIL, "
            + "  STUDENTS.PROGRAM_ID, "
            + "  STUDENTS.LAST_MODIFIED, "
            + "  ENROLLMENT_TYPE, "
            + "  CURR_YEAR, "
            + "  CURR_SEMESTER, "
            + "  CURR_ENROLLED_SEMESTER, "
            + "  THEORY_SECTION, "
            + "  SESSIONAL_SECTION, "
            + "  ADVISER, "
            + "  STATUS "
            + "FROM STUDENTS "
            + "WHERE STUDENT_ID IN (SELECT DISTINCT (r.STUDENT_ID) "
            + "                     FROM "
            + "                       UG_REGISTRATION_RESULT r, "
            + "                       SP_GROUP g, "
            + "                       EXAM_ROUTINE e, "
            + "                       MST_COURSE c "
            + "                     WHERE g.GROUP_NO = ? AND r.SEMESTER_ID = ? AND r.EXAM_TYPE = ? "
            + "                           AND e.SEMESTER = r.SEMESTER_ID AND e.EXAM_TYPE = r.EXAM_TYPE "
            + "                           AND r.COURSE_ID = e.COURSE_ID AND "
            + "                           c.COURSE_ID = r.COURSE_ID AND g.SEMESTER_ID = r.SEMESTER_ID AND g.TYPE = r.EXAM_TYPE "
            + "                           AND c.YEAR = g.YEAR AND c.SEMESTER = g.SEMESTER) "
            + "ORDER BY PROGRAM_ID, STUDENT_ID, CURR_YEAR, CURR_SEMESTER";
    return mJdbcTemplate.query(query, new Object[] {pGroupNo, pSemesterId, pExamType}, new StudentRowMapper());
  }

  @Override
  public int update(List<MutableStudent> pStudentList) {
    String query = UPDATE_ALL + " WHERE STUDENT_ID = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamArray(pStudentList)).length;
  }

  @Override
  public int updateStudentsAdviser(List<MutableStudent> pStudents) {
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
      params.add(new Object[] {student.getFullName(), student.getDepartmentId(), student.getSemesterId(),
          student.getFatherName(), student.getMotherName(), student.getDateOfBirth(), student.getGender(),
          student.getPresentAddress(), student.getPermanentAddress(), student.getMobileNo(), student.getPhoneNo(),
          student.getBloodGroup(), student.getEmail(), student.getGuardianName(), student.getGuardianMobileNo(),
          student.getGuardianPhoneNo(), student.getGuardianEmail(), student.getEnrollmentType().getValue(),
          student.getCurrentYear(), student.getCurrentAcademicSemester(), student.getCurrentEnrolledSemester().getId(),
          student.getTheorySection(), student.getSessionalSection(), student.getId()});
    }

    return params;
  }

  @Override
  public List<Student> getStudentListFromStudentsString(String pStudents) {
    String query =
        "Select * From Students where Student_Id in ( " + "select regexp_substr(?,'[^,]+', 1, level)  " + "from dual  "
            + "connect by  " + "regexp_substr(?, '[^,]+', 1, level)  " + "is not null  " + ") ";
    return mJdbcTemplate.query(query, new Object[] {pStudents, pStudents}, new StudentRowMapper());
  }

  @Override
  public List<Student> getActiveStudentsByAdviser(String pTeacherId) {
    String query = SELECT_ALL + " where adviser=? and status=1";
    return mJdbcTemplate.query(query, new Object[] {pTeacherId}, new StudentRowMapper());
  }

  @Override
  public List<Student> getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(String pCourseId, int pSemesterId) {
    String query2 =
        " select s.student_id,p.program_short_name,s.year,s.semester,application_type,s.program_id from STUDENTS s,mst_program p,(  "
            + "         select course_no,student_id,application_type from exam_routine r,mst_course c,        "
            + "         (select distinct(course_id),student_id,application_type from application_cci where semester_id=? ) a          "
            + "         where exam_type=2 and exam_date = to_date(?,'MM-DD-YYYY') and r.course_id=c.course_id and a.course_id=c.course_id order by c.course_no,a.student_id) a        "
            + "         where a.student_id=s.student_id and s.program_id=p.program_id";
    return mJdbcTemplate.query(query2, new Object[] {pSemesterId, pCourseId}, new SpStudentRowMapperForCCI());
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

    return mJdbcTemplate.query(query2, new Object[] {pSemesterId, pExamDate}, new SpStudentRowMapperForCCI2());

  }

  @Override
  public int updateStudentsStatus(StudentStatus pStudentStatus, String pStudentId) {
    String query = "update students set status=? where student_id=?";
    return mJdbcTemplate.update(query, pStudentStatus.getId(), pStudentId);
  }

  @Override
  public Optional<Student> getByEmail(String pEmail) {
    String query = SELECT_ALL + "WHERE EMAIL_ADDRESS IS NOT NULL AND EMAIL_ADDRESS = ?";
    List<Student> students = mJdbcTemplate.query(query, new Object[] {pEmail}, new StudentRowMapper());
    return students.size() == 1 ? Optional.of(students.get(0)) : Optional.empty();
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
