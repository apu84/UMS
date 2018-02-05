package org.ums.accounts.resource.definitions.general.ledger.transactions.journal.voucher;

import org.springframework.stereotype.Component;
import org.ums.accounts.resource.definitions.general.ledger.transactions.AccountTransactionCommonResourceHelper;
import org.ums.accounts.resource.definitions.general.ledger.transactions.helper.TransactionResponse;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
@Component
public class JournalVoucherResourceHelper extends AccountTransactionCommonResourceHelper {
  Long JOURNAL_VOUCHER = 1L;

  TransactionResponse getJournalVoucherNo() throws Exception {
    return getVoucherNo(JOURNAL_VOUCHER);
  }
}
