package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.Course;
import org.ums.domain.model.MutableCourse;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class CourseResourceHelper extends ResourceHelper<Course, MutableCourse, String> {
  @Autowired
  @Qualifier("courseManager")
  private ContentManager<Course, MutableCourse, String> mManager;

  @Autowired
  private List<Builder<Course, MutableCourse>> mBuilders;

  @Override
  protected Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<Course, MutableCourse, String> getContentManager() {
    return mManager;
  }

  @Override
  protected List<Builder<Course, MutableCourse>> getBuilders() {
    return mBuilders;
  }

  @Override
  protected String getEtag(Course pReadonly) {
    return pReadonly.getLastModified();
  }
}
