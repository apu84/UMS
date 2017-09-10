package org.ums.decorator;

import java.util.List;

import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;

public class BearerAccessTokenDaoDecorator extends
    ContentDaoDecorator<BearerAccessToken, MutableBearerAccessToken, String, BearerAccessTokenManager> implements
    BearerAccessTokenManager {
  @Override
  public List<BearerAccessToken> getByUser(String userId) {
    return getManager().getByUser(userId);
  }

  @Override
  public List<BearerAccessToken> getByAccessToken(String pId) {
    return getManager().getByAccessToken(pId);
  }

  @Override
  public List<BearerAccessToken> getByRefreshToken(String pRefreshToken) {
    return getManager().getByRefreshToken(pRefreshToken);
  }
}
