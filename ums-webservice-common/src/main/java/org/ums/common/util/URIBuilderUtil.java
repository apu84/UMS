package org.ums.common.util;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class UriBuilderUtil {
  public static UriBuilder makeAbsolute(final URI pURI, final UriInfo pUriInfo) {
    return pURI.isAbsolute()
        ? UriBuilder.fromUri(pURI)
        : UriBuilder.fromUri(pUriInfo.resolve(pURI));
  }
}