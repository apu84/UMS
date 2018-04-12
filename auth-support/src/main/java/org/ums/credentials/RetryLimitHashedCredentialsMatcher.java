package org.ums.credentials;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MapCache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryLimitHashedCredentialsMatcher extends PasswordMatcher {
  private static final Logger mLogger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);
  private Map<String, AtomicInteger> passwordRetryCache = new HashMap<>();

  @Override
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    String username = (String) token.getPrincipal();
    AtomicInteger retryCount = passwordRetryCache.get(username);
    if(retryCount == null) {
      retryCount = new AtomicInteger(0);
      passwordRetryCache.put(username, retryCount);
    }
    if(retryCount.incrementAndGet() > 10) {
      mLogger.info("[{}]: Excessive wrong login attempt detected. Total attempt:{}", username, retryCount);
      throw new ExcessiveAttemptsException();
    }

    boolean matches = super.doCredentialsMatch(token, info);
    if(matches) {
      mLogger.info("[{}]: Successfully logged into the system", username);
      passwordRetryCache.remove(username);
    }
    else {
      mLogger.info("[{}]: Login attempt with wrong password", username);
    }
    return matches;
  }

  public void resetRetryLimit(final String pUserId) {
    if(passwordRetryCache.containsKey(pUserId)) {
      passwordRetryCache.put(pUserId, new AtomicInteger(0));
    }
  }
}
