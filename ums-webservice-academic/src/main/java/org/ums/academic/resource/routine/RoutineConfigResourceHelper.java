package org.ums.academic.resource.routine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.generator.IdGenerator;
import org.ums.manager.routine.RoutineConfigManager;
import org.ums.persistent.model.routine.PersistentRoutineConfig;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Component
public class RoutineConfigResourceHelper extends ResourceHelper<RoutineConfig, MutableRoutineConfig, Long> {

  @Autowired
  private RoutineConfigManager mRoutineConfigManager;
  @Autowired
  private RoutineConfigBuilder mRoutineConfigBuilder;
  @Autowired
  private IdGenerator mIdGenerator;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableRoutineConfig routineConfig = new PersistentRoutineConfig();
    LocalCache localCache = new LocalCache();
    getBuilder().build(routineConfig, pJsonObject, localCache);
    routineConfig.setId(mIdGenerator.getNumericId());
    getContentManager().create(routineConfig);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public MutableRoutineConfig createOrUpdate(JsonObject pJsonObject, UriInfo pUrInfo) throws Exception {
    MutableRoutineConfig routineConfig = new PersistentRoutineConfig();
    LocalCache localCache = new LocalCache();
    getBuilder().build(routineConfig, pJsonObject, localCache);
    if(routineConfig.getId() != null)
      getContentManager().update(routineConfig);
    else {
      routineConfig.setId(mIdGenerator.getNumericId());
      getContentManager().create(routineConfig);
    }
    return routineConfig;
  }

  public JsonObject get(final Integer pSemesterId, final Integer pProgramId, final UriInfo pUriInfo) {
    RoutineConfig routineConfig = getContentManager().get(pSemesterId, pProgramId);
    LocalCache localCache = new LocalCache();
    return toJson(routineConfig, pUriInfo, localCache);
  }

  @Override
  protected RoutineConfigManager getContentManager() {
    return mRoutineConfigManager;
  }

  @Override
  protected Builder<RoutineConfig, MutableRoutineConfig> getBuilder() {
    return mRoutineConfigBuilder;
  }

  @Override
  protected String getETag(RoutineConfig pReadonly) {
    return pReadonly.getLastModified();
  }
}
