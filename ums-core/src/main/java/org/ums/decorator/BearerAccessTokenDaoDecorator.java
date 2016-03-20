package org.ums.decorator;

import org.ums.cache.ContentDaoDecorator;
import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;


public class BearerAccessTokenDaoDecorator
    extends ContentDaoDecorator<BearerAccessToken, MutableBearerAccessToken, String, BearerAccessTokenManager>
    implements BearerAccessTokenManager {
    @Override
    public int deleteToken(String pToken) {
        return getManager().deleteToken(pToken);
    }

    @Override
    public int invalidateTokens(String pUserId) {
        return getManager().invalidateTokens(pUserId);
    }
}
