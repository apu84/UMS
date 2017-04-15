package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.PublisherBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.manager.library.PublisherManager;
import org.ums.persistent.model.library.PersistentPublisher;
import org.ums.resource.ResourceHelper;
import org.ums.resource.SemesterResource;
import org.ums.util.UmsUtils;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by Ifti on 04-Feb-17.
 */
@Component
public class PublisherResourceHelper extends ResourceHelper<Publisher, MutablePublisher, Long>

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

  public JsonObject getAllForPagination(final Integer pItemPerPage, final Integer pPage, final String pOrder,
      final String pWhereClause, final UriInfo pUriInfo) {
    List<Publisher> publishers = getContentManager().getAllForPagination(pItemPerPage, pPage, pWhereClause, pOrder);
    int total = getContentManager().getTotalForPagination(pWhereClause);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Publisher publisher : publishers) {
      children.add(toJson(publisher, pUriInfo, localCache));
    }
    object.add("entries", children);
    object.add("total", total);

    localCache.invalidate();

    return object.build();
  }

  @Override
  protected String getETag(Publisher pReadonly) {
    return UmsUtils.nullConversion(pReadonly.getLastModified());
  }

}
