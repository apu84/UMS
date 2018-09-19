package org.ums.academic.resource.optCourse.optCourseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptOfferedSubGroupCourseMapBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.optCourse.OptOfferedSubGroupCourseMap;
import org.ums.domain.model.mutable.optCourse.MutableOptOfferedSubGroupCourseMap;
import org.ums.manager.ContentManager;
import org.ums.manager.optCourse.OptOfferedSubGroupCourseMapManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
@Component
public class OptOfferedSubGroupCourseMapResourceHelper extends
    ResourceHelper<OptOfferedSubGroupCourseMap, MutableOptOfferedSubGroupCourseMap, Long> {
  @Autowired
  OptOfferedSubGroupCourseMapBuilder mBuilder;
  @Autowired
  OptOfferedSubGroupCourseMapManager mManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<OptOfferedSubGroupCourseMap, MutableOptOfferedSubGroupCourseMap, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<OptOfferedSubGroupCourseMap, MutableOptOfferedSubGroupCourseMap> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(OptOfferedSubGroupCourseMap pReadonly) {
    return null;
  }
}
