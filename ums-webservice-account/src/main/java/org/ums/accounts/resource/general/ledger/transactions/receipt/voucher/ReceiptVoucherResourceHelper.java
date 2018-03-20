package org.ums.accounts.resource.general.ledger.transactions.receipt.voucher;

import org.springframework.stereotype.Component;
import org.ums.accounts.resource.general.ledger.transactions.AccountTransactionCommonResourceHelper;
import org.ums.accounts.resource.general.ledger.transactions.helper.PaginatedVouchers;
import org.ums.accounts.resource.general.ledger.transactions.helper.TransactionResponse;

/**
 * Created by Monjur-E-Morshed on 03-Mar-18.
 */
@Component
public class ReceiptVoucherResourceHelper extends AccountTransactionCommonResourceHelper {
  final Long RECEIPT_VOUCHER = 7L;

  TransactionResponse getReceiptVoucherNo() throws Exception {
    return getVoucherNo(RECEIPT_VOUCHER);
  }

  public PaginatedVouchers getAllReceiptVouchers(int itemPerPage, int pageNumber, String voucherNo) {
    return getAll(itemPerPage, pageNumber, voucherNo, RECEIPT_VOUCHER);
  }
}
