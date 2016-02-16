package org.ums.academic.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.academic.model.PersistentClassRoom;
import org.ums.academic.model.PersistentDepartment;
import org.ums.domain.model.mutable.*;
import org.ums.domain.model.readOnly.ClassRoom;
import org.ums.domain.model.readOnly.Department;
import org.ums.enums.ClassRoomType;
import org.ums.enums.CourseType;
import org.ums.manager.ContentManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentClassRoomDao  extends ClassRoomDaoDecorator {
  static String SELECT_ALL = "Select ROOM_ID,ROOM_NO,DESCRIPTION,TOTAL_ROW,TOTAL_COLUMN,CAPACITY,ROOM_TYPE,DEPT_ID,EXAM_SEAT_PLAN,LAST_MODIFIED From ROOM_INFO ";
  static String UPDATE_ONE = "UPDATE ROOM_INFO SET ROOM_NO = ?, DESCRIPTION = ?, TOTAL_ROW=?,TOTAL_COLUMN=?,CAPACITY=?,ROOM_TYPE=?,DEPT_ID=?,EXAM_SEAT_PLAN=?,LAST_MODIFIED = " + getLastModifiedSql() + " ";
  static String DELETE_ONE = "DELETE FROM ROOM_INFO ";
  static String INSERT_ONE = "INSERT INTO ROOM_INFO(ROOM_ID,ROOM_NO,DESCRIPTION,TOTAL_ROW,TOTAL_COLUMN,CAPACITY,ROOM_TYPE,DEPT_ID,EXAM_SEAT_PLAN,LAST_MODIFIED) " +
      "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, " + getLastModifiedSql() + ")";


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
  public List<ClassRoom> getAll() throws Exception {
    String query = SELECT_ALL+ " Order by ROOM_NO";
    return mJdbcTemplate.query(query, new ClassRoomRowMapper());
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

  public int create(final MutableSyllabus pSyllabus) throws Exception {
    return mJdbcTemplate.update(INSERT_ONE,
        pSyllabus.getId(),
        pSyllabus.getSemester().getId(),
        pSyllabus.getProgram().getId());
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
      //classRoom.setId(resultSet.getInt("ROOM_TYPE"));
      //classRoom.setId(resultSet.getInt("DEPT_ID"));
      //classRoom.setId(resultSet.getInt("EXAM_SEAT_PLAN"));
      classRoom.setExamSeatPlan(resultSet.getBoolean("EXAM_SEAT_PLAN"));
      classRoom.setRoomType(ClassRoomType.get(resultSet.getInt("ROOM_TYPE")));

      classRoom.setLastModified(resultSet.getString("LAST_MODIFIED"));
      AtomicReference<ClassRoom> atomicReference = new AtomicReference<>(classRoom);
      return atomicReference.get();
    }
  }
}
