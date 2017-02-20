package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import org.ums.decorator.TaskStatusDaoDecorator;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.mutable.MutableTaskStatus;
import org.ums.persistent.model.PersistentTaskStatus;

public class PersistentTaskStatusDao extends TaskStatusDaoDecorator {
  String SELECT_ALL =
      "SELECT TASK_ID, STATUS, PROGRESS_DESC, TASK_COMPLETION_DATE, LAST_MODIFIED FROM TASK_STATUS ";
  String UPDATE_ALL =
      "UPDATE TASK_STATUS SET STATUS = ?, PROGRESS_DESC = ?, TASK_COMPLETION_DATE = SYSDATE, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM TASK_STATUS ";
  String INSERT_ALL =
      "INSERT INTO TASK_STATUS(TASK_ID, STATUS, PROGRESS_DESC, TASK_COMPLETION_DATE, LAST_MODIFIED) "
          + "VALUES(?, ?, ?, SYSDATE, " + getLastModifiedSql() + ")";
  String MAX_SERIAL = "SELECT MAX(SERIAL) FROM TASK_STATUS WHERE TASK_ID = ?";
  String EXISTS = "SELECT COUNT(TASK_ID) EXIST FROM TASK_STATUS ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentTaskStatusDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public TaskStatus get(String pId) {
    String query = SELECT_ALL + "WHERE TASK_ID = ? AND SERIAL = (" + MAX_SERIAL + ")";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId, pId}, new TaskStatusRowMapper());
  }

  @Override
  public int update(MutableTaskStatus pMutable) {
    String query = UPDATE_ALL + "WHERE TASK_ID = ? AND SERIAL = (" + MAX_SERIAL + ")";
    return mJdbcTemplate.update(query, pMutable.getStatus().getId(), StringUtils.isEmpty(pMutable
        .getProgressDescription()) ? "" : pMutable.getProgressDescription(), pMutable.getId(),
        pMutable.getId());
  }

  @Override
  public int delete(MutableTaskStatus pMutable) {
    String query = DELETE_ALL + "WHERE TASK_ID = ? AND SERIAL = (" + MAX_SERIAL + ")";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getId());
  }

  @Override
  public String create(MutableTaskStatus pMutable) {
    mJdbcTemplate.update(INSERT_ALL, pMutable.getId(), pMutable.getStatus().getId(), StringUtils
        .isEmpty(pMutable.getProgressDescription()) ? "" : pMutable.getProgressDescription());
    return pMutable.getId();
  }

  @Override
  public boolean exists(String pId) {
    String query = EXISTS + "WHERE TASK_ID = ? ";
    return mJdbcTemplate.queryForObject(query, Boolean.class, pId);
  }

  private class TaskStatusRowMapper implements RowMapper<TaskStatus> {
    @Override
    public TaskStatus mapRow(ResultSet rs, int rowNum) {
      try {
        MutableTaskStatus taskStatus = new PersistentTaskStatus();
        taskStatus.setId(rs.getString("TASK_ID"));
        taskStatus.setStatus(TaskStatus.Status.get(rs.getInt("STATUS")));
        taskStatus.setProgressDescription(StringUtils.isEmpty(rs.getString("PROGRESS_DESC")) ? ""
            : rs.getString("PROGRESS_DESC"));
        taskStatus.setTaskCompletionDate(rs.getObject("TASK_COMPLETION_DATE") == null ? null : rs
            .getDate("TASK_COMPLETION_DATE"));
        taskStatus.setLastModified(rs.getString("LAST_MODIFIED"));
        AtomicReference<TaskStatus> atomicReference = new AtomicReference<>(taskStatus);
        return atomicReference.get();
      } catch(SQLException sqlE) {
        throw new RuntimeException(sqlE);
      }
    }
  }
}
