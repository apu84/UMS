package org.ums.exception;

import javax.ws.rs.core.Response;

public class CustomReasonPhraseExceptionStatusType extends AbstractStatusType {
  private static final String CUSTOM_EXCEPTION_REASON_PHRASE = "Operation Failed";

  public CustomReasonPhraseExceptionStatusType(Response.Status httpStatus) {
    super(httpStatus, CUSTOM_EXCEPTION_REASON_PHRASE);
  }

  public CustomReasonPhraseExceptionStatusType(Response.Status httpStatus, String pStatusText) {
    super(httpStatus, pStatusText);
  }
}
