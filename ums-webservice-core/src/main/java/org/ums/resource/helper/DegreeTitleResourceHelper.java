package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.DegreeTitleBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.DegreeTitle;
import org.ums.domain.model.mutable.common.MutableDegreeTitle;
import org.ums.manager.ContentManager;
import org.ums.manager.common.DegreeTitleManager;
import org.ums.persistent.model.common.PersistentDegreeTitle;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class DegreeTitleResourceHelper extends ResourceHelper<DegreeTitle, MutableDegreeTitle, Integer> {

  @Autowired
  private DegreeTitleManager mManager;

  @Autowired
  private DegreeTitleBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    LocalCache localCache = new LocalCache();
    MutableDegreeTitle mutableDegreeTitle = new PersistentDegreeTitle();
    mBuilder.build(mutableDegreeTitle, pJsonObject, localCache);
    mManager.create(mutableDegreeTitle);
    localCache.invalidate();
    return Response.ok().build();
  }

  @Override
  protected ContentManager<DegreeTitle, MutableDegreeTitle, Integer> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<DegreeTitle, MutableDegreeTitle> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(DegreeTitle pReadonly) {
    return pReadonly.getLastModified();
  }
}
