package org.ums.domain.model.dto.logger;

import java.sql.Timestamp;

/**
 * Created by Monjur-E-Morshed on 07-Aug-17.
 */
public class ActivityLogger {
  private Timestamp time;

  private String userId;

  private Timestamp accessTime;

  private String className;

  private String methodName;

  public ActivityLogger() {}

  public ActivityLogger(Timestamp pTime, String pUserId, Timestamp pAccessTime, String pClassName, String pMethodName) {
    time = pTime;
    userId = pUserId;
    accessTime = pAccessTime;
    className = pClassName;
    methodName = pMethodName;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp pTime) {
    time = pTime;
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

}
