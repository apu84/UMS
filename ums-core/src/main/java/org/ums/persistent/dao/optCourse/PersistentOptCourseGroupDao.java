package org.ums.persistent.dao.optCourse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.optCourse.OptCourseGroupDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptCourseGroup;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.optCourse.PersistentOptCourseGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class PersistentOptCourseGroupDao extends OptCourseGroupDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentOptCourseGroupDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String GET_ALL = "Select ID,NAME from OPT_COURSE_GROUP";

  @Override
  public List<OptCourseGroup> getAll() {
    return mJdbcTemplate.query(GET_ALL, new OptCourseGroupRowMapper());
  }

  class OptCourseGroupRowMapper implements RowMapper<OptCourseGroup> {
    @Override
    public OptCourseGroup mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentOptCourseGroup application = new PersistentOptCourseGroup();
      application.setOptGroupId(pResultSet.getInt("ID"));
      application.setOptGroupName(pResultSet.getString("NAME"));
      return application;
    }
  }

}
