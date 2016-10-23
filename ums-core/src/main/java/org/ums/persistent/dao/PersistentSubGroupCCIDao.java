package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SubGroupCCIDaoDecorator;
import org.ums.domain.model.immutable.SubGroupCCI;
import org.ums.domain.model.mutable.MutableSubGroupCCI;
import org.ums.persistent.model.PersistentSubGroupCCI;

import javax.ws.rs.DELETE;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistentSubGroupCCIDao extends SubGroupCCIDaoDecorator {

  String INSERT_ONE =
      "INSERT INTO SP_SUB_GROUP_CCI (SEMESTER_ID,SUB_GROUP_NO,TOTAL_STUDENT,COURSE_ID,EXAM_DATE,LAST_MODIFIED)"
          + " VALUES (?,?,?,?,?," + getLastModifiedSql() + ")";
  String DELETE_ONE = "DELETE FROM SP_SUB_GROUP_CCI ";
  String UPDATE_ONE =
      " UPDATE SP_SUB_GROUP_CCI SET SEMESTER_ID=?, SUB_GROUP_NO=?,TOTAL_STUDENT=?,COURSE_ID=?,EXAM_DATE=?,LAST_MODIFIED="
          + getLastModifiedSql();
  String SELECT_ALL_SUB_GROUP =
      "select s.id,s.semester_id,s.sub_group_no,s.total_student,s.course_id,s.exam_date,c.course_no,c.year,c.semester,s.last_modified  "
          + "from sp_sub_group_cci s,mst_course c where s.exam_date=to_date(?,'MM-DD-YYYY') and s.course_id = c.course_id AND s.semester_id=? order by s.sub_group_no, s.id";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSubGroupCCIDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<SubGroupCCI> getBySemesterAndExamDate(Integer pSemesterId, String pExamDate) {
    String query = SELECT_ALL_SUB_GROUP;
    return mJdbcTemplate.query(query, new Object[] {pExamDate, pSemesterId},
        new SubGroupCCIRowMapperForSeatPlan());
  }

  @Override
  public Integer checkOccuranceBySemesterAndExamDate(Integer pSemesterId, String pExamDate) {
    String query =
        "select count(*) from sp_sub_group_cci where exam_date=to_date(?,'MM-DD-YYYY') and semester_id=?";
    return mJdbcTemplate.queryForObject(query, Integer.class, pExamDate, pSemesterId);
  }

  @Override
  public Integer checkForHalfFinishedSubGroup(Integer pSemesterId, String pExamDate) {
    String query =
        "select count(*) from sp_sub_group_cci where exam_date=to_date(?,'MM-DD-YYYY') and semester_id=? and sub_group_no=0";
    return mJdbcTemplate.queryForObject(query, Integer.class, pExamDate, pSemesterId);
  }

  @Override
  public Integer checkSubGroupNumber(Integer pSemesterId, String pExamDate) {
    String query =
        "select count(sub_group_no) from ( "
            + "select distinct(sub_group_no) from sp_sub_group_cci where exam_date=to_date(?,'MM-DD-YYYY') and semester_id=?)a";
    return mJdbcTemplate.queryForObject(query, Integer.class, pExamDate, pSemesterId);
  }

  @Override
  public int create(List<MutableSubGroupCCI> pMutableList) throws Exception {
    String query =
        "INSERT INTO SP_SUB_GROUP_CCI (SEMESTER_ID,SUB_GROUP_NO,TOTAL_STUDENT,COURSE_ID,EXAM_DATE,LAST_MODIFIED)"
            + " VALUES (?,?,?,?,to_date(?,'MM-DD-YYYY')," + getLastModifiedSql() + ")";
    return mJdbcTemplate.batchUpdate(query, getInsertParamList(pMutableList)).length;
  }

  @Override
  public int delete(MutableSubGroupCCI pMutable) throws Exception {
    String query = DELETE_ONE + " where semester_id=? and exam_date=to_date(?,'MM-DD-YYYY')";
    return mJdbcTemplate.update(query, pMutable.getSemesterId(), pMutable.getExamDate());
  }

  @Override
  public Integer deleteBySemesterAndExamDate(Integer pSemesterId, String pExamDate) {
    String query =
        "delete from sp_sub_group_cci where exam_date=to_date(?,'MM-DD-YYYY') and semester_id=?";
    return mJdbcTemplate.update(query, pExamDate, pSemesterId);
  }

  private List<Object[]> getInsertParamList(List<MutableSubGroupCCI> pMutableSubGroupCCIs)
      throws Exception {
    List<Object[]> params = new ArrayList<>();
    for(SubGroupCCI subGroup : pMutableSubGroupCCIs) {
      params.add(new Object[] {subGroup.getSemesterId(), subGroup.getSubGroupNo(),
          subGroup.getTotalStudent(), subGroup.getCourseId(), subGroup.getExamDate()});
    }
    return params;
  }

  class SubGroupCCIRowMapperForSeatPlan implements RowMapper<SubGroupCCI> {
    @Override
    public SubGroupCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSubGroupCCI subGroup = new PersistentSubGroupCCI();
      subGroup.setId(pResultSet.getInt("id"));
      subGroup.setSemesterId(pResultSet.getInt("semester_id"));
      subGroup.setSubGroupNo(pResultSet.getInt("sub_group_no"));
      subGroup.setTotalStudent(pResultSet.getInt("total_student"));
      subGroup.setCourseId(pResultSet.getString("course_id"));
      subGroup.setExamDate(pResultSet.getString("exam_date"));
      subGroup.setCourseNo(pResultSet.getString("course_no"));
      subGroup.setCourseYear(pResultSet.getInt("year"));
      subGroup.setCourseSemester(pResultSet.getInt("semester"));
      subGroup.setLastModified(pResultSet.getString("last_modified"));
      return subGroup;
    }
  }

}
