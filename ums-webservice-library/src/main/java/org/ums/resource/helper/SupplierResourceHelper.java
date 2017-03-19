package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.SupplierBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.library.SupplierManager;
import org.ums.persistent.model.library.PersistentSupplier;
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
public class SupplierResourceHelper extends ResourceHelper<Supplier, MutableSupplier, Long>

{

  @Autowired
  private SupplierManager mManager;

  @Autowired
  private SupplierBuilder mBuilder;

  @Override
  public SupplierManager getContentManager() {
    return mManager;
  }

  @Override
  public SupplierBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    MutableSupplier mutableSupplier = new PersistentSupplier();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableSupplier, pJsonObject, localCache);
    mutableSupplier.commit(false);

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(SemesterResource.class).path(SemesterResource.class, "get")
            .build(mutableSupplier.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getAllForPagination(final Integer pItemPerPage, final Integer pPage,
      final String pOrder, final UriInfo pUriInfo) {
    List<Supplier> suppliers = getContentManager().getAllForPagination(pItemPerPage, pPage, pOrder);
    int total = getContentManager().getTotalForPagination();

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Supplier supplier : suppliers) {
      children.add(toJson(supplier, pUriInfo, localCache));
    }
    object.add("entries", children);
    object.add("total", total);

    localCache.invalidate();

    return object.build();
  }

  @Override
  protected String getETag(Supplier pReadonly) {
    return UmsUtils.nullConversion(pReadonly.getLastModified());
  }

}
