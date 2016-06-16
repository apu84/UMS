package org.ums.generator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface XlsGenerator {
  void build(List collection, OutputStream out, InputStream templateLocation) throws Exception;
}
