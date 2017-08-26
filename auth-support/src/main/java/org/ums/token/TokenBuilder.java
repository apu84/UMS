package org.ums.token;

public interface TokenBuilder {
  Token accessToken();

  Token refreshToken();

  Token passwordResetToken(String pUser);
}
