package org.ums.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class Resource {
  public static final String MIME_TYPE_JSON = "application/json";

  public static final String HEADER_IF_MATCH = "If-Match";

  public static final String PATH_PARAM_OBJECT_ID = "/{object-id}";

  @Context
  protected UriInfo mUriInfo;

  @Context
  protected HttpServletRequest mRequest;
}
