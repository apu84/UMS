package org.ums.academic.resource.fee.installment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.semesterfee.InstallmentSettings;
import org.ums.fee.semesterfee.InstallmentSettingsManager;
import org.ums.fee.semesterfee.MutableInstallmentSettings;
import org.ums.fee.semesterfee.PersistentInstallmentSettings;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@Component
public class InstallmentSettingsHelper extends ResourceHelper<InstallmentSettings, MutableInstallmentSettings, Long> {
  @Autowired
  InstallmentSettingsManager mInstallmentSettingsManager;

  @Autowired
  InstallmentSettingsBuilder mInstallmentSettingsBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    LocalCache localCache = new LocalCache();
    MutableInstallmentSettings mutable = new PersistentInstallmentSettings();
    getBuilder().build(mutable, pJsonObject, localCache);
    mutable.create();
    return Response.ok().build();
  }

  public JsonObject getInstallmentSettings(Integer pSemesterId, UriInfo pUriInfo) {
    Optional<InstallmentSettings> settings = mInstallmentSettingsManager.getInstallmentSettings(pSemesterId);
    LocalCache localCache = new LocalCache();
    if(settings.isPresent()) {
      return toJson(settings.get(), pUriInfo, localCache);
    }
    return Json.createObjectBuilder().build();
  }

  @Override
  protected ContentManager<InstallmentSettings, MutableInstallmentSettings, Long> getContentManager() {
    return mInstallmentSettingsManager;
  }

  @Override
  protected Builder<InstallmentSettings, MutableInstallmentSettings> getBuilder() {
    return mInstallmentSettingsBuilder;
  }

  @Override
  protected String getETag(InstallmentSettings pReadonly) {
    return pReadonly.getLastModified();
  }
}
