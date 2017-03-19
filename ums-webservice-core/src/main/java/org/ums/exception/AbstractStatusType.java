package org.ums.exception;

import javax.ws.rs.core.Response;

public class AbstractStatusType implements Response.StatusType {
  public AbstractStatusType(final Response.Status.Family family, final int statusCode, final String reasonPhrase) {
    super();

    this.family = family;
    this.statusCode = statusCode;
    this.reasonPhrase = reasonPhrase;
  }

  protected AbstractStatusType(final Response.Status status, final String reasonPhrase) {
    this(status.getFamily(), status.getStatusCode(), reasonPhrase);
  }

  @Override
  public Response.Status.Family getFamily() {
    return family;
  }

  @Override
  public String getReasonPhrase() {
    return reasonPhrase;
  }

  @Override
  public int getStatusCode() {
    return statusCode;
  }

  private final Response.Status.Family family;
  private final int statusCode;
  private final String reasonPhrase;
}
