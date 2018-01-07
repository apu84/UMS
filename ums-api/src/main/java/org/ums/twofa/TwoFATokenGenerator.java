package org.ums.twofa;

public interface TwoFATokenGenerator {
  TwoFAToken generateToken(String pUserId, String pType);

  TwoFAToken getTokenForValidation(String pUserId, String pState);
}
