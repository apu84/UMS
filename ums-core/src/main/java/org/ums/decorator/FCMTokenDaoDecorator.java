package org.ums.decorator;

import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.manager.FCMTokenManager;

public class FCMTokenDaoDecorator extends ContentDaoDecorator<FCMToken, MutableFCMToken, Long, FCMTokenManager>
    implements FCMTokenManager {
  @Override
  public FCMToken getFCMToken(String pUserId) {
    return getManager().getFCMToken(pUserId);
  }
}
