package org.ums.manager;

import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;

import java.util.List;

public interface BearerAccessTokenManager extends ContentManager<BearerAccessToken, MutableBearerAccessToken, String> {
  List<BearerAccessToken> getByUser(final String userId);

  List<BearerAccessToken> getByAccessToken(final String pAcessToken);

  List<BearerAccessToken> getByRefreshToken(String pRefreshToken);
}
