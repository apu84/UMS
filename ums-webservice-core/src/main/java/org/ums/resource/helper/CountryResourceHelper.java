package org.ums.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.CountryBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.common.MutableCountry;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.manager.ContentManager;
import org.ums.manager.common.CountryManager;
import org.ums.manager.library.AuthorManager;
import org.ums.persistent.model.library.PersistentAuthor;
import org.ums.resource.ResourceHelper;
import org.ums.resource.SemesterResource;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by Ifti on 31-Jan-17.
 */
@Component
public class CountryResourceHelper extends ResourceHelper<Country, MutableCountry, Integer> {

  @Autowired
  private CountryManager mManager;

  @Autowired
  private CountryBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Country, MutableCountry, Integer> getContentManager() {
    return mManager;
  }

  @Override
  public CountryBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Country pReadonly) {
    return pReadonly.getLastModified();
  }

}
