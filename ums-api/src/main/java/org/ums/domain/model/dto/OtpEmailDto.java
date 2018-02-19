package org.ums.domain.model.dto;

public class OtpEmailDto {
  private String otp;
  private String expireAt;

  public OtpEmailDto(String otp, String expireAt) {
    this.otp = otp;
    this.expireAt = expireAt;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

  public String getExpireAt() {
    return expireAt;
  }

  public void setExpireAt(String expireAt) {
    this.expireAt = expireAt;
  }
}
