package org.ums.decorator.optCourse;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupSubGroupMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;
import org.ums.manager.optCourse.OptOfferedGroupSubGroupMapManager;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
public class OptOfferedGroupSubGroupMapDaoDecorator
    extends
    ContentDaoDecorator<OptOfferedGroupSubGroupMap, MutableOptOfferedGroupSubGroupMap, Long, OptOfferedGroupSubGroupMapManager>
    implements OptOfferedGroupSubGroupMapManager {

}
