package org.ums.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.CacheFactory;
import org.ums.manager.CacheWarmerManager;

@Component
@Path("/flushCache")
@Produces(Resource.MIME_TYPE_JSON)
public class FlushCache extends Resource {
  @Autowired
  CacheFactory mCacheFactory;

  @POST
  public Response flushCache(final @Context Request pRequest) {
    mCacheFactory.getCacheManager().flushAll();
    return Response.ok().build();
  }
}
