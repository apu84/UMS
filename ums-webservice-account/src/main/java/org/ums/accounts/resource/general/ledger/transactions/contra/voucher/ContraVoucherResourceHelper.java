package org.ums.accounts.resource.general.ledger.transactions.contra.voucher;

import org.springframework.stereotype.Component;
import org.ums.accounts.resource.general.ledger.transactions.helper.PaginatedVouchers;
import org.ums.accounts.resource.general.ledger.transactions.AccountTransactionCommonResourceHelper;
import org.ums.accounts.resource.general.ledger.transactions.helper.TransactionResponse;

/**
 * Created by Monjur-E-Morshed on 22-Feb-18.
 */
@Component
public class ContraVoucherResourceHelper extends AccountTransactionCommonResourceHelper {
  final Long CONTRA_VOUCHER = 8L;

  TransactionResponse getContraVoucherNo() throws Exception {
    return getVoucherNo(CONTRA_VOUCHER);
  }

  public PaginatedVouchers getAllContraVouchers(int itemPerPage, int pageNumber, String voucherNo) {
    return getAll(itemPerPage, pageNumber, voucherNo, CONTRA_VOUCHER);
  }
}
