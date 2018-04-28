package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.GroupManager;
import org.ums.manager.accounts.SystemGroupMapManager;
import org.ums.serializer.UmsDateSerializer;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 26-Apr-18.
 */
public class PersistentSystemGroupMap implements MutableSystemGroupMap {

  @JsonIgnore
  private static GroupManager sGroupManager;
  @JsonIgnore
  private static CompanyManager sCompanyManager;
  @JsonIgnore
  private static SystemGroupMapManager sSystemGroupMapManager;
  private String mId;
  private GroupType mGroupType;
  private Group mGroup;
  private Long mGroupId;
  @JsonIgnore
  private Company mCompany;
  private String mCompanyId;
  private String mModifiedBy;
  @JsonSerialize(using = UmsDateSerializer.class)
  private Date mModifiedDate;
  private String mLastModified;

  @Override
  public String getModifiedBy() {
    return mModifiedBy;
  }

  @Override
  public void setModifiedBy(String pModifiedBy) {
    mModifiedBy = pModifiedBy;
  }

  @Override
  public Date getModifiedDate() {
    return mModifiedDate;
  }

  @Override
  public void setModifiedDate(Date pModifiedDate) {
    mModifiedDate = pModifiedDate;
  }

  @Override
  @JsonValue
  public Company getCompany() {
    return mCompany == null ? sCompanyManager.get(mCompanyId) : sCompanyManager.validate(mCompany);
  }

  @Override
  @JsonIgnore
  public void setCompany(Company pCompany) {
    mCompany = pCompany;
  }

  @Override
  public String getCompanyId() {
    return mCompanyId;
  }

  @Override
  public void setCompanyId(String pCompanyId) {
    mCompanyId = pCompanyId;
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void setId(String pId) {
    this.mId = pId;
  }

  @Override
  public GroupType getGroupType() {
    return mGroupType;
  }

  @Override
  public void setGroupType(GroupType pGroupType) {
    this.mGroupType = pGroupType;
  }

  @Override
  public Group getGroup() {
    return mGroup == null ? sGroupManager.get(mGroupId) : sGroupManager.validate(mGroup);
  }

  @Override
  public void setGroup(Group pGroup) {
    this.mGroup = pGroup;
  }

  @Override
  public Long getGroupId() {
    return mGroupId;
  }

  @Override
  public void setGroupId(Long pGroupId) {
    this.mGroupId = pGroupId;
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
  public String create() {
    return sSystemGroupMapManager.create(this);
  }

  @Override
  public void update() {
    sSystemGroupMapManager.update(this);
  }

  @Override
  public MutableSystemGroupMap edit() {
    return new PersistentSystemGroupMap(this);
  }

  @Override
  public void delete() {
    sSystemGroupMapManager.delete(this);
  }

  public PersistentSystemGroupMap() {}

  public PersistentSystemGroupMap(MutableSystemGroupMap pSystemGroupMap) {
    setId(pSystemGroupMap.getId());
    setGroupType(pSystemGroupMap.getGroupType());
    setGroup(pSystemGroupMap.getGroup());
    setGroupId(pSystemGroupMap.getGroupId());
    setCompany(pSystemGroupMap.getCompany());
    setCompanyId(pSystemGroupMap.getCompanyId());
    setLastModified(pSystemGroupMap.getLastModified());
  }

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sGroupManager = applicationContext.getBean("groupManager", GroupManager.class);
    sCompanyManager = applicationContext.getBean("companyManager", CompanyManager.class);
    sSystemGroupMapManager = applicationContext.getBean("systemGroupMapManager", SystemGroupMapManager.class);
  }
}
