package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.persistent.model.PersistentProgramType;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ProgramTypeResource;
import org.ums.common.ResourceHelper;
import org.ums.common.builder.ProgramTypeBuilder;
import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.manager.ProgramTypeManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Component
public class ProgramTypeResourceHelper extends ResourceHelper<ProgramType, MutableProgramType, Integer> {
  @Autowired
  private ProgramTypeManager mManager;

  @Autowired
  private ProgramTypeBuilder mBuilder;

  @Override
  public ProgramTypeManager getContentManager() {
    return mManager;
  }

  @Override
  public ProgramTypeBuilder getBuilder() {
    return mBuilder;
  }

  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    MutableProgramType mutableProgramType = new PersistentProgramType();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableProgramType, pJsonObject, localCache);
    mutableProgramType.commit(false);

    URI contextURI = pUriInfo.getBaseUriBuilder().path(ProgramTypeResource.class).path(ProgramTypeResource.class, "get")
        .build(mutableProgramType.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  @Override
  protected String getEtag(ProgramType pReadonly) {
    return "";
  }
}
