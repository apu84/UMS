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
  private Long id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("shortName")
  private String shortName;
  @JsonIgnore
  private String lastModified;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long pId) {
    this.id = pId;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String pName) {
    this.name = pName;
  }

  @Override
  public String getShortName() {
    return shortName;
  }

  @Override
  public void setShortName(String pShortName) {
    this.shortName = pShortName;
  }

  @Override
  public String getLastModified() {
    return lastModified;
  }

  @Override
  public void setLastModified(String pLastModified) {
    this.lastModified = pLastModified;
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
