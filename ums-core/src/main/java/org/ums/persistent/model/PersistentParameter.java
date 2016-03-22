package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableParameter;
import org.ums.manager.ParameterManager;

/**
 * Created by Monjur-E-Morshed on 3/13/2016.
 */
public class PersistentParameter implements MutableParameter {

  private static ParameterManager sParameterManager;

  static{
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sParameterManager = applicationContext.getBean("parameterManager",ParameterManager.class);
  }

  private String mId;
  private String mParameter;
  private String mShortDescription;
  private String mLongDescription;
  private String mLastModified;
  private int mType;

  public PersistentParameter(){

  }

  public PersistentParameter(final PersistentParameter pOriginal)throws Exception{
    mId = pOriginal.getId();
    mParameter = pOriginal.getParameter();
    mShortDescription = pOriginal.getShortDescription();
    mLongDescription = pOriginal.getLongDescription();
    mType = pOriginal.getType();
  }


  @Override
  public void setShortDescription(String pShortDescription) {
    mShortDescription = pShortDescription;
  }

  @Override
  public String getLongDescription() {
    return mLongDescription;
  }

  @Override
  public void setParameter(String pParameter) {
      mParameter = pParameter;
  }

  @Override
  public void setLongDescription(String pLongDescription) {
      mLongDescription = pLongDescription;
  }

  @Override
  public String getParameter() {
    return mParameter;
  }

  @Override
  public String getShortDescription() {
    return mShortDescription;
  }

  @Override
  public void setType(int pType) {
    mType = pType;
  }

  @Override
  public int getType() {
    return mType;
  }

  @Override
  public String getLastModified() {
    return mLastModified;
  }

  @Override
  public MutableParameter edit() throws Exception {
    return new PersistentParameter(this);
  }

  @Override
  public String getId() {
    return mId;
  }

  @Override
  public void commit(boolean update) throws Exception {
    if(update){
      sParameterManager.update(this);
    }
    else{
      sParameterManager.create(this);
    }
  }

  @Override
  public void delete() throws Exception {
    sParameterManager.delete(this);
  }

  @Override
  public void setId(String pId) {
    mId = pId;
  }

  @Override
  public void setLastModified(String pLastModified) {
      mLastModified = pLastModified;
  }
}
