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

  public JWTBuilder(String pSigningKey, int pAccessTokenExpiration, int pRefreshTokenExpiration,
      int pPasswordResetTokenExpiration) {
    mTokenSigningKey = pSigningKey;
    mAccessTokenExpiration = pAccessTokenExpiration;
    mRefreshTokenExpiration = pRefreshTokenExpiration;
    mPasswordResetTokenExpiration = pPasswordResetTokenExpiration;
  }

  @Override
  public Token accessToken() {
    Calendar accessTokenExpiration = Calendar.getInstance();
    accessTokenExpiration.add(Calendar.MINUTE, mAccessTokenExpiration);
    return new TokenImpl(Jwts.builder().setSubject(SecurityUtils.getSubject().getPrincipal().toString())
        .setExpiration(accessTokenExpiration.getTime()).signWith(SignatureAlgorithm.HS256, mTokenSigningKey).compact(),
        (mAccessTokenExpiration * 60) - 60);
  }

  @Override
  public Token refreshToken() {
    Calendar refreshTokenExpiration = Calendar.getInstance();
    refreshTokenExpiration.add(Calendar.HOUR, mRefreshTokenExpiration);
    return new TokenImpl(
        Jwts.builder().setSubject(SecurityUtils.getSubject().getPrincipal().toString())
            .setExpiration(refreshTokenExpiration.getTime()).signWith(SignatureAlgorithm.HS256, mTokenSigningKey)
            .compact(), mRefreshTokenExpiration * 60 * 60);
  }

  @Override
  public Token passwordResetToken(String pUser) {
    Calendar refreshTokenExpiration = Calendar.getInstance();
    refreshTokenExpiration.add(Calendar.HOUR, mPasswordResetTokenExpiration);
    return new TokenImpl(Jwts.builder().setSubject(pUser).setExpiration(refreshTokenExpiration.getTime())
        .signWith(SignatureAlgorithm.HS256, mTokenSigningKey).compact(), mPasswordResetTokenExpiration * 60 * 60);
  }
}
