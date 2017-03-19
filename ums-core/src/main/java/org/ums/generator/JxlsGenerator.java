package org.ums.generator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

public class JxlsGenerator implements XlsGenerator {
  @Override
  public void build(List collection, OutputStream pOutputStream, InputStream pTemplateLocation) throws IOException {
    Context context = new Context();
    context.putVar("collection", collection);
    JxlsHelper.getInstance().processTemplate(pTemplateLocation, pOutputStream, context);

  }

  @Override
  public void build(Map<String, Collection> map, OutputStream pOutputStream, InputStream pTemplateLocation)
      throws IOException {
    Context context = new Context();
    for(Map.Entry<String, Collection> entry : map.entrySet()) {
      context.putVar(entry.getKey(), entry.getValue());
    }

    JxlsHelper.getInstance().processTemplate(pTemplateLocation, pOutputStream, context);
  }
}
