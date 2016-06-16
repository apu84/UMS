package org.ums.generator;


import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class JxlsGenerator implements XlsGenerator {
  @Override
  public void build(List collection, OutputStream pOutputStream, InputStream pTemplateLocation) throws Exception {
    Context context = new Context();
    context.putVar("collection", collection);
    JxlsHelper.getInstance().processTemplate(pTemplateLocation, pOutputStream, context);
  }
}
