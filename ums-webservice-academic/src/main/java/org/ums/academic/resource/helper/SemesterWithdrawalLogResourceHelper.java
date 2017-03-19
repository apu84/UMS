package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.academic.resource.SemesterWithdrawalLogResource;
import org.ums.builder.SemesterWithdrawalLogBuilder;
import org.ums.domain.model.immutable.SemesterWithdrawalLog;
import org.ums.domain.model.mutable.MutableSemesterWithdrawalLog;
import org.ums.manager.SemesterWithdrawalLogManager;
import org.ums.persistent.model.PersistentSemesterWithdrawalLog;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Component
public class SemesterWithdrawalLogResourceHelper extends
    ResourceHelper<SemesterWithdrawalLog, MutableSemesterWithdrawalLog, Long> {

  @Autowired
  private SemesterWithdrawalLogManager mManager;

  @Autowired
  public SemesterWithdrawalLogBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableSemesterWithdrawalLog mutableLog = new PersistentSemesterWithdrawalLog();
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableLog, pJsonObject, localCache);
    mutableLog.create();
    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(SemesterWithdrawalLogResource.class)
            .path(SemesterWithdrawalLogResource.class, "get").build(mutableLog.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getBySemesterWithdrawalId(final int semesterWithdrawalid,
      final Request pRequest, final UriInfo pUriInfo) {
    SemesterWithdrawalLog mLog =
        getContentManager().getBySemesterWithdrawalId(semesterWithdrawalid);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    children.add(toJson(mLog, pUriInfo, localCache));
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public SemesterWithdrawalLogManager getContentManager() {
    return mManager;
  }

  @Override
  public Builder<SemesterWithdrawalLog, MutableSemesterWithdrawalLog> getBuilder() {
    return mBuilder;
  }

  @Override
  public String getETag(SemesterWithdrawalLog pReadonly) {
    return pReadonly.getLastModified();
  }
}
