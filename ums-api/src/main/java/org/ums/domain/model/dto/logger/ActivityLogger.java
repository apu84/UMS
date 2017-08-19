package org.ums.domain.model.dto.logger;

import java.sql.Timestamp;

/**
 * Created by Monjur-E-Morshed on 07-Aug-17.
 */
public class ActivityLogger {

  private String userId;

  private Timestamp accessTime;

  private String className;

  private String methodName;

  private String ipAddress;

  private String device;

  private String exception;

  public ActivityLogger() {

  }

  public ActivityLogger(String pUserId, Timestamp pAccessTime, String pClassName, String pMethodName,
      String pIpAddress, String pDevice, String pException) {
    userId = pUserId;
    accessTime = pAccessTime;
    className = pClassName;
    methodName = pMethodName;
    ipAddress = pIpAddress;
    device = pDevice;
    exception = pException;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String pUserId) {
    userId = pUserId;
  }

  public Timestamp getAccessTime() {
    return accessTime;
  }

  public void setAccessTime(Timestamp pAccessTime) {
    accessTime = pAccessTime;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String pClassName) {
    className = pClassName;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String pMethodName) {
    methodName = pMethodName;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String pIpAddress) {
    ipAddress = pIpAddress;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String pDevice) {
    device = pDevice;
  }

  public String getException() {
    return exception;
  }

  public void setException(String pException) {
    exception = pException;
  }
}
