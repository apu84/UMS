package org.ums.persistent.dao.meeting;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.meeting.AgendaResolutionDaoDecorator;
import org.ums.domain.model.immutable.meeting.AgendaResolution;
import org.ums.domain.model.mutable.meeting.MutableAgendaResolution;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.meeting.PersistentAgendaResolution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentAgendaResolutionDao extends AgendaResolutionDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO MEETING_AGENDA_RESOLUTION (ID, AGENDA_NO, AGENDA, AGENDA_EDITOR, PLAIN_AGENDA, RESOLUTION, RESOLUTION_EDITOR, PLAIN_RESOLUTION, SCHEDULE_ID, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, "
          + getLastModifiedSql() + ")";

  static String GET_ONE =
      "SELECT ID, AGENDA_NO, AGENDA, AGENDA_EDITOR, PLAIN_AGENDA, RESOLUTION, RESOLUTION_EDITOR, PLAIN_RESOLUTION, SCHEDULE_ID FROM MEETING_AGENDA_RESOLUTION ";

  static String UPDATE_ONE =
      "UPDATE MEETING_AGENDA_RESOLUTION SET AGENDA_NO = ?, AGENDA = ?, AGENDA_EDITOR = ?, PLAIN_AGENDA = ?, RESOLUTION = ?, RESOLUTION_EDITOR = ?, PLAIN_RESOLUTION = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM MEETING_AGENDA_RESOLUTION ";

  static String EXISTS_ONE = "SELECT COUNT(ID) FROM MEETING_AGENDA_RESOLUTION ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAgendaResolutionDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long create(final MutableAgendaResolution pMutable) {
    Long id = mIdGenerator.getNumericId();
    String query = INSERT_ONE;
    mJdbcTemplate.update(query, id, pMutable.getAgendaNo(), pMutable.getAgenda(), pMutable.getAgendaEditor(),
        pMutable.getPlainAgenda(), pMutable.getResolution(), pMutable.getResolutionEditor(),
        pMutable.getPlainResolution(), pMutable.getScheduleId());
    return id;
  }

  @Override
  public List<AgendaResolution> getAgendaResolution(final Long pScheduleId) {
    String query = GET_ONE + " WHERE SCHEDULE_ID = ?";
    return mJdbcTemplate.query(query, new Object[] {pScheduleId}, new PersistentAgendaResolutionDao.RoleRowMapper());
  }

  @Override
  public List<AgendaResolution> getAll() {
    String query = GET_ONE;
    return mJdbcTemplate.query(query, new PersistentAgendaResolutionDao.RoleRowMapper());
  }

  @Override
  public AgendaResolution get(final Long pId) {
    String query = GET_ONE + " WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentAgendaResolutionDao.RoleRowMapper());
  }

  @Override
  public int update(final MutableAgendaResolution pMutable) {
    String query = UPDATE_ONE + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getAgendaNo(), pMutable.getAgenda(), pMutable.getAgendaEditor(),
        pMutable.getPlainAgenda(), pMutable.getResolution(), pMutable.getResolutionEditor(),
        pMutable.getPlainResolution(), pMutable.getId());
  }

  @Override
  public int delete(final MutableAgendaResolution pMutable) {
    String query = DELETE_ONE + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public boolean exists(final Long pScheduleId) {
    String query = EXISTS_ONE + " WHERE SCHEDULE_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pScheduleId}, Boolean.class);
  }

  class RoleRowMapper implements RowMapper<AgendaResolution> {

    @Override
    public AgendaResolution mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAgendaResolution persistentAgendaResolution = new PersistentAgendaResolution();
      persistentAgendaResolution.setId(resultSet.getLong("ID"));
      persistentAgendaResolution.setAgendaNo(resultSet.getString("AGENDA_NO"));
      persistentAgendaResolution.setAgenda(resultSet.getString("AGENDA"));
      persistentAgendaResolution.setAgendaEditor(resultSet.getString("AGENDA_EDITOR"));
      persistentAgendaResolution.setPlainAgenda(resultSet.getString("PLAIN_AGENDA"));
      persistentAgendaResolution.setResolution(resultSet.getString("RESOLUTION"));
      persistentAgendaResolution.setResolutionEditor(resultSet.getString("RESOLUTION_EDITOR"));
      persistentAgendaResolution.setPlainResolution(resultSet.getString("PLAIN_RESOLUTION"));
      persistentAgendaResolution.setScheduleId(resultSet.getLong("SCHEDULE_ID"));
      return persistentAgendaResolution;
    }
  }
}
