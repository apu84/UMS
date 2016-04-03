package org.ums.common.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.manager.CacheManager;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Component
@Path("/flushCache")
@Produces(Resource.MIME_TYPE_JSON)
public class FlushCache extends Resource {
  @Autowired
  @Qualifier("localCache")
  CacheManager mCacheManager;

  @POST
  public Response flushCache(final @Context Request pRequest)
      throws Exception {
    mCacheManager.flushAll();
    return Response.ok().build();
  }
}
