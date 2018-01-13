package org.ums.accounts.resource.definitions.predefined.narration;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.accounts.PredefinedNarration;
import org.ums.domain.model.mutable.accounts.MutablePredefinedNarration;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 13-Jan-18.
 */
@Component
public class PredefinedNarrationBuilder implements Builder<PredefinedNarration, MutablePredefinedNarration> {
  @Override
  public void build(JsonObjectBuilder pBuilder, PredefinedNarration pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

  }

  @Override
  public void build(MutablePredefinedNarration pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }

  public void build(MutablePredefinedNarration pMutablePredefinedNarration, JsonObject pJsonObject) {
    if (pJsonObject.containsKey("id"))
      pMutablePredefinedNarration.setId(Long.parseLong(pJsonObject.getString("id")));
    if (pJsonObject.containsKey("voucherId"))
      pMutablePredefinedNarration.setVoucherId(Long.parseLong(pJsonObject.getString("voucherId")));
    if (pJsonObject.containsKey("narration"))
      pMutablePredefinedNarration.setNarration(pJsonObject.getString("narration"));

  }
}
