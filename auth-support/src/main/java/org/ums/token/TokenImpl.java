package org.ums.token;

public class TokenImpl implements Token {
  private String mHash;
  private long mExpiry;

  public TokenImpl(String pHash, long pExpiry) {
    mHash = pHash;
    mExpiry = pExpiry;
  }

  @Override
  public String getHash() {
    return mHash;
  }

  @Override
  public long getTokenExpiry() {
    return mExpiry;
  }
}
