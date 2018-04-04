package org.ums.punishment;

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
public class PunishmentResourceHelper extends ResourceHelper<Punishment, MutablePunishment, Long> {

  @Autowired
  private PunishmentManager mManager;

  @Autowired
  private PunishmentBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Punishment, MutablePunishment, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Punishment, MutablePunishment> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Punishment pReadonly) {
    return pReadonly.getLastModified();
  }
}
