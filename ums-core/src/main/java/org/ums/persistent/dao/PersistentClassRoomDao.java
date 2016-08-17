package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.persistent.model.PersistentClassRoom;
import org.ums.decorator.ClassRoomDaoDecorator;
import org.ums.domain.model.mutable.*;
import org.ums.domain.model.immutable.ClassRoom;
import org.ums.enums.ClassRoomType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentClassRoomDao  extends ClassRoomDaoDecorator {
  static String SELECT_ALL = "Select ROOM_ID,ROOM_NO,DESCRIPTION,TOTAL_ROW,TOTAL_COLUMN,CAPACITY,ROOM_TYPE,DEPT_ID,EXAM_SEAT_PLAN,LAST_MODIFIED From ROOM_INFO ";
  static String UPDATE_ONE = "UPDATE ROOM_INFO SET ROOM_NO = ?, DESCRIPTION = ?, TOTAL_ROW=?,TOTAL_COLUMN=?,CAPACITY=?,ROOM_TYPE=?,DEPT_ID=?,EXAM_SEAT_PLAN=?,LAST_MODIFIED = " + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM ROOM_INFO ";
  static String INSERT_ONE = "INSERT INTO ROOM_INFO(ROOM_ID,ROOM_NO,DESCRIPTION,TOTAL_ROW,TOTAL_COLUMN,CAPACITY,ROOM_TYPE,DEPT_ID,EXAM_SEAT_PLAN,LAST_MODIFIED) " +
      "VALUES(SQN_CLASS_ROOM.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";


  private JdbcTemplate mJdbcTemplate;

  public PersistentClassRoomDao(final JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public ClassRoom get(final Integer pId) throws Exception {
    String query = SELECT_ALL + " WHERE ROOM_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, new ClassRoomRowMapper());
  }

  @Override
  public ClassRoom getByRoomNo(String pRoomNo) throws Exception {
    String query = SELECT_ALL+" WHERE ROOM_NO=? ";
    return mJdbcTemplate.queryForObject(query,new Object[]{pRoomNo},new ClassRoomRowMapper());
  }

  @Override
  public List<ClassRoom> getAll() throws Exception {
    String query = SELECT_ALL+ " Order by ROOM_ID";
    return mJdbcTemplate.query(query, new ClassRoomRowMapper());
  }


  @Override
  public List<ClassRoom> getSeatPlanRooms(Integer pSemesterId, Integer pExamType) throws Exception {
    String query = "select " +
        "  ROOM_INFO.ROOM_ID, " +
        "  ROOM_INFO.ROOM_NO, " +
        "  ROOM_INFO.DESCRIPTION, " +
        "  ROOM_INFO.TOTAL_ROW, " +
        "  ROOM_INFO.TOTAL_COLUMN, " +
        "  ROOM_INFO.CAPACITY, " +
        "  ROOM_INFO.ROOM_TYPE, " +
        "  ROOM_INFO.DEPT_ID, " +
        "  ROOM_INFO.EXAM_SEAT_PLAN, " +
        "  ROOM_INFO.LAST_MODIFIED " +
        "from ROOM_INFO, " +
        "  ( " +
        "    SELECT " +
        "  DISTINCT (ROOM_ID) Room_id " +
        "from SEAT_PLAN " +
        "WHERE " +
        "  SEMESTER_ID=? AND " +
        "  EXAM_TYPE=? " +
        "  ) seatPlan " +
        "WHERE " +
        "  seatPlan.Room_id=ROOM_INFO.ROOM_ID " +
        "ORDER BY room_info.ROOM_ID";
    return mJdbcTemplate.query(query,new Object[]{pSemesterId,pExamType}, new ClassRoomRowMapper());
  }

  @Override
  public int update(final MutableClassRoom pRoom) throws Exception {
    String query = UPDATE_ONE + "WHERE ROOM_ID = ?";
    return mJdbcTemplate.update(query,
        pRoom.getRoomNo(),
        pRoom.getDescription(),
        pRoom.getTotalRow(),
        pRoom.getTotalColumn(),
        pRoom.getCapacity(),
        pRoom.getRoomType().getValue(),
        pRoom.getDeptId(),
        pRoom.isExamSeatPlan(),
        pRoom.getId());
  }

  public int delete(final MutableClassRoom pClassRoom) throws Exception {
    String query = DELETE_ONE + "WHERE ROOM_ID = ?";
    return mJdbcTemplate.update(query, pClassRoom.getId());
  }

  public int create(final MutableClassRoom pClassRoom) throws Exception {
    return mJdbcTemplate.update(INSERT_ONE,
        pClassRoom.getRoomNo(),
        pClassRoom.getDescription(),
        pClassRoom.getTotalRow(),
        pClassRoom.getTotalColumn(),
        pClassRoom.getCapacity(),
        pClassRoom.getRoomType().getValue(),
        pClassRoom.getDeptId(),
        pClassRoom.isExamSeatPlan());
  }
  /*
  @Override
  public int update(final MutableSyllabus pSyllabus) throws Exception {
    String query = UPDATE_ONE + "WHERE SYLLABUS_ID = ?";
    return mJdbcTemplate.update(query,
        pSyllabus.getSemester().getId(),
        pSyllabus.getProgram().getId(),
        pSyllabus.getId());
  }

  public int delete(final MutableSyllabus pSyllabus) throws Exception {
    String query = DELETE_ONE + "WHERE SYLLABUS_ID = ?";
    return mJdbcTemplate.update(query, pSyllabus.getId());
  }


  */
  class ClassRoomRowMapper implements RowMapper<ClassRoom> {
    @Override
    public ClassRoom mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentClassRoom classRoom = new PersistentClassRoom();
      classRoom.setId(resultSet.getInt("ROOM_ID"));
      classRoom.setRoomNo(resultSet.getString("ROOM_NO"));
      classRoom.setDescription(resultSet.getString("DESCRIPTION"));
      classRoom.setTotalRow(resultSet.getInt("TOTAL_ROW"));
      classRoom.setTotalColumn(resultSet.getInt("TOTAL_COLUMN"));
      classRoom.setDeptId(resultSet.getString("DEPT_ID") == null ? "" : resultSet.getString("DEPT_ID"));
      classRoom.setExamSeatPlan(resultSet.getBoolean("EXAM_SEAT_PLAN"));
      classRoom.setRoomType(ClassRoomType.get(resultSet.getInt("ROOM_TYPE")));

      classRoom.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<ClassRoom> atomicReference = new AtomicReference<>(classRoom);
      return atomicReference.get();
    }
  }
}
