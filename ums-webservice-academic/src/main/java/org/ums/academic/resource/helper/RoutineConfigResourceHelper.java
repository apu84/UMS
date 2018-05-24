package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.RoutineConfigBuilder;
import org.ums.domain.model.immutable.routine.Routine;
import org.ums.domain.model.immutable.routine.RoutineConfig;
import org.ums.domain.model.mutable.routine.MutableRoutineConfig;
import org.ums.manager.ContentManager;
import org.ums.manager.routine.RoutineConfigManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class RoutineConfigResourceHelper extends ResourceHelper<RoutineConfig, MutableRoutineConfig, Long> {

  @Autowired
  private RoutineConfigManager mRoutineConfigManager;
  @Autowired
  private RoutineConfigBuilder mRoutineConfigBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
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
