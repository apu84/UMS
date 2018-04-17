package org.ums.token;

import java.util.Calendar;

import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.*;

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
  public Token accessToken(String pUserId, long pExpiration) {
    Calendar accessTokenExpiration = Calendar.getInstance();
    accessTokenExpiration.add(Calendar.MINUTE, (int) pExpiration);
    return new TokenImpl(Jwts.builder().setSubject(pUserId).setExpiration(accessTokenExpiration.getTime())
        .signWith(SignatureAlgorithm.HS256, mTokenSigningKey).compact(), (pExpiration * 60) - 60);
  }

  @Override
  public Token refreshToken(String pUserId, long pExpiration) {
    Calendar refreshTokenExpiration = Calendar.getInstance();
    refreshTokenExpiration.add(Calendar.HOUR, (int) pExpiration);
    return new TokenImpl(Jwts.builder().setSubject(pUserId).setExpiration(refreshTokenExpiration.getTime())
        .signWith(SignatureAlgorithm.HS256, mTokenSigningKey).compact(), pExpiration * 60 * 60);
  }

  @Override
  public Token accessToken() {
    return accessToken(SecurityUtils.getSubject().getPrincipal().toString(), mAccessTokenExpiration);
  }

  @Override
  public Token refreshToken() {
    return refreshToken(SecurityUtils.getSubject().getPrincipal().toString(), mRefreshTokenExpiration);
  }

  @Override
  public Token passwordResetToken(String pUser) {
    Calendar refreshTokenExpiration = Calendar.getInstance();
    refreshTokenExpiration.add(Calendar.HOUR, mPasswordResetTokenExpiration);
    return new TokenImpl(Jwts.builder().setSubject(pUser).setExpiration(refreshTokenExpiration.getTime())
        .signWith(SignatureAlgorithm.HS256, mTokenSigningKey).compact(), mPasswordResetTokenExpiration * 60 * 60);
  }

  @Override
  public boolean isValidToken(String pToken) {
    return !StringUtils.isEmpty(pToken) && !isExpiredToken(pToken);
  }

  public String getUserName(final String jwt) throws ExpiredJwtException {
    Jws<Claims> claims = Jwts.parser().setSigningKey(mTokenSigningKey).parseClaimsJws(jwt);
    return claims.getBody().getSubject();
  }

  private boolean isExpiredToken(final String jwt) {
    boolean isExpired = false;
    try {
      getUserName(jwt);
    } catch(JwtException jwte) {
      isExpired = true;
    }
    return isExpired;
  }
}
