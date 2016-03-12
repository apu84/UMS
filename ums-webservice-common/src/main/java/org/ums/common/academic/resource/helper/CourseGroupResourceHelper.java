package org.ums.common.academic.resource.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.builder.CourseGroupBuilder;
import org.ums.domain.model.mutable.MutableCourseGroup;
import org.ums.domain.model.immutable.CourseGroup;
import org.ums.manager.CourseGroupManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class CourseGroupResourceHelper extends ResourceHelper<CourseGroup, MutableCourseGroup, Integer> {
  @Autowired
  @Qualifier("courseGroupManager")
  private CourseGroupManager mManager;

  @Autowired
  private CourseGroupBuilder mBuilder;

  @Override
  protected Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected CourseGroupManager getContentManager() {
    return mManager;
  }

  @Override
  protected CourseGroupBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(CourseGroup pReadonly) {
    return pReadonly.getLastModified();
  }
}
