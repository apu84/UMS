package org.ums.common.academic.resources.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resources.LibraryResource;
import org.ums.common.builder.LibraryBuilder;
import org.ums.domain.model.immutable.Library;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.domain.model.mutable.MutableLibrary;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.manager.BinaryContentManager;
import org.ums.manager.ContentManager;
import org.ums.manager.LibraryManager;
import org.ums.persistent.model.PersistentEmployee;
import org.ums.persistent.model.PersistentLibrary;
import org.ums.persistent.model.PersistentUser;
import org.ums.resource.ResourceHelper;
import org.ums.util.UmsUtils;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import javax.ws.rs.core.Response;

/**
 * Created by kawsu on 12/5/2016.
 */

@Component
@Qualifier("LibraryResourceHelper")
public class LibraryResourceHelper extends ResourceHelper<Library, MutableLibrary, Integer> {
  @Autowired
  @Qualifier("fileContentManager")
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Autowired
  private LibraryManager mManager;

  @Autowired
  @Qualifier("LibraryBuilder")
  private LibraryBuilder mBuilder;

  // TODO: Move this to service layer
  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableLibrary mutableLibrary = new PersistentLibrary();

    JsonArray entries = pJsonObject.getJsonArray("entries");

    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableLibrary, entries.getJsonObject(0), localCache);
    mutableLibrary.commit(false);
    /*
     * URI contextURI =
     * pUriInfo.getBaseUriBuilder().path(LibraryResource.class).path(LibraryResource.class, "get")
     * .build(mutableLibrary.getId());
     */
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  protected LibraryManager getContentManager() {
    return mManager;
  }

  @Override
  protected LibraryBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(Library pReadonly) {
    return pReadonly.getLastModified();
  }
}
