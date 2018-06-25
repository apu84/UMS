package org.ums.academic.resource.teacher.evaluation.system;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.ApplicationTES;
import org.ums.domain.model.immutable.ApplicationTesQuestions;
import org.ums.domain.model.mutable.MutableApplicationTES;
import org.ums.domain.model.mutable.MutableApplicationTesQuestions;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 4/16/2018.
 */
@Component
public class ApplicationTesQuestionResourceHelper extends
    ResourceHelper<ApplicationTesQuestions, MutableApplicationTesQuestions, Long> {

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<ApplicationTesQuestions, MutableApplicationTesQuestions, Long> getContentManager() {
    return null;
  }

  @Override
  protected Builder<ApplicationTesQuestions, MutableApplicationTesQuestions> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(ApplicationTesQuestions pReadonly) {
    return null;
  }
}
