package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.DivisionBuilder;
import org.ums.domain.model.immutable.common.Division;
import org.ums.domain.model.mutable.common.MutableDivision;
import org.ums.manager.ContentManager;
import org.ums.manager.common.DivisionManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class DivisionResourceHelper extends ResourceHelper<Division, MutableDivision, String> {

  @Autowired
  private DivisionManager mDivisionManager;

  @Autowired
  private DivisionBuilder mDivisionBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Division, MutableDivision, String> getContentManager() {
    return mDivisionManager;
  }

  @Override
  protected Builder<Division, MutableDivision> getBuilder() {
    return mDivisionBuilder;
  }

  @Override
  protected String getETag(Division pReadonly) {
    return pReadonly.getLastModified();
  }
}
