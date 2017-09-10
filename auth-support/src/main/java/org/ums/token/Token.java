package org.ums.token;

public interface Token {
  String getHash();

  long getTokenExpiry();
}
