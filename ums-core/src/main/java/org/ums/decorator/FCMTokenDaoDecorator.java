package org.ums.decorator;

import org.ums.domain.model.immutable.FCMToken;
import org.ums.domain.model.mutable.MutableFCMToken;
import org.ums.manager.FCMTokenManager;

public class FCMTokenDaoDecorator extends ContentDaoDecorator<FCMToken, MutableFCMToken, String, FCMTokenManager>
    implements FCMTokenManager {
    @Override
    public String hasDuplicate(String pFCMToken) {
        return getManager().hasDuplicate(pFCMToken);
    }
}
