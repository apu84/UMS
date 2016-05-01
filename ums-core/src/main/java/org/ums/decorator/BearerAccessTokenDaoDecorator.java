package org.ums.decorator;

import org.ums.domain.model.immutable.BearerAccessToken;
import org.ums.domain.model.mutable.MutableBearerAccessToken;
import org.ums.manager.BearerAccessTokenManager;


public class BearerAccessTokenDaoDecorator
    extends ContentDaoDecorator<BearerAccessToken, MutableBearerAccessToken, String, BearerAccessTokenManager>
    implements BearerAccessTokenManager {
    @Override
    public BearerAccessToken getByUser(String userId) {
        return getManager().getByUser(userId);
    }
}
