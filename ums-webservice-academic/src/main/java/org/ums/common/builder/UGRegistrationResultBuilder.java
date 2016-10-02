package org.ums.common.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.manager.CourseManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class UGRegistrationResultBuilder implements Builder<UGRegistrationResult, MutableUGRegistrationResult> {

  @Autowired
  CourseManager mCourseManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, UGRegistrationResult pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    pBuilder.add("studentId", pReadOnly.getStudentId());
    pBuilder.add("courseId", pReadOnly.getCourseId());
    pBuilder.add("gradeLetter", pReadOnly.getGradeLetter());
    pBuilder.add("examType", pReadOnly.getExamType().getId());
    pBuilder.add("type", pReadOnly.getType().getId());
    pBuilder.add("courseNo", mCourseManager.get(pReadOnly.getCourseId()).getNo());
    pBuilder.add("courseTitle", mCourseManager.get(pReadOnly.getCourseId()).getTitle());
    pBuilder.add("examDate", pReadOnly.getExamDate());
    if (pReadOnly.getMessage() == null) {
      pBuilder.add("message", "null");

    } else {
      pBuilder.add("message", pReadOnly.getMessage());
    }

  }

  @Override
  public void build(MutableUGRegistrationResult pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {

  }
}
