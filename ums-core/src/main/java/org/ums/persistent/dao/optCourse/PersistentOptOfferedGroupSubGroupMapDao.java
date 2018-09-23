package org.ums.persistent.dao.optCourse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.optCourse.OptOfferedGroupSubGroupMapDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupSubGroupMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroupSubGroupMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

  String INSERT = "Insert into OPT_GROUP_SUB_GROUP_MAP (ID,GROUP_ID,SUB_GROUP_ID,SUB_GROUP_NAME) values (?,?,?,?)";
  String GET_BY_SEMESTER_ID =
      "SELECT b.GROUP_ID,b.SUB_GROUP_ID,b.SUB_GROUP_NAME FROM OPT_GROUP a,OPT_GROUP_SUB_GROUP_MAP b WHERE SEMESTER_ID=? AND PROGRAM_ID=? AND \"YEAR\"=? AND SEMESTER=? AND a.ID=b.GROUP_ID";

  @Override
  public List<OptOfferedGroupSubGroupMap> getBySemesterId(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {
    return mJdbcTemplate.query(GET_BY_SEMESTER_ID, new Object[] {pSemesterId, pProgramId, pYear, pSemester},
        new OptOfferedGroupSubGroupMapDaoRowMapper());
  }

  @Override
  public List<Long> create(List<MutableOptOfferedGroupSubGroupMap> pOptOfferedGroupSubGroupMap) {
    List<Object[]>  parameters  =  getInsertParamList(pOptOfferedGroupSubGroupMap);
    mJdbcTemplate.batchUpdate(INSERT,  parameters);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableOptOfferedGroupSubGroupMap> pOptOfferedGroupCourseMap) {
    List<Object[]> params = new ArrayList<>();
    for(OptOfferedGroupSubGroupMap app : pOptOfferedGroupCourseMap) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getGroupId(), mIdGenerator.getNumericId(),
          app.getSubGroupName()});
    }
    return params;
  }

}


class OptOfferedGroupSubGroupMapDaoRowMapper implements RowMapper<OptOfferedGroupSubGroupMap> {
  @Override
  public OptOfferedGroupSubGroupMap mapRow(ResultSet pResultSet, int pI) throws SQLException {
    PersistentOptOfferedGroupSubGroupMap application = new PersistentOptOfferedGroupSubGroupMap();
    application.setGroupId(pResultSet.getLong("GROUP_ID"));
    application.setSubGroupId(pResultSet.getLong("SUB_GROUP_ID"));
    application.setSubGroupName(pResultSet.getString("SUB_GROUP_NAME"));
    return application;
  }
}
