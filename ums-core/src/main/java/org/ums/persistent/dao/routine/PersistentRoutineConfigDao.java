package org.ums.persistent.dao.routine;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.ums.decorator.routine.RoutineConfigDaoDecorator;
import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.enums.ProgramType;
import org.ums.enums.routine.DayType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.routine.PersistentRoutineConfig;
import org.ums.util.UmsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PersistentRoutineConfigDao extends RoutineConfigDaoDecorator {
  private JdbcTemplate mJdbcTemplate;
  private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;
  private IdGenerator mIdGenerator;

  private String SELECT_ONE = "select * from routine_config";
  private String INSERT_ONE =
      "INSERT INTO routine_config(id, program_type, semester_id, day_from, day_to, start_time, end_time, duration, last_modified)  "
          + "    VALUES (:id, :programType, :semesterId, :dayFrom, :dayTo, :startTime, :endTime, :duration, :lastModified)";
  private String UPDATE_ONE =
      "update routine_config set program_type=:programType, semester_id=:semesterId, day_from=:dayFrom, "
          + "  day_to=:dayTo, start_time=:startTime, end_time=:endTime, duration=:duration, last_modified=:lastModified where id=:id";

  public PersistentRoutineConfigDao(JdbcTemplate pJdbcTemplate, NamedParameterJdbcTemplate pNamedParameterJdbcTemplate,
      IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mNamedParameterJdbcTemplate = pNamedParameterJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public RoutineConfig get(Long pId) {
    String query = SELECT_ONE + " where id=:id";
    Map parameterMap = new HashMap();
    parameterMap.put("id", pId);
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, new PersistentRoutineConfigRowMapper());
  }

  @Override
  public RoutineConfig get(Integer pSemesterId, ProgramType pProgramType) {
    String query = SELECT_ONE + " where semester_id=:semesterId and program_type=:programType";
    Map parameterMap = new HashMap();
    parameterMap.put("semesterId", pSemesterId);
    parameterMap.put("programType", pProgramType.getValue());
    return mNamedParameterJdbcTemplate.queryForObject(query, parameterMap, new PersistentRoutineConfigRowMapper());
  }

  @Override
  public Long create(MutableRoutineConfig pMutable) {
    String query = INSERT_ONE;
    Map parameterMap = getInsertOrUpdateParameters(pMutable);
    mNamedParameterJdbcTemplate.update(query, parameterMap);
    return pMutable.getId();
  }

  @Override
  public int update(MutableRoutineConfig pMutable) {
    String query = UPDATE_ONE;
    Map parameterMap = getInsertOrUpdateParameters(pMutable);
    return mNamedParameterJdbcTemplate.update(query, parameterMap);
  }

  private Map getInsertOrUpdateParameters(MutableRoutineConfig pMutableRoutineConfig) {
    Map parameter = new HashMap();
    parameter.put("id", pMutableRoutineConfig.getId());
    parameter.put("programType", pMutableRoutineConfig.getProgramType().getValue());
    parameter.put("semesterId", pMutableRoutineConfig.getSemesterId());
    parameter.put("dayFrom", pMutableRoutineConfig.getDayFrom().getValue());
    parameter.put("dayTo", pMutableRoutineConfig.getDayTo().getValue());
    parameter.put("startTime", java.sql.Time.valueOf(pMutableRoutineConfig.getStartTime()));
    parameter.put("endTime", java.sql.Time.valueOf(pMutableRoutineConfig.getEndTime()));
    parameter.put("duration", pMutableRoutineConfig.getDuration());
    parameter.put("lastModified", UmsUtils.formatDate(new Date(), "YYYYMMDDHHMMSS"));
    return parameter;
  }

  class PersistentRoutineConfigRowMapper implements RowMapper<RoutineConfig> {
    @Override
    public RoutineConfig mapRow(ResultSet resultSet, int i) throws SQLException {
      MutableRoutineConfig routineConfig = new PersistentRoutineConfig();
      routineConfig.setId(resultSet.getLong("id"));
      routineConfig.setProgramType(ProgramType.get(resultSet.getInt("program_type")));
      routineConfig.setSemesterId(resultSet.getInt("semester_id"));
      routineConfig.setDayFrom(DayType.get(resultSet.getInt("day_from")));
      routineConfig.setDayTo(DayType.get(resultSet.getInt("day_to")));
      routineConfig.setStartTime(resultSet.getTime("start_time").toLocalTime());
      routineConfig.setEndTime(resultSet.getTime("end_time").toLocalTime());
      routineConfig.setDuration(resultSet.getInt("duration"));
      routineConfig.setLastModified(resultSet.getString("last_modified"));
      return routineConfig;
    }
  }
}
