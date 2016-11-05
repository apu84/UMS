package org.ums.generator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface XlsGenerator {
  void build(List collection, OutputStream out, InputStream templateLocation) throws Exception;

  void build(Map<String, Collection> map, OutputStream out, InputStream templateLocation)
      throws Exception;

}
