package org.ums.generator;

import java.security.SecureRandom;
import java.util.Random;
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

  public String getAlphaNumericId(final String pPrefix, final int pNumericLength, final String pSeparator) {
    return pPrefix + pSeparator + generateRandomNumber(pNumericLength);
  }

  public String getAlphaNumericId(final String pPrefix, final int pNumericLength) {
    return getAlphaNumericId(pPrefix, pNumericLength, "");
  }

  private String generateRandomNumber(int charLength) {
    return String.valueOf(charLength < 1 ? 0 : new Random().nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
        + (int) Math.pow(10, charLength - 1));
  }
}
