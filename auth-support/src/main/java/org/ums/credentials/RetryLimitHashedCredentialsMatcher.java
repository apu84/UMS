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

public class RetryLimitHashedCredentialsMatcher extends PasswordMatcher {

  private Map<String, AtomicInteger> passwordRetryCache = new HashMap<>();

  @Override
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    String username = (String) token.getPrincipal();
    // retry count + 1
    AtomicInteger retryCount = passwordRetryCache.get(username);
    if(retryCount == null) {
      retryCount = new AtomicInteger(0);
      passwordRetryCache.put(username, retryCount);
    }
    if(retryCount.incrementAndGet() > 5) {
      // if retry count > 5 throw
      throw new ExcessiveAttemptsException();
    }

    boolean matches = super.doCredentialsMatch(token, info);
    if(matches) {
      // clear retry count
      passwordRetryCache.remove(username);
    }
    return matches;
  }
}
