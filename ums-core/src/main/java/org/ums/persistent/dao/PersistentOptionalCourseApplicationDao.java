package org.ums.persistent.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.domain.model.dto.OptCourseStudentDto;
import org.ums.domain.model.dto.OptSectionDto;
import org.ums.domain.model.dto.OptionalCourseApplicationStatDto;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.enums.OptApplicationType;
import org.ums.enums.OptCourseApplicationStatus;
import org.ums.enums.OptCourseCourseStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentOptionalCourseApplicationDao {

  private JdbcTemplate mJdbcTemplate;

  public PersistentOptionalCourseApplicationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public List<OptionalCourseApplicationStatDto> getApplicationStatistics(int pSemesterId,
      int pProgramId, int pYear, int pSemester) throws Exception {
    String query =
        "Select COURSE_NO,COURSE_TITLE,TOTAL_APPLIED From OPT_COURSE_OFFER,MST_COURSE "
            + "Where OPT_COURSE_OFFER.course_id=MST_COURSE.COURSE_ID ";

    return mJdbcTemplate.query(query, new Object[] {}, new CourseRowMapper());
  }

  public int deleteApplicationCourses(int pSemesterId, int pProgramId, int pYear, int pSemester)
      throws Exception {
    String query =
        "Delete OPT_COURSE_OFFER Where Semester_Id=? And Program_Id=? And Year=? And Semester=? And Call_For_Application='Y' ";
    return mJdbcTemplate.update(query, pSemesterId, pProgramId, pYear, pSemester);
  }

  public int insertApplicationCourses(int pSemesterId, int pProgramId, int pYear, int pSemester,
      final List<Course> pCourseList) throws Exception {
    batchInsertApplicationCourse(pSemesterId, pProgramId, pYear, pSemester, pCourseList);
    return 1;
  }

  public void batchInsertApplicationCourse(int pSemesterId, int pProgramId, int pYear,
      int pSemester, final List<Course> pCourseList) {
    String sql =
        "Insert into OPT_COURSE_OFFER(SEMESTER_ID, PROGRAM_ID, YEAR, SEMESTER, COURSE_ID, CALL_FOR_APPLICATION, APPROVED, TOTAL_APPLIED) "
            + " Values(?, ?, ?, ?, ?,'Y', 'N', 0) ";

    mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Course course = pCourseList.get(i);
        ps.setInt(1, pSemesterId);
        ps.setInt(2, pProgramId);
        ps.setInt(3, pYear);
        ps.setInt(4, pSemester);
        ps.setString(5, course.getId());
      }

      @Override
      public int getBatchSize() {
        return pCourseList.size();
      }

    });
  }

  public int insertApprovedCourses(int pSemesterId, int pProgramId, int pYear, int pSemester,
      final List<Course> pCourseList) throws Exception {
    batchMergeApprovedCourse(pSemesterId, pProgramId, pYear, pSemester, pCourseList);
    return 1;
  }

  public void batchMergeApprovedCourse(int pSemesterId, int pProgramId, int pYear, int pSemester,
      final List<Course> pCourseList) {
    String sql =
        "merge into OPT_COURSE_OFFER Offer1 "
            + "    using "
            + "      (select ? SEMESTER_ID,? PROGRAM_ID,? YEAR,? SEMESTER ,? COURSE_ID From Dual) Offer2 "
            + "    on (Offer1.Semester_Id = Offer2.Semester_Id And Offer1.Program_Id = Offer2.Program_Id And Offer1.Year = Offer2.Year And Offer1.Semester = Offer2.Semester And Offer1.Course_Id = Offer2.Course_Id) "
            + "    when matched then "
            + "      update set Offer1.Approved = 'Y' "
            + "  when not matched then "
            + "     insert (SEMESTER_ID, PROGRAM_ID, YEAR, SEMESTER, COURSE_ID,CALL_FOR_APPLICATION, APPROVED, TOTAL_APPLIED) "
            + "     values (?,?,?,?,?,'N','Y',0) ";

    mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Course course = pCourseList.get(i);
        ps.setInt(1, pSemesterId);
        ps.setInt(2, pProgramId);
        ps.setInt(3, pYear);
        ps.setInt(4, pSemester);
        ps.setString(5, course.getId());
        ps.setInt(6, pSemesterId);
        ps.setInt(7, pProgramId);
        ps.setInt(8, pYear);
        ps.setInt(9, pSemester);
        ps.setString(10, course.getId());
      }

      @Override
      public int getBatchSize() {
        return pCourseList.size();
      }

    });
  }

  public List<OptCourseStudentDto> getStudentList(int pSemesterId, String pCourseId, String pStatus)
      throws Exception {
    Object[] objectArray = new Object[] {pSemesterId, pCourseId};
    String query =
        "Select opt.*,std.full_name From OPT_COURSE_APPLICATION opt,STUDENTS std Where std.Student_Id in  "
            + "(Select std.Student_Id From OPT_COURSE_APPLICATION_STATUS Where Status=1)  "
            + "And opt.student_id=std.student_id " + "And opt.Semester_Id=?  and Course_Id=?";

    if(!pStatus.equalsIgnoreCase("all")) {
      query +=
          " and  Status in   " + "( " + "select regexp_substr(?,'[^,]+', 1, level) " + "from dual "
              + "connect by " + "regexp_substr(?, '[^,]+', 1, level) " + "is not null " + ") "
              + "Order by Applied_On Desc ";
      objectArray = new Object[] {pSemesterId, pCourseId, pStatus, pStatus};
    }

    return mJdbcTemplate.query(query, objectArray, new OptStudentRowMapper());
  }

  public List<OptCourseStudentDto> getNonAssignedSectionStudentList(int pSemesterId,
      int pProgramId, String pCourseId) throws Exception {
    String query =
        "Select * From ( "
            + "Select app_status.student_id,students.full_name From OPT_COURSE_APPLICATION_STATUS app_status,STUDENTS,OPT_COURSE_APPLICATION opt_course "
            + "Where  app_status.student_id=students.student_id And app_status.Status=1 "
            + "And OPT_COURSE.STUDENT_ID=students.student_id   AND COURSE_ID='"
            + pCourseId
            + "' "
            + "And app_status.semester_id=opt_course.semester_id "
            + "and STUDENTS.program_id='110500' "
            + "And OPT_COURSE.STATUS=1)tmp1 "
            + "Where tmp1.student_id not in "
            + "(WITH DATA AS "
            + "      ( SELECT STUDENT_LIST str FROM OPT_COURSE_SECTION_INFO WHERE SEMESTER_ID='11012017' AND PROGRAM_ID='110500' AND COURSE_ID='"
            + pCourseId + "' " + "      ) "
            + "    SELECT trim(regexp_substr(str, '[^,]+', 1, LEVEL)) str " + "    FROM DATA "
            + "    CONNECT BY instr(str, ',', 1, LEVEL - 1) > 0 " + ") ";

    return mJdbcTemplate.query(query, new Object[] {}, new OptNonAssignSectionStudentMapper());
  }

  public List<OptSectionDto> getOptionalSectionListWithStudents(int pSemesterId, int pProgramId,
      String pCourseId) throws Exception {
    String query =
        "Select * from OPT_COURSE_SECTION_INFO " + "Where Semester_Id=11012017 "
            + "And Program_id=110500 " + "And Course_Id='" + pCourseId + "' "
            + " Order by Section_Name ";

    return mJdbcTemplate.query(query, new Object[] {}, new OptSectionMapper());
  }

  public int deleteSection(int pSemesterId, int pProgramId, String pCourseId, String pSectionName)
      throws Exception {
    String query =
        "Delete OPT_COURSE_SECTION_INFO Where SEMESTER_ID=? and PROGRAM_ID=? and COURSE_ID=? and SECTION_NAME=?";
    return mJdbcTemplate.update(query, pSemesterId, pProgramId, pCourseId, pSectionName);
  }

  public int mergeSection(int pSemesterId, int pProgramId, String pCourseId, String pSectionName,
      String pStudentList) throws Exception {
    String query =
        " merge into OPT_COURSE_SECTION_INFO tbl1 "
            + "    using "
            + "      (select ? SEMESTER_ID, ? PROGRAM_ID, ? COURSE_ID,?  SECTION_NAME "
            + "       from dual) tbl2 "
            + "    on (tbl1.SEMESTER_ID = tbl2.SEMESTER_ID And tbl1.PROGRAM_ID = tbl2.PROGRAM_ID And tbl1.COURSE_ID = tbl2.COURSE_ID And tbl1.SECTION_NAME = tbl2.SECTION_NAME)  "
            + "    when matched then " + "      update set tbl1.STUDENT_LIST =? "
            + "    when not matched then "
            + "     insert (SEMESTER_ID, PROGRAM_ID, COURSE_ID, SECTION_NAME, STUDENT_LIST) "
            + "     values (?,?,?,?,?) ";

    return mJdbcTemplate.update(query, pSemesterId, pProgramId, pCourseId, pSectionName,
        pStudentList, pSemesterId, pProgramId, pCourseId, pSectionName, pStudentList);
  }

  public OptCourseApplicationStatus getApplicationStatus(String pStudentId, int pSemesterId)
      throws Exception {
    String query =
        "Select status From OPT_COURSE_APPLICATION_STATUS Where Student_Id=? and Semester_Id=?";
    Integer statusId =
        this.mJdbcTemplate.queryForObject(query, new Object[] {pStudentId, pSemesterId},
            Integer.class);

    return OptCourseApplicationStatus.values()[statusId];
  }

  public List<OptCourseStudentDto> getAppliedCoursesByStudent(String pStudentId, int pSemesterId)
      throws Exception {

    String query =
        "Select course.course_id,course.course_no,course.course_title,applied_on,status From OPT_COURSE_APPLICATION,MST_COURSE course "
            + "Where course.course_id=OPT_COURSE_APPLICATION.course_id "
            + "And Student_Id=? and Semester_Id=? ";

    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId},
        new OptStudentCourseRowMapper());
  }

  public int updateCourseStatus(int pSemesterId, String pCourseId, final List<String> pStudentList,
      int pNewStatus) throws Exception {
    batchStatusUpdateForOptionalCourse(pSemesterId, pCourseId, pStudentList, pNewStatus);
    return 1;
  }

  public void batchStatusUpdateForOptionalCourse(int pSemesterId, String pCourseId,
      final List<String> pStudentList, int pNewStatus) {
    String sql =
        "Update OPT_COURSE_APPLICATION Set Status=? Where Student_Id=? and Semester_Id=? and Course_Id=? ";

    mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        ps.setInt(1, pNewStatus);
        ps.setString(2, pStudentList.get(i));
        ps.setInt(3, pSemesterId);
        ps.setString(4, pCourseId);
      }

      @Override
      public int getBatchSize() {
        return pStudentList.size();
      }

    });
  }

  public int updateCourseStatusForDependentCourse(int pSemesterId, String pCourseId,
      final List<String> pStudentList, int pNewStatus) throws Exception {
    batchStatusUpdateForOptionalCourseForDependentCourse(pSemesterId, pCourseId, pStudentList,
        pNewStatus);
    return 1;
  }

  public void batchStatusUpdateForOptionalCourseForDependentCourse(int pSemesterId,
      String pCourseId, final List<String> pStudentList, int pNewStatus) {
    String sql =
        "Update OPT_COURSE_APPLICATION Set Status=? Where  "
            + "(Student_Id,Semester_Id,Course_Id) in  " + "( "
            + "Select Student_Id,Semester_Id,Dependent_Course_Id From OPT_COURSE_APPLICATION "
            + "Where Student_Id=? and Semester_Id=? and Course_Id=? " + ") ";

    mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        ps.setInt(1, pNewStatus);
        ps.setString(2, pStudentList.get(i));
        ps.setInt(3, pSemesterId);
        ps.setString(4, pCourseId);
      }

      @Override
      public int getBatchSize() {
        return pStudentList.size();
      }

    });
  }

  public int deleteCourseAppliedByTeacher(int pSemesterId, String pCourseId,
      final List<String> pStudentList) throws Exception {
    batchDeleteCourseAppliedByTeacher(pSemesterId, pCourseId, pStudentList);
    return 1;
  }

  public void batchDeleteCourseAppliedByTeacher(int pSemesterId, String pCourseId,
      final List<String> pStudentList) {
    String sql =
        " Delete OPT_COURSE_APPLICATION Where Student_Id=? and Semester_Id=? and Course_Id=? and APPLICATION_TYPE=1";
    mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        ps.setString(1, pStudentList.get(i));
        ps.setInt(2, pSemesterId);
        ps.setString(3, pCourseId);

      }

      @Override
      public int getBatchSize() {
        return pStudentList.size();
      }

    });
  }

  public int insertShiftApplication(int pSemesterId, String pCourseId, String pDependentCourseId,
      final List<String> pStudentList) throws Exception {
    batchInsertShiftApplication(pSemesterId, pCourseId, pDependentCourseId, pStudentList);
    return 1;
  }

  public void batchInsertShiftApplication(int pSemesterId, String pCourseId,
      String pDependentCourseId, final List<String> pStudentList) {
    String sql =
        "Insert Into OPT_COURSE_APPLICATION(STUDENT_ID, SEMESTER_ID, COURSE_ID, APPLIED_ON, STATUS, APPLICATION_TYPE, DEPENDENT_COURSE_ID) "
            + "Values(?,?,?,sysdate,1,1,?) ";

    mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        String studentId = pStudentList.get(i);
        ps.setString(1, studentId);
        ps.setInt(2, pSemesterId);
        ps.setString(3, pCourseId);
        ps.setString(4, pDependentCourseId);
      }

      @Override
      public int getBatchSize() {
        return pStudentList.size();
      }

    });
  }

  public int deleteCoursesAppliedByStudent(String pStudentId, int pSemesterId) throws Exception {
    String query = "Delete OPT_COURSE_APPLICATION Where Student_Id=? and Semester_Id=? ";
    return mJdbcTemplate.update(query, pStudentId, pSemesterId);
  }

  public int saveStudentApplication(String pStudentId, int pSemester, final List<String> pCourseList)
      throws Exception {
    batchMergeStudentApplication(pStudentId, pSemester, pCourseList);
    return 1;
  }

  public void batchMergeStudentApplication(String pStudentId, int pSemester,
      final List<String> pCourseList) {
    String sql =
        "merge into OPT_COURSE_APPLICATION application1 "
            + "    using  "
            + "      (select ? STUDENT_ID,? SEMESTER_ID,? COURSE_ID from dual) application2  "
            + "    on (application1.STUDENT_ID = application2.STUDENT_ID And application1.SEMESTER_ID = application2.SEMESTER_ID And application1.COURSE_ID = application2.COURSE_ID)  "
            + "    when matched then  "
            + "      update set APPLIED_ON=Sysdate "
            + "  when not matched then  "
            + "     insert(STUDENT_ID, SEMESTER_ID, COURSE_ID, APPLIED_ON, STATUS,APPLICATION_TYPE)  "
            + "     values (?,?,?,SYSDATE,0,0)  ";

    mJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        String courseId = pCourseList.get(i);
        ps.setString(1, pStudentId);
        ps.setInt(2, pSemester);
        ps.setString(3, courseId);
        ps.setString(4, pStudentId);
        ps.setInt(5, pSemester);
        ps.setString(6, courseId);
      }

      @Override
      public int getBatchSize() {
        return pCourseList.size();
      }

    });
  }

  public int updateStatus(String pStudentId, Integer pSemesterId,
      final OptCourseApplicationStatus status) throws Exception {
    String query =
        "Update OPT_COURSE_APPLICATION_STATUS Set Status=? Where Student_id=? and Semester_Id=?";
    return mJdbcTemplate.update(query, status.getId(), pStudentId, pSemesterId);
  }

  class OptSectionMapper implements RowMapper<OptSectionDto> {
    @Override
    public OptSectionDto mapRow(ResultSet resultSet, int i) throws SQLException {
      OptSectionDto section = new OptSectionDto();
      section.setSectionName(resultSet.getString("SECTION_NAME"));
      section.setStudents(resultSet.getString("STUDENT_LIST"));
      AtomicReference<OptSectionDto> atomicReference = new AtomicReference<>(section);
      return atomicReference.get();
    }
  }

  class OptNonAssignSectionStudentMapper implements RowMapper<OptCourseStudentDto> {
    @Override
    public OptCourseStudentDto mapRow(ResultSet resultSet, int i) throws SQLException {
      OptCourseStudentDto student = new OptCourseStudentDto();
      student.setStudentId(resultSet.getString("STUDENT_ID"));
      student.setStudentName(resultSet.getString("FULL_NAME"));
      AtomicReference<OptCourseStudentDto> atomicReference = new AtomicReference<>(student);
      return atomicReference.get();
    }
  }

  class CourseRowMapper implements RowMapper<OptionalCourseApplicationStatDto> {
    @Override
    public OptionalCourseApplicationStatDto mapRow(ResultSet resultSet, int i) throws SQLException {
      OptionalCourseApplicationStatDto stat = new OptionalCourseApplicationStatDto();
      stat.setCourseNumber(resultSet.getString("COURSE_NO"));
      stat.setTotalApplied(resultSet.getInt("TOTAL_APPLIED"));
      AtomicReference<OptionalCourseApplicationStatDto> atomicReference =
          new AtomicReference<>(stat);
      return atomicReference.get();
    }
  }

  class OptStudentRowMapper implements RowMapper<OptCourseStudentDto> {
    @Override
    public OptCourseStudentDto mapRow(ResultSet resultSet, int i) throws SQLException {
      OptCourseStudentDto student = new OptCourseStudentDto();
      student.setStudentId(resultSet.getString("STUDENT_ID"));
      student.setStudentName(resultSet.getString("FULL_NAME"));
      student.setAppliedOn(resultSet.getString("APPLIED_ON"));
      student.setStatusId(resultSet.getInt("STATUS"));
      student.setStatusLabel(OptCourseCourseStatus.values()[resultSet.getInt("STATUS")].getLabel());

      student.setApplicationTypeId(resultSet.getInt("APPLICATION_TYPE"));
      student.setApplicationTypeLabel(OptApplicationType.values()[resultSet
          .getInt("APPLICATION_TYPE")].getLabel());

      AtomicReference<OptCourseStudentDto> atomicReference = new AtomicReference<>(student);
      return atomicReference.get();
    }
  }

  class OptStudentCourseRowMapper implements RowMapper<OptCourseStudentDto> {
    @Override
    public OptCourseStudentDto mapRow(ResultSet resultSet, int i) throws SQLException {
      OptCourseStudentDto student = new OptCourseStudentDto();
      student.setCourseId(resultSet.getString("COURSE_ID"));
      student.setCourseNo(resultSet.getString("COURSE_NO"));
      student.setCourseTitle(resultSet.getString("COURSE_TITLE"));
      student.setAppliedOn(resultSet.getString("APPLIED_ON"));
      student.setStatusId(resultSet.getInt("STATUS"));
      student.setStatusLabel(OptCourseCourseStatus.values()[resultSet.getInt("STATUS")].getLabel());
      AtomicReference<OptCourseStudentDto> atomicReference = new AtomicReference<>(student);
      return atomicReference.get();
    }
  }

}
