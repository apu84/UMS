package org.ums.persistent.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.ums.decorator.NotificationDaoDecorator;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.mutable.MutableNotification;

import java.util.List;

public class PersistentObjectNotificationDao extends NotificationDaoDecorator {
  private MongoOperations mMongoOperations;

  public PersistentObjectNotificationDao(MongoOperations pMongoOperations) {
    mMongoOperations = pMongoOperations;
  }

  @Override
  public Notification get(Long pId) {
    return mMongoOperations.findOne(Query.query(Criteria.where("mId").is(pId)), Notification.class);
  }

  @Override
  public int update(MutableNotification pMutable) {
    Query query = Query.query(Criteria.where("mId").is(pMutable.getId()));
    DBObject dbDoc = new BasicDBObject();
    mMongoOperations.getConverter().write(pMutable, dbDoc);
    Update update = Update.fromDBObject(dbDoc);
    mMongoOperations.updateFirst(query, update, Notification.class);
    return 1;
  }

  @Override
  public int delete(MutableNotification pMutable) {
    mMongoOperations.remove(pMutable);
    return 1;
  }

  @Override
  public int create(MutableNotification pMutable) {
    mMongoOperations.insert(pMutable);
    return 1;
  }

  @Override
  public int create(List<MutableNotification> pMutableList) {
    mMongoOperations.insert(pMutableList, Notification.class);
    return pMutableList.size();
  }

  @Override
  public List<Notification> getNotifications(String pConsumerId, String pNotificationType) {
    Query query = new Query();
    query.addCriteria(new Criteria().andOperator(Criteria.where("mConsumerId").is(pConsumerId),
        Criteria.where("mNotificationType").is(pNotificationType)));
    return mMongoOperations.find(query, Notification.class);
  }

  @Override
  public List<Notification> getNotifications(String pConsumerId, Integer pNumOfLatestNotification) {
    Query query = new Query(Criteria.where("mConsumerId").is(pConsumerId));
    query.limit(pNumOfLatestNotification);
    query.with(new Sort(Sort.Direction.DESC, "mProducedOn"));
    return mMongoOperations.find(query, Notification.class);
  }
}
