package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.manager.common.LmsAppStatusManager;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public class LmsAppStatusDaoDecorator extends
    ContentDaoDecorator<LmsAppStatus, MutableLmsAppStatus, Integer, LmsAppStatusManager> implements LmsAppStatusManager {

}
