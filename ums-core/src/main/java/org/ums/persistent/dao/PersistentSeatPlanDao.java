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
  String SELECT_ALL="SELECT ID,ROOM_ID,SEMESTER_ID,GROUP_NO,STUDENT_ID,ROW_NO,COL_NO,EXAM_TYPE,LAST_MODIFIED FROM SEAT_PLAN ";
  String SELECT_ALL_CCI="SELECT ID,ROOM_ID,SEMESTER_ID,GROUP_NO,STUDENT_ID,ROW_NO,COL_NO,EXAM_TYPE,EXAM_DATE,application_type,LAST_MODIFIED FROM SEAT_PLAN ";
  String INSERT_ALL="INSERT INTO SEAT_PLAN(ROOM_ID,SEMESTER_ID,GROUP_NO,STUDENT_ID,ROW_NO,COL_NO,EXAM_TYPE,LAST_MODIFIED) VALUES" +
      " (?,?,?,?,?,?,?,"+ getLastModifiedSql()+" )";
  String INSERT_ALL_CCI="INSERT INTO SEAT_PLAN(ROOM_ID,SEMESTER_ID,GROUP_NO,STUDENT_ID,ROW_NO,COL_NO,EXAM_TYPE,EXAM_DATE,APPLICATION_TYPE,LAST_MODIFIED) VALUES" +
      " (?,?,?,?,?,?,?,to_date(?,'MM-DD-YYYY'),?,"+ getLastModifiedSql()+" )";
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
  public List<SeatPlan> getBySemesterGroupTypeRoomRowAndCol(int pSemesterId, int pGroupNo, int pType, int pRoomId, int pRow, int pCol) {
    String query = SELECT_ALL+ " WHERE SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=? AND ROOM_ID=? AND ROW_NO=? AND COL_NO=? AND ROWNUM = 1";
    return mJdbcTemplate.query(query,new Object[]{pSemesterId,pGroupNo,pType,pRoomId,pRow,pCol},new SeatPlanRowMapper());
  }

  @Override
  public List<SeatPlan> getByRoomSemesterGroupExamType(int pRoomId, int pSemesterId, int pGroupNo,int pExamType) {
    String query = SELECT_ALL+" WHERE ROOM_ID=? AND  SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=?";
    return mJdbcTemplate.query(query,new Object[]{pRoomId,pSemesterId,pGroupNo,pExamType},new SeatPlanRowMapper());
  }

  @Override
  public List<SeatPlan> getBySemesterAndGroupAndExamTypeAndExamDate(int pSemesterId, int pGropuNo, int pExamType, String pExamDate) {
    String query = SELECT_ALL_CCI+" WHERE SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=? AND exam_date=to_date(?,'MM-DD-YYYY')";
    return mJdbcTemplate.query(query,new Object[]{pSemesterId,pGropuNo,pExamType,pExamDate},new SeatPlanRowMapperForCCI());
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
  public int deleteBySemesterGroupExamTypeAndExamDate(int pSemesterId, int pGroupNo, int pExamType, String pExamDate) {
    String query = DELETE_ALL+" WHERE SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=? AND exam_date=to_date(?,'MM-DD-YYYY')";
    return mJdbcTemplate.update(query,pSemesterId,pGroupNo,pExamType,pExamDate);
  }

  @Override
  public int create(List<MutableSeatPlan> pMutableList) throws Exception {
    return mJdbcTemplate.batchUpdate(INSERT_ALL,getInsertParamList(pMutableList)).length;
  }

  @Override
  public int createSeatPlanForCCI(List<MutableSeatPlan> pSeatPlans) throws Exception{
    return mJdbcTemplate.batchUpdate(INSERT_ALL_CCI,getInsertParamListCCI(pSeatPlans)).length;
  }

  @Override
  public int checkIfExistsBySemesterGroupTypeRoomRowAndCol(int pSemesterId, int pGroupNo, int pType, int pRoomId, int pRow, int pCol) {
    String query= "SELECT COUNT(*) FROM SEAT_PLAN  WHERE SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=? AND ROOM_ID=? AND ROW_NO=? AND COL_NO=?";
    return mJdbcTemplate.queryForObject(query,Integer.class,pSemesterId,pGroupNo,pType,pRoomId,pRow,pCol);
  }

  @Override
  public int checkIfExistsBySemesterGroupTypeExamDateRoomRowAndCol(int pSemesterId, int pGroupNo, int pType, String pExamDate, int pRoomId, int pRow, int pCol) {
    String query= "SELECT COUNT(*) FROM SEAT_PLAN  WHERE SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=? AND ROOM_ID=? AND ROW_NO=? AND COL_NO=? AND exam_date=to_date(?,'MM-DD-YYYY')";
    return mJdbcTemplate.queryForObject(query,Integer.class,pSemesterId,pGroupNo,pType,pRoomId,pRow,pCol,pExamDate);
  }

  @Override
  public int checkIfExistsByRoomSemesterGroupExamType(int pRoomId, int pSemesterId, int pGroupNo, int pExamType) {
    String query = "SELECT COUNT(*) FROM SEAT_PLAN WHERE ROOM_ID=? AND SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=?";
    return mJdbcTemplate.queryForObject(query,Integer.class,pRoomId,pSemesterId,pGroupNo,pExamType);
  }




  /**
   * This method will bring out total room information for the student consisting the student id.
   * This method will be used for showing the student to total view of the room, which will be sitted in the exam.
   * @param pStudentId
   * @param pSemesterId
   * @return
   */
  @Override
  public List<SeatPlan> getForStudent(String pStudentId, Integer pSemesterId) {

    String query="select  s.ID, s.ROOM_ID, s.SEMESTER_ID, s.GROUP_NO, s.STUDENT_ID,s.ROW_NO,s.COL_NO,s.EXAM_TYPE,s.LAST_MODIFIED from seat_plan s, SP_PUBLISH sp " +
        " where s.student_id=? and s.semester_id=? and s.semester_id=sp.semester_id and s.exam_type=1 and s.exam_type=sp.exam_type and sp.published=1" +
        " order by row_no,col_no";
    return mJdbcTemplate.query(query,new Object[]{pStudentId,pSemesterId},new SeatPlanRowMapper());
  }

  @Override
  public List<SeatPlan> getForStudentAndCCIExam(String pStudentId, Integer pSemesterid, String pExamDate) {
    String query="select  s.ID,s.ROOM_ID,s.SEMESTER_ID,s.GROUP_NO,s.STUDENT_ID,s.ROW_NO,s.COL_NO,s.EXAM_TYPE,s.LAST_MODIFIED from seat_plan s,SP_PUBLISH sp " +
        "         where s.student_id=? and s.exam_type=2 and s.semester_id=? and sp.SEMESTER_ID=s.SEMESTER_ID and s.exam_date=to_date(?,'DD-MM-YYYY') and s.EXAM_DATE=sp.EXAM_DATE and sp.PUBLISHED=1 " +
        "         order by row_no,col_no";
    return mJdbcTemplate.query(query,new Object[]{pStudentId,pSemesterid,pExamDate},new SeatPlanRowMapper());
  }

  @Override
  public int create(MutableSeatPlan pMutable) throws Exception {
    return mJdbcTemplate.update(INSERT_ALL,
          pMutable.getClassRoom().getId(),
        pMutable.getSemester().getId(),
        pMutable.getGroupNo(),
        pMutable.getStudent().getId(),
        pMutable.getRowNo(),
        pMutable.getColumnNo(),
        pMutable.getExamType()
        );
  }


  private List<Object[]> getInsertParamList(List<MutableSeatPlan> pSeatPlans) throws Exception{
    List<Object[]> params = new ArrayList<>();

    for(SeatPlan seatPlan:pSeatPlans){
      params.add(new Object[]{
          seatPlan.getClassRoom().getId(),
          seatPlan.getSemester().getId(),
          seatPlan.getGroupNo(),
          seatPlan.getStudent().getId(),
          seatPlan.getRowNo(),
          seatPlan.getColumnNo(),
          seatPlan.getExamType()
      });
    }
    return  params;
  }

  private List<Object[]> getInsertParamListCCI(List<MutableSeatPlan> pSeatPlans) throws Exception{
    List<Object[]> params = new ArrayList<>();
    for(SeatPlan seatPlan:pSeatPlans){
      params.add(new Object[]{
          seatPlan.getClassRoom().getId(),
          seatPlan.getSemester().getId(),
          seatPlan.getGroupNo(),
          seatPlan.getStudent().getId(),
          seatPlan.getRowNo(),
          seatPlan.getColumnNo(),
          seatPlan.getExamType(),
          seatPlan.getExamDate(),
          seatPlan.getApplicationType()
      });
    }
    return params;
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

  class SeatPlanRowMapperForCCI implements RowMapper<SeatPlan>{
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
      mSeatPlan.setExamDate(pResultSet.getString("EXAM_DATE"));
      mSeatPlan.setApplicationType(pResultSet.getInt("APPLICATION_TYPE"));
      mSeatPlan.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return mSeatPlan;
    }
  }
}
