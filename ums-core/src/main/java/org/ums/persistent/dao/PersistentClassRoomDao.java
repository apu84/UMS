package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ClassRoomDaoDecorator;
import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.mutable.MutableClassRoom;
import org.ums.enums.ClassRoomType;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentClassRoom;

public class PersistentClassRoomDao extends ClassRoomDaoDecorator {
  static String SELECT_ALL =
      "Select ROOM_ID,ROOM_NO,DESCRIPTION,TOTAL_ROW,TOTAL_COLUMN,CAPACITY,ROOM_TYPE,DEPT_ID,EXAM_SEAT_PLAN,LAST_MODIFIED From ROOM_INFO ";
  static String UPDATE_ONE =
      "UPDATE ROOM_INFO SET ROOM_NO = ?, DESCRIPTION = ?, TOTAL_ROW=?,TOTAL_COLUMN=?,CAPACITY=?,ROOM_TYPE=?,DEPT_ID=?,EXAM_SEAT_PLAN=?,LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM ROOM_INFO ";
  static String INSERT_ONE =
      "INSERT INTO ROOM_INFO(ROOM_ID,ROOM_NO,DESCRIPTION,TOTAL_ROW,TOTAL_COLUMN,CAPACITY,ROOM_TYPE,DEPT_ID,EXAM_SEAT_PLAN,LAST_MODIFIED) "
          + "VALUES(? , ?, ?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentClassRoomDao(final JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public ClassRoom get(final Long pId) {
    String query = SELECT_ALL + " WHERE ROOM_ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new ClassRoomRowMapper());
  }

  @Override
  public ClassRoom getByRoomNo(String pRoomNo) {
    String query = SELECT_ALL + " WHERE ROOM_NO=? ";
    return mJdbcTemplate.queryForObject(query, new Object[] {pRoomNo}, new ClassRoomRowMapper());
  }

  @Override
  public List<ClassRoom> getAll() {
    String query = SELECT_ALL + " Order by ROOM_ID";
    return mJdbcTemplate.query(query, new ClassRoomRowMapper());
  }

  @Override
  public List<ClassRoom> getSeatPlanRooms(Integer pSemesterId, Integer pExamType) {
    String query =
        "select " + "  ROOM_INFO.ROOM_ID, " + "  ROOM_INFO.ROOM_NO, " + "  ROOM_INFO.DESCRIPTION, "
            + "  ROOM_INFO.TOTAL_ROW, " + "  ROOM_INFO.TOTAL_COLUMN, " + "  ROOM_INFO.CAPACITY, "
            + "  ROOM_INFO.ROOM_TYPE, " + "  ROOM_INFO.DEPT_ID, " + "  ROOM_INFO.EXAM_SEAT_PLAN, "
            + "  ROOM_INFO.LAST_MODIFIED " + "from ROOM_INFO, " + "  ( " + "    SELECT "
            + "  DISTINCT (ROOM_ID) Room_id " + "from SEAT_PLAN " + "WHERE " + "  SEMESTER_ID=? AND "
            + "  EXAM_TYPE=? " + "  ) seatPlan " + "WHERE " + "  seatPlan.Room_id=ROOM_INFO.ROOM_ID "
            + "ORDER BY room_info.ROOM_ID";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamType}, new ClassRoomRowMapper());
  }

  @Override
  public int update(final MutableClassRoom pRoom) {
    String query = UPDATE_ONE + "WHERE ROOM_ID = ?";
    return mJdbcTemplate.update(query, pRoom.getRoomNo(), pRoom.getDescription(), pRoom.getTotalRow(),
        pRoom.getTotalColumn(), pRoom.getCapacity(), pRoom.getRoomType().getValue(), pRoom.getDeptId(),
        pRoom.isExamSeatPlan(), pRoom.getId());
  }

  public int delete(final MutableClassRoom pClassRoom) {
    String query = DELETE_ONE + "WHERE ROOM_ID = ?";
    return mJdbcTemplate.update(query, pClassRoom.getId());
  }

  @Override
  public List<ClassRoom> getRoomsBasedOnRoutine(int pSemesterId, int pProgramId) {
    String query =
        SELECT_ALL
            + " where ROOM_ID  in (select distinct(room_id) from class_routine where semester_id=? and program_id=?)";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pProgramId}, new ClassRoomRowMapper());
  }

  public Long create(final MutableClassRoom pClassRoom) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ONE, id, pClassRoom.getRoomNo(), pClassRoom.getDescription(), pClassRoom.getTotalRow(),
        pClassRoom.getTotalColumn(), pClassRoom.getCapacity(), pClassRoom.getRoomType().getValue(),
        pClassRoom.getDeptId(), pClassRoom.isExamSeatPlan());
    return id;
  }

  /*
   * @Override public int update(final MutableSyllabus pSyllabus) { String query = UPDATE_ONE +
   * "WHERE SYLLABUS_ID = ?"; return mJdbcTemplate.update(query, pSyllabus.getSemester().getId(),
   * pSyllabus.getProgram().getId(), pSyllabus.getId()); }
   * 
   * public int delete(final MutableSyllabus pSyllabus) { String query = DELETE_ONE +
   * "WHERE SYLLABUS_ID = ?"; return mJdbcTemplate.update(query, pSyllabus.getId()); }
   */
  class ClassRoomRowMapper implements RowMapper<ClassRoom> {
    @Override
    public ClassRoom mapRow(ResultSet resultSet, int i) throws SQLException {
      PersistentClassRoom classRoom = new PersistentClassRoom();
      classRoom.setId(resultSet.getLong("ROOM_ID"));
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
