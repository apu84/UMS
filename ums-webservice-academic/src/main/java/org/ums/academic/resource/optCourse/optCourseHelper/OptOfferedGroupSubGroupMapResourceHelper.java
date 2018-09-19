package org.ums.academic.resource.optCourse.optCourseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptOfferedGroupSubGroupMapBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.optCourse.OptOfferedGroupSubGroupMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedGroupSubGroupMap;
import org.ums.manager.ContentManager;
import org.ums.manager.optCourse.OptOfferedGroupSubGroupMapManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 9/18/2018.
 */
@Component
public class OptOfferedGroupSubGroupMapResourceHelper extends
    ResourceHelper<OptOfferedGroupSubGroupMap, MutableOptOfferedGroupSubGroupMap, Long> {
  @Autowired
  OptOfferedGroupSubGroupMapManager mManager;
  @Autowired
  OptOfferedGroupSubGroupMapBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<OptOfferedGroupSubGroupMap, MutableOptOfferedGroupSubGroupMap, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<OptOfferedGroupSubGroupMap, MutableOptOfferedGroupSubGroupMap> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(OptOfferedGroupSubGroupMap pReadonly) {
    return null;
  }
}
