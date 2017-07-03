package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.NationalityBuilder;
import org.ums.domain.model.immutable.common.Nationality;
import org.ums.domain.model.mutable.common.MutableNationality;
import org.ums.manager.ContentManager;
import org.ums.manager.common.NationalityManager;
import org.ums.resource.ResourceHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class NationalityResourceHelper extends ResourceHelper<Nationality, MutableNationality, Integer> {

  @Autowired
  private NationalityManager mManager;

  @Autowired
  private NationalityBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Nationality, MutableNationality, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Nationality, MutableNationality> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Nationality pReadonly) {
    return pReadonly.getLastModified();
  }
}
