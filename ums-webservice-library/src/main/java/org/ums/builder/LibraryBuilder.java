package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Library;
import org.ums.domain.model.mutable.MutableLibrary;
import org.ums.manager.BinaryContentManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;

/**
 * Created by kawsu on 12/5/2016.
 */

@Component
@Qualifier("LibraryBuilder")
public class LibraryBuilder implements Builder<Library, MutableLibrary> {

  @Autowired
  private DateFormat mDateFormate;

  @Autowired
  @Qualifier("fileContentManager")
  private BinaryContentManager<byte[]> mBinaryContentManager;

  public void build(final JsonObjectBuilder pBuidler, final Library pLibrary,
      final UriInfo pUriInfo, final LocalCache pLocalCache) {
    pBuidler.add("bookName", pLibrary.getBookName());
    pBuidler.add("authorName", pLibrary.getAuthorName());
  }

  @Override
  public void build(final MutableLibrary pMutableLibrary, final JsonObject pJsonObject,
      final LocalCache pLocalCache) {
    String book = pJsonObject.getString("book");
    String author = pJsonObject.getString("author");
    pMutableLibrary.setBookName(pJsonObject.getString("book"));
    pMutableLibrary.setAuthorName(pJsonObject.getString("author"));

  }
}
