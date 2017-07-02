package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.ThanaBuilder;
import org.ums.domain.model.immutable.common.Thana;
import org.ums.domain.model.mutable.common.MutableThana;
import org.ums.manager.ContentManager;
import org.ums.manager.common.ThanaManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class ThanaResourceHelper extends ResourceHelper<Thana, MutableThana, Integer> {

  @Autowired
  private ThanaManager mThanaManager;

  @Autowired
  private ThanaBuilder mThanaBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Thana, MutableThana, Integer> getContentManager() {
    return mThanaManager;
  }

  @Override
  public ThanaBuilder getBuilder() {
    return mThanaBuilder;
  }

  @Override
  protected String getETag(Thana pReadonly) {
    return pReadonly.getLastModified();
  }

}
