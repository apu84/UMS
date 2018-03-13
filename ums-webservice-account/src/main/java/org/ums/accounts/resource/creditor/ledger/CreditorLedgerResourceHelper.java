package org.ums.accounts.resource.creditor.ledger;

import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.CreditorLedger;
import org.ums.domain.model.mutable.accounts.MutableCreditorLedger;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 13-Mar-18.
 */
public class CreditorLedgerResourceHelper extends ResourceHelper<CreditorLedger, MutableCreditorLedger, Long> {
  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<CreditorLedger, MutableCreditorLedger, Long> getContentManager() {
    return null;
  }

  @Override
  protected Builder<CreditorLedger, MutableCreditorLedger> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(CreditorLedger pReadonly) {
    return null;
  }
}
