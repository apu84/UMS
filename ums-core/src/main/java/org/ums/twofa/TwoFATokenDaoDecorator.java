package org.ums.twofa;

import java.util.List;

import org.ums.decorator.ContentDaoDecorator;

public class TwoFATokenDaoDecorator extends ContentDaoDecorator<TwoFAToken, MutableTwoFAToken, Long, TwoFATokenManager>
    implements TwoFATokenManager {

  public int updateWrongTryCount(Long pTokenId) {
    return getManager().updateWrongTryCount(pTokenId);
  }

  public int updateRightTryCount(Long pTokenId) {
    return getManager().updateRightTryCount(pTokenId);
  }

  public List<TwoFAToken> getUnExpiredTokens(String pUserId, String pType) {
    return getManager().getUnExpiredTokens(pUserId, pType);
  }

  public List<TwoFAToken> getTokens(String pUserId, String pState) {
    return getManager().getTokens(pUserId, pState);
  }
}
