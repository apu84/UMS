package org.ums.manager;

import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;

public interface FCMTokenManager extends ContentManager<FCMToken, MutableFCMToken, String> {

  boolean hasDuplicate(String pToken);

  boolean isDuplicate(String pUserId, String pToken);

  FCMToken getToken(String pToken);
}
