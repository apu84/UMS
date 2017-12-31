package org.ums.twofa;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;

public class TwoFATokenDaoDecorator extends ContentDaoDecorator<TwoFAToken, MutableTwoFAToken, Long, TwoFATokenManager>
    implements TwoFATokenManager {
  @Override
  public List<TwoFAToken> getUnExpiredTokens(String pUserId) {
    return getManager().getUnExpiredTokens(pUserId);
  }

  @Override
  public List<TwoFAToken> getTokens(String pUserId, String pState) {
    return getManager().getTokens(pUserId, pState);
  }
}
