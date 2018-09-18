package org.ums.persistent.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.persistent.model.PersistentTeacher;
import org.ums.decorator.TeacherDaoDecorator;
import org.ums.domain.model.mutable.MutableTeacher;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class PersistentTeacherDao extends TeacherDaoDecorator {
  static String SELECT_ALL =
      "SELECT TEACHER_ID, TEACHER_NAME, DESIGNATION_ID, DESIGNATION_NAME, DEPT_ID, DEPT_NAME, STATUS FROM MVIEW_TEACHERS ";

  Logger mLogger = LoggerFactory.getLogger(PersistentTeacherDao.class);

  private JdbcTemplate mJdbcTemplate;

  public PersistentTeacherDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<Teacher> getAll() {
    return mJdbcTemplate.query(SELECT_ALL, new TeacherRowMapper());
  }

  @Override
  public Teacher get(String pId) {
    String query = SELECT_ALL + "WHERE TEACHER_ID = ?";
    try {
      return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new TeacherRowMapper());
    } catch(Exception e) {
      mLogger.debug("Id error-->" + pId);
      return null;
    }
  }

  @Override
  public List<Teacher> getByDepartment(Department pDepartment) {
    String query = SELECT_ALL + "WHERE DEPT_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pDepartment.getId()}, new TeacherRowMapper());
  }

  class TeacherRowMapper implements RowMapper<Teacher> {
    @Override
    public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableTeacher teacher = new PersistentTeacher();
      teacher.setId(rs.getString("TEACHER_ID"));
      teacher.setName(rs.getString("TEACHER_NAME"));
      teacher.setDesignationId(rs.getString("DESIGNATION_ID"));
      teacher.setDesignationName(rs.getString("DESIGNATION_NAME"));
      teacher.setDepartmentId(rs.getString("DEPT_ID"));
      teacher.setDepartmentName(rs.getString("DEPT_NAME"));
      teacher.setStatus(rs.getBoolean("STATUS"));
      return teacher;
    }
  }
}
