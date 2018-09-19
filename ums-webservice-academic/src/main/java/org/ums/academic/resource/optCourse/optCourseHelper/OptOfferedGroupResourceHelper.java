package org.ums.academic.resource.optCourse.optCourseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptCourseGroupBuilder;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptOfferedGroupBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroup;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroup;
import org.ums.manager.ContentManager;
import org.ums.manager.optCourse.OptOfferedGroupManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
@Component
public class OptOfferedGroupResourceHelper extends ResourceHelper<OptOfferedGroup, MutableOptOfferedGroup, Long> {
  @Autowired
  OptOfferedGroupManager mOptOfferedGroupManager;

  @Autowired
  OptOfferedGroupBuilder mOptOfferedGroupBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<OptOfferedGroup, MutableOptOfferedGroup, Long> getContentManager() {
    return mOptOfferedGroupManager;
  }

  @Override
  protected Builder<OptOfferedGroup, MutableOptOfferedGroup> getBuilder() {
    return mOptOfferedGroupBuilder;
  }

  @Override
  protected String getETag(OptOfferedGroup pReadonly) {
    return null;
  }
}
