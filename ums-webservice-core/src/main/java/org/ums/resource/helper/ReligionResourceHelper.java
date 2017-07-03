package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.ReligionBuilder;
import org.ums.domain.model.immutable.common.Religion;
import org.ums.domain.model.mutable.common.MutableReligion;
import org.ums.manager.ContentManager;
import org.ums.manager.common.ReligionManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class ReligionResourceHelper extends ResourceHelper<Religion, MutableReligion, Integer> {

  @Autowired
  private ReligionManager mManager;

  @Autowired
  private ReligionBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Religion, MutableReligion, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Religion, MutableReligion> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Religion pReadonly) {
    return pReadonly.getLastModified();
  }
}
