package org.ums.decorator.optCourse;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptOfferedSubGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedSubGroupCourseMap;
import org.ums.manager.optCourse.OptOfferedSubGroupCourseMapManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class OptOfferedSubGroupCourseMapDaoDecorator
    extends
    ContentDaoDecorator<OptOfferedSubGroupCourseMap, MutableOptOfferedSubGroupCourseMap, Long, OptOfferedSubGroupCourseMapManager>
    implements OptOfferedSubGroupCourseMapManager {

  @Override
  public List<OptOfferedSubGroupCourseMap> getSubGroupCourses() {
    return getManager().getSubGroupCourses();
  }
}
