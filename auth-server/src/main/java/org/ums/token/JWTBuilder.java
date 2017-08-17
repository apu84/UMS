package org.ums.token;

import java.util.Calendar;

import org.apache.shiro.SecurityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTBuilder implements TokenBuilder {
  private String mTokenSigningKey;
  private int mAccessTokenExpiration;
  private int mRefreshTokenExpiration;
  private int mPasswordResetTokenExpiration;

  public JWTBuilder(String pSigningKey, int pAccessTokenExpiration, int pRefreshTokenExpiration, int pPasswordResetTokenExpiration) {
    mTokenSigningKey = pSigningKey;
    mAccessTokenExpiration = pAccessTokenExpiration;
    mRefreshTokenExpiration = pRefreshTokenExpiration;
    mPasswordResetTokenExpiration = pPasswordResetTokenExpiration;
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

  @Override
  public String passwordResetToken() {
    Calendar refreshTokenExpiration = Calendar.getInstance();
    refreshTokenExpiration.add(Calendar.HOUR, mPasswordResetTokenExpiration);
    return Jwts.builder().setSubject(SecurityUtils.getSubject().getPrincipal().toString())
        .setExpiration(refreshTokenExpiration.getTime()).signWith(SignatureAlgorithm.HS256, mTokenSigningKey).compact();
  }
}
