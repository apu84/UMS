package org.ums.accounts.resource.definitions.general.ledger.transactions.journal.voucher;

import org.springframework.stereotype.Component;
import org.ums.accounts.resource.definitions.general.ledger.transactions.AccountTransactionCommonResourceHelper;
import org.ums.accounts.resource.definitions.general.ledger.transactions.helper.TransactionResponse;
import org.ums.domain.model.immutable.accounts.Account;
import org.ums.enums.accounts.definitions.group.GroupFlag;

import java.util.List;

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
