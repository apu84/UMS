package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.mutable.MutableOptionalCourseOffer;
import org.ums.domain.model.readOnly.ExamRoutine;
import org.ums.domain.model.readOnly.OptionalCourseOffer;
import org.ums.manager.ExamRoutineManager;
import org.ums.manager.OptionalCourseOfferManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class OptionalCourseOfferResourceHelper extends ResourceHelper<OptionalCourseOffer, MutableOptionalCourseOffer, Object> {

  @Autowired
  private OptionalCourseOfferManager mManager;

  @Autowired
  private CourseResourceHelper mCourseManager;

  @Autowired
  private List<Builder<OptionalCourseOffer, MutableOptionalCourseOffer>> mBuilders;

  @Override
  public OptionalCourseOfferManager getContentManager() {
    return mManager;
  }
  @Override
  public List<Builder<OptionalCourseOffer, MutableOptionalCourseOffer>> getBuilders() {
    return mBuilders;
  }
  @Override
  protected Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }
  @Override
  protected String getEtag(OptionalCourseOffer pReadonly) {
    return null;
  }

  public JsonObject getOptionalCourses(final Integer pSemesterId,final Integer pProgramId,final Integer pYear,final Integer pSemester,  final Request pRequest, final UriInfo pUriInfo) throws Exception {
    return mCourseManager.getOptionalCourses(pSemesterId, pProgramId, pYear,pSemester,pRequest, pUriInfo);
  }


}
