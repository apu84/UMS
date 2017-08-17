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
import java.util.ArrayList;
import java.util.List;

public class PersistentAgendaResolutionDao extends AgendaResolutionDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO MEETING_AGENDA_RESOLUTION (ID, AGENDA_NO, AGENDA, RESOLUTION, SCHEDULE_ID, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, "
          + getLastModifiedSql() + ")";

  static String GET_ONE = "SELECT ID, AGENDA_NO, AGENDA, RESOLUTION, SCHEDULE_ID FROM MEETING_AGENDA_RESOLUTION ";

  static String UPDATE_ONE =
      "UPDATE MEETING_AGENDA_RESOLUTION SET AGENDA_NO = ?, AGENDA = ?, RESOLUTION = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM MEETING_AGENDA_RESOLUTION ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentAgendaResolutionDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public int saveAgendaResolution(final MutableAgendaResolution pMutableAgendaResolution) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, mIdGenerator.getNumericId(), pMutableAgendaResolution.getAgendaNo(),
        pMutableAgendaResolution.getAgenda(), pMutableAgendaResolution.getResolution(),
        pMutableAgendaResolution.getScheduleId());
  }

  private List<Object[]> getSaveAgendaResolutionParams(List<MutableAgendaResolution> pMutableAgendaResolution) {
    List<Object[]> params = new ArrayList<>();
    for(AgendaResolution agendaResolution : pMutableAgendaResolution) {
      params.add(new Object[] {mIdGenerator.getNumericId(), agendaResolution.getAgendaNo(),
          agendaResolution.getAgenda(), agendaResolution.getResolution(), agendaResolution.getScheduleId()});
    }
    return params;
  }

  @Override
  public List<AgendaResolution> getAgendaResolution(final int pMeetingId, final int pMeetingNo) {
    String query = GET_ONE + " WHERE MEETING_ID = ? AND MEETING_NO = ?";
    return mJdbcTemplate.query(query, new Object[] {pMeetingId, pMeetingNo},
        new PersistentAgendaResolutionDao.RoleRowMapper());
  }

  class RoleRowMapper implements RowMapper<AgendaResolution> {

    @Override
    public AgendaResolution mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentAgendaResolution persistentAgendaResolution = new PersistentAgendaResolution();
      persistentAgendaResolution.setId(resultSet.getLong("ID"));
      persistentAgendaResolution.setAgendaNo(resultSet.getString("AGENDA_NO"));
      persistentAgendaResolution.setAgenda(resultSet.getString("AGENDA"));
      persistentAgendaResolution.setResolution(resultSet.getString("RESOLUTION"));
      persistentAgendaResolution.setScheduleId(resultSet.getLong("SCHEDULE_ID"));
      return persistentAgendaResolution;
    }
  }
}
