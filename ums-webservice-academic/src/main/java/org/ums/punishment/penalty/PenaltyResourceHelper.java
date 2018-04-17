package org.ums.punishment.penalty;

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
public class PenaltyResourceHelper extends ResourceHelper<Penalty, MutablePenalty, Long> {

  @Autowired
  private PenaltyManager mManager;

  @Autowired
  private PenaltyBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Penalty, MutablePenalty, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Penalty, MutablePenalty> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Penalty pReadonly) {
    return pReadonly.getLastModified();
  }
}
