package org.ums.decorator;

import org.ums.domain.model.immutable.AbsLateComingInfo;
import org.ums.domain.model.mutable.MutableAbsLateComingInfo;
import org.ums.manager.AbsLateComingInfoManager;

/**
 * Created by Monjur-E-Morshed on 7/1/2018.
 */
public class AbsLateComingInfoDaoDecorator extends
    ContentDaoDecorator<AbsLateComingInfo, MutableAbsLateComingInfo, Long, AbsLateComingInfoManager> implements
    AbsLateComingInfoManager {
}
