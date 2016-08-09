package org.ums.persistent.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.ums.decorator.NotificationDaoDecorator;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.persistent.model.PersistentNotification;
import org.ums.util.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PersistentNotificationDao extends NotificationDaoDecorator {
  String SELECT_ALL = "SELECT ID, PRODUCER_ID, CONSUMER_ID, NOTIFICATION_TYPE, PAYLOAD, PRODUCED_ON, LAST_MODIFIED FROM NOTIFICATION ";
  String INSERT_ALL = "INSERT INTO NOTIFICATION (PRODUCER_ID, CONSUMER_ID, NOTIFICATION_TYPE, PAYLOAD, PRODUCED_ON, LAST_MODIFIED) " +
      "VALUES (?, ?, ?, ?, SYSDATE, " + getLastModifiedSql() + ")";
  String UPDATE_ALL = "UPDATE NOTIFICATION SET PRODUCER_ID = ?, CONSUMER_ID = ?, NOTIFICATION_TYPE = ?, PAYLOAD = ?," +
      " PRODUCED_ON = TO_DATE(?, '" + Constants.DATE_FORMAT + "'), CONSUMED_ON = TO_DATE(?, '" + Constants.DATE_FORMAT + "')," +
      " LAST_MODIFIED = " + getLastModifiedSql() + " ";

  String DELETE_ALL = "DELETE FROM NOTIFICATION ";

  private JdbcTemplate mJdbcTemplate;
  private DateFormat mDateFormat;

  public PersistentNotificationDao(JdbcTemplate pJdbcTemplate, DateFormat pDateFormat) {
    mJdbcTemplate = pJdbcTemplate;
    mDateFormat = pDateFormat;
  }

  @Override
  public int update(MutableNotification pMutable) throws Exception {
    String query = UPDATE_ALL + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int update(List<MutableNotification> pMutableList) throws Exception {
    String query = UPDATE_ALL + " WHERE ID = ?";
    return mJdbcTemplate.batchUpdate(query, getUpdateParamArray(pMutableList)).length;
  }

  private List<Object[]> getUpdateParamArray(List<MutableNotification> pMutableList) throws Exception {
    List<Object[]> params = new ArrayList<>();
    for (Notification notification : pMutableList) {
      params.add(new Object[]{
          notification.getProducer().getId(),
          notification.getConsumer().getId(),
          notification.getNotificationType(),
          notification.getPayload(),
          mDateFormat.format(notification.getProducedOn()),
          mDateFormat.format(notification.getConsumedOn()),
          notification.getId()
      });
    }
    return params;
  }

  @Override
  public int delete(MutableNotification pMutable) throws Exception {
    String query = DELETE_ALL + " WHERE ID = ?";
    return mJdbcTemplate.update(query, pMutable.getId());
  }

  @Override
  public int delete(List<MutableNotification> pMutableList) throws Exception {
    String query = DELETE_ALL + " WHERE ID = ?";
    return mJdbcTemplate.update(query, getDeleteParamArray(pMutableList));
  }

  @Override
  public int create(MutableNotification pNotification) throws Exception {
    return mJdbcTemplate.update(INSERT_ALL,
        pNotification.getProducer().getId(),
        pNotification.getConsumer().getId(),
        pNotification.getNotificationType(),
        pNotification.getPayload(),
        mDateFormat.format(pNotification.getProducedOn()),
        mDateFormat.format(pNotification.getConsumedOn()));

  }

  @Override
  public int create(List<MutableNotification> pMutableList) throws Exception {
    return mJdbcTemplate.batchUpdate(INSERT_ALL, getInsertParamArray(pMutableList)).length;
  }

  @Override
  public Notification get(Integer pId) throws Exception {
    return super.get(pId);
  }

  private List<Object[]> getInsertParamArray(List<MutableNotification> pMutableList) throws Exception {
    List<Object[]> params = new ArrayList<>();
    for (Notification notification : pMutableList) {
      params.add(new Object[]{
          notification.getProducer().getId(),
          notification.getConsumer().getId(),
          notification.getNotificationType(),
          notification.getPayload()
      });
    }
    return params;
  }

  private List<Object[]> getDeleteParamArray(List<MutableNotification> pMutableList) throws Exception {
    List<Object[]> params = new ArrayList<>();
    for (Notification notification : pMutableList) {
      params.add(new Object[]{
          notification.getId(),
      });
    }
    return params;
  }

  class NotificationRowMapper implements RowMapper<Notification> {
    @Override
    public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
      MutableNotification notification = new PersistentNotification();
      notification.setId(rs.getInt("ID"));
      notification.setProducerId(rs.getString("PRODUCER_ID"));
      notification.setConsumerId(rs.getString("CONSUMER_ID"));
      notification.setNotificationType(rs.getString("NOTIFICATION_TYPE"));
      notification.setPayload(rs.getString("PAYLOAD"));
      notification.setProducedOn(rs.getDate("PRODUCED_ON"));
      notification.setConsumedOn(rs.getDate("CONSUMED_ON"));
      notification.setLastModified(rs.getString("LAST_MODIFIED"));
      AtomicReference<Notification> atomicReference = new AtomicReference<>(notification);
      return atomicReference.get();
    }
  }
}
