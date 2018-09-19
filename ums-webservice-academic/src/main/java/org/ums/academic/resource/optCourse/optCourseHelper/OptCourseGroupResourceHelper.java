package org.ums.academic.resource.optCourse.optCourseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptCourseGroupBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.optCourse.OptCourseGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseGroup;
import org.ums.manager.ContentManager;
import org.ums.manager.optCourse.OptCourseGroupManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
@Component
public class OptCourseGroupResourceHelper extends ResourceHelper<OptCourseGroup, MutableOptCourseGroup, Long> {
  @Autowired
  OptCourseGroupManager mManager;
  @Autowired
  OptCourseGroupBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<OptCourseGroup, MutableOptCourseGroup, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<OptCourseGroup, MutableOptCourseGroup> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(OptCourseGroup pReadonly) {
    return null;
  }
}
