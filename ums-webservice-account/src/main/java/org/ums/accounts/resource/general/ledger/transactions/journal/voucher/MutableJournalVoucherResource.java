package org.ums.accounts.resource.general.ledger.transactions.journal.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.ums.domain.model.dto.notification.NotificationMessage;
import org.ums.domain.model.immutable.accounts.AccountTransaction;
import org.ums.logs.UmsLogMessage;
import org.ums.manager.accounts.AccountTransactionManager;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
public class MutableJournalVoucherResource {
  @Autowired
  protected JournalVoucherResourceHelper mJournalVoucherResourceHelper;
  @Autowired
  protected AccountTransactionManager mAccountTransactionManager;

  @POST
  @Path("/save")
  public List<AccountTransaction> save(JsonArray pJsonArray) throws Exception {
    send(new NotificationMessage("Journal Voucher", "Data is saved"));
    return mJournalVoucherResourceHelper.save(pJsonArray);
  }

  @MessageMapping("/chat")
  @SendTo("/topic/messages")
  public NotificationMessage send(NotificationMessage pNotificationMessage) throws Exception {
    System.out.println("Chat message");
    return pNotificationMessage;
  }

  @PUT
  @Path("/delete")
  public Response delete(final String pTransactionId) {
    AccountTransaction pMutableAccountTransaction = mAccountTransactionManager.get(Long.parseLong(pTransactionId));
    return mJournalVoucherResourceHelper.delete(pMutableAccountTransaction);
  }

  @POST
  @Path("/post")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @UmsLogMessage(message = "Posting")
  public List<AccountTransaction> post(@Context HttpServletRequest pHttpServletRequest, JsonArray pJsonArray)
      throws Exception {
    return mJournalVoucherResourceHelper.postTransactions(pJsonArray);
  }

}
