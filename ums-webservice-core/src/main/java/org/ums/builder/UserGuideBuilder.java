package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableUserGuide;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Ifti on 18-Dec-16.
 */
@Component
public class UserGuideBuilder implements Builder<UserGuide, MutableUserGuide> {
  @Override
  public void build(JsonObjectBuilder pBuilder, UserGuide pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("guideId", pReadOnly.getGuideId());
    pBuilder.add("navigationId", pReadOnly.getNavigationId());
    pBuilder.add("manualTitle", pReadOnly.getManualTitle());
    pBuilder.add("pdfFilePath", pReadOnly.getPdfFilePath());
    pBuilder.add("htmlContent", pReadOnly.getHtmlContent());
    pBuilder.add("viewOrder", pReadOnly.getViewOrder());
    pBuilder.add("visibility", pReadOnly.getVisibility());
  }

  @Override
  public void build(MutableUserGuide pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {}

}
