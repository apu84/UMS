package org.ums.manager;

import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;

public interface BearerAccessTokenManager extends ContentManager<BearerAccessToken, MutableBearerAccessToken, String> {
  int deleteToken(final String pToken);

  int invalidateTokens(final String pUserId);
}
