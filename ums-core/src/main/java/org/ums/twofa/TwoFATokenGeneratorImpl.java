package org.ums.twofa;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import java.util.List;
import java.util.Random;

public class TwoFATokenGeneratorImpl implements TwoFATokenGenerator {
  private TwoFATokenManager mTwoFATokenManager;
  private TwoFATokenEmailSender mTwoFATokenEmailSender;
  private UserManager mUserManager;

  public TwoFATokenGeneratorImpl(TwoFATokenManager pTwoFATokenManager, TwoFATokenEmailSender pTwoFATokenEmailSender,
      UserManager pUserManager) {
    mTwoFATokenManager = pTwoFATokenManager;
    mTwoFATokenEmailSender = pTwoFATokenEmailSender;
    mUserManager = pUserManager;
  }

  @Override
  public TwoFAToken generateToken(String pUserId, String pType) {
    List<TwoFAToken> tokens = mTwoFATokenManager.getUnExpiredTokens(pUserId, pType);
    TwoFAToken existingToken = null;
    if(tokens != null && tokens.size() > 0) {
      existingToken = tokens.get(0);
    }
    if(existingToken == null) {
      SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
      MutableTwoFAToken newToken = new PersistentTwoFAToken();
      newToken.setUserId(pUserId);
      newToken.setType(pType);

      Random rnd = new Random();
      int n = 100000 + rnd.nextInt(900000);
      String sha256hex = DigestUtils.sha256Hex(String.valueOf(n));
      newToken.setOtp(sha256hex);
      newToken.setId(newToken.create());
      existingToken = mTwoFATokenManager.get(newToken.getId());
      User user = mUserManager.get(pUserId);

      mTwoFATokenEmailSender.sendEmail(String.valueOf(n), existingToken.getExpiredOn(), user.getEmail(), "IUMS",
          "One-Time Password for Online Marks Submission ");

      return newToken;
    }
    else {
      return existingToken;
    }
  }

  public TwoFAToken getTokenForValidation(String pUserId, String pState) {
    List<TwoFAToken> tokens = mTwoFATokenManager.getTokens(pUserId, pState);
    if(tokens == null || tokens.size() == 0) {
      return null;
    }
    else {
      return tokens.get(0);
    }
  }

}
