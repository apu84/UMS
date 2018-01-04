package org.ums.accounts.resource.definitions.period.close;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.PeriodClose;
import org.ums.domain.model.mutable.accounts.MutablePeriodClose;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 04-Jan-18.
 */
@Component
public class PeriodCloseBuilder implements Builder<PeriodClose, MutablePeriodClose> {
  @Override
  public void build(JsonObjectBuilder pBuilder, PeriodClose pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutablePeriodClose pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
