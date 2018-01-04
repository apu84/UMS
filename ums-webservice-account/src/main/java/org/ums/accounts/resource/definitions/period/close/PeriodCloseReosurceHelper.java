package org.ums.accounts.resource.definitions.period.close;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.PeriodClose;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
@Component
public class PeriodCloseReosurceHelper extends ResourceHelper<PeriodClose, MutablePeriodClose, Long> {

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<PeriodClose, MutablePeriodClose, Long> getContentManager() {
    return null;
  }

  @Override
  protected Builder<PeriodClose, MutablePeriodClose> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(PeriodClose pReadonly) {
    return null;
  }
}
