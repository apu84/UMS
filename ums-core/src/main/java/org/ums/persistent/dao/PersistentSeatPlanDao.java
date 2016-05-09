package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SeatPlanDaoDecorator;
import org.ums.domain.model.immutable.SeatPlan;
import org.ums.domain.model.mutable.MutableSeatPlan;
import org.ums.persistent.model.PersistentSeatPlan;

import javax.ws.rs.DELETE;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My Pc on 5/8/2016.
 */
public class PersistentSeatPlanDao extends SeatPlanDaoDecorator{
  String SELECT_ALL="SELECT ID,ROOM_ID,SEMESTER_ID,GROUP_NO,STUDENT_ID,ROW_NO,COL_NO,EXAM_TYPE,LAST_MODIFIED FRO SEAT_PLAN ";
  String INSERT_ALL="INSERT INTO SEAT_PLAN(ROOM_ID,SEMESTER_ID,GROUP_NO,STUDENT_ID,ROW_NO,COL_NO,EXAM_TYPE,LAST_MODIFIED) VALUES" +
      " (?,?,?,?,?,?,"+ getLastModifiedSql()+" )";
  String UPDATE_ALL="UPDATE SEAT_PLAN SET ROOM_ID=?,SEMESTER_ID=?,GROUP_NO=?,STUDENT_ID=?,ROW_NO=?," +
      "COL_NO=?,EXAM_TYPE=?,LAST_MODIFIED="+getLastModifiedSql()+" ";
  String DELETE_ALL = "DELETE FROM SEAT_PLAN ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSeatPlanDao(JdbcTemplate pJdbcTemplate){
    mJdbcTemplate = pJdbcTemplate;
  }


  @Override
  public List<SeatPlan> getBySemesterAndGroupAndExamType(int pSemesterId, int pGropNo,int pExamType) {
    String query = SELECT_ALL+" WHERE SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=? ";
    return mJdbcTemplate.query(query,new Object[]{pSemesterId,pGropNo,pExamType},new SeatPlanRowMapper());
  }

  @Override
  public List<SeatPlan> getByRoomSemesterGroupExamType(int pRoomId, int pSemesterId, int pGroupNo,int pExamType) {
    String query = SELECT_ALL+" WHERE ROOM_ID=? AND  SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=?";
    return mJdbcTemplate.query(query,new Object[]{pRoomId,pSemesterId,pGroupNo,pExamType},new SeatPlanRowMapper());
  }

  @Override
  public List<SeatPlan> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query,new SeatPlanRowMapper());
  }

  @Override
  public SeatPlan get(Integer pId) throws Exception {
    String query = SELECT_ALL+" WHERE ID=?";
    return mJdbcTemplate.queryForObject(query,new Object[]{pId},new SeatPlanRowMapper());
  }

  //will check if it is needed
  @Override
  public int update(MutableSeatPlan pMutable) throws Exception {
    return super.update(pMutable);
  }

  @Override
  public int delete(MutableSeatPlan pMutableList) throws Exception {
    String query = DELETE_ALL+" WHERE ID=?";
    return mJdbcTemplate.update(query,pMutableList.getId());
  }

  @Override
  public int deleteBySemesterGroupExamType(int pSemesterId, int pGroupNo, int pExamType) {
    String query = DELETE_ALL+" WHERE SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=?";
    return mJdbcTemplate.update(query,pSemesterId,pGroupNo,pExamType);
  }

  @Override
  public int create(List<MutableSeatPlan> pMutableList) throws Exception {
    return mJdbcTemplate.batchUpdate(INSERT_ALL,getInsertParamList(pMutableList)).length;
  }

/*  @Override
  public int create(MutableSeatPlan pMutable) throws Exception {
    return super.create(pMutable);
  }*/


  private List<Object[]> getInsertParamList(List<MutableSeatPlan> pSeatPlans) throws Exception{
    List<Object[]> params = new ArrayList<>();

    for(SeatPlan seatPlan:pSeatPlans){
      params.add(new Object[]{
          seatPlan.getClassRoom().getId(),
          seatPlan.getRowNo(),
          seatPlan.getColumnNo(),
          seatPlan.getStudent().getId(),
          seatPlan.getExamType(),
          seatPlan.getSemester().getId(),
          seatPlan.getGroupNo(),
      });
    }
    return  params;
  }

  class SeatPlanRowMapper implements RowMapper<SeatPlan>{
    @Override
    public SeatPlan mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSeatPlan mSeatPlan = new PersistentSeatPlan();
      mSeatPlan.setId(pResultSet.getInt("ID"));
      mSeatPlan.setClassRoomId(pResultSet.getInt("ROOM_ID"));
      mSeatPlan.setRowNo(pResultSet.getInt("ROW_NO"));
      mSeatPlan.setColumnNo(pResultSet.getInt("COL_NO"));
      mSeatPlan.setStudentId(pResultSet.getString("STUDENT_ID"));
      mSeatPlan.setExamType(pResultSet.getInt("EXAM_TYPE"));
      mSeatPlan.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      mSeatPlan.setGroupNo(pResultSet.getInt("GROUP_NO"));
      mSeatPlan.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return mSeatPlan;
    }
  }
}
