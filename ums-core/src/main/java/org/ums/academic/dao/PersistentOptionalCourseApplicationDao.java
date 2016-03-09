package org.ums.academic.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.domain.model.dto.OptionalCourseApplicationStatDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

  public class PersistentOptionalCourseApplicationDao {

  static String SELECT_OFFERED_COURSES="Select MST_COURSE.COURSE_ID,COURSE_NO,COURSE_TITLE,CRHR,COURSE_TYPE,PAIR_COURSE_ID From OPT_COURSE_OFFER,MST_COURSE " +
      "Where OPT_COURSE_OFFER.COURSE_ID=MST_COURSE.COURSE_ID " +
      "And Semester_Id=? and Program_Id=? and OPT_COURSE_OFFER.Year=4 and OPT_COURSE_OFFER.Semester=1 ";


  private JdbcTemplate mJdbcTemplate;

  public PersistentOptionalCourseApplicationDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  public List<OptionalCourseApplicationStatDto> getApplicationStatistics(int semesterId, int programId,int year, int semester) throws Exception {
    String query = "Select COURSE_NO,COURSE_TITLE,TOTAL_APPLIED From OPT_COURSE_OFFER,MST_COURSE " +
        "Where OPT_COURSE_OFFER.course_id=MST_COURSE.COURSE_ID ";

    return mJdbcTemplate.query(query, new Object[]{}, new CourseRowMapper());
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
}
