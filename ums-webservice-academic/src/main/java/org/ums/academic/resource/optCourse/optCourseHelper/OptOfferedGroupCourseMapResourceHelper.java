package org.ums.academic.resource.optCourse.optCourseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptOfferedGroupCourseMapBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupCourseMap;
import org.ums.manager.ContentManager;
import org.ums.manager.optCourse.OptOfferedGroupCourseMapManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
@Component
public class OptOfferedGroupCourseMapResourceHelper extends
    ResourceHelper<OptOfferedGroupCourseMap, MutableOptOfferedGroupCourseMap, Long> {
  @Autowired
  OptOfferedGroupCourseMapManager mManager;
  @Autowired
  OptOfferedGroupCourseMapBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<OptOfferedGroupCourseMap, MutableOptOfferedGroupCourseMap, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<OptOfferedGroupCourseMap, MutableOptOfferedGroupCourseMap> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(OptOfferedGroupCourseMap pReadonly) {
    return null;
  }
}
