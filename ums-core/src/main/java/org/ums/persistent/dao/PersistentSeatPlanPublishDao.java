package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.SeatPlanPublishDaoDecorator;
import org.ums.domain.model.immutable.SeatPlanPublish;
import org.ums.domain.model.mutable.MutableSeatPlanPublish;
import org.ums.enums.ExamType;
import org.ums.persistent.model.PersistentSeatPlanPublish;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My Pc on 8/2/2016.
 */
public class PersistentSeatPlanPublishDao extends SeatPlanPublishDaoDecorator {

  private JdbcTemplate mJdbcTemplate;

  public PersistentSeatPlanPublishDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public List<SeatPlanPublish> getBySemester(Integer pSemesterId) {
    String query =
        "select id,semester_id,exam_type,to_char(exam_date,'DD/MM/YYYY') exam_date,published from sp_publish where semester_id=?";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId}, new SeatPlanPublishRowMapper());
  }

  @Override
  public Integer checkBySemester(Integer pSemesterId) {
    String query = " select count(*) from sp_publish where semester_id=?";
    return mJdbcTemplate.queryForObject(query, Integer.class, pSemesterId);
  }

  @Override
  public int update(List<MutableSeatPlanPublish> pMutableList) {
    String query = " update sp_publish set  published=? where id=?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamList(pMutableList)).length;
  }

  @Override
  public int create(List<MutableSeatPlanPublish> pMutableList) {
    String query =
        " insert into sp_publish(semester_id,exam_type,exam_date,published) values(?,?,to_date(?,'dd/MM/YYYY'),?)";
    return mJdbcTemplate.batchUpdate(query, getInsertParamList(pMutableList)).length;
  }

  @Override
  public Integer deleteBySemester(Integer pSemesterId) {
    String query = "delete from sp_publish where semester_id=?";
    return mJdbcTemplate.update(query, pSemesterId);
  }

  public List<Object[]> getUpdateParamList(List<MutableSeatPlanPublish> pMutableSeatPlanPublishs) {
    List<Object[]> params = new ArrayList<>();
    for(SeatPlanPublish publish : pMutableSeatPlanPublishs) {
      params.add(new Object[] {publish.getPublishStatus(), publish.getId()});
    }
    return params;
  }

  public List<Object[]> getInsertParamList(List<MutableSeatPlanPublish> pMutableSeatPlanPublishs) {
    List<Object[]> params = new ArrayList<>();
    for(SeatPlanPublish publish : pMutableSeatPlanPublishs) {
      params.add(new Object[] {publish.getSemesterId(), publish.getExamType().getId(),
          publish.getExamDate(), publish.getPublishStatus(),});
    }
    return params;
  }

  class SeatPlanPublishRowMapper implements RowMapper<SeatPlanPublish> {
    @Override
    public SeatPlanPublish mapRow(ResultSet pResultSet, int pI) throws SQLException {
      PersistentSeatPlanPublish publish = new PersistentSeatPlanPublish();
      publish.setId(pResultSet.getInt("id"));
      publish.setSemesterId(pResultSet.getInt("semester_id"));
      publish.setExamType(ExamType.get(pResultSet.getInt("exam_type")));
      publish.setExamDate(pResultSet.getString("exam_date"));
      publish.setPublishStatus(pResultSet.getInt("published"));
      return publish;
    }
  }

}
