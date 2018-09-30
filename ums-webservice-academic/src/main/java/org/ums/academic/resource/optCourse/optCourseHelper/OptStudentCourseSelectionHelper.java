package org.ums.academic.resource.optCourse.optCourseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptStudentCourseSelectionBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.optCourse.OptStudentCourseSelection;
import org.ums.domain.model.mutable.optCourse.MutableOptStudentCourseSelection;
import org.ums.manager.ContentManager;
import org.ums.manager.optCourse.OptStudentCourseSelectionManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 9/27/2018.
 */
@Component
public class OptStudentCourseSelectionHelper extends
    ResourceHelper<OptStudentCourseSelection, MutableOptStudentCourseSelection, Long> {
  @Autowired
  OptStudentCourseSelectionManager mManager;
  @Autowired
  OptStudentCourseSelectionBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<OptStudentCourseSelection, MutableOptStudentCourseSelection, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<OptStudentCourseSelection, MutableOptStudentCourseSelection> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(OptStudentCourseSelection pReadonly) {
    return null;
  }
}
