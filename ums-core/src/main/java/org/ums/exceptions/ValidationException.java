package org.ums.exceptions;

public class ValidationException extends RuntimeException {
  public ValidationException(String exception) {
    super(exception);
  }
}
