package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.CourseGroupBuilder;
import org.ums.domain.model.immutable.CourseGroup;
import org.ums.domain.model.mutable.MutableCourseGroup;
import org.ums.manager.CourseGroupManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class CourseGroupResourceHelper extends ResourceHelper<CourseGroup, MutableCourseGroup, Integer> {
  @Autowired
  private CourseGroupManager mManager;

  @Autowired
  private CourseGroupBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
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
  protected String getETag(CourseGroup pReadonly) {
    return pReadonly.getLastModified();
  }
}
