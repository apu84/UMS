package org.ums.generator;

import java.security.SecureRandom;
import java.util.UUID;

public class IdGenerator {
  SecureRandom mSecureRandom;

  public IdGenerator() {
    mSecureRandom = new SecureRandom();
  }

  public Long getNumericId() {
    return mSecureRandom.nextLong();
  }

  public String getAlphaNumericId() {
    return UUID.randomUUID().toString();
  }
}
