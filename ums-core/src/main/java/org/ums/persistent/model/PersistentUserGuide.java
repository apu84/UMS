package org.ums.persistent.model;

import org.springframework.context.ApplicationContext;
import org.ums.context.AppContext;
import org.ums.domain.model.mutable.MutableUserGuide;
import org.ums.manager.UserGuideManager;

/**
 * Created by Ifti on 17-Dec-16.
 */
public class PersistentUserGuide implements MutableUserGuide {
  private static UserGuideManager sUserGuide;

  static {
    ApplicationContext applicationContext = AppContext.getApplicationContext();
    sUserGuide = applicationContext.getBean("userGuideManager", UserGuideManager.class);
  }

  private Integer mId;
  private Integer mUserGuideId;
  private Integer mNavigationId;
  private String mManualTitle;
  private String mPdfFilePath;
  private String mHtmlContent;
  private Integer mViewOrder;
  private Integer mVisibility;

  public PersistentUserGuide() {}

  public PersistentUserGuide(MutableUserGuide pMutableUserGuide) {
    setId(pMutableUserGuide.getId());
    setNavigationId(pMutableUserGuide.getNavigationId());
    setManualTitle(pMutableUserGuide.getManualTitle());
    setPdfFilePath(pMutableUserGuide.getPdfFilePath());
    setHtmlContent(pMutableUserGuide.getHtmlContent());
    setViewOrder(pMutableUserGuide.getViewOrder());
    setVisibility(pMutableUserGuide.getVisibility());
  }

  @Override
  public Integer create() {
    return sUserGuide.create(this);
  }

  @Override
  public void update() {
    sUserGuide.update(this);
  }

  @Override
  public void delete() {

  }

  @Override
  public PersistentUserGuide edit() {
    return new PersistentUserGuide(this);
  }

  @Override
  public void setGuideId(Integer pGuideId) {
    this.mUserGuideId = pGuideId;
  }

  @Override
  public void setNavigationId(Integer pNavigationId) {
    this.mNavigationId = pNavigationId;
  }

  @Override
  public void setManualTitle(String pManualTitle) {
    this.mManualTitle = pManualTitle;
  }

  @Override
  public void setPdfFilePath(String pPdfFilePath) {
    mPdfFilePath = pPdfFilePath;
  }

  @Override
  public void setHtmlContent(String pHtmlContent) {
    mHtmlContent = pHtmlContent;
  }

  @Override
  public void setViewOrder(Integer pViewOrder) {
    this.mViewOrder = pViewOrder;
  }

  @Override
  public void setVisibility(Integer pVisibility) {
    this.mVisibility = pVisibility;
  }

  @Override
  public Integer getGuideId() {
    return mUserGuideId;
  }

  @Override
  public Integer getNavigationId() {
    return mNavigationId;
  }

  @Override
  public String getManualTitle() {
    return mManualTitle;
  }

  @Override
  public String getPdfFilePath() {
    return mPdfFilePath;
  }

  @Override
  public String getHtmlContent() {
    return mHtmlContent;
  }

  @Override
  public Integer getViewOrder() {
    return mViewOrder;
  }

  @Override
  public Integer getVisibility() {
    return mVisibility;
  }

  @Override
  public String getLastModified() {
    return null;
  }

  @Override
  public Integer getId() {
    return mId;
  }

  @Override
  public void setId(Integer pId) {
    this.mId = pId;
  }
}
