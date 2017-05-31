package org.ums.fee.accounts;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;

import java.math.BigDecimal;
import java.util.Date;

public class PersistentPaymentStatus implements MutablePaymentStatus {

  private static PaymentStatusManager sPaymentStatusManager;
  private Long mId;
  private String mAccount;
  private String mTransactionId;
  private PaymentMethod mMethodOfPayment;
  private boolean mPaymentComplete;
  private Date mReceivedOn;
  private Date mCompletedOn;
  private String mLastModified;
  private BigDecimal mAmount;
  private String mPaymentDetails;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public String getAccount() {
    return mAccount;
  }

  @Override
  public void setAccount(String pAccount) {
    this.mAccount = pAccount;
  }

  @Override
  public String getTransactionId() {
    return mTransactionId;
  }

  @Override
  public void setTransactionId(String pTransactionId) {
    this.mTransactionId = pTransactionId;
  }

  @Override
  public PaymentMethod getMethodOfPayment() {
    return mMethodOfPayment;
  }

  @Override
  public void setMethodOfPayment(PaymentMethod pMethodOfPayment) {
    this.mMethodOfPayment = pMethodOfPayment;
  }

  @Override
  public boolean isPaymentComplete() {
    return mPaymentComplete;
  }

  @Override
  public void setPaymentComplete(boolean pPaymentComplete) {
    this.mPaymentComplete = pPaymentComplete;
  }

  @Override
  public Date getReceivedOn() {
    return mReceivedOn;
  }

  @Override
  public void setReceivedOn(Date pReceivedOn) {
    this.mReceivedOn = pReceivedOn;
  }

  @Override
  public Date getCompletedOn() {
    return mCompletedOn;
  }

  @Override
  public void setCompletedOn(Date pCompletedOn) {
    this.mCompletedOn = pCompletedOn;
  }

  @Override
  public BigDecimal getAmount() {
    return mAmount;
  }

  @Override
  public void setAmount(BigDecimal pAmount) {
    mAmount = pAmount;
  }

  @Override
  public String getPaymentDetails() {
    return mPaymentDetails;
  }

  @Override
  public void setPaymentDetails(String pPaymentDetails) {
    mPaymentDetails = pPaymentDetails;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.mLastModified = pLastModified;
  }

  @Override
  public Long create() {
    return sPaymentStatusManager.create(this);
  }

  @Override
  public void update() {
    sPaymentStatusManager.update(this);
  }

  @Override
  public MutablePaymentStatus edit() {
    return new PersistentPaymentStatus(this);
  }

  @Override
  public void delete() {
    sPaymentStatusManager.delete(this);
  }

  public PersistentPaymentStatus() {}

  public PersistentPaymentStatus(MutablePaymentStatus pPaymentStatus) {
    setId(pPaymentStatus.getId());
    setAccount(pPaymentStatus.getAccount());
    setTransactionId(pPaymentStatus.getTransactionId());
    setMethodOfPayment(pPaymentStatus.getMethodOfPayment());
    setPaymentComplete(pPaymentStatus.isPaymentComplete());
    setReceivedOn(pPaymentStatus.getReceivedOn());
    setCompletedOn(pPaymentStatus.getCompletedOn());
    setLastModified(pPaymentStatus.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sPaymentStatusManager = applicationContext.getBean("paymentStatusManager", PaymentStatusManager.class);
  }
}
