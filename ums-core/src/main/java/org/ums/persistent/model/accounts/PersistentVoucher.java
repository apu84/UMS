package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.accounts.MutableVoucher;
import org.ums.manager.accounts.VoucherManager;

/**
 * Created by Monjur-E-Morshed on 08-Jan-18.
 */

public class PersistentVoucher implements MutableVoucher {

  @JsonIgnore
  private static VoucherManager sVoucherManager;
  @JsonProperty("id")
  private Long mId;
  @JsonProperty("name")
  private String mName;
  @JsonProperty("shortName")
  private String mShortName;
  @JsonIgnore
  private String mLastModified;

  @Override
  public Long getId() {
    return mId;
  }

  @Override
  public void setId(Long pId) {
    this.mId = pId;
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public void setName(String pName) {
    this.mName = pName;
  }

  @Override
  public String getShortName() {
    return mShortName;
  }

  @Override
  public void setShortName(String pShortName) {
    this.mShortName = pShortName;
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
    return sVoucherManager.create(this);
  }

  @Override
  public void update() {
    sVoucherManager.update(this);
  }

  @Override
  public MutableVoucher edit() {
    return new PersistentVoucher(this);
  }

  @Override
  public void delete() {
    sVoucherManager.delete(this);
  }

  public PersistentVoucher() {}

  public PersistentVoucher(MutableVoucher pVoucher) {
    setId(pVoucher.getId());
    setName(pVoucher.getName());
    setShortName(pVoucher.getShortName());
    setLastModified(pVoucher.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sVoucherManager = applicationContext.getBean("voucherManager", VoucherManager.class);
  }
}
