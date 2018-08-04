package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.FCMTokenBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.immutable.Notification;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.domain.model.mutable.MutableNotification;
import org.ums.formatter.DateFormat;
import org.ums.manager.ContentManager;
import org.ums.manager.FCMTokenManager;
import org.ums.manager.NotificationManager;
import org.ums.persistent.model.PersistentFCMToken;
import org.ums.persistent.model.PersistentNotification;
import org.ums.resource.ResourceHelper;
import org.ums.services.FirebaseMessagingImpl;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FCMTokenResourceHelper extends ResourceHelper<FCMToken, MutableFCMToken, String> {

  @Autowired
  private FCMTokenManager mManager;

  @Autowired
  private FCMTokenBuilder mBuilder;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  FirebaseMessagingImpl mFirebaseMessaging;

  @Autowired
  private NotificationManager mNotificationManager;

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Override
  @Transactional
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    String token = pJsonObject.getJsonObject("entries").getString("fcmToken");
    User user = getCurrentLoggedInUser();

    if(!mManager.isDuplicate(user.getId(), token)) {
      LocalCache localCache = new LocalCache();
      MutableFCMToken mutableFCMToken = new PersistentFCMToken();
      mutableFCMToken.setId(user.getId());
      mutableFCMToken.setToken(token);
      mutableFCMToken.setCreatedOn(new Date());
      mManager.create(mutableFCMToken);
      localCache.invalidate();
    }
    sendQueuedMessages(user.getId());
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private User getCurrentLoggedInUser() {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    return mUserManager.get(userId);
  }

  private void sendQueuedMessages(String sendTo) {
    List<Notification> notifications = mNotificationManager.getNotifications(sendTo);
    if(notifications.size() > 3) {
      mFirebaseMessaging
          .send(sendTo, sendTo, "IUMS", "You have " + notifications.size() + " new notifications.", false);
    }
    else {
      for(Notification notification : notifications) {
        mFirebaseMessaging.send(sendTo, sendTo, notification.getNotificationType(), notification.getPayload(), false);
      }
    }

    List<MutableNotification> mutableNotifications = new ArrayList<>();
    for(Notification notification : notifications) {
      MutableNotification mutableNotification = new PersistentNotification();
      mutableNotification = (MutableNotification) notification;
      mutableNotifications.add(mutableNotification);
    }

    mNotificationManager.update(mutableNotifications);
  }

  @Override
  protected ContentManager<FCMToken, MutableFCMToken, String> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<FCMToken, MutableFCMToken> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(FCMToken pReadonly) {
    return pReadonly.getLastModified();
  }
}
