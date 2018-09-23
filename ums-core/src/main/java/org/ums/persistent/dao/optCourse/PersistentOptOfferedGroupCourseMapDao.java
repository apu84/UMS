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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

  String INSERT = "Insert into OPT_GROUP_COURSE_MAP (ID,GROUP_ID,COURSE_ID) values (?,?,?)";
  String GET_INFO =
      "SELECT b.GROUP_ID,b.COURSE_ID FROM OPT_GROUP a,OPT_GROUP_COURSE_MAP b WHERE SEMESTER_ID=? AND PROGRAM_ID=? AND \"YEAR\"=? AND SEMESTER=? AND a.ID=b.GROUP_ID";

  @Override
  public List<OptOfferedGroupCourseMap> getInfo(Integer pSemesterId, Integer pProgramId, Integer pYear,
      Integer pSemester) {
    return mJdbcTemplate.query(GET_INFO, new Object[] {pSemesterId, pProgramId, pYear, pSemester},
        new OptOfferedGroupCourseMapRowMapper());
  }

  @Override
  public List<Long> create(List<MutableOptOfferedGroupCourseMap> pMutableList) {
    List<Object[]>  parameters  =  getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT,  parameters);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableOptOfferedGroupCourseMap> pOptOfferedGroupCourseMap) {
    List<Object[]> params = new ArrayList<>();
    for(OptOfferedGroupCourseMap app : pOptOfferedGroupCourseMap) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getGroupId(), app.getCourseId()});
    }

    return params;
  }
}


class OptOfferedGroupCourseMapRowMapper implements RowMapper<OptOfferedGroupCourseMap> {
  @Override
  public OptOfferedGroupCourseMap mapRow(ResultSet pResultSet, int pI) throws SQLException {
    PersistentOptOfferedGroupCourseMap application = new PersistentOptOfferedGroupCourseMap();
    application.setGroupId(pResultSet.getLong("GROUP_ID"));
    application.setCourseId(pResultSet.getString("COURSE_ID"));
    return application;
  }
}
