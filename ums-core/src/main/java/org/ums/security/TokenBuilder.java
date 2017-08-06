package org.ums.security;

public interface TokenBuilder {
  String accessToken();

  String refreshToken();
}
