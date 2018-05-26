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
import org.ums.formatter.DateFormat;
import org.ums.manager.ContentManager;
import org.ums.manager.FCMTokenManager;
import org.ums.manager.NotificationManager;
import org.ums.persistent.model.PersistentFCMToken;
import org.ums.resource.ResourceHelper;
import org.ums.services.FirebaseMessagingImpl;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

  public JsonObject getToken(String pId, UriInfo mUriInfo) {
    LocalCache localCache = new LocalCache();
    FCMToken fcmToken = new PersistentFCMToken();
    if(mManager.exists(pId)) {
      fcmToken = mManager.get(pId);
      return toJson(fcmToken, mUriInfo, localCache);
    }
    else {
      return null;
    }
  }

  private void update(String consumerId, String token, Date pTokenLastRefreshedOn, Date pTokenDeletedOn,
      UriInfo pUriInfo) {
    MutableFCMToken mutableFCMToken = new PersistentFCMToken();
    LocalCache localeCache = new LocalCache();
    mutableFCMToken.setId(consumerId);
    mutableFCMToken.setToken(token);
    mutableFCMToken.setRefreshedOn(pTokenLastRefreshedOn);
    mutableFCMToken.setDeletedOn(pTokenDeletedOn);
    mManager.update(mutableFCMToken);
    localeCache.invalidate();
  }

  @Override
  @Transactional
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    JsonObject jsonObject = (JsonObject) pJsonObject.getJsonObject("entries");

    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);

    if(mManager.exists(user.getId())) {
      FCMToken fcmToken = mManager.get(user.getId());
      return operationForExistingUser(pUriInfo, jsonObject, user, fcmToken);
    }
    else {
      operationForNonExistingUser(pUriInfo, jsonObject, user);
    }
    return null;
  }

  private void operationForNonExistingUser(UriInfo pUriInfo, JsonObject jsonObject, User user) {
    if(mManager.hasDuplicate(jsonObject.getString("fcmToken"))) {
      FCMToken duplicateFcmToken = mManager.getToken(jsonObject.getString("fcmToken"));
      update(duplicateFcmToken.getId(), null, new Date(duplicateFcmToken.getRefreshedOn().getTime()), new Date(),
          pUriInfo);
      doPost(jsonObject, user);
    }
    else {
      doPost(jsonObject, user);
    }
  }

  private Response operationForExistingUser(UriInfo pUriInfo, JsonObject jsonObject, User user, FCMToken fcmToken)
      throws ExecutionException, InterruptedException {
    if(mManager.hasDuplicate(jsonObject.getString("fcmToken"))) {
      FCMToken duplicateFcmToken = mManager.getToken(jsonObject.getString("fcmToken"));
      update(duplicateFcmToken.getId(), null, new Date(duplicateFcmToken.getRefreshedOn().getTime()), new Date(),
          pUriInfo);
      update(user.getId(), jsonObject.getString("fcmToken"), new Date(), null, pUriInfo);
      messageSendingProcess(fcmToken);
    }
    else {
      update(user.getId(), jsonObject.getString("fcmToken"), new Date(), null, pUriInfo);
      messageSendingProcess(fcmToken);
    }
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private void messageSendingProcess(FCMToken fcmToken) throws ExecutionException, InterruptedException {
    if(fcmToken.getDeleteOn() == null) {
      sendQueuedMessages(fcmToken.getId(), fcmToken.getDeleteOn());
    }
    else {
      sendQueuedMessages(fcmToken.getId(), new Date(fcmToken.getRefreshedOn().getTime()));
    }
  }

  private Response doPost(JsonObject jsonObject, User user) {
    MutableFCMToken mutableFCMToken = new PersistentFCMToken();
    LocalCache localCache = new LocalCache();
    mutableFCMToken.setId(user.getId());
    mutableFCMToken.setRefreshedOn(new Date());
    mutableFCMToken.setDeletedOn(null);
    mBuilder.build(mutableFCMToken, jsonObject, localCache);
    mManager.create(mutableFCMToken);
    localCache.invalidate();
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private void sendQueuedMessages(String sendTo, Date pQueuedFrom) throws ExecutionException, InterruptedException {
    List<Notification> notifications = mNotificationManager.getNotifications(sendTo, pQueuedFrom);
    for(Notification notification : notifications) {
      mFirebaseMessaging.send(sendTo, notification.getNotificationType(), notification.getPayload());
    }
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