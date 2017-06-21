package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.AreaOfInterestBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.AreaOfInterest;
import org.ums.domain.model.mutable.MutableAreaOfInterest;
import org.ums.manager.AreaOfInterestManager;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class AreaOfInterestResourceHelper extends ResourceHelper<AreaOfInterest, MutableAreaOfInterest, Integer> {

  @Autowired
  AreaOfInterestManager mManager;

  @Autowired
  AreaOfInterestBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<AreaOfInterest, MutableAreaOfInterest, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<AreaOfInterest, MutableAreaOfInterest> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AreaOfInterest pReadonly) {
    return "";
  }
}
