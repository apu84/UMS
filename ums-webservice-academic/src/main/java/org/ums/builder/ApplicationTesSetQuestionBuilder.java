package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationTesSetQuestions;
import org.ums.domain.model.mutable.MutableApplicationTesSetQuestions;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
@Component
public class ApplicationTesSetQuestionBuilder implements
    Builder<ApplicationTesSetQuestions, MutableApplicationTesSetQuestions> {
  @Override
  public void build(JsonObjectBuilder pBuilder, ApplicationTesSetQuestions pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    if(pReadOnly.getApplicationDate() != null)
      pBuilder.add("applicationDate", pReadOnly.getApplicationDate());

    if(pReadOnly.getQuestionId() != null)
      pBuilder.add("questionId", (pReadOnly.getQuestionId().toString()));

    if(pReadOnly.getSemesterId() != null)
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
  }

  @Override
  public void build(MutableApplicationTesSetQuestions pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    if(pJsonObject.containsKey("questionId"))
      pMutable.setQuestionId(Long.parseLong(pJsonObject.getString("questionId")));

    if(pJsonObject.containsKey("semesterId"))
      pMutable.setSemesterId(pJsonObject.getInt("semesterId"));

  }
}
