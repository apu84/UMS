package org.ums.persistent.dao.optCourse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.optCourse.OptOfferedGroupCourseMapDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupCourseMap;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroupCourseMap;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class PersistentOptOfferedGroupCourseMapDao extends OptOfferedGroupCourseMapDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentOptOfferedGroupCourseMapDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

}


class OptOfferedGroupCourseMapRowMapper implements RowMapper<OptOfferedGroupCourseMap> {
  @Override
  public OptOfferedGroupCourseMap mapRow(ResultSet pResultSet, int pI) throws SQLException {
    PersistentOptOfferedGroupCourseMap application = new PersistentOptOfferedGroupCourseMap();
    application.setId(pResultSet.getLong("ID"));
    application.setGroupId(pResultSet.getLong("GROUP_ID"));
    application.setCourseId(pResultSet.getString("COURSE_ID"));
    return application;
  }
}
