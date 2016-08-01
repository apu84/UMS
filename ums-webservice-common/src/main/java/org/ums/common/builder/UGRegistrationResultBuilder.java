package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.domain.model.mutable.MutableUGRegistrationResult;
import org.ums.enums.ExamType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by My Pc on 7/13/2016.
 */

@Component
public class UGRegistrationResultBuilder implements Builder<UGRegistrationResult,MutableUGRegistrationResult> {

  @Override
  public void build(JsonObjectBuilder pBuilder, UGRegistrationResult pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    pBuilder.add("studentId",pReadOnly.getStudentId());
    pBuilder.add("courseId",pReadOnly.getCourseId());
    pBuilder.add("gradeLetter",pReadOnly.getGradeLetter());
    pBuilder.add("examType", pReadOnly.getExamType().getValue());
    pBuilder.add("type",pReadOnly.getType());
    pBuilder.add("courseNo",pReadOnly.getCourseNo());
    pBuilder.add("courseTitle",pReadOnly.getCourseTitle());
    pBuilder.add("examDate",pReadOnly.getExamDate());
    if(pReadOnly.getMessage()==null){
      pBuilder.add("message","null");

    } else{
      pBuilder.add("message",pReadOnly.getMessage());
    }

  }

  @Override
  public void build(MutableUGRegistrationResult pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {

  }
}
