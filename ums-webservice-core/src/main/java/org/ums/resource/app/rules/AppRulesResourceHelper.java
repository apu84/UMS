package org.ums.resource.app.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.applications.AppRules;
import org.ums.domain.model.mutable.applications.MutableAppRules;
import org.ums.manager.ContentManager;
import org.ums.manager.applications.AppRulesManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 28-Sep-17.
 */
@Component
public class AppRulesResourceHelper extends ResourceHelper<AppRules, MutableAppRules, Long> {

  @Autowired
  AppRulesManager mAppRulesManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<AppRules, MutableAppRules, Long> getContentManager() {
    return null;
  }

  @Override
  protected Builder<AppRules, MutableAppRules> getBuilder() {
    return null;
  }

  @Override
  protected String getETag(AppRules pReadonly) {
    return null;
  }
}
