package org.ums.accounts.resource.definitions.receipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.accounts.Receipt;
import org.ums.domain.model.mutable.accounts.MutableReceipt;
import org.ums.manager.ContentManager;
import org.ums.manager.accounts.ReceiptManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 04-Mar-18.
 */
@Component
public class ReceiptResourceHelper extends ResourceHelper<Receipt, MutableReceipt, Long> {

  @Autowired
  private ReceiptManager mReceiptManager;

  public List<Receipt> getAllReceipts() {
    return mReceiptManager.getAll();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<Receipt, MutableReceipt, Long> getContentManager() {
    return null;
  }

  @Override
  protected Builder<Receipt, MutableReceipt> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(Receipt pReadonly) {
    return null;
  }
}
