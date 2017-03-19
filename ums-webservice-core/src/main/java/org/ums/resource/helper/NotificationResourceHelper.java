package org.ums.resource.helper;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.NotificationBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.manager.ContentManager;
import org.ums.manager.NotificationManager;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class NotificationResourceHelper extends ResourceHelper<Notification, MutableNotification, Long> {
  @Autowired
  NotificationManager mNotificationManager;

  @Autowired
  NotificationBuilder mNotificationBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    throw new NotImplementedException("Post method not implemented for UserResourceHelper");
  }

  @Override
  protected ContentManager<Notification, MutableNotification, Long> getContentManager() {
    return mNotificationManager;
  }

  @Override
  protected Builder<Notification, MutableNotification> getBuilder() {
    return mNotificationBuilder;
  }

  @Override
  protected String getETag(Notification pReadonly) {
    return pReadonly.getLastModified();
  }

  public JsonObject getNotifications(String pConsumerId, String pNotificationType, UriInfo pUriInfo) {
    List<Notification> notifications = mNotificationManager.getNotifications(pConsumerId, pNotificationType);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Notification readOnly : notifications) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getNotifications(String pConsumerId, Integer pNumOfLatestNotification, UriInfo pUriInfo) {
    List<Notification> notifications = mNotificationManager.getNotifications(pConsumerId, pNumOfLatestNotification);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Notification readOnly : notifications) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  public void updateReadStatus(JsonArray pJsonArray) {
    List<MutableNotification> notifications = new ArrayList<>();
    for(int i = 0; i < pJsonArray.size(); i++) {
      JsonObject notificationObject = pJsonArray.getJsonObject(i);
      Notification notification = mNotificationManager.get(Long.parseLong(notificationObject.getString("id")));
      MutableNotification mutableNotification = notification.edit();
      mutableNotification.setConsumedOn(new Date());
      notifications.add(mutableNotification);
    }
    mNotificationManager.update(notifications);
  }
}
