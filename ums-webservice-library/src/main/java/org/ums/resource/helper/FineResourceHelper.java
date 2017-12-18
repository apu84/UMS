package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.FineBuilder;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.manager.ContentManager;
import org.ums.manager.library.FineManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class FineResourceHelper extends ResourceHelper<Fine, MutableFine, Long> {

  @Autowired
  FineManager mManager;

  @Autowired
  FineBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<Fine, MutableFine, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Fine, MutableFine> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Fine pReadonly) {
    return pReadonly.getLastModified();
  }
}
