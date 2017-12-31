package org.ums.twofa;

public interface TwoFATokenGenerator {
  TwoFAToken generateToken(String pUserId);

  boolean validateToken(String pUserId, String pState, String pToken);
}
