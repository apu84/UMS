package org.ums.fee.accounts;

import java.util.Date;

public class PaymentStatusFilterImpl implements PaymentStatusFilter {
  private Date mReceivedStart;
  private Date mReceivedEnd;
  private String mTransactionId;
  private String mAccount;
  private PaymentStatus.PaymentMethod mPaymentMethod;
  private String mPaymentCompleted;

  public Date getReceivedStart() {
    return mReceivedStart;
  }

  public void setReceivedStart(Date pReceivedStart) {
    mReceivedStart = pReceivedStart;
  }

  public Date getReceivedEnd() {
    return mReceivedEnd;
  }

  public void setReceivedEnd(Date pReceivedEnd) {
    mReceivedEnd = pReceivedEnd;
  }

  public String getTransactionId() {
    return mTransactionId;
  }

  public void setTransactionId(String pTransactionId) {
    mTransactionId = pTransactionId;
  }

  public String getAccount() {
    return mAccount;
  }

  public void setAccount(String pAccount) {
    mAccount = pAccount;
  }

  public PaymentStatus.PaymentMethod getPaymentMethod() {
    return mPaymentMethod;
  }

  public void setPaymentMethod(PaymentStatus.PaymentMethod pPaymentMethod) {
    mPaymentMethod = pPaymentMethod;
  }

  public String isPaymentCompleted() {
    return mPaymentCompleted;
  }

  public void setPaymentCompleted(String pPaymentCompleted) {
    mPaymentCompleted = pPaymentCompleted;
  }
}
