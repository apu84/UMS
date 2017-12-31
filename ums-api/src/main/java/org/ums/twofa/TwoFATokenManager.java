package org.ums.twofa;

import org.ums.manager.ContentManager;

import java.util.List;

public interface TwoFATokenManager extends ContentManager<TwoFAToken, MutableTwoFAToken, Long> {
  List<TwoFAToken> getUnExpiredTokens(String pUserId);

  List<TwoFAToken> getTokens(String pUserId, String pState);
}
