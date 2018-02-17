package org.ums.token;

public interface TokenBuilder {
  Token accessToken();

  Token accessToken(String pUserId, long pExpiration);

  Token refreshToken();

  Token refreshToken(String pUserId, long pExpiration);

  Token passwordResetToken(String pUser);

  boolean isValidToken(String pToken);

  String getUserName(String pToken);
}
