package org.ums.decorator.optCourse;

import org.ums.cache.ContentCache;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroup;
import org.ums.manager.optCourse.OptOfferedGroupManager;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class OptOfferedGroupDaoDecorator extends
    ContentDaoDecorator<OptOfferedGroup, MutableOptOfferedGroup, Long, OptOfferedGroupManager> implements
    OptOfferedGroupManager {
}
