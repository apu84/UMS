package org.ums.punishment.authority;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class AuthorityResourceHelper extends ResourceHelper<Authority, MutableAuthority, Long> {

  @Autowired
  private AuthorityManager mManager;

  @Autowired
  private AuthorityBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  @Override
  protected ContentManager<Authority, MutableAuthority, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<Authority, MutableAuthority> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(Authority pReadonly) {
    return pReadonly.getLastModified();
  }
}
