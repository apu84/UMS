package org.ums.accounts.resource.general.ledger.transactions.helper;

/**
 * Created by Monjur-E-Morshed on 31-Jan-18.
 */
public class TransactionResponse {
  private String voucherNo;
  private String message;

  public TransactionResponse() {
  }

  public TransactionResponse(String pVoucherNo, String pMessage) {
    voucherNo = pVoucherNo;
    message = pMessage;
  }

  public String getVoucherNo() {
    return voucherNo;
  }

  public void setVoucherNo(String pVoucherNo) {
    voucherNo = pVoucherNo;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String pMessage) {
    message = pMessage;
  }
}
