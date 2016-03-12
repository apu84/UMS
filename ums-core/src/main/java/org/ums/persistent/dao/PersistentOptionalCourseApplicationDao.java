package org.ums.persistent.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.domain.model.dto.OptCourseStudentDto;
import org.ums.domain.model.dto.OptionalCourseApplicationStatDto;
import org.ums.domain.model.immutable.Course;

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

  public List<OptionalCourseApplicationStatDto> getApplicationStatistics(int pSemesterId, int pProgramId,int pYear, int pSemester) throws Exception {
    String query = "Select COURSE_NO,COURSE_TITLE,TOTAL_APPLIED From OPT_COURSE_OFFER,MST_COURSE " +
        "Where OPT_COURSE_OFFER.course_id=MST_COURSE.COURSE_ID ";

    return mJdbcTemplate.query(query, new Object[]{}, new CourseRowMapper());
  }

    public int deleteApplicationCourses(int pSemesterId, int pProgramId,int pYear, int pSemester) throws Exception {
      String query="Delete OPT_COURSE_OFFER Where Semester_Id=? And Program_Id=? And Year=? And Semester=? And Call_For_Application='Y' ";
      return mJdbcTemplate.update(query, pSemesterId,pProgramId,pYear,pSemester);
    }

    public int insertApplicationCourses(int pSemesterId, int pProgramId,int pYear, int pSemester,final List<Course> pCourseList) throws Exception {
      batchInsertApplicationCourse(pSemesterId, pProgramId, pYear, pSemester, pCourseList);
      return 1;
    }

    public void batchInsertApplicationCourse(int pSemesterId, int pProgramId,int pYear, int pSemester,final List<Course> pCourseList){
      String sql = "Insert into DB_IUMS.OPT_COURSE_OFFER(SEMESTER_ID, PROGRAM_ID, YEAR, SEMESTER, COURSE_ID, CALL_FOR_APPLICATION, APPROVED, TOTAL_APPLIED) " +
          " Values(?, ?, ?, ?, ?,'Y', 'N', 0) ";

      mJdbcTemplate .batchUpdate(sql, new BatchPreparedStatementSetter() {

        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
          Course course = pCourseList.get(i);
          ps.setInt(1,pSemesterId);
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

    public int insertApprovedCourses(int pSemesterId, int pProgramId,int pYear, int pSemester,final List<Course> pCourseList) throws Exception {
      batchMergeApprovedCourse(pSemesterId, pProgramId, pYear, pSemester, pCourseList);
      return 1;
    }

    public void batchMergeApprovedCourse(int pSemesterId, int pProgramId,int pYear, int pSemester,final List<Course> pCourseList){
      String sql = "merge into OPT_COURSE_OFFER Offer1 " +
          "    using " +
          "      (select ? SEMESTER_ID,? PROGRAM_ID,? YEAR,? SEMESTER ,? COURSE_ID From Dual) Offer2 " +
          "    on (Offer1.Semester_Id = Offer2.Semester_Id And Offer1.Program_Id = Offer2.Program_Id And Offer1.Year = Offer2.Year And Offer1.Semester = Offer2.Semester And Offer1.Course_Id = Offer2.Course_Id) " +
          "    when matched then " +
          "      update set Offer1.Approved = 'Y' " +
          "  when not matched then " +
          "     insert (SEMESTER_ID, PROGRAM_ID, YEAR, SEMESTER, COURSE_ID,CALL_FOR_APPLICATION, APPROVED, TOTAL_APPLIED) " +
          "     values (?,?,?,?,?,'N','Y',0) "
          ;

      mJdbcTemplate .batchUpdate(sql, new BatchPreparedStatementSetter() {

        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
          Course course = pCourseList.get(i);
          ps.setInt(1,pSemesterId);
          ps.setInt(2, pProgramId);
          ps.setInt(3, pYear);
          ps.setInt(4, pSemester);
          ps.setString(5, course.getId());
          ps.setInt(6,pSemesterId);
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


    public List<OptCourseStudentDto> getStudentList(int pSemesterId,String pCourseId, String pStatus) throws Exception {
      String query = "Select opt.*,std.full_name From OPT_COURSE_APPLICATION opt,STUDENTS std Where std.Student_Id in  " +
          "(Select std.Student_Id From OPT_COURSE_APPLICATION_STATUS Where Status=1)  " +
          "And opt.student_id=std.student_id " +
          "And opt.Semester_Id=?  and Course_Id=? and  Status in   " +
          "( " +
          "select regexp_substr(?,'[^,]+', 1, level) " +
          "from dual " +
          "connect by " +
          "regexp_substr(?, '[^,]+', 1, level) " +
          "is not null " +
          ") "+
          "Order by Applied_On Desc ";

      return mJdbcTemplate.query(query, new Object[]{pSemesterId,pCourseId,pStatus,pStatus}, new OptStudentRowMapper());
    }


    class CourseRowMapper implements RowMapper<OptionalCourseApplicationStatDto> {
      @Override
      public OptionalCourseApplicationStatDto mapRow(ResultSet resultSet, int i) throws SQLException {
        OptionalCourseApplicationStatDto stat= new OptionalCourseApplicationStatDto();
        stat.setCourseNumber(resultSet.getString("COURSE_NO"));
        stat.setTotalApplied(resultSet.getInt("TOTAL_APPLIED"));
        AtomicReference<OptionalCourseApplicationStatDto> atomicReference = new AtomicReference<>(stat);
        return atomicReference.get();
      }
    }

    class OptStudentRowMapper implements RowMapper<OptCourseStudentDto> {
      @Override
      public OptCourseStudentDto mapRow(ResultSet resultSet, int i) throws SQLException {
        OptCourseStudentDto student= new OptCourseStudentDto();
        student.setStudentId(resultSet.getString("STUDENT_ID"));
        student.setStudentName(resultSet.getString("FULL_NAME"));
        student.setAppliedOn(resultSet.getString("APPLIED_ON"));
        student.setStatus(resultSet.getString("STATUS"));
        AtomicReference<OptCourseStudentDto> atomicReference = new AtomicReference<>(student);
        return atomicReference.get();
      }
    }
}
