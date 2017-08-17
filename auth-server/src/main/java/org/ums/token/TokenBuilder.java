package org.ums.token;

public interface TokenBuilder {
  String accessToken();

  String refreshToken();

  String passwordResetToken(String pUser);
}
