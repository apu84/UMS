package org.ums.punishment.offence;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class OffenceResourceHelper extends ResourceHelper<Offence, MutableOffence, Long> {

  @Autowired
  private OffenceManager mManager;

  @Autowired
  private OffenceBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Offence, MutableOffence, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Offence, MutableOffence> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Offence pReadonly) {
    return pReadonly.getLastModified();
  }
}
