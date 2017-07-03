package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.BloodGroupBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.common.BloodGroup;
import org.ums.domain.model.mutable.common.MutableBloodGroup;
import org.ums.manager.ContentManager;
import org.ums.manager.common.BloodGroupManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class BloodGroupResourceHelper extends ResourceHelper<BloodGroup, MutableBloodGroup, Integer> {

  @Autowired
  private BloodGroupManager mManager;

  @Autowired
  private BloodGroupBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<BloodGroup, MutableBloodGroup, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<BloodGroup, MutableBloodGroup> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(BloodGroup pReadonly) {
    return pReadonly.getLastModified();
  }
}
