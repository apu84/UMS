package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.AssignedTeacherDaoDecorator;
import org.ums.domain.model.common.Identifier;
import org.ums.enums.CourseCategory;
import org.ums.manager.AssignedTeacherManager;

import java.util.List;

public abstract class AbstractAssignedTeacherDao<R extends Identifier<I>, M extends Identifier<I>, I>
    extends AssignedTeacherDaoDecorator<R, M, I, AssignedTeacherManager<R, M, I>> {

  protected JdbcTemplate mJdbcTemplate;

  protected abstract String getSelectSql();

  protected abstract RowMapper<R> getRowMapper();

  protected abstract String getSelectBySemesterProgram();

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId) {
    String query = String.format(getSelectBySemesterProgram(), "", "");
    return mJdbcTemplate.query(query, new Object[]{pProgramId, pSemesterId}, getRowMapper());
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester) {
    String query = String.format(getSelectBySemesterProgram(), " AND t2.year = ? AND T2.SEMESTER = ? ", "", "");
    return mJdbcTemplate.query(query, new Object[]{pProgramId, pSemesterId, pYear, pSemester}, getRowMapper());
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear) {
    String query = String.format(getSelectBySemesterProgram(), " AND t2.year = ? ", "", "");
    return mJdbcTemplate.query(query, new Object[]{pProgramId, pSemesterId, pYear}, getRowMapper());
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, CourseCategory pCourseCategory) {
    String query = String.format(getSelectBySemesterProgram(), " AND t2.year = ? ", " AND t2.COURSE_CATEGORY = ? ", "");
    return mJdbcTemplate.query(query, new Object[]{pProgramId, pSemesterId, pYear, pCourseCategory.getValue()}, getRowMapper());
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, Integer pYear, Integer pSemester, CourseCategory pCourseCategory) {
    String query = String.format(getSelectBySemesterProgram(), " AND t2.year = ? AND t2.semester = ? ", " AND t2.COURSE_CATEGORY = ? ", "");
    return mJdbcTemplate.query(query, new Object[]{pProgramId, pSemesterId, pYear, pSemester, pCourseCategory.getValue()}, getRowMapper());
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, CourseCategory pCourseCategory) {
    String query = String.format(getSelectBySemesterProgram(), "", " AND t2.COURSE_CATEGORY = ? ", "");
    return mJdbcTemplate.query(query, new Object[]{pProgramId, pSemesterId, pCourseCategory.getValue()}, getRowMapper());
  }

  @Override
  public List<R> getAssignedTeachers(Integer pProgramId, Integer pSemesterId, String pCourseId) {
    String query = String.format(getSelectBySemesterProgram(), "", "", " WHERE t3.course_id = ? ");
    return mJdbcTemplate.query(query, new Object[]{pProgramId, pSemesterId, pCourseId}, getRowMapper());
  }

  @Override
  public List<R> getAll() throws Exception {
    String query = getSelectSql();
    return mJdbcTemplate.query(query, getRowMapper());
  }

  @Override
  public R get(I pId) throws Exception {
    String query = getSelectSql() + "WHERE ID = ? ";
    return mJdbcTemplate.queryForObject(query, new Object[]{pId}, getRowMapper());
  }
}
