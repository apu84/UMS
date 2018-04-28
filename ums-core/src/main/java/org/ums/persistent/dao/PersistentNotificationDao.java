package org.ums.persistent.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.NotificationDaoDecorator;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.generator.IdGenerator;
import org.ums.persistent.model.PersistentNotification;

public class PersistentNotificationDao extends NotificationDaoDecorator {
  String SELECT_ALL =
      "SELECT ID, PRODUCER_ID, CONSUMER_ID, NOTIFICATION_TYPE, PAYLOAD, PRODUCED_ON, CONSUMED_ON, LAST_MODIFIED FROM NOTIFICATION ";
  String INSERT_ALL =
      "INSERT INTO NOTIFICATION (ID, PRODUCER_ID, CONSUMER_ID, NOTIFICATION_TYPE, PAYLOAD, PRODUCED_ON, LAST_MODIFIED) "
          + "VALUES (?, ?, ?, ?, ?, SYSDATE, " + getLastModifiedSql() + ")";
  String UPDATE_ALL = "UPDATE NOTIFICATION SET PRODUCER_ID = ?, CONSUMER_ID = ?, NOTIFICATION_TYPE = ?, PAYLOAD = ?,"
      + "CONSUMED_ON = SYSDATE, LAST_MODIFIED = " + getLastModifiedSql() + " ";

  String DELETE_ALL = "DELETE FROM NOTIFICATION ";

  private JdbcTemplate mJdbcTemplate;
  private IdGenerator mIdGenerator;

  public PersistentNotificationDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
    mJdbcTemplate = pJdbcTemplate;
    mIdGenerator = pIdGenerator;
  }

  @Override
  public int update(MutableNotification pNotification) {
    List<MutableNotification> notifications = new ArrayList<>();
    notifications.add(pNotification);
    return update(notifications);
  }

  @Override
  public int update(List<MutableNotification> pMutableList) {
    String query = UPDATE_ALL + " WHERE ID = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamArray(pMutableList)).length;
  }

  private List<Object[]> getUpdateParamArray(List<MutableNotification> pMutableList) {
    List<Object[]> params = new ArrayList<>();
    for(Notification notification : pMutableList) {
      params.add(new Object[] {notification.getProducerId(), notification.getConsumerId(),
          notification.getNotificationType(), notification.getPayload(), notification.getId()});
    }
    return params;
  }

  @Override
  public int delete(MutableNotification pMutable) {
    String query = DELETE_ALL + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int delete(List<MutableNotification> pMutableList) {
    String query = DELETE_ALL + " WHERE ID = ?";
    return mJdbcTemplate.update(query, getDeleteParamArray(pMutableList));
  }

  @Override
  public Long create(MutableNotification pNotification) {
    Long id = mIdGenerator.getNumericId();
    mJdbcTemplate.update(INSERT_ALL, id, pNotification.getProducerId(), pNotification.getConsumerId(),
        pNotification.getNotificationType(), pNotification.getPayload());
    return id;
  }

  @Override
  public List<Long> create(List<MutableNotification> pMutableList) {
    List<Object[]> params = getInsertParamArray(pMutableList);
    mJdbcTemplate.batchUpdate(INSERT_ALL, params);
    return params.stream().map(param -> (Long) param[0])
        .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public Notification get(Long pId) {
    String query = SELECT_ALL + " WHERE ID = ?";
    return mJdbcTemplate.queryForObject(query, new Object[] {pId}, new NotificationRowMapper());
  }

  @Override
  public List<Notification> getNotifications(String consumerId, Date pProducedOn) {
    String query = SELECT_ALL + " WHERE CONSUMER_ID = ? AND PRODUCED_ON >= ? ORDER BY PRODUCED_ON DESC";
    return mJdbcTemplate.query(query, new Object[] {consumerId, pProducedOn}, new NotificationRowMapper());
  }

  @Override
  public List<Notification> getNotifications(String pConsumerId, String pNotificationType) {
    String query = SELECT_ALL + " WHERE CONSUMER_ID = ? AND NOTIFICATION_TYPE = ? ORDER BY PRODUCED_ON DESC";
    return mJdbcTemplate.query(query, new Object[] {pConsumerId, pNotificationType}, new NotificationRowMapper());
  }

  @Override
  public List<Notification> getNotifications(String pConsumerId, Integer pNumOfLatestNotification) {
    String query =
        "SELECT * FROM (" + SELECT_ALL + " WHERE CONSUMER_ID = ? ORDER BY PRODUCED_ON DESC) WHERE ROWNUM <= ?";
    return mJdbcTemplate
        .query(query, new Object[] {pConsumerId, pNumOfLatestNotification}, new NotificationRowMapper());
  }

  private List<Object[]> getInsertParamArray(List<MutableNotification> pMutableList) {
    List<Object[]> params = new ArrayList<>();
    for(Notification notification : pMutableList) {
      params.add(new Object[] {mIdGenerator.getNumericId(), notification.getProducerId(), notification.getConsumerId(),
          notification.getNotificationType(), notification.getPayload()});
    }
    return params;
  }

  private List<Object[]> getDeleteParamArray(List<MutableNotification> pMutableList) {
    List<Object[]> params = new ArrayList<>();
    for(Notification notification : pMutableList) {
      params.add(new Object[] {notification.getId(),});
    }
    return params;
  }

  class NotificationRowMapper implements RowMapper<Notification> {
    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableNotification notification = new PersistentNotification();
      notification.setId(rs.getLong("ID"));
      notification.setProducerId(rs.getString("PRODUCER_ID"));
      notification.setConsumerId(rs.getString("CONSUMER_ID"));
      notification.setNotificationType(rs.getString("NOTIFICATION_TYPE"));
      notification.setPayload(rs.getString("PAYLOAD"));
      notification.setProducedOn(rs.getTimestamp("PRODUCED_ON"));
      notification.setConsumedOn(rs.getObject("CONSUMED_ON") == null ? null : rs.getTimestamp("CONSUMED_ON"));
      notification.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<Notification> atomicReference = new AtomicReference<>(notification);
      return atomicReference.get();
    }
  }
}
