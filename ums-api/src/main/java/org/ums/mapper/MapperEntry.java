package org.ums.mapper;

import java.io.Serializable;
import java.net.URI;

public interface MapperEntry extends Serializable {
  String getEntity();

  URI getUri();

  String getMediaType();

  String getMethod();
}
