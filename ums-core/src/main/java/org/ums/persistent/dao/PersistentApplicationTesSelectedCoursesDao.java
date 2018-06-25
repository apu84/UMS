package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ApplicationTesSelectedCoursesDaoDecorator;
import org.ums.domain.model.immutable.ApplicationTesSelectedCourses;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentApplicationTesSelectedCourses;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
public class PersistentApplicationTesSelectedCoursesDao extends ApplicationTesSelectedCoursesDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentApplicationTesSelectedCoursesDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String SELECTED_COURSES =
      "SELECT COURSE_ID,SEMESTER_ID,TEACHER_ID,DEPT_ID,ASSIGNED_SECTION,INSERTED_ON from TES_SELECTED_COURSES WHERE ID=?";

  @Override
  public ApplicationTesSelectedCourses get(Long pId) {
    String query = SELECTED_COURSES;
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new setSelectedCoursesRowMapper());
  }

  class setSelectedCoursesRowMapper implements RowMapper<ApplicationTesSelectedCourses> {
    @Override
    public ApplicationTesSelectedCourses mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationTesSelectedCourses application = new PersistentApplicationTesSelectedCourses();
      application.setCourseId(pResultSet.getString("COURSE_ID"));
      application.setSemester(pResultSet.getInt("SEMESTER_ID"));
      application.setTeacherId(pResultSet.getString("TEACHER_ID"));
      application.setDeptId(pResultSet.getString("DEPT_ID"));
      application.setSection(pResultSet.getString("ASSIGNED_SECTION"));
      application.setInsertionDate(pResultSet.getString("INSERTED_ON"));
      return application;
    }
  }
}
