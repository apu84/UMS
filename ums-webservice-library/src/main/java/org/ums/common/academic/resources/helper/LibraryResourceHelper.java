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

import javax.json.*;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
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
  private LibraryManager mLibraryManager;

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
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getTheLibraryBooks(final String pBook, final UriInfo pUriInfo) throws Exception {
    List<Library> libs = getContentManager().getLibraryBooks(pBook);
    return ConvertToJSon(libs, pUriInfo);
  }

  public JsonObject getAllLibraryBooks(final UriInfo pUriInfo) throws Exception {
    List<Library> libs = getContentManager().getAllTheLibraryBooks();
    return ConvertToJSon(libs, pUriInfo);
  }

  private JsonObject ConvertToJSon(List<Library> plibraryBook, final UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Library l : plibraryBook) {
      children.add(toJson(l, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();

    return object.build();
  }

  public Response deleteByBookAndAuthor(String pBookName, String pAuthorname) {
    mLibraryManager.deleteByBookNameAndAuthorName(pBookName, pAuthorname);
    return Response.noContent().build();
  }

  @Override
  protected LibraryManager getContentManager() {
    return mLibraryManager;
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
