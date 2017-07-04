package org.ums.persistent.model.common;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.common.MutableRelationType;
import org.ums.manager.common.RelationTypeManager;

public class PersistentRelationType implements MutableRelationType {

  private static RelationTypeManager sRelationTypeManager;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sRelationTypeManager = applicationContext.getBean("relationTypeManager", RelationTypeManager.class);
  }

  private Integer mId;
  private String mRelationType;
  private String mLastModified;

  public PersistentRelationType() {}

  public PersistentRelationType(PersistentRelationType pPersistentRelationType) {
    mId = pPersistentRelationType.getId();
    mRelationType = pPersistentRelationType.getRelationType();
    mLastModified = pPersistentRelationType.getLastModified();
  }

  @Override
  public MutableRelationType edit() {
    return new PersistentRelationType(this);
  }

  @Override
  public Integer create() {
    return sRelationTypeManager.create(this);
  }

  @Override
  public void update() {
    sRelationTypeManager.update(this);
  }

  @Override
  public void delete() {
    sRelationTypeManager.delete(this);
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
  public void setRelationType(String pRelationType) {
    mRelationType = pRelationType;
  }

  @Override
  public String getRelationType() {
    return mRelationType;
  }
}
