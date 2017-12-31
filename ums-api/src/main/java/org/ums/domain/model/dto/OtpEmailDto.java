package org.ums.domain.model.dto;

public class OtpEmailDto {
  public String otp;
  public String expireAt;

  public OtpEmailDto(String otp, String expireAt) {
    this.otp = otp;
    this.expireAt = expireAt;
  }
}
