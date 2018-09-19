package org.ums.persistent.dao.optCourse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.optCourse.OptOfferedGroupSubGroupMapDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupSubGroupMap;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroupSubGroupMap;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class PersistentOptOfferedGroupSubGroupMapDao extends OptOfferedGroupSubGroupMapDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentOptOfferedGroupSubGroupMapDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  String INSERT_ALL =
      "Insert into DER_EMP_ATTENDANT (ID,SEMESTER_ID,EXAM_TYPE,IS_ROOM_IN_CHARGE,ROOM_ID,EMPLOYEE_ID) values (?,?,?,?,?,?)";

}


class OptOfferedGroupSubGroupMapDaoRowMapper implements RowMapper<OptOfferedGroupSubGroupMap> {
  @Override
  public OptOfferedGroupSubGroupMap mapRow(ResultSet pResultSet, int pI) throws SQLException {
    PersistentOptOfferedGroupSubGroupMap application = new PersistentOptOfferedGroupSubGroupMap();
    application.setId(pResultSet.getLong("ID"));
    application.setGroupId(pResultSet.getLong("GROUP_ID"));
    application.setSubGroupId(pResultSet.getLong("SUB_GROUP_ID"));
    application.setSubGroupName(pResultSet.getString("SUB_GROUP_NAME"));
    return application;
  }
}
