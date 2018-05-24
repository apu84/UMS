package org.ums.decorator;

import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.manager.FCMTokenManager;

public class FCMTokenDaoDecorator extends ContentDaoDecorator<FCMToken, MutableFCMToken, String, FCMTokenManager>
    implements FCMTokenManager {

  @Override
  public boolean hasDuplicate(String pToken) {
    return getManager().hasDuplicate(pToken);
  }

  @Override
  public FCMToken getToken(String pToken) {
    return getManager().getToken(pToken);
  }
}
