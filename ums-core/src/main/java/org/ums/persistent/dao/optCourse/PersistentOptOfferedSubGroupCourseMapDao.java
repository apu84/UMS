package org.ums.persistent.dao.optCourse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.optCourse.OptOfferedSubGroupCourseMapDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroup;
import org.ums.domain.model.immutable.optCourse.OptOfferedSubGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedSubGroupCourseMap;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroup;
import org.ums.persistent.model.optCourse.PersistentOptOfferedSubGroupCourseMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

  String INSERT = "Insert into OPT_SUBGROUP_COURSE_MAP (ID,SUB_GROUP_ID,COURSE_ID) values (?,?,?)";

  @Override
  public List<Long> create(List<MutableOptOfferedSubGroupCourseMap> pOptOfferedSubGroupCourseMap) {
    List<Object[]>  parameters  =  getInsertParamList(pOptOfferedSubGroupCourseMap);
    mJdbcTemplate.batchUpdate(INSERT,  parameters);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableOptOfferedSubGroupCourseMap> pOptOfferedSubGroupCourseMap) {
    List<Object[]> params = new ArrayList<>();
    for(OptOfferedSubGroupCourseMap app : pOptOfferedSubGroupCourseMap) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getSubGroupId(), app.getCourseId()});
    }
    return params;
  }

  @Override
  public List<OptOfferedSubGroupCourseMap> getSubGroupCourses() {
    return super.getSubGroupCourses();
  }

}


class OptOfferedSubGroupCourseMapRowMapper implements RowMapper<OptOfferedSubGroupCourseMap> {
  @Override
  public OptOfferedSubGroupCourseMap mapRow(ResultSet pResultSet, int pI) throws SQLException {
    PersistentOptOfferedSubGroupCourseMap application = new PersistentOptOfferedSubGroupCourseMap();
    application.setId(pResultSet.getLong("ID"));
    application.setSubGroupId(pResultSet.getLong("SUB_GROUP_ID"));
    application.setCourseId(pResultSet.getString("COURSE_ID"));
    return application;
  }
}
