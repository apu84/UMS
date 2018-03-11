package org.ums.accounts.resource.general.ledger.transactions.payment.voucher;

import org.springframework.stereotype.Component;
import org.ums.accounts.resource.general.ledger.transactions.helper.PaginatedVouchers;
import org.ums.accounts.resource.general.ledger.transactions.AccountTransactionCommonResourceHelper;
import org.ums.accounts.resource.general.ledger.transactions.helper.TransactionResponse;

/**
 * Created by Monjur-E-Morshed on 19-Feb-18.
 */
@Component
public class PaymentVoucherResourceHelper extends AccountTransactionCommonResourceHelper {
  final Long BANK_PAYMENT = 6L;

  TransactionResponse getPaymentVoucherNo() throws Exception {
    return getVoucherNo(BANK_PAYMENT);
  }

  public PaginatedVouchers getAllPaymentVouchers(int itemPerPage, int pageNumber, String voucherNo) {
    return getAll(itemPerPage, pageNumber, voucherNo, BANK_PAYMENT);
  }
}
