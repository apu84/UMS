package org.ums.academic.resource.applications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.applications.AppConfig;
import org.ums.domain.model.mutable.applications.MutableAppConfig;
import org.ums.manager.applications.AppConfigManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 20-Sep-17.
 */
@Component
public class AppConfigResourceHelper extends ResourceHelper<AppConfig, MutableAppConfig, Long> {

  @Autowired
  AppConfigManager mAppConfigManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected AppConfigManager getContentManager() {
    return mAppConfigManager;
  }

  @Override
  protected Builder<AppConfig, MutableAppConfig> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(AppConfig pReadonly) {
    return pReadonly.getLastModified();
  }
}
