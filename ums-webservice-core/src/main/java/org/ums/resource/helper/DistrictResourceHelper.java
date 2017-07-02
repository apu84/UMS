package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.DistrictBuilder;
import org.ums.domain.model.immutable.common.District;
import org.ums.domain.model.mutable.common.MutableDistrict;
import org.ums.manager.ContentManager;
import org.ums.manager.common.DistrictManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class DistrictResourceHelper extends ResourceHelper<District, MutableDistrict, Integer> {

  @Autowired
  private DistrictManager mDistrictManager;

  @Autowired
  private DistrictBuilder mDistrictBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<District, MutableDistrict, Integer> getContentManager() {
    return mDistrictManager;
  }

  @Override
  public DistrictBuilder getBuilder() {
    return mDistrictBuilder;
  }

  @Override
  protected String getETag(District pReadonly) {
    return pReadonly.getLastModified();
  }

}
