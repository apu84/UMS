package org.ums.common.academic.resource.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.readOnly.CourseGroup;
import org.ums.domain.model.mutable.MutableCourseGroup;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class CourseGroupResourceHelper extends ResourceHelper<CourseGroup, MutableCourseGroup, Integer> {
  @Autowired
  @Qualifier("courseGroupManager")
  private ContentManager<CourseGroup, MutableCourseGroup, Integer> mManager;

  @Autowired
  private List<Builder<CourseGroup, MutableCourseGroup>> mBuilders;

  @Override
  protected Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<CourseGroup, MutableCourseGroup, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected List<Builder<CourseGroup, MutableCourseGroup>> getBuilders() {
    return mBuilders;
  }

  @Override
  protected String getEtag(CourseGroup pReadonly) {
    return pReadonly.getLastModified();
  }
}
