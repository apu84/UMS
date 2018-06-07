package org.ums.academic.resource.teacher.evaluation.system;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.ApplicationTesSelectedCourses;
import org.ums.domain.model.mutable.MutableApplicationTesSelectedCourses;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
@Component
public class ApplicationTesSelectedCourseResourceHelper extends
    ResourceHelper<ApplicationTesSelectedCourses, MutableApplicationTesSelectedCourses, Long> {
  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<ApplicationTesSelectedCourses, MutableApplicationTesSelectedCourses, Long> getContentManager() {
    return null;
  }

  @Override
  protected Builder<ApplicationTesSelectedCourses, MutableApplicationTesSelectedCourses> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(ApplicationTesSelectedCourses pReadonly) {
    return null;
  }
}
