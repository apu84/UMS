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
public class NotificationResourceHelper extends
    ResourceHelper<Notification, MutableNotification, String> {
  @Autowired
  NotificationManager mNotificationManager;

  @Autowired
  NotificationBuilder mNotificationBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException("Post method not implemented for UserResourceHelper");
  }

  @Override
  protected ContentManager<Notification, MutableNotification, String> getContentManager() {
    return mNotificationManager;
  }

  @Override
  protected Builder<Notification, MutableNotification> getBuilder() {
    return mNotificationBuilder;
  }

  @Override
  protected String getEtag(Notification pReadonly) {
    return pReadonly.getLastModified();
  }

  public JsonObject getNotifications(String pConsumerId, String pNotificationType, UriInfo pUriInfo)
      throws Exception {
    List<Notification> notifications =
        mNotificationManager.getNotifications(pConsumerId, pNotificationType);

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

  public JsonObject getNotifications(String pConsumerId, Integer pNumOfLatestNotification,
      UriInfo pUriInfo) throws Exception {
    List<Notification> notifications =
        mNotificationManager.getNotifications(pConsumerId, pNumOfLatestNotification);

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

  public void updateReadStatus(JsonArray pJsonArray) throws Exception {
    List<MutableNotification> notifications = new ArrayList<>();
    for(int i = 0; i < pJsonArray.size(); i++) {
      JsonObject notificationObject = pJsonArray.getJsonObject(i);
      Notification notification = mNotificationManager.get(notificationObject.getString("id"));
      MutableNotification mutableNotification = notification.edit();
      mutableNotification.setConsumedOn(new Date());
      notifications.add(mutableNotification);
    }
    mNotificationManager.update(notifications);
  }
}
