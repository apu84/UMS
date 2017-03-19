package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.AuthorBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.manager.library.AuthorManager;
import org.ums.persistent.model.library.PersistentAuthor;
import org.ums.resource.ResourceHelper;
import org.ums.resource.SemesterResource;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by Ifti on 30-Jan-17.
 */
@Component
public class AuthorResourceHelper extends ResourceHelper<Author, MutableAuthor, Integer> {

  @Autowired
  private AuthorManager mManager;

  @Autowired
  private AuthorBuilder mBuilder;

  @Override
  public AuthorManager getContentManager() {
    return mManager;
  }

  @Override
  public AuthorBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    MutableAuthor mutableAuthor = new PersistentAuthor();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableAuthor, pJsonObject, localCache);
    mutableAuthor.create();

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(SemesterResource.class)
            .path(SemesterResource.class, "get").build(mutableAuthor.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  protected String getETag(Author pReadonly) {
    return pReadonly.getLastModified();
  }

}
