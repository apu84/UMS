package org.ums.manager.common;

import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.manager.ContentManager;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public interface LmsAppStatusManager extends ContentManager<LmsAppStatus, MutableLmsAppStatus, Integer> {
}
