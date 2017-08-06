package org.ums.security;

import java.util.Calendar;

import org.apache.shiro.SecurityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTBuilder implements TokenBuilder {
  private String mTokenSigningKey;
  private int mAccessTokenExpiration;
  private int mRefreshTokenExpiration;

  public JWTBuilder(String pSigningKey, int pAccessTokenExpiration, int pRefreshTokenExpiration) {
    mTokenSigningKey = pSigningKey;
    mAccessTokenExpiration = pAccessTokenExpiration;
    mRefreshTokenExpiration = pRefreshTokenExpiration;
  }

  @Override
  public String accessToken() {
    Calendar accessTokenExpiration = Calendar.getInstance();
    accessTokenExpiration.add(Calendar.MINUTE, mAccessTokenExpiration);
    return Jwts.builder().setSubject(SecurityUtils.getSubject().getPrincipal().toString())
        .setExpiration(accessTokenExpiration.getTime()).signWith(SignatureAlgorithm.HS256, mTokenSigningKey).compact();
  }

  @Override
  public String refreshToken() {
    Calendar refreshTokenExpiration = Calendar.getInstance();
    refreshTokenExpiration.add(Calendar.HOUR, mRefreshTokenExpiration);
    return Jwts.builder().setSubject(SecurityUtils.getSubject().getPrincipal().toString())
        .setExpiration(refreshTokenExpiration.getTime()).signWith(SignatureAlgorithm.HS256, mTokenSigningKey).compact();
  }
}
