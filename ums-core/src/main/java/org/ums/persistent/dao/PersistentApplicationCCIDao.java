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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My Pc on 7/14/2016.
 */
public class PersistentApplicationCCIDao extends ApplicationCCIDaoDecorator {

  String SELECT_ALL="select a.id,a.semester_id,a.student_id,a.course_id,a.application_type,a.applied_on,c.course_no,c.course_title,to_char(exam_routine.exam_date,'DD-MM-YYYY') exam_date from application_cci a, mst_course c,exam_routine where " +
      " a.course_id= c.course_id and a.course_id=exam_routine.course_id and exam_routine.exam_type=2";
  String INSERT_ONE="Insert into APPLICATION_CCI (SEMESTER_ID,STUDENT_ID,COURSE_ID,APPLICATION_TYPE,APPLIED_ON) values (?,?,?,?,systimestamp)";
  String UPDATE_ONE="update application_cci set semester_id=?, student_id=?, course_id=?,application_type=?,applied_on=systimestamp ";
  String DELETE_ONE="delete from application_cci";


  private JdbcTemplate mJdbcTemplate;

  public PersistentApplicationCCIDao(JdbcTemplate pJdbcTemplate){
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<ApplicationCCI> getAll() throws Exception {
    String query = SELECT_ALL;
    return mJdbcTemplate.query(query, new ApplicationCCIRowMapper());
  }

  @Override
  public int create(List<MutableApplicationCCI> pMutableList) throws Exception {
    return mJdbcTemplate.batchUpdate(INSERT_ONE,getInsertParamList(pMutableList)).length;
  }

  @Override
  public int create(MutableApplicationCCI pMutable) throws Exception {
    String query = INSERT_ONE;
    return mJdbcTemplate.update(query,
        pMutable.getSemesterId(),
        pMutable.getStudentId(),
        pMutable.getCourseId(),
        pMutable.getApplicationType().getValue()
    );
  }

  @Override
  public int delete(MutableApplicationCCI pMutable) throws Exception {
    return super.delete(pMutable);
  }

  @Override
  public int deleteByStudentId(String pStudentId) {
    String query = DELETE_ONE+" where student_id=?";
    return mJdbcTemplate.update(query,new Object[]{pStudentId});
  }

  @Override
  public int update(MutableApplicationCCI pMutable) throws Exception {
    String query = DELETE_ONE+" WHERE STUDENT_ID=? ";
    return mJdbcTemplate.update(query,pMutable.getStudentId());
  }

  @Override
  public int update(List<MutableApplicationCCI> pMutableList) throws Exception {
    return super.update(pMutableList);
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemesterAndType(String pStudentId, int pSemesterId, int pExamType) {
    String query = SELECT_ALL+" STUDENT_ID=? AND SEMESTER_ID=? AND APPLICATION_TYPE=? ";
    return mJdbcTemplate.query(query,new Object[]{pStudentId,pSemesterId,pExamType}, new ApplicationCCIRowMapper());
  }

  @Override
  public List<ApplicationCCI> getBySemesterAndType(int pSemesterId, int pExamType) {
    return super.getBySemesterAndType(pSemesterId, pExamType);
  }

  @Override
  public List<ApplicationCCI> getByProgramAndSemesterAndType(int pProgramId, int pSemesterId, int pExamType) {
    return super.getByProgramAndSemesterAndType(pProgramId, pSemesterId, pExamType);
  }

  @Override
  public List<ApplicationCCI> getByStudentIdAndSemester(String pStudentId, int pSemesterId) {
    String query = SELECT_ALL+" and  a.student_id=? and a.semester_id=?";
    return mJdbcTemplate.query(query,new Object[]{pStudentId,pSemesterId}, new ApplicationCCIRowMapper());
  }

  private List<Object[]> getInsertParamList(List<MutableApplicationCCI> pMutableApplicationCCIs)throws Exception{
    List<Object[]> params = new ArrayList<>();
    for(ApplicationCCI app: pMutableApplicationCCIs){
      params.add(new Object[]{
          app.getSemesterId(),
          app.getStudentId(),
          app.getCourseId(),
          app.getApplicationType().getValue()
      });
    }
    return params;
  }

  class ApplicationCCIRowMapper implements RowMapper<ApplicationCCI>{
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
      return application;
    }
  }

}
