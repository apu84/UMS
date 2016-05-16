package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SeatPlanGroupDaoDecorator;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;
import org.ums.persistent.model.PersistentSeatPlanGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by My Pc on 4/20/2016.
 */
public class PersistentSeatPlanGroupDao extends SeatPlanGroupDaoDecorator {



  String SELECT_ALL = "SELECT ID,SEMESTER_ID,PROGRAM_ID,YEAR,SEMESTER,GROUP_NO,TYPE,LAST_UPDATED FROM SP_GROUP ";
  String INSERT_ONE = "INSERT INTO SP_GROUP (SEMESTER_ID,PROGRAM_ID,YEAR,SEMESTER,GROUP_NO,LAST_UPDATED,TYPE) VALUES(?,?,?,?,?, systimestamp,?)";
  String UPDATE_ONE = "UPDATE SP_GROUP SET SEMESTER_ID=?,PROGRAM_ID=?,YEAR=?, SEMESTER=?, GROUP_NO=?, LAST_UPDATED = systimestamp, TYPE=?";
  String DELETE_ONE = "DELETE  FROM SP_GROUP";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSeatPlanGroupDao(JdbcTemplate pJdbcTemplate){
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<SeatPlanGroup> getGroupBySemester(int pSemesterId,int pExamType) {
    String query = SELECT_ALL+" WHERE SEMESTER_ID=? AND TYPE=? ORDER BY GROUP_NO,PROGRAM_ID,YEAR,SEMESTER ASC ";
    return mJdbcTemplate.query(query,new Object[]{pSemesterId,pExamType},
        new  SeatPlanGroupRowmapper());
  }

  @Override
  public List<SeatPlanGroup> getBySemesterGroupAndType(int pSemesterId, int pGroupNo, int pType) {
    String query=SELECT_ALL+" WHERE SEMESTER_ID=? AND GROUP_NO=? AND TYPE=? ORDER BY GROUP_NO,PROGRAM_ID,YEAR,SEMESTER ASC";
    return mJdbcTemplate.query(query,new Object[]{pSemesterId,pGroupNo,pType},new SeatPlanGroupRowmapper());
  }

  @Override
  public List<SeatPlanGroup> getAll() throws Exception {
    String query = SELECT_ALL+ " ORDER BY GROUP_NO,PROGRAM_ID,YEAR,SEMESTER ASC";
    return mJdbcTemplate.query(query,
        new SeatPlanGroupRowmapper());
  }

  @Override
  public SeatPlanGroup get(Integer pId) throws Exception {
    String query = SELECT_ALL+" WHERE ID=?";
    return mJdbcTemplate.queryForObject(query,new Object[]{pId},new SeatPlanGroupRowmapper());
  }

  @Override
  public int update(MutableSeatPlanGroup pMutable) throws Exception {
    return super.update(pMutable);
  }

  @Override
  public int update(List<MutableSeatPlanGroup> pMutableList) throws Exception {
    return super.update(pMutableList);
  }

  @Override
  public int delete(MutableSeatPlanGroup pMutable) throws Exception {
    String query = DELETE_ONE+" WHERE SEMESTER_ID=?";
    return mJdbcTemplate.update(query,pMutable.getSemester().getId());
  }

  @Override
  public int delete(List<MutableSeatPlanGroup> pMutableList) throws Exception {
    return super.delete(pMutableList);
  }

  @Override
  public int create(MutableSeatPlanGroup pMutable) throws Exception {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query,
          pMutable.getSemester().getId(),
          pMutable.getProgram().getId(),
          pMutable.getAcademicYear(),
          pMutable.getAcademicSemester(),
          pMutable.getGroupNo(),
          pMutable.getExamType()
        );
  }

  @Override
  public int create(List<MutableSeatPlanGroup> pMutableList) throws Exception {
    return super.create(pMutableList);
  }

  class SeatPlanGroupRowmapper implements RowMapper<SeatPlanGroup>{
    @Override
    public SeatPlanGroup mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSeatPlanGroup seatPlanGroup = new PersistentSeatPlanGroup();
      seatPlanGroup.setId(pResultSet.getInt("ID"));
      seatPlanGroup.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      seatPlanGroup.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      seatPlanGroup.setAcademicYear(pResultSet.getInt("YEAR"));
      seatPlanGroup.setAcademicSemester(pResultSet.getInt("SEMESTER"));
      seatPlanGroup.setGroupNo(pResultSet.getInt("GROUP_NO"));
      seatPlanGroup.setExamType(pResultSet.getInt("TYPE"));   //SELECT ID,SEMESTER_ID,PROGRAM_ID,YEAR,SEMESTER,GROUP_NO,TYPE,TO_CHAR(LAST_UPDATED,'DD/MM/YYYY'
      seatPlanGroup.setLastUpdateDate(pResultSet.getString("LAST_UPDATED"));
      return seatPlanGroup;
    }
  }


}
