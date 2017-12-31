package org.ums.twofa;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

public class TwoFATokenGeneratorImpl implements TwoFATokenGenerator {
  private TwoFATokenManager mTwoFATokenManager;
  private TwoFATokenEmailSender mTwoFATokenEmailSender;
  private UserManager mUserManager;
  private String mEmailSender;
  private Random mRandom = new Random();

  public TwoFATokenGeneratorImpl(TwoFATokenManager pTwoFATokenManager, TwoFATokenEmailSender pTwoFATokenEmailSender,
      UserManager pUserManager, String pEmailSender) {
    mTwoFATokenManager = pTwoFATokenManager;
    mTwoFATokenEmailSender = pTwoFATokenEmailSender;
    mUserManager = pUserManager;
    mEmailSender = pEmailSender;
  }

  @Override
  public TwoFAToken generateToken(String pUserId) {
    List<TwoFAToken> tokens = mTwoFATokenManager.getUnExpiredTokens(pUserId);
    TwoFAToken existingToken = null;
    if(tokens != null && tokens.size() > 0) {
      existingToken = tokens.get(0);
    }
    if(existingToken == null) {
      SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
      MutableTwoFAToken newToken = new PersistentTwoFAToken();
      newToken.setUserId(pUserId);
      newToken.setState(generator.nextBytes().toBase64());
      newToken.setToken(String.valueOf(Math.abs(mRandom.nextInt(100000))));
      newToken.setId(newToken.create());

      User user = mUserManager.get(pUserId);
      mTwoFATokenEmailSender.sendEmail(user.getEmail(), mEmailSender, "Two FA token", newToken.getToken());
      return newToken;
    }
    else {
      return existingToken;
    }
  }

  @Override
  public boolean validateToken(String pUserId, String pState, String pToken) {
    List<TwoFAToken> tokens = mTwoFATokenManager.getTokens(pUserId, pState);
    TwoFAToken existingToken = null;
    if(tokens == null || tokens.size() == 0) {
      return false;
    }
    else {
      existingToken = tokens.get(0);
    }
    return existingToken != null && existingToken.getToken().equals(pToken)
        && existingToken.getTokenExpiry().after(new Date());
  }
}
