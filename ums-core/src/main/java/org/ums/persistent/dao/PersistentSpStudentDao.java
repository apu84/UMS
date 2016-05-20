package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SpStudentDaoDecorator;
import org.ums.domain.model.immutable.SpStudent;
import org.ums.domain.model.mutable.MutableSpStudent;
import org.ums.persistent.model.PersistentSpStudent;

import javax.ws.rs.DELETE;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by My Pc on 4/28/2016.
 */
public class PersistentSpStudentDao extends SpStudentDaoDecorator {
  String SELECT_ALL="SELECT STUDENT_ID, PROGRAM_ID, SEMESTER_ID, YEAR, SEMESTER, \n" +
      "    ACTIVE, LAST_MODIFIED FROM SP_STUDENT ";
  String UPDATE_ONE="UPDATE SP_STUDENT SET  PROGRAM_ID=?, SEMESTER_ID=?, YEAR=?, SEMESTER=?, \n" +
      "      \"    ACTIVE=?, LAST_MODIFIED ="+getLastModifiedSql();
  String DELETE_ONE="DELETE FROM SP_STUDENT ";
  String INSERT_ONE=" INSERT INTO SP_STUDENT(STUDENT_ID, PROGRAM_ID, SEMESTER_ID, YEAR, SEMESTER,ACTIVE,LAST_MODIFIED)"+
      "VALUES?,?,?,?,?,?,"+getLastModifiedSql()+" )";


  private JdbcTemplate mJdbcTemplate;

  public PersistentSpStudentDao(JdbcTemplate pJdbcTemplate){
    mJdbcTemplate = pJdbcTemplate;
  }
  @Override
  public List<SpStudent> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query,new SpStudentRowMapper());
  }

  @Override
  public SpStudent get(String pId) throws Exception {
    String query = SELECT_ALL+" WHERE STUDENT_ID=? AND ROWNUM = 1";

    return mJdbcTemplate.queryForObject(query,new Object[]{pId},new SpStudentRowMapper());
  }

  @Override
  public List<SpStudent> getStudentByProgramYearSemesterStatus(int pProgramId, int pYear, int pSemester, int pStatus) {
    String query = SELECT_ALL+" WHERE PROGRAM_ID=? AND YEAR=? AND SEMESTER=? AND ACTIVE=?";
    return mJdbcTemplate.query(query,new Object[]{pProgramId,pYear,pSemester,pStatus},new SpStudentRowMapper());
  }

  @Override
  public int create(MutableSpStudent pMutable) throws Exception {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query,
          pMutable.getId(),
          pMutable.getProgram().getId(),
          pMutable.getSemester().getId(),
          pMutable.getAcademicYear(),
          pMutable.getAcademicSemester(),
          pMutable.getStatus()
        );
  }

  @Override
  public int delete(MutableSpStudent pMutable) throws Exception {
    String query = DELETE_ONE+" WHERE STUDENT_ID=?";
    return mJdbcTemplate.update(query,pMutable.getId());
  }

  @Override
  public int update(MutableSpStudent pMutable) throws Exception {
    String query = UPDATE_ONE+" WHERE STUDENT_ID=?";
    return mJdbcTemplate.update(query,pMutable.getId());
  }

  class SpStudentRowMapper implements RowMapper<SpStudent>{
    @Override
    public SpStudent mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSpStudent spStudent = new PersistentSpStudent();
      spStudent.setId(pResultSet.getString("STUDENT_ID"));
      spStudent.setProgramId(pResultSet.getInt("PROGRAM_ID"));
      spStudent.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      spStudent.setAcademicYear(pResultSet.getInt("YEAR"));
      spStudent.setAcademicSemester(pResultSet.getInt("SEMESTER"));
      spStudent.setStatus(pResultSet.getInt("ACTIVE"));
      spStudent.setLastModified(pResultSet.getString("LAST_MODIFIED"));
      return spStudent;
    }
  }
}
