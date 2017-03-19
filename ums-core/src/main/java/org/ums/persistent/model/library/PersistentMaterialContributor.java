package org.ums.persistent.model.library;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.mutable.library.MutableMaterialContributor;
import org.ums.enums.library.ContributorRole;
import org.ums.manager.library.MaterialContributorManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class PersistentMaterialContributor implements MutableMaterialContributor {
  private static MaterialContributorManager sMaterialContributorManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sMaterialContributorManager =
        applicationContext.getBean("materialContributorManager", MaterialContributorManager.class);
  }

  private Integer mId;
  private String mMfn;
  private ContributorRole mRole;
  private Contributor mContributor;
  private Integer mViewOrder;
  private String mLastModified;

  public PersistentMaterialContributor() {}

  public PersistentMaterialContributor(final PersistentMaterialContributor pPersistentMaterialContributor) {
    mId = pPersistentMaterialContributor.getId();
    mMfn = pPersistentMaterialContributor.getMfn();
    mRole = pPersistentMaterialContributor.getContributorRole();
    mContributor = pPersistentMaterialContributor.getContributor();
    mViewOrder = pPersistentMaterialContributor.getViewOrder();
    mLastModified = pPersistentMaterialContributor.getLastModified();
  }

  @Override
  public Integer create() {
    return sMaterialContributorManager.create(this);
  }

  @Override
  public void update() {
    sMaterialContributorManager.update(this);
  }

  @Override
  public void delete() {
    sMaterialContributorManager.delete(this);
  }

  @Override
  public PersistentMaterialContributor edit() {
    return new PersistentMaterialContributor(this);
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
  public String getMfn() {
    return mMfn;
  }

  @Override
  public Integer getViewOrder() {
    return mViewOrder;
  }

  @Override
  public void setMfn(String pMfn) {
    mMfn = pMfn;
  }

  @Override
  public ContributorRole getContributorRole() {
    return mRole;
  }

  @Override
  public void setViewOrder(Integer pViewOrder) {
    mViewOrder = pViewOrder;
  }

  @Override
  public Contributor getContributor() {
    return mContributor;
  }

  @Override
  public void setContributorRole(ContributorRole pContributorRole) {
    mRole = pContributorRole;
  }

  @Override
  public void setContributor(Contributor pContributor) {
    mContributor = pContributor;
  }
}
