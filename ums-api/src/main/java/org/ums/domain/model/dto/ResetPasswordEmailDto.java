package org.ums.domain.model.dto;

/**
 * Created by Ifti on 09-Feb-16.
 */
public class ResetPasswordEmailDto {
  private String id;
  private String umsRootUrl;
  private String umsForgotPasswordUrl;
  private String umsResetPasswordUrl;
  private String umsSupportUrl;
  private String forgotPasswordRequestDateTime;

  public String getUmsRootUrl() {
    return umsRootUrl;
  }

  public void setUmsRootUrl(String umsRootUrl) {
    this.umsRootUrl = umsRootUrl;
  }

  public String getUmsForgotPasswordUrl() {
    return umsForgotPasswordUrl;
  }

  public void setUmsForgotPasswordUrl(String umsForgotPasswordUrl) {
    this.umsForgotPasswordUrl = umsForgotPasswordUrl;
  }

  public String getUmsResetPasswordUrl() {
    return umsResetPasswordUrl;
  }

  public void setUmsResetPasswordUrl(String umsResetPasswordUrl) {
    this.umsResetPasswordUrl = umsResetPasswordUrl;
  }

  public String getUmsSupportUrl() {
    return umsSupportUrl;
  }

  public void setUmsSupportUrl(String umsSupportUrl) {
    this.umsSupportUrl = umsSupportUrl;
  }

  public String getForgotPasswordRequestDateTime() {
    return forgotPasswordRequestDateTime;
  }

  public void setForgotPasswordRequestDateTime(String forgotPasswordRequestDateTime) {
    this.forgotPasswordRequestDateTime = forgotPasswordRequestDateTime;
  }

  public String getId() {
    return id;
  }

  public void setId(String pId) {
    id = pId;
  }
}
