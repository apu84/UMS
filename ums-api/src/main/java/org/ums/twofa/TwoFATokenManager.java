package org.ums.twofa;

import org.ums.manager.ContentManager;

import java.util.List;

public interface TwoFATokenManager extends ContentManager<TwoFAToken, MutableTwoFAToken, Long> {
  int updateWrongTryCount(Long pTokenId);

  int updateRightTryCount(Long pTokenId);

  List<TwoFAToken> getUnExpiredTokens(String pUserId, String pState);

  List<TwoFAToken> getTokens(String pUserId, String pState);
}
