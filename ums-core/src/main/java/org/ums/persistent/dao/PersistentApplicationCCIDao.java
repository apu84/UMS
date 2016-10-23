package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.ApplicationCCIDaoDecorator;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.mutable.MutableApplicationCCI;
import org.ums.enums.ApplicationType;
import org.ums.persistent.model.PersistentApplicationCCI;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by My Pc on 7/14/2016.
 */
public class PersistentApplicationCCIDao extends ApplicationCCIDaoDecorator {

  String SELECT_ALL =
      "select a.id,a.semester_id,a.student_id,a.course_id,a.application_type,a.applied_on,c.course_no,c.course_title,to_char(exam_routine.exam_date,'DD-MM-YYYY') exam_date from application_cci a, mst_course c,exam_routine where "
          + " a.course_id= c.course_id and a.course_id=exam_routine.course_id and exam_routine.exam_type=2";
  String INSERT_ONE =
      "Insert into APPLICATION_CCI (SEMESTER_ID,STUDENT_ID,COURSE_ID,APPLICATION_TYPE,APPLIED_ON) values (?,?,?,?,systimestamp)";
  String UPDATE_ONE =
      "update application_cci set semester_id=?, student_id=?, course_id=?,application_type=?,applied_on=systimestamp ";
  String DELETE_ONE = "delete from application_cci";

  private JdbcTemplate mJdbcTemplate;

  public PersistentApplicationCCIDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<ApplicationCCI> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new ApplicationCCIRowMapper());
  }

  @Override
  public int create(List<MutableApplicationCCI> pMutableList) throws Exception {
    return mJdbcTemplate.batchUpdate(INSERT_ONE, getInsertParamList(pMutableList)).length;
  }

  @Override
  public int create(MutableApplicationCCI pMutable) throws Exception {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query, pMutable.getSemesterId(), pMutable.getStudentId(),
        pMutable.getCourseId(), pMutable.getApplicationType().getValue());
  }

  @Override
  public int delete(MutableApplicationCCI pMutable) throws Exception {
    return super.delete(pMutable);
  }

  @Override
  public int deleteByStudentId(String pStudentId) {
    String query = DELETE_ONE + " where student_id=?";
    return mJdbcTemplate.update(query, new Object[] {pStudentId});
  }

  @Override
  public int update(MutableApplicationCCI pMutable) throws Exception {
    String query = DELETE_ONE + " WHERE STUDENT_ID=? ";
    return mJdbcTemplate.update(query, pMutable.getStudentId());
  }

  @Override
  public int update(List<MutableApplicationCCI> pMutableList) throws Exception {
    return super.update(pMutableList);
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemesterAndType(String pStudentId, int pSemesterId,
      int pExamType) {
    String query = SELECT_ALL + " STUDENT_ID=? AND SEMESTER_ID=? AND APPLICATION_TYPE=? ";
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId, pExamType},
        new ApplicationCCIRowMapper());
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemesterForSeatPlanView(String pStudentId,
      Integer pSemesterId) {
    String query =
        "select to_char( e.EXAM_DATE,'DD-MM-YYYY') EXAM_DATE,c.COURSE_NO,a.APPLICATION_TYPE,r.ROOM_NO,r.ROOM_ID from EXAM_ROUTINE e,MST_COURSE c,APPLICATION_CCI a,ROOM_INFO r,SEAT_PLAN s  "
            + "where a.STUDENT_ID=? and a.SEMESTER_ID=? and a.COURSE_ID=c.COURSE_ID and  e.SEMESTER=a.SEMESTER_ID and e.COURSE_ID=a.COURSE_ID and e.EXAM_TYPE=2 and a.STUDENT_ID= s.STUDENT_ID  "
            + "and s.ROOM_ID=r.ROOM_ID and s.EXAM_TYPE=2 and s.STUDENT_ID=a.STUDENT_ID";
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId},
        new ApplicationCCIRowMapperForSeatPlanView());
  }

  @Override
  public List<ApplicationCCI> getBySemesterAndExamDate(Integer pSemesterId, String pExamDate)
      throws Exception {
    SimpleDateFormat formatter = new SimpleDateFormat("MM-DD-YYYY");
    // Date date = (Date) formatter.parse(pExamDate);
    // Date date = (Date) formatter.parse(pExamDate);
    // Timestamp timestamp = new Timestamp(date.getTime());
    /*
     * Date date = (Date) formatter.parse(pExamDate); String examDate = date.toString();
     */
    String query =
        "select e.exam_date,course.course_no,course.course_id,course.course_title,course.total_student,course.year,course.semester from ( "
            + "select c.course_no,c.course_id,c.course_title,c.year,c.semester,a.semester_id,count(a.student_id) total_student  "
            + "from application_cci a, mst_course c  "
            + "where a.semester_id=?  "
            + "and a.course_id = c.course_id group by c.course_no,c.course_id,c.course_title,c.year,c.semester,a.semester_id) course,exam_routine e where e.course_id = course.course_id and "
            + "e.exam_date = to_date(?,'MM-DD-YYYY') and e.exam_type=2 and e.semester = course.semester_id order by course.course_no";

    String query2 =
        "select s.student_id,p.program_short_name,s.year,s.semester,application_type,s.program_id from sp_student s,mst_program p,(  "
            + "select course_no,student_id,application_type from exam_routine r,mst_course c,  "
            + "(select distinct(course_id),student_id,application_type from application_cci where semester_id=? ) a    "
            + "where exam_type=2 and exam_date = to_date(?,'MM-DD-YYYY') and r.course_id=c.course_id and a.course_id=c.course_id order by c.course_no,a.student_id) a  "
            + "where a.student_id=s.student_id and s.program_id=p.program_id;";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pExamDate},
        new ApplicationCCIRowMapperForSeatPlan());
  }

  @Override
  public List<ApplicationCCI> getBySemesterAndType(int pSemesterId, int pExamType) {
    return super.getBySemesterAndType(pSemesterId, pExamType);
  }

  @Override
  public List<ApplicationCCI> getByProgramAndSemesterAndType(int pProgramId, int pSemesterId,
      int pExamType) {
    return super.getByProgramAndSemesterAndType(pProgramId, pSemesterId, pExamType);
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemester(String pStudentId, int pSemesterId) {
    String query = SELECT_ALL + " and  a.student_id=? and a.semester_id=?";
    return mJdbcTemplate.query(query, new Object[] {pStudentId, pSemesterId},
        new ApplicationCCIRowMapper());
  }

  private List<Object[]> getInsertParamList(List<MutableApplicationCCI> pMutableApplicationCCIs)
      throws Exception {
    List<Object[]> params = new ArrayList<>();
    for(ApplicationCCI app : pMutableApplicationCCIs) {
      params.add(new Object[] {app.getSemesterId(), app.getStudentId(), app.getCourseId(),
          app.getApplicationType().getValue()});
    }
    return params;
  }

  class ApplicationCCIRowMapper implements RowMapper<ApplicationCCI> {
    @Override
    public ApplicationCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationCCI application = new PersistentApplicationCCI();
      application.setId(pResultSet.getInt("id"));
      application.setSemesterId(pResultSet.getInt("SEMESTER_ID"));
      application.setStudentId(pResultSet.getString("student_id"));
      application.setCourseId(pResultSet.getString("course_id"));
      application.setApplicationType(ApplicationType.get(pResultSet.getInt("application_type")));
      application.setApplicationDate(pResultSet.getString("applied_on"));
      application.setCourseNo(pResultSet.getString("course_no"));
      application.setCourseTitle(pResultSet.getString("course_title"));
      application.setExamDate(pResultSet.getString("exam_date"));
      // application.setTotalStudent(pResultSet.getInt("total_student"));
      return application;
    }
  }

  class ApplicationCCIRowMapperForSeatPlan implements RowMapper<ApplicationCCI> {
    @Override
    public ApplicationCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationCCI applicaton = new PersistentApplicationCCI();
      // applicaton.setId(pResultSet.getInt("id"));
      applicaton.setExamDate(pResultSet.getString("exam_date"));
      applicaton.setCourseNo(pResultSet.getString("course_no"));
      applicaton.setCourseId(pResultSet.getString("course_id"));
      applicaton.setCourseTitle(pResultSet.getString("course_title"));
      applicaton.setTotalStudent(pResultSet.getInt("total_student"));
      applicaton.setCourseYear(pResultSet.getInt("year"));
      applicaton.setCourseSemester(pResultSet.getInt("semester"));
      return applicaton;
    }
  }

  class ApplicationCCIRowMapperForSeatPlanView implements RowMapper<ApplicationCCI> {
    @Override
    public ApplicationCCI mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentApplicationCCI applicationCCI = new PersistentApplicationCCI();
      applicationCCI.setExamDate(pResultSet.getString("EXAM_DATE"));
      applicationCCI.setCourseNo(pResultSet.getString("COURSE_NO"));
      applicationCCI.setApplicationType(ApplicationType.get(pResultSet.getInt("APPLICATION_TYPE")));
      applicationCCI.setRoomNo(pResultSet.getString("ROOM_NO"));
      applicationCCI.setRoomId(pResultSet.getInt("ROOM_ID"));
      return applicationCCI;
    }
  }

}
