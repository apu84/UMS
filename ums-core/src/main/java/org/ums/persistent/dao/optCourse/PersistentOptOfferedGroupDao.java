package org.ums.persistent.dao.optCourse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.optCourse.OptOfferedGroupDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroup;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.optCourse.PersistentOptOfferedGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

  String INSERT =
      "Insert into OPT_GROUP (ID,GROUP_NAME,SEMESTER_ID,PROGRAM_ID,IS_MANDATORY,\"YEAR\",SEMESTER) values (?,?,?,?,?,?,?)";

  String GET_BY_NAME =
      "SELECT ID,GROUP_NAME,SEMESTER_ID,PROGRAM_ID,IS_MANDATORY,\"YEAR\",SEMESTER_ID FROM OPT_GROUP WHERE SEMESTER_ID=? AND GROUP_NAME=?";
  String DELETE = "DELETE FROM OPT_GROUP WHERE ID=?";

  String GET_BY_SEMESTER_ID =
      "SELECT ID,GROUP_NAME,SEMESTER_ID,PROGRAM_ID,IS_MANDATORY,\"YEAR\",SEMESTER FROM OPT_GROUP WHERE SEMESTER_ID=? AND PROGRAM_ID=? AND \"YEAR\"=? AND SEMESTER=?";

  @Override
  public List<OptOfferedGroup> getBySemesterId(Integer pSemesterId, Integer pProgramId, Integer pYear, Integer pSemester) {
    return mJdbcTemplate.query(GET_BY_SEMESTER_ID, new Object[] {pSemesterId, pProgramId, pYear, pSemester},
        new OptOfferedGroupRowMapper());
  }

  @Override
  public OptOfferedGroup getByGroupName(String pGroupName) {
    return mJdbcTemplate.queryForObject(GET_BY_NAME, new Object[] {pGroupName}, new OptOfferedGroupRowMapper());
  }

  @Override
  public List<Long> create(List<MutableOptOfferedGroup> pMutableList) {
    List<Object[]>  parameters  =  getInsertParamList(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT,  parameters);
    return  parameters.stream()
            .map(paramArray  ->  (Long)  paramArray[0])
            .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<Object[]> getInsertParamList(List<MutableOptOfferedGroup> pOptOfferedGroup) {
    List<Object[]> params = new ArrayList<>();
    for(OptOfferedGroup app : pOptOfferedGroup) {
      params.add(new Object[] {mIdGenerator.getNumericId(), app.getGroupName(), app.getSemesterId(),
          app.getProgramId(), app.getIsMandatory(), app.getYear(), app.getSemester()});
    }

    return params;
  }

  @Override
  public int delete(List<MutableOptOfferedGroup> pMutableList) {
    List<Object[]> parameters = getDeleteParamList(pMutableList);
    return mJdbcTemplate.batchUpdate(DELETE, parameters).length;
  }

  private List<Object[]> getDeleteParamList(List<MutableOptOfferedGroup> pMutableList) {
    List<Object[]> params = new ArrayList<>();
    for(OptOfferedGroup app : pMutableList) {
      params.add(new Object[] {app.getId()});
    }
    return params;
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
