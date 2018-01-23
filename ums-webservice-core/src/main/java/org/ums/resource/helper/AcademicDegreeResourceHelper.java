package org.ums.resource.helper;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.AcademicDegreeBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.common.AcademicDegree;
import org.ums.domain.model.mutable.common.MutableAcademicDegree;
import org.ums.manager.ContentManager;
import org.ums.manager.common.AcademicDegreeManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class AcademicDegreeResourceHelper extends ResourceHelper<AcademicDegree, MutableAcademicDegree, Integer> {

  @Autowired
  private AcademicDegreeManager mManager;

  @Autowired
  private AcademicDegreeBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<AcademicDegree, MutableAcademicDegree, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<AcademicDegree, MutableAcademicDegree> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AcademicDegree pReadonly) {
    return pReadonly.getLastModified();
  }
}
