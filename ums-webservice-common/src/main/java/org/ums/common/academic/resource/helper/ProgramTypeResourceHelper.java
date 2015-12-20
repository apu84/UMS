package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentProgramType;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ProgramTypeResource;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.MutableProgramType;
import org.ums.domain.model.ProgramType;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Component
public class ProgramTypeResourceHelper extends ResourceHelper<ProgramType, MutableProgramType, Integer> {
  @Autowired
  @Qualifier("programTypeManager")
  private ContentManager<ProgramType, MutableProgramType, Integer> mManager;

  @Autowired
  private List<Builder<ProgramType, MutableProgramType>> mBuilders;

  @Override
  public ContentManager<ProgramType, MutableProgramType, Integer> getContentManager() {
    return mManager;
  }

  @Override
  public List<Builder<ProgramType, MutableProgramType>> getBuilders() {
    return mBuilders;
  }

  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) throws Exception {
    MutableProgramType mutableProgramType = new PersistentProgramType();
    LocalCache localCache = new LocalCache();
    for (Builder<ProgramType, MutableProgramType> builder : mBuilders) {
      builder.build(mutableProgramType, pJsonObject, localCache);
    }
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
