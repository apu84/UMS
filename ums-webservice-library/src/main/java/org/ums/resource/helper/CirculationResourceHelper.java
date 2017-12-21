package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.CirculationBuilder;
import org.ums.domain.model.immutable.library.Circulation;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.manager.ContentManager;
import org.ums.manager.library.CirculationManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class CirculationResourceHelper extends ResourceHelper<Circulation, MutableCirculation, Long> {

  @Autowired
  CirculationManager mManager;

  @Autowired
  CirculationBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<Circulation, MutableCirculation, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Circulation, MutableCirculation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Circulation pReadonly) {
    return pReadonly.getLastModified();
  }
}
