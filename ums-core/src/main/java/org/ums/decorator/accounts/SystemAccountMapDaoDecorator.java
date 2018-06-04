package org.ums.decorator.accounts;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.accounts.SystemAccountMap;
import org.ums.domain.model.mutable.accounts.MutableSystemAccountMap;
import org.ums.manager.accounts.SystemAccountMapManager;

/**
 * Created by Monjur-E-Morshed on 04-Jun-18.
 */
public class SystemAccountMapDaoDecorator extends
    ContentDaoDecorator<SystemAccountMap, MutableSystemAccountMap, Long, SystemAccountMapManager> implements
    SystemAccountMapManager {
}
