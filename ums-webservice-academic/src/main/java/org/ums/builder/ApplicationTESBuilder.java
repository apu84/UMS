package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.mutable.MutableApplicationTES;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Rumi on 2/20/2018.
 */
@Component
public class ApplicationTESBuilder implements Builder<ApplicationTES, MutableApplicationTES> {
  @Override
  public void build(JsonObjectBuilder pBuilder, ApplicationTES pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if(pReadOnly.getApplicationDate() != null)
      pBuilder.add("applicationDate", pReadOnly.getApplicationDate());

    if(pReadOnly.getQuestionId() != null)
      pBuilder.add("questionId", pReadOnly.getQuestionId());

    if(pReadOnly.getQuestionDetails() != null)
      pBuilder.add("questionDetails", pReadOnly.getQuestionDetails());

    if(pReadOnly.getReviewEligibleCourses() != null)
      pBuilder.add("courseName", pReadOnly.getReviewEligibleCourses());

    if(pReadOnly.getSemesterName() != null)
      pBuilder.add("semesterName", pReadOnly.getSemesterName());

    if(pReadOnly.getCourseTitle() != null)
      pBuilder.add("courseTitle", pReadOnly.getCourseTitle());

    if(pReadOnly.getCourseNo() != null)
      pBuilder.add("courseNo", pReadOnly.getCourseNo());
  }

  @Override
  public void build(MutableApplicationTES pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
