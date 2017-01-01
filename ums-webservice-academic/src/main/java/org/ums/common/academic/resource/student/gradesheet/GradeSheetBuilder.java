package org.ums.common.academic.resource.student.gradesheet;

import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.util.UmsUtils;

@Component
public class GradeSheetBuilder implements
    Builder<UGRegistrationResult, MutableUGRegistrationResult> {

  private final Map<String, Double> GPA_MAP = UmsUtils.getGPAMap();

  @Override
  public void build(JsonObjectBuilder pBuilder, UGRegistrationResult pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    Course course = (Course) pLocalCache.cache(() -> pReadOnly.getCourse(), pReadOnly.getCourseId(),
        Course.class);
    pBuilder.add("courseNo", course.getNo());
    pBuilder.add("courseTitle", course.getTitle());
    pBuilder.add("courseId", course.getId());
    pBuilder.add("crhr", course.getCrHr());
    pBuilder.add("grade", pReadOnly.getGradeLetter());
    double totalGPA = GPA_MAP.get(pReadOnly.getGradeLetter()) * course.getCrHr();
    pBuilder.add("gradePoint", totalGPA);
    pBuilder.add("regType", pReadOnly.getType().getId());
    pBuilder.add("semesterId", String.valueOf(pReadOnly.getSemesterId()));
  }

  @Override
  public void build(MutableUGRegistrationResult pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache) {

  }
}
