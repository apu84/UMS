package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import org.ums.decorator.TaskStatusDaoDecorator;
import org.ums.domain.model.immutable.TaskStatus;
import org.ums.domain.model.mutable.MutableTaskStatus;
import org.ums.persistent.model.PersistentTaskStatus;
import org.ums.statistics.UMSJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentTaskStatusDao extends TaskStatusDaoDecorator {
  String SELECT_ALL =
      "SELECT TASK_ID, TASK_NAME, STATUS, PROGRESS_DESC, TASK_COMPLETION_DATE, LAST_MODIFIED FROM TASK_STATUS ";
  String UPDATE_ALL =
      "UPDATE TASK_STATUS SET TASK_NAME = ?, STATUS = ?, PROGRESS_DESC = ?, TASK_COMPLETION_DATE = SYSDATE, LAST_MODIFIED = "
          + getLastModifiedSql() + " ";
  String DELETE_ALL = "DELETE FROM TASK_STATUS ";
  String INSERT_ALL =
      "INSERT INTO TASK_STATUS(TASK_ID, TASK_NAME, STATUS, PROGRESS_DESC, TASK_COMPLETION_DATE, LAST_MODIFIED) "
          + "VALUES(?, ?, ?, ?, SYSDATE, " + getLastModifiedSql() + ")";
  String MAX_SERIAL = "SELECT MAX(SERIAL) FROM TASK_STATUS WHERE TASK_ID = ?";
  String EXISTS = "SELECT COUNT(TASK_ID) EXIST FROM TASK_STATUS ";

  private JdbcTemplate mJdbcTemplate;

  public PersistentTaskStatusDao(JdbcTemplate pJdbcTemplate) {
    mJdbcTemplate = pJdbcTemplate;
  }

  @Override
  public TaskStatus get(String pId) throws Exception {
    String query = SELECT_ALL + "WHERE TASK_ID = ? AND SERIAL = (" + MAX_SERIAL + ")";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId, pId}, new TaskStatusRowMapper());
  }

  @Override
  public int update(MutableTaskStatus pMutable) throws Exception {
    String query = UPDATE_ALL + "WHERE TASK_ID = ? AND SERIAL = (" + MAX_SERIAL + ")";
    return mJdbcTemplate.update(
        query,
        pMutable.getTaskName(),
        pMutable.getStatus().getId(),
        StringUtils.isEmpty(pMutable.getProgressDescription()) ? "" : pMutable
            .getProgressDescription(), pMutable.getId(), pMutable.getId());
  }

  @Override
  public int delete(MutableTaskStatus pMutable) throws Exception {
    String query = DELETE_ALL + "WHERE TASK_ID = ? AND SERIAL = (" + MAX_SERIAL + ")";
    return mJdbcTemplate.update(query, pMutable.getId(), pMutable.getId());
  }

  @Override
  public int create(MutableTaskStatus pMutable) throws Exception {
    return mJdbcTemplate.update(INSERT_ALL, pMutable.getId(), pMutable.getTaskName(), pMutable
        .getStatus().getId(), StringUtils.isEmpty(pMutable.getProgressDescription()) ? ""
        : pMutable.getProgressDescription());
  }

  @Override
  public boolean exists(String pId) throws Exception {
    String query = EXISTS + "WHERE TASK_ID = ? ";
    return mJdbcTemplate.queryForObject(query, Boolean.class, pId);
  }

  private class TaskStatusRowMapper implements RowMapper<TaskStatus> {
    @Override
    public TaskStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableTaskStatus taskStatus = new PersistentTaskStatus();
      taskStatus.setId(rs.getString("TASK_ID"));
      taskStatus.setTaskName(rs.getString("TASK_NAME"));
      taskStatus.setStatus(TaskStatus.Status.get(rs.getInt("STATUS")));
      taskStatus.setProgressDescription(StringUtils.isEmpty(rs.getString("PROGRESS_DESC")) ? ""
          : rs.getString("PROGRESS_DESC"));
      taskStatus.setTaskCompletionDate(rs.getObject("TASK_COMPLETION_DATE") == null ? null : rs
          .getDate("TASK_COMPLETION_DATE"));
      taskStatus.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<TaskStatus> atomicReference = new AtomicReference<>(taskStatus);
      return atomicReference.get();
    }
  }
}
