package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.PublisherBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.manager.library.PublisherManager;
import org.ums.persistent.model.library.PersistentPublisher;
import org.ums.resource.ResourceHelper;
import org.ums.resource.SemesterResource;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by Ifti on 04-Feb-17.
 */
@Component
public class PublisherResourceHelper extends ResourceHelper<Publisher, MutablePublisher, Integer>

{

  @Autowired
  private PublisherManager mManager;

  @Autowired
  private PublisherBuilder mBuilder;

  @Override
  public PublisherManager getContentManager() {
    return mManager;
  }

  @Override
  public PublisherBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    MutablePublisher mutablePublisher = new PersistentPublisher();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutablePublisher, pJsonObject, localCache);
    mutablePublisher.create();

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(SemesterResource.class).path(SemesterResource.class, "get")
            .build(mutablePublisher.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  protected String getETag(Publisher pReadonly) {
    return UmsUtils.nullConversion(pReadonly.getLastModified());
  }

}
