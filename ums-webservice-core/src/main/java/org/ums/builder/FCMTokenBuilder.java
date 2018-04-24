package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.formatter.DateFormat;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class FCMTokenBuilder implements Builder<FCMToken, MutableFCMToken> {

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Autowired
  private UserManager mUserManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, FCMToken pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("token", pReadOnly.getFCMToken());
    pBuilder.add("tokenDeletedOn", mDateFormat.format(pReadOnly.getTokenDeleteOn()));
    pBuilder.add("lastModified", pReadOnly.getLastModified());
  }

  @Override
  public void build(MutableFCMToken pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);

    pMutable.setId(user.getId());
    pMutable.setFCMToken(pJsonObject.getString("fcmToken"));
    pMutable.setTokenDeletedOn(null);
  }
}
