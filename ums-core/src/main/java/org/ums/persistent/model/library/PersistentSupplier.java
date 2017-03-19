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

  private Long mId;
  private String mName;
  private String mEmail;
  private String mContactPerson;
  private String mContactNumber;
  private String mAddress;
  private String mNote;
  private String mLastModified;

  public PersistentSupplier() {}

  public PersistentSupplier(final PersistentSupplier pPersistentSupplier) {
    mId = pPersistentSupplier.getId();
    mName = pPersistentSupplier.getName();
    mEmail = pPersistentSupplier.getEmail();
    mContactPerson = pPersistentSupplier.getContactPerson();
    mContactNumber = pPersistentSupplier.getContactNumber();
    mAddress = pPersistentSupplier.getAddress();
    mNote = pPersistentSupplier.getNote();
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
  public String getName() {
    return mName;
  }

  @Override
  public String getEmail() {
    return mEmail;
  }

  @Override
  public String getAddress() {
    return mAddress;
  }

  @Override
  public String getNote() {
    return mNote;
  }

  @Override
  public void setName(String pName) {
    mName = pName;
  }

  @Override
  public void setEmail(String pEmail) {
    mEmail = pEmail;
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
  public void setNote(String pNote) {
    mNote = pNote;
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
  public Long create() {
    return sSupplierManager.create(this);
  }

  @Override
  public void update() {
    sSupplierManager.update(this);
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
