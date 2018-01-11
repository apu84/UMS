package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.accounts.MutableMonth;
import org.ums.manager.accounts.MonthManager;

/**
 * Created by Monjur-E-Morshed on 11-Jan-18.
 */
public class PersistentMonth implements MutableMonth {

  @JsonIgnore
  private static MonthManager sMonthManager;
  @JsonProperty("id")
  @JsonIgnore
  private Long mId;
  @JsonProperty("name")
  @JsonIgnore
  private String mName;
  @JsonProperty("shortName")
  private String mShortName;
  @JsonIgnore
  private String mLastModified;

  @Override
  @JsonSerialize(using = ToStringSerializer.class)
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
    return sMonthManager.create(this);
  }

  @Override
  public void update() {
    sMonthManager.update(this);
  }

  @Override
  public MutableMonth edit() {
    return new PersistentMonth(this);
  }

  @Override
  public void delete() {
    sMonthManager.delete(this);
  }

  public PersistentMonth() {
  }

  public PersistentMonth(MutableMonth pMonth) {
    setId(pMonth.getId());
    setName(pMonth.getName());
    setShortName(pMonth.getShortName());
    setLastModified(pMonth.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sMonthManager = applicationContext.getBean("monthManager", MonthManager.class);
  }
}
