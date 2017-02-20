package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SpStudentDaoDecorator;
import org.ums.domain.model.immutable.SpStudent;
import org.ums.domain.model.mutable.MutableSpStudent;
import org.ums.persistent.model.PersistentSpStudent;

/**
 * Created by My Pc on 4/28/2016.
 */
public class PersistentSpStudentDao extends SpStudentDaoDecorator {
  String SELECT_ALL = "SELECT STUDENT_ID, PROGRAM_ID, SEMESTER_ID, YEAR, SEMESTER, \n"
      + "    ACTIVE, LAST_MODIFIED FROM SP_STUDENT ";
  String UPDATE_ONE = "UPDATE SP_STUDENT SET  PROGRAM_ID=?, SEMESTER_ID=?, YEAR=?, SEMESTER=?, \n"
      + "      \"    ACTIVE=?, LAST_MODIFIED =" + getLastModifiedSql();
  String DELETE_ONE = "DELETE FROM SP_STUDENT ";
  String INSERT_ONE =
      " INSERT INTO SP_STUDENT(STUDENT_ID, PROGRAM_ID, SEMESTER_ID, YEAR, SEMESTER,ACTIVE,LAST_MODIFIED)"
          + "VALUES?,?,?,?,?,?," + getLastModifiedSql() + " )";

  private JdbcTemplate mJdbcTemplate;

  public PersistentSpStudentDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<SpStudent> getAll() {
    String query = SELECT_ALL + " WHERE ACTIVE=1  ORDER BY STUDENT_ID";
    return mJdbcTemplate.query(query, new SpStudentRowMapper());
  }

  @Override
  public SpStudent get(String pId) {
    String query = SELECT_ALL + " WHERE STUDENT_ID=? AND ROWNUM = 1";

    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new SpStudentRowMapper());
  }

  @Override
  public List<SpStudent> getStudentByProgramYearSemesterStatus(int pProgramId, int pYear,
      int pSemester, int pStatus) {
    String query = SELECT_ALL + " WHERE PROGRAM_ID=? AND YEAR=? AND SEMESTER=? AND ACTIVE=?";
    return mJdbcTemplate.query(query, new Object[] {pProgramId, pYear, pSemester, pStatus},
        new SpStudentRowMapper());
  }

  @Override
  public String create(MutableSpStudent pMutable) {
    mJdbcTemplate.update(INSERT_ONE, pMutable.getId(), pMutable.getProgram().getId(), pMutable
        .getSemester().getId(), pMutable.getAcademicYear(), pMutable.getAcademicSemester(),
        pMutable.getStatus());
    return pMutable.getId();
  }

  @Override
  public int delete(MutableSpStudent pMutable) {
    String query = DELETE_ONE + " WHERE STUDENT_ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int update(MutableSpStudent pMutable) {
    String query = UPDATE_ONE + " WHERE STUDENT_ID=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public List<SpStudent> getStudentByCourseIdAndSemesterIdForSeatPlanForCCI(String pCourseId,
      Integer pSemesterId) {
    String query =
        "select distinct(s.student_id),p.program_short_name,s.year,s.semester,a.application_type from sp_student s,mst_program p, "
            + "(select distinct(student_id),application_type from application_cci where course_id=? and semester_id=? )a  "
            + "where a.student_id=s.student_id and s.program_id=p.program_id  order by  p.program_short_name,s.student_id";

    String query2 =
        "select s.student_id,p.program_short_name,s.year,s.semester,application_type,s.program_id from sp_student s,mst_program p,(  "
            + "select course_no,student_id,application_type from exam_routine r,mst_course c,  "
            + "(select distinct(course_id),student_id,application_type from application_cci where semester_id=? ) a    "
            + "where exam_type=2 and a.course_id=? and r.course_id=c.course_id and a.course_id=c.course_id order by c.course_no,a.student_id) a  "
            + "where a.student_id=s.student_id and s.program_id=p.program_id";
    return mJdbcTemplate.query(query2, new Object[] {pSemesterId, pCourseId},
        new SpStudentRowMapperForCCI());
  }

  @Override
  public List<SpStudent> getStudentBySemesterIdAndExamDateForCCI(Integer pSemesterId,
      String pExamDate) {
    String query =
        "select distinct(s.student_id),p.program_short_name,s.year,s.semester,a.application_type,p.program_id from sp_student s,mst_program p, "
            + "(select distinct(application_cci.course_id),student_id,application_type,exam_date from application_cci,exam_routine where application_cci.course_id=exam_routine.course_id and exam_routine.exam_type=2 and exam_date=to_date(?,'MM-DD-YYYY') and semester_id=? )a  "
            + "where a.student_id=s.student_id and s.program_id=p.program_id  order by  p.program_short_name,s.student_id";

    String query2 =
        "select s.student_id,p.program_short_name,s.year,s.semester,application_type,s.program_id from sp_student s,mst_program p,(  "
            + "select course_no,student_id,application_type from exam_routine r,mst_course c,  "
            + "(select distinct(course_id),student_id,application_type from application_cci where semester_id=? ) a    "
            + "where exam_type=2 and exam_date = to_date(?,'MM-DD-YYYY') and r.course_id=c.course_id and a.course_id=c.course_id order by c.course_no,a.student_id) a  "
            + "where a.student_id=s.student_id and s.program_id=p.program_id";

    return mJdbcTemplate.query(query2, new Object[] {pSemesterId, pExamDate},
        new SpStudentRowMapperForCCI2());

  }

  class SpStudentRowMapper implements RowMapper<SpStudent> {
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

  class SpStudentRowMapperForCCI implements RowMapper<SpStudent> {
    @Override
    public SpStudent mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSpStudent student = new PersistentSpStudent();
      student.setId(pResultSet.getString("student_id"));
      student.setProgramShortName(pResultSet.getString("program_short_name"));
      student.setAcademicYear(pResultSet.getInt("year"));
      student.setAcademicSemester(pResultSet.getInt("semester"));
      student.setApplicationType(pResultSet.getInt("application_type"));
      return student;
    }
  }
  class SpStudentRowMapperForCCI2 implements RowMapper<SpStudent> {
    @Override
    public SpStudent mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSpStudent student = new PersistentSpStudent();
      student.setId(pResultSet.getString("student_id"));
      student.setProgramShortName(pResultSet.getString("program_short_name"));
      student.setAcademicYear(pResultSet.getInt("year"));
      student.setAcademicSemester(pResultSet.getInt("semester"));
      student.setApplicationType(pResultSet.getInt("application_type"));
      student.setProgramId(pResultSet.getInt("program_id"));
      return student;
    }
  }
}
