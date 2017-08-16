package org.ums.persistent.dao.meeting;

import org.springframework.data.annotation.Persistent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.meeting.ScheduleDaoDecorator;
import org.ums.domain.model.immutable.meeting.Schedule;
import org.ums.domain.model.mutable.meeting.MutableSchedule;
import org.ums.enums.meeting.MeetingType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.meeting.PersistentSchedule;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistentScheduleDao extends ScheduleDaoDecorator {

  static String INSERT_ONE =
      "INSERT INTO MEETING_SCHEDULE (ID, MEETING_TYPE, MEETING_NO, MEETING_REF_NO, DATE_TIME, ROOM) VALUES (?, ?, ?, ?, ?, ?)";

  static String GET_ONE = "SELECT ID, MEETING_TYPE, MEETING_NO, MEETING_REF_NO, DATE_TIME, ROOM FROM MEETING_SCHEDULE ";

  static String UPDATE_ONE = "UPDATE MEETING_SCHEDULE SET MEETING_REF_NO = ?, DATE_TIME = ?, ROOM = ? ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentScheduleDao(final JdbcTemplate pJdbcTemplate, final IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public int saveMeetingSchedule(final MutableSchedule pMutableSchedule) {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, mIdGenerator.getNumericId(), pMutableSchedule.getMeetingTypeId(),
        pMutableSchedule.getMeetingNo(), pMutableSchedule.getMeetingRefNo(), pMutableSchedule.getMeetingDateTime(),
        pMutableSchedule.getMeetingRoomNo());
  }

  @Override
  public Schedule getMeetingSchedule(final MeetingType pMeetingType, final int pMeetingNo) {
    String query = GET_ONE + " WHERE MEETING_TYPE = ? AND MEETING_NO = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pMeetingType.getId(), pMeetingNo},
        new PersistentScheduleDao.RoleRowMapper());
  }

  @Override
  public int updateMeetingSchedule(final MutableSchedule pMeetingSchedule) {
    String query = UPDATE_ONE + " WHERE ID = ?, MEETING_TYPE = ?, MEETING_NO = ?";
    return mJdbcTemplate.update(query, pMeetingSchedule.getId(), pMeetingSchedule.getMeetingType().getId(),
        pMeetingSchedule.getMeetingNo());
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
      persistentSchedule.setMeetingRefNo(resultSet.getString("ROOM"));
      return persistentSchedule;
    }
  }
}
