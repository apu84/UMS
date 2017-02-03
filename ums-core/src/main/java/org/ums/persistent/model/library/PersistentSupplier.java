package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.library.SupplierManager;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class PersistentSupplier implements MutableSupplier {

  private static SupplierManager sSupplierManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sSupplierManager = applicationContext.getBean("supplierManager", SupplierManager.class);
  }

  private Integer mId;
  private String mName;
  private String mAddress;
  private String mContactPerson;
  private String mContactNumber;
  private String mLastModified;

  public PersistentSupplier() {}

  public PersistentSupplier(final PersistentSupplier pPersistentSupplier) {
    mId = pPersistentSupplier.getId();
    mName = pPersistentSupplier.getName();
    mAddress = pPersistentSupplier.getAddress();
    mContactPerson = pPersistentSupplier.getContactPerson();
    mContactNumber = pPersistentSupplier.getContactNumber();
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
    mLastModified = pLastModified;
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public String getAddress() {
    return mAddress;
  }

  @Override
  public void setName(String pName) {
    mName = pName;
  }

  @Override
  public String getContactPerson() {
    return mContactPerson;
  }

  @Override
  public void setAddress(String pAddress) {
    mAddress = pAddress;
  }

  @Override
  public String getContactNumber() {
    return mContactNumber;
  }

  @Override
  public void setContactPerson(String pContactPerson) {
    mContactPerson = pContactPerson;
  }

  @Override
  public void setContactNumber(String pContactNumber) {
    mContactNumber = pContactNumber;
  }

  @Override
  public void commit(boolean update) {
    if(update) {
      sSupplierManager.update(this);
    }
    else {
      sSupplierManager.create(this);
    }
  }

  @Override
  public MutableSupplier edit() {
    return new PersistentSupplier(this);
  }

  @Override
  public void delete() {

    sSupplierManager.delete(this);
  }
}
