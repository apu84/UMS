package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SubGroupDaoDecorator;
import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.mutable.MutableSubGroup;
import org.ums.persistent.model.PersistentSubGroup;
import sun.misc.resources.Messages_ja;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My Pc on 5/4/2016.
 */
public class PersistentSubGroupDao extends SubGroupDaoDecorator {

  String SELECT_ALL =
      "SELECT s.ID,s.SEMESTER_ID,s.GROUP_NO,s.SUB_GROUP_NO,s.GROUP_ID,s.POSITION,s.STUDENT_NUMBER,s.EXAM_TYPE,p.program_short_name,g.year,g.semester,s.LAST_MODIFIED  "
          + "FROM SP_SUB_GROUP s,sp_group g,mst_program p where  s.group_id=g.id and g.program_id=p.program_id";
  String UPDATE_ONE =
      "UPDATE SP_SUB_GROUP SET SEMESTER_ID=?,GROUP_NO=?,SUB_GROUP_NO=?,GROUP_ID=?,POSITION=?,STUDENT_NUMBER=?,EXAM_TYPE=?,LAST_MODIFIED="
          + getLastModifiedSql() + " ";
  String DELETE_ONE = "DELETE FROM SP_SUB_GROUP ";
  String INSERT_ONE =
      " INSERT INTO SP_SUB_GROUP(SEMESTER_ID,GROUP_NO,SUB_GROUP_NO,GROUP_ID,POSITION,STUDENT_NUMBER,EXAM_TYPE,LAST_MODIFIED) "
          + " VALUES(?,?,?,?,?,?," + getLastModifiedSql() + ") ";

  String INSERT_ALL =
      " INSERT INTO SP_SUB_GROUP(SEMESTER_ID,GROUP_NO,SUB_GROUP_NO,GROUP_ID,POSITION,STUDENT_NUMBER,EXAM_TYPE,LAST_MODIFIED) "
          + " VALUES(?,?,?,?,?,?,?," + getLastModifiedSql() + ") ";
  String UPDATE_ALL =
      "UPDATE SP_SUB_GROUP SET SEMESTER_ID=?,GROUP_NO=?,SUB_GROUP_NO=?,GROUP_ID=?,POSITION=?,STUDENT_NUMBER=?,EXAM_TYPE=?,LAST_MODIFIED="
          + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM SP_SUB_GROUP ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSubGroupDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<SubGroup> getByGroupNo(int pGroupNo) {
    String query = SELECT_ALL + " WHERE GROUP_NO = ?";
    return mJdbcTemplate.query(query, new Object[] {pGroupNo}, new SubGroupRowMapper());
  }

  @Override
  public int checkBySemesterGroupNoAndType(int pSemesterId, int pGroupNo, int pType) {
    String query =
        "SELECT COUNT(*) FROM SP_SUB_GROUP WHERE SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=?";
    return mJdbcTemplate.queryForObject(query, Integer.class, pSemesterId, pGroupNo, pType);
  }

  @Override
  public int checkForHalfFinishedSubGroupsBySemesterGroupNoAndType(int pSemesterId, int pGroupNo,
      int pType) {
    String query =
        "SELECT COUNT(*) FROM SP_SUB_GROUP WHERE SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=? and sub_group_no=0";
    return mJdbcTemplate.queryForObject(query, Integer.class, pSemesterId, pGroupNo, pType);
  }

  @Override
  public List<SubGroup> getBySemesterGroupNoAndType(int pSemesterId, int pGroupNo, int pType) {
    String query =
        SELECT_ALL
            + " and  s.SEMESTER_ID=? AND s.GROUP_NO=? AND s.EXAM_TYPE=? ORDER BY s.SUB_GROUP_NO ASC, s.ID ASC ";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pGroupNo, pType},
        new SubGroupRowMapper());
  }

  @Override
  public int deleteBySemesterGroupAndType(int pSemesterId, int pGroupNo, int pType) {
    String query = DELETE_ALL + " WHERE SEMESTER_ID=? AND GROUP_NO=? AND EXAM_TYPE=? ";
    return mJdbcTemplate.update(query, pSemesterId, pGroupNo, pType);
  }

  @Override
  public int create(List<MutableSubGroup> pMutableList) {
    return mJdbcTemplate.batchUpdate(INSERT_ALL, getInsertParamList(pMutableList)).length;
  }

  @Override
  public int getSubGroupNumberOfAGroup(int pSemesterId, int pExamType, int pGroupNo) {
    String query =
        "select count(distinct sub_group_no) from sp_sub_group where group_no=? and semester_id=? and exam_type=? and sub_group_no!=7";
    return mJdbcTemplate.queryForObject(query, Integer.class, pGroupNo, pSemesterId, pExamType);
  }

  @Override
  public List<SubGroup> getSubGroupMembers(int pSemesterId, int pExamTYpe, int pGroupNo,
      int pSubGroupNo) {
    String query =
        SELECT_ALL
            + " and  s.SEMESTER_ID=? AND s.EXAM_TYPE=? AND s.GROUP_NO=? AND s.SUB_GROUP_NO=? ORDER BY ID ASC";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamTYpe, pGroupNo, pSubGroupNo},
        new SubGroupRowMapper());
  }

  @Override
  public int create(MutableSubGroup pMutable) {
    return mJdbcTemplate.update(INSERT_ALL, pMutable.getSemester().getId(), pMutable.getGroup()
        .getGroupNo(), pMutable.subGroupNo(), pMutable.getGroup().getId(), pMutable.getPosition(),
        pMutable.getStudentNumber(), pMutable.getExamType());
  }

  @Override
  public int delete(MutableSubGroup pMutable) {
    String query = DELETE_ONE + " WHERE ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int update(MutableSubGroup pMutable) {
    String query = UPDATE_ALL + " WHERE ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public SubGroup get(Integer pId) {
    String query = SELECT_ALL + " WHERE ID=?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new SubGroupRowMapper());
  }

  @Override
  public List<SubGroup> getAll() {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new SubGroupRowMapper());
  }

  private List<Object[]> getInsertParamList(List<MutableSubGroup> pSubGroups) {
    List<Object[]> params = new ArrayList<>();

    for(SubGroup subGroup : pSubGroups) {
      params.add(new Object[] {subGroup.getSemester().getId(), subGroup.getGroupNo(),
          subGroup.subGroupNo(), subGroup.getGroupId(), subGroup.getPosition(),
          subGroup.getStudentNumber(), subGroup.getExamType()});
    }

    return params;

  }

  class SubGroupRowMapper implements RowMapper<SubGroup> {
    @Override
    public SubGroup mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSubGroup mSubGroup = new PersistentSubGroup();
      mSubGroup.setId(pResultSet.getInt("ID"));
      mSubGroup.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      mSubGroup.setGroupNo(pResultSet.getInt("GROUP_NO"));
      mSubGroup.setSubGroupNo(pResultSet.getInt("SUB_GROUP_NO"));
      mSubGroup.setGroupId(pResultSet.getInt("GROUP_ID"));
      mSubGroup.setPosition(pResultSet.getInt("POSITION"));
      mSubGroup.setStudentNumber(pResultSet.getInt("STUDENT_NUMBER"));
      mSubGroup.setExamType(pResultSet.getInt("EXAM_TYPE"));
      mSubGroup.setProgramShortName(pResultSet.getString("PROGRAM_SHORT_NAME"));
      mSubGroup.setStudentYear(pResultSet.getInt("YEAR"));
      mSubGroup.setStudentSemester(pResultSet.getInt("SEMESTER"));
      mSubGroup.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return mSubGroup;
    }
  }
}
