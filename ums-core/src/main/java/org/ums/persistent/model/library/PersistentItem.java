package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.enums.library.ItemStatus;
import org.ums.manager.library.ItemManager;
import org.ums.manager.library.SupplierManager;

/**
 * Created by Ifti on 04-Mar-17.
 */
public class PersistentItem implements MutableItem {

  private static ItemManager sItemManager;
  private static SupplierManager sSupplierManager;

  private Long mId;
  private Long mMfn;
  private Integer mCopyNumber;
  private String mAccessionNumber;
  private String mAccessionDate;
  private String mBarcode;
  private Double mPrice;
  private String mInternalNote;
  private Supplier mSupplier;
  private Long mSupplierId;
  private ItemStatus mStatus;
  private String mInsertedBy;
  private String mInsertedOn;
  private String mLastUpdatedBy;
  private String mLastUpdatedOn;
  private String mLastModified;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sItemManager = applicationContext.getBean("itemManager", ItemManager.class);
    sSupplierManager = applicationContext.getBean("supplierManager", SupplierManager.class);

  }

  public PersistentItem() {}

  public PersistentItem(final PersistentItem pPersistentItem) {
    mId = pPersistentItem.getId();
    mMfn = pPersistentItem.getMfn();
    mCopyNumber = pPersistentItem.getCopyNumber();
    mAccessionNumber = pPersistentItem.getAccessionNumber();
    mAccessionDate = pPersistentItem.getAccessionDate();
    mBarcode = pPersistentItem.getBarcode();
    mPrice = pPersistentItem.getPrice();
    mInternalNote = pPersistentItem.getInternalNote();
    mSupplierId = pPersistentItem.getSupplierId();
    mStatus = pPersistentItem.getStatus();

    mInsertedBy = pPersistentItem.getInsertedBy();
    mInsertedOn = pPersistentItem.getInsertedOn();
    mLastUpdatedBy = pPersistentItem.getLastUpdatedBy();
    mLastUpdatedOn = pPersistentItem.getLastUpdatedOn();
  }

  @Override
  public Long create() {
    return sItemManager.create(this);
  }

  @Override
  public void update() {
    sItemManager.update(this);
  }

  @Override
  public MutableItem edit() {
    return new PersistentItem(this);
  }

  @Override
  public void delete() {
    sItemManager.delete(this);
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public Long getMfn() {
    return mMfn;
  }

  @Override
  public Integer getCopyNumber() {
    return mCopyNumber;
  }

  @Override
  public String getAccessionNumber() {
    return mAccessionNumber;
  }

  @Override
  public String getAccessionDate() {
    return mAccessionDate;
  }

  @Override
  public String getBarcode() {
    return mBarcode;
  }

  @Override
  public Double getPrice() {
    return mPrice;
  }

  @Override
  public String getInternalNote() {
    return mInternalNote;
  }

  @Override
  public Supplier getSupplier() {
    return mSupplierId!=0?sSupplierManager.get(mSupplierId):null;
  }

  @Override
  public Long getSupplierId() {
    return mSupplierId;
  }

  @Override
  public ItemStatus getStatus() {
    return mStatus;
  }

  @Override
  public String getInsertedBy() {
    return mInsertedBy;
  }

  @Override
  public String getInsertedOn() {
    return mInsertedOn;
  }

  @Override
  public String getLastUpdatedBy() {
    return mLastUpdatedBy;
  }

  @Override
  public String getLastUpdatedOn() {
    return mLastUpdatedOn;
  }

  @Override
  public void setMfn(Long pMfn) {
    mMfn = pMfn;
  }

  @Override
  public void setCopyNumber(Integer pCopyNumber) {
    mCopyNumber = pCopyNumber;
  }

  @Override
  public void setAccessionNumber(String pAccessionNumber) {
    mAccessionNumber = pAccessionNumber;
  }

  @Override
  public void setAccessionDate(String pAccessionDate) {
    mAccessionDate = pAccessionDate;
  }

  @Override
  public void setBarcode(String pBarcode) {
    mBarcode = pBarcode;
  }

  @Override
  public void setPrice(Double pPrice) {
    mPrice = pPrice;
  }

  @Override
  public void setInternalNote(String pInternalNote) {
    mInternalNote = pInternalNote;
  }

  @Override
  public void setSupplier(Supplier pSupplier) {
    mSupplier = pSupplier;
  }

  @Override
  public void setSupplierId(Long pSupplierId) {
    mSupplierId = pSupplierId;
  }

  @Override
  public void setStatus(ItemStatus pStatus) {
    mStatus = pStatus;
  }

  @Override
  public void setInsertedBy(String pInsertedBy) {
    mInsertedBy = pInsertedBy;
  }

  @Override
  public void setInsertedOn(String pInsertedOn) {
    mInsertedOn = pInsertedOn;
  }

  @Override
  public void setLastUpdatedBy(String pUpdatedBy) {
    mLastUpdatedBy = pUpdatedBy;
  }

  @Override
  public void setLastUpdatedOn(String pLastUpdatedOn) {
    mLastUpdatedOn = pLastUpdatedOn;
  }
}
