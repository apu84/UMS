package org.ums.persistent.dao.optCourse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.optCourse.OptOfferedGroupDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroup;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroup;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class PersistentOptOfferedGroupDao extends OptOfferedGroupDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentOptOfferedGroupDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

}


class OptOfferedGroupRowMapper implements RowMapper<OptOfferedGroup> {
  @Override
  public OptOfferedGroup mapRow(ResultSet pResultSet, int pI) throws SQLException {
    PersistentOptOfferedGroup application = new PersistentOptOfferedGroup();
    application.setId(pResultSet.getLong("ID"));
    application.setGroupName(pResultSet.getString("GROUP_NAME"));
    application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
    application.setProgramId(pResultSet.getInt("PROGRAM_ID"));
    application.setIsMandatory(pResultSet.getInt("IS_MANDATORY"));
    application.setYear(pResultSet.getInt("YEAR"));
    application.setSemester(pResultSet.getInt("SEMESTER"));
    return application;
  }
}
