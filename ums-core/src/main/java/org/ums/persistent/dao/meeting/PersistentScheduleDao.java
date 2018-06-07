package org.ums.persistent.dao.meeting;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.meeting.ScheduleDaoDecorator;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.meeting.PersistentSchedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersistentScheduleDao extends ScheduleDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO MEETING_SCHEDULE (ID, MEETING_TYPE, MEETING_NO, MEETING_REF_NO, DATE_TIME, ROOM, LAST_MODIFIED) VALUES (?, ?, ?, ?, ?, ?,"
          + getLastModifiedSql() + ")";

  static String GET_ONE = "SELECT ID, MEETING_TYPE, MEETING_NO, MEETING_REF_NO, DATE_TIME, ROOM FROM MEETING_SCHEDULE ";

  static String UPDATE_ONE =
      "UPDATE MEETING_SCHEDULE SET MEETING_REF_NO = ?, DATE_TIME = ?, ROOM = ?, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";

  static String DELETE_ONE = "DELETE FROM MEETING_SCHEDULE ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentScheduleDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public Long create(final MutableSchedule pMutableSchedule) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pMutableSchedule.getMeetingTypeId(), pMutableSchedule.getMeetingNo(),
        pMutableSchedule.getMeetingRefNo(), pMutableSchedule.getMeetingDateTime(), pMutableSchedule.getMeetingRoomNo());
    return id;
  }

  @Override
  public Schedule get(final int pMeetingTypeId, final int pMeetingNo) {
    String query = GET_ONE + " WHERE MEETING_TYPE = ? AND MEETING_NO = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pMeetingTypeId, pMeetingNo},
        new PersistentScheduleDao.RoleRowMapper());
  }

  @Override
  public Schedule get(final Long pId) {
    String query = GET_ONE + " WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new PersistentScheduleDao.RoleRowMapper());
  }

  @Override
  public List<Schedule> get(final int pMeetingTypeId) {
    String query = GET_ONE + " WHERE MEETING_TYPE = ?";
    return mJdbcTemplate.query(query, new Object[] {pMeetingTypeId}, new PersistentScheduleDao.RoleRowMapper());
  }

  @Override
  public List<Schedule> getAll() {
    String query = GET_ONE + " ORDER BY MEETING_TYPE, DATE_TIME DESC";
    return mJdbcTemplate.query(query, new PersistentScheduleDao.RoleRowMapper());
  }

  @Override
  public int update(final MutableSchedule pMeetingSchedule) {
    String query = UPDATE_ONE + " WHERE ID = ? AND MEETING_TYPE = ? AND MEETING_NO = ?";
    return mJdbcTemplate.update(query, pMeetingSchedule.getMeetingRefNo(), pMeetingSchedule.getMeetingDateTime(),
        pMeetingSchedule.getMeetingRoomNo(), pMeetingSchedule.getId(), pMeetingSchedule.getMeetingTypeId(),
        pMeetingSchedule.getMeetingNo());
  }

  @Override
  public int getNextMeetingNo(final int pMeetingTypeId) {
    String query = "SELECT COUNT(*) FROM MEETING_SCHEDULE WHERE MEETING_TYPE = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pMeetingTypeId}, Integer.class);
  }

  @Override
  public int delete(MutableSchedule pMutable) {
    String query = DELETE_ONE + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  class RoleRowMapper implements RowMapper<Schedule> {
    @Override
    public Schedule mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentSchedule persistentSchedule = new PersistentSchedule();
      persistentSchedule.setId(resultSet.getLong("ID"));
      persistentSchedule.setMeetingTypeId(resultSet.getInt("MEETING_TYPE"));
      persistentSchedule.setMeetingNo(resultSet.getInt("MEETING_NO"));
      persistentSchedule.setMeetingRefNo(resultSet.getString("MEETING_REF_NO"));
      persistentSchedule.setMeetingDateTime(resultSet.getTimestamp("DATE_TIME"));
      persistentSchedule.setMeetingRoomNo(resultSet.getString("ROOM"));
      return persistentSchedule;
    }
  }
}
