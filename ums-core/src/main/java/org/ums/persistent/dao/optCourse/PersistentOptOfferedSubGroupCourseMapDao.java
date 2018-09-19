package org.ums.persistent.dao.optCourse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.optCourse.OptOfferedSubGroupCourseMapDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedSubGroupCourseMap;
import org.ums.generator.IdGenerator;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class PersistentOptOfferedSubGroupCourseMapDao extends OptOfferedSubGroupCourseMapDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentOptOfferedSubGroupCourseMapDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public List<OptOfferedSubGroupCourseMap> getSubGroupCourses() {
    return super.getSubGroupCourses();
  }
}
