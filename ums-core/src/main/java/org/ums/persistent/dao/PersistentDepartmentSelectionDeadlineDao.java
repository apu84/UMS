package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.DepartmentSelectionDeadlineDaoDecorator;
import org.ums.domain.model.immutable.DepartmentSelectionDeadline;
import org.ums.domain.model.mutable.MutableDepartmentSelectionDeadline;
import org.ums.persistent.model.PersistentDepartmentSelectionDeadline;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Monjur-E-Morshed on 27-Apr-17.
 */
public class PersistentDepartmentSelectionDeadlineDao extends DepartmentSelectionDeadlineDaoDecorator {

  private JdbcTemplate mJdbcTemplate;

  public PersistentDepartmentSelectionDeadlineDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public int delete(MutableDepartmentSelectionDeadline pMutable) {
    String query = "delete from dept_selection_deadline where id=?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public List<DepartmentSelectionDeadline> getDeadline(int pSemesterId, String pUnit, String pQuota) {
    String query = "select * from dept_selection_deadline where semester_id=? and unit=? and quota=? order by id";
    return mJdbcTemplate.query(query, new Object[] {pSemesterId, pUnit, pQuota},
        new DepartmentSelectionDeadlineRowMapper());
  }

  @Override
  public List<Integer> create(List<MutableDepartmentSelectionDeadline> pMutableList) {
    String query="insert into dept_selection_deadline(semester_id, quota, unit, merit_from, merit_to, deadline, last_modified) VALUES (?,?,?,?,?,?,"+getLastModifiedSql() +")";
    List<Object[]> params = getDepartmentSelectionDeadlineParams(pMutableList);
    mJdbcTemplate.batchUpdate(query, params);
    return params.stream().map(param->(Integer) param[0]).collect(Collectors.toCollection((ArrayList::new)));
  }

  @Override
  public int update(List<MutableDepartmentSelectionDeadline> pMutableList) {
    String query =
        "update dept_selection_deadline set merit_from=?, merit_to=?, deadline=?, last_modified="
            + getLastModifiedSql() + " where semester_id=? and quota=? and unit=? and id=?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParams(pMutableList)).length;
  }

  private List<Object[]> getDepartmentSelectionDeadlineParams(List<MutableDepartmentSelectionDeadline> pList) {
    List<Object[]> params = new ArrayList<>();
    for(DepartmentSelectionDeadline deadline : pList) {
      params.add(new Object[] {deadline.getSemester().getId(), deadline.getQuota(), deadline.getUnit(),
          deadline.getFromMeritSerialNumber(), deadline.getToMeritSerialNumber(), deadline.getDeadline()});
    }
    return params;
  }

  private List<Object[]> getUpdateParams(List<MutableDepartmentSelectionDeadline> pList) {
    List<Object[]> params = new ArrayList<>();
    for(DepartmentSelectionDeadline deadline : pList) {
      params.add(new Object[] {deadline.getFromMeritSerialNumber(), deadline.getToMeritSerialNumber(),
          deadline.getDeadline(), deadline.getSemester().getId(), deadline.getQuota(), deadline.getUnit(),
          deadline.getId()});
    }
    return params;
  }

  class DepartmentSelectionDeadlineRowMapper implements RowMapper<DepartmentSelectionDeadline> {
    @Override
    public DepartmentSelectionDeadline mapRow(ResultSet rs, int rowNum) throws SQLException {
      PersistentDepartmentSelectionDeadline departmentSelectionDeadline = new PersistentDepartmentSelectionDeadline();
      departmentSelectionDeadline.setId(rs.getInt("id"));
      departmentSelectionDeadline.setSemesterId(rs.getInt("semester_id"));
      departmentSelectionDeadline.setQuota(rs.getString("quota"));
      departmentSelectionDeadline.setUnit("unit");
      departmentSelectionDeadline.setFromMeritSerialNumber(rs.getInt("merit_from"));
      departmentSelectionDeadline.setToMeritSerialNumber(rs.getInt("merit_to"));
      departmentSelectionDeadline.setDeadline(rs.getDate("deadline"));
      departmentSelectionDeadline.setLastModified(rs.getString("last_modified"));
      return departmentSelectionDeadline;
    }
  }
}
