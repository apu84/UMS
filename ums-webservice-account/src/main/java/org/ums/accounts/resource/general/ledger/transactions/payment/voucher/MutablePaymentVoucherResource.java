package org.ums.accounts.resource.general.ledger.transactions.payment.voucher;

import org.ums.domain.model.immutable.accounts.AccountTransaction;

import javax.json.JsonArray;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 19-Feb-18.
 */
public class MutablePaymentVoucherResource {
  protected PaymentVoucherResourceHelper mHelper;

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
