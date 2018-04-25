package org.ums.academic.resource.teacher.evaluation.system;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationTesQuestions;
import org.ums.domain.model.mutable.MutableApplicationTesQuestions;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 4/16/2018.
 */
@Component
public class ApplicationTesQuestionBuilder implements Builder<ApplicationTesQuestions, MutableApplicationTesQuestions> {
  @Override
  public void build(JsonObjectBuilder pBuilder, ApplicationTesQuestions pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    if(pReadOnly.getApplicationDate() != null)
      pBuilder.add("applicationDate", pReadOnly.getApplicationDate());
    if(pReadOnly.getQuestionDetails() != null)
      pBuilder.add("questionDetails", pReadOnly.getQuestionDetails());
    if(pReadOnly.getQuestionId() != null)
      pBuilder.add("questionId", (pReadOnly.getQuestionId().toString()));
    if(pReadOnly.getObservationType() != null)
      pBuilder.add("observationType", pReadOnly.getObservationType());
    if(pReadOnly.getInsertionDate() != null)
      pBuilder.add("insertionDate", pReadOnly.getInsertionDate());
  }

  @Override
  public void build(MutableApplicationTesQuestions pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
