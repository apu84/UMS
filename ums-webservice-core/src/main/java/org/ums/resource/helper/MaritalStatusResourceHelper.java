package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.MaritalStatusBuilder;
import org.ums.domain.model.immutable.common.MaritalStatus;
import org.ums.domain.model.mutable.common.MutableMaritalStatus;
import org.ums.manager.ContentManager;
import org.ums.manager.common.MaritalStatusManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class MaritalStatusResourceHelper extends ResourceHelper<MaritalStatus, MutableMaritalStatus, Integer> {

  @Autowired
  private MaritalStatusManager mManager;

  @Autowired
  private MaritalStatusBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<MaritalStatus, MutableMaritalStatus, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<MaritalStatus, MutableMaritalStatus> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(MaritalStatus pReadonly) {
    return pReadonly.getLastModified();
  }
}
