package org.ums.accounts.resource.general.ledger.transactions.contra.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.accounts.AccountTransaction;

import javax.json.JsonArray;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 22-Feb-18.
 */
public class MutableContraVoucher {
  @Autowired
  protected ContraVoucherResourceHelper mHelper;

  @POST
  @Path("/save")
  public List<AccountTransaction> save(JsonArray pJsonArray) throws Exception {
    return mHelper.save(pJsonArray);
  }

  @POST
  @Path("/post")
  public List<AccountTransaction> post(JsonArray pJsonArray) throws Exception {
    return mHelper.postTransactions(pJsonArray);
  }
}
