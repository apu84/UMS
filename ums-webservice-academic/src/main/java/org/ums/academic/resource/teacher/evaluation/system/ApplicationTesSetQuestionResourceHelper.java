package org.ums.academic.resource.teacher.evaluation.system;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.ApplicationTesSetQuestions;
import org.ums.domain.model.mutable.MutableApplicationTesSetQuestions;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 4/26/2018.
 */
@Component
public class ApplicationTesSetQuestionResourceHelper extends
    ResourceHelper<ApplicationTesSetQuestions, MutableApplicationTesSetQuestions, Long> {
  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<ApplicationTesSetQuestions, MutableApplicationTesSetQuestions, Long> getContentManager() {
    return null;
  }

  @Override
  protected Builder<ApplicationTesSetQuestions, MutableApplicationTesSetQuestions> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(ApplicationTesSetQuestions pReadonly) {
    return null;
  }
}
