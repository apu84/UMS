package org.ums.persistent.model.accounts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.Company;
import org.ums.domain.model.immutable.accounts.Group;
import org.ums.domain.model.mutable.accounts.MutableSystemGroupMap;
import org.ums.employee.personal.PersonalInformationManager;
import org.ums.enums.accounts.definitions.group.GroupType;
import org.ums.manager.CompanyManager;
import org.ums.manager.accounts.GroupManager;
import org.ums.manager.accounts.SystemGroupMapManager;
import org.ums.serializer.UmsDateSerializer;

import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 26-Apr-18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersistentSystemGroupMap implements MutableSystemGroupMap {

  @JsonIgnore
  private static GroupManager sGroupManager;
  @JsonIgnore
  private static CompanyManager sCompanyManager;
  @JsonIgnore
  private static PersonalInformationManager sPersonalInformationManager;
  @JsonIgnore
  private static SystemGroupMapManager sSystemGroupMapManager;
  @JsonProperty("id")
  private String mId;
  @JsonProperty("groupType")
  private GroupType mGroupType;
  @JsonIgnore
  @JsonProperty("mGroup")
  private Group mGroup;
  @JsonProperty("groupId")
  private Long mGroupId;
  @JsonIgnore
  @JsonProperty("company")
  private Company mCompany;
  @JsonProperty("companyId")
  private String mCompanyId;
  @JsonProperty("modifiedBy")
  private String mModifiedBy;
  @JsonProperty("modifierName")
  private String mModifierName;
  @JsonIgnore
  @JsonProperty("modifiedDate")
  private Date mModifiedDate;
  @JsonProperty("lastModified")
  private String mLastModified;

  @Override
  public String getModifierName() {
    return mModifierName == null ? sPersonalInformationManager.get(mModifiedBy).getName() : mModifierName;
  }

  @Override
  public void setModifierName(String pModifierName) {
    mModifierName = pModifierName;
  }

  @Override
  public String getModifiedBy() {
    return mModifiedBy;
  }

  @Override
  public void setModifiedBy(String pModifiedBy) {
    mModifiedBy = pModifiedBy;
  }

  @Override
  @JsonSerialize(using = UmsDateSerializer.class)
  public Date getModifiedDate() {
    return mModifiedDate;
  }

  @Override
  @JsonIgnore
  public void setModifiedDate(Date pModifiedDate) {
    mModifiedDate = pModifiedDate;
  }

  @Override
  @JsonProperty("company")
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
  @JsonProperty("group")
  public Group getGroup() {
    return mGroup == null ? sGroupManager.get(mGroupId) : sGroupManager.validate(mGroup);
  }

  @Override
  @JsonIgnore
  public void setGroup(Group pGroup) {
    this.mGroup = pGroup;
  }

  @Override
  @JsonFormat(shape = JsonFormat.Shape.STRING)
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
    sPersonalInformationManager =
        applicationContext.getBean("personalInformationManager", PersonalInformationManager.class);
    sSystemGroupMapManager = applicationContext.getBean("systemGroupMapManager", SystemGroupMapManager.class);
  }
}
