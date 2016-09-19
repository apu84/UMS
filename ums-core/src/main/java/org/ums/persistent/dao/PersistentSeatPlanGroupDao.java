package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SeatPlanGroupDaoDecorator;
import org.ums.domain.model.immutable.SeatPlanGroup;
import org.ums.domain.model.mutable.MutableSeatPlanGroup;
import org.ums.persistent.model.PersistentSeatPlanGroup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PersistentSeatPlanGroupDao extends SeatPlanGroupDaoDecorator {


  String SELECT_ALL = "SELECT ID,SEMESTER_ID,PROGRAM_ID,YEAR,SEMESTER,GROUP_NO,TYPE,PROGRAM_SHORT_NAME,TOTAL_STUDENT,LAST_UPDATED FROM SP_GROUP ";
  String INSERT_ONE = "INSERT INTO SP_GROUP (SEMESTER_ID,PROGRAM_ID,YEAR,SEMESTER,GROUP_NO,LAST_UPDATED,TYPE,PROGRAM_SHORT_NAME,TOTAL_STUDENT) VALUES(?,?,?,?,?, systimestamp,?,?,?)";
  String UPDATE_ONE = "UPDATE SP_GROUP SET SEMESTER_ID=?,PROGRAM_ID=?,YEAR=?, SEMESTER=?, GROUP_NO=?, LAST_UPDATED = systimestamp, TYPE=?,PROGRAM_SHORT_NAME=?,TOTAL_STUDENT=?";
  String DELETE_ONE = "DELETE  FROM SP_GROUP";
  String SELECT_ALL_FROM_EXAM_ROTINE =" select tmp5.*,program_short_name from " +
      "             (      " +
      "             select group_name,tmp4.program_id,tmp4.semester,tmp4.exam_type,course_year,course_semester,count(student_id) total_student from      " +
      "             (      " +
      "             select tmp3.*,students.student_id from " +
      "             (select distinct semester,exam_type,program_id,course_year,course_semester,group_name from      " +
      "             (select * from       " +
      "             (Select EXAM_ROUTINE.*,YEAR COURSE_YEAR,MST_COURSE.SEMESTER COURSE_SEMESTER From EXAM_ROUTINE,MST_COURSE      " +
      "             Where EXAM_ROUTINE.COURSE_ID=MST_COURSE.COURSE_ID and EXAM_ROUTINE.EXAM_TYPE=? and EXAM_ROUTINE.SEMESTER=?     " +
      "             order by exam_date      " +
      "             ) tmp1,      " +
      "             (select exam_date,      " +
      "             CASE WHEN mod(ind,3)=0 THEN 3 ELSE mod(ind,3) END AS Group_name      " +
      "             from (      " +
      "             select tmp.exam_date,tmp.exam_type,rownum ind from (      " +
      "             Select Distinct Exam_Date,Exam_type From EXAM_ROUTINE  where exam_date not in " +
      "                           ( select exclude_date from sp_parameter where exam_type=? and semester_id=?)  Order by Exam_Date " +
      "             ) tmp)       " +
      "             )tmp2      " +
      "             where tmp1.exam_date = tmp2.exam_date )      " +
      "             order by group_name      " +
      "             )tmp3, students,(select distinct(STUDENT_ID) from UG_REGISTRATION_RESULT) ugRegistrationResult " +
      "             where " +
      "             STUDENTS.STUDENT_ID = ugRegistrationResult.STUDENT_ID and " +
      "             tmp3.program_id =  students.program_id " +
      "             and tmp3.course_year =  students.CURRENT_YEAR " +
      "             and tmp3.course_semester =  students.CURRENT_SEMESTER " +
      "             )tmp4 group by group_name,      " +
      "             tmp4.program_id,tmp4.semester,tmp4.exam_type,course_year,course_semester      " +
      "             )tmp5,mst_program      " +
      "             where tmp5.program_id = mst_program.program_id      " +
      "             order by group_name,program_short_name,course_year,course_semester";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSeatPlanGroupDao(JdbcTemplate pJdbcTemplate){
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<SeatPlanGroup> getGroupBySemester(int pSemesterId,int pExamType) {
    String query = SELECT_ALL_FROM_EXAM_ROTINE;
    return mJdbcTemplate.query(
        query,
        new Object[]{pExamType,pSemesterId,pExamType,pSemesterId},
        new SeatPlanGroupRowmapperTemp()
    );
  }

  @Override
  public List<SeatPlanGroup> getGroupBySemesterTypeFromDb(int pSemesterId, int pExamType) {
    String query = SELECT_ALL+" WHERE SEMESTER_ID=? AND TYPE=? order by group_no,program_id,year,semester";
    return mJdbcTemplate.query(
        query,
        new Object[]{pSemesterId,pExamType},
        new SeatPlanGroupRowmapper()
    );
  }

  @Override
  public int deleteBySemesterAndExamType(int pSemesterId, int pExamType) {
    String query = DELETE_ONE+" WHERE SEMESTER_ID=?  AND TYPE=?";
    return mJdbcTemplate.update(query,pSemesterId,pExamType);
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
  public int checkSeatPlanGroupDataSize(int pSemesterId, int pExamType) {
    String query = "SELECT  COUNT(*) FROM SP_GROUP WHERE SEMESTER_ID=? AND TYPE=?";
    return mJdbcTemplate.queryForObject(query,Integer.class,pSemesterId,pExamType);
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
    return mJdbcTemplate.batchUpdate(INSERT_ONE,getInsertParamList(pMutableList)).length;
  }


  private List<Object[]> getInsertParamList(List<MutableSeatPlanGroup> pSeatPlanGroups) throws Exception{
    List<Object[]> params = new ArrayList<>();
    for(SeatPlanGroup seatPlanGroup:pSeatPlanGroups){
      params.add(new Object[]{
          //seatPlanGroup.getId(),
          seatPlanGroup.getSemester().getId(),
          seatPlanGroup.getProgram().getId(),
          seatPlanGroup.getAcademicYear(),
          seatPlanGroup.getAcademicSemester(),
          seatPlanGroup.getGroupNo(),
          seatPlanGroup.getExamType(),
          seatPlanGroup.getProgramName(),
          seatPlanGroup.getTotalStudentNumber(),
      });
    }

    return params;
  }


  class SeatPlanGroupRowmapperTemp implements RowMapper<SeatPlanGroup>{
    @Override
    public SeatPlanGroup mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSeatPlanGroup seatPlanGroup = new PersistentSeatPlanGroup();
      seatPlanGroup.setGroupNo(pResultSet.getInt("GROUP_NAME"));
      seatPlanGroup.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      seatPlanGroup.setSemesterId(pResultSet.getInt("SEMESTER"));
      seatPlanGroup.setExamType(pResultSet.getInt("EXAM_TYPE"));   //SELECT ID,SEMESTER_ID,PROGRAM_ID,YEAR,SEMESTER,GROUP_NO,TYPE,TO_CHAR(LAST_UPDATED,'DD/MM/YYYY'
      seatPlanGroup.setAcademicYear(pResultSet.getInt("COURSE_YEAR"));
      seatPlanGroup.setAcademicSemester(pResultSet.getInt("COURSE_SEMESTER"));
      seatPlanGroup.setTotalStudentNumber(pResultSet.getInt("TOTAL_STUDENT"));
      seatPlanGroup.setProgramShortName(pResultSet.getString("PROGRAM_SHORT_NAME"));
     // seatPlanGroup.setLastUpdateDate(pResultSet.getString("LAST_UPDATED"));
      return seatPlanGroup;
    }
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
      seatPlanGroup.setLastUpdateDate(pResultSet.getString("LAST_UPDATED"));
      seatPlanGroup.setExamType(pResultSet.getInt("TYPE"));   //SELECT ID,SEMESTER_ID,PROGRAM_ID,YEAR,SEMESTER,GROUP_NO,TYPE,TO_CHAR(LAST_UPDATED,'DD/MM/YYYY'
      seatPlanGroup.setProgramShortName(pResultSet.getString("PROGRAM_SHORT_NAME"));
      seatPlanGroup.setTotalStudentNumber(pResultSet.getInt("TOTAL_STUDENT"));
      return seatPlanGroup;
    }
  }

}
