package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.CheckInBuilder;
import org.ums.domain.model.immutable.library.CheckIn;
import org.ums.domain.model.mutable.library.MutableCheckIn;
import org.ums.manager.ContentManager;
import org.ums.manager.library.CheckInManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class CheckInResourceHelper extends ResourceHelper<CheckIn, MutableCheckIn, Long> {

  @Autowired
  CheckInManager mManager;

  @Autowired
  CheckInBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<CheckIn, MutableCheckIn, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<CheckIn, MutableCheckIn> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(CheckIn pReadonly) {
    return pReadonly.getLastModified();
  }
}
