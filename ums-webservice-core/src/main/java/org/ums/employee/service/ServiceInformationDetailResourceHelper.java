package org.ums.employee.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class ServiceInformationDetailResourceHelper extends
    ResourceHelper<ServiceInformationDetail, MutableServiceInformationDetail, Long> {

  private static final Logger mLogger = LoggerFactory.getLogger(ServiceInformationResourceHelper.class);

  @Autowired
  private ServiceInformationDetailManager mManager;

  @Autowired
  private ServiceInformationDetailBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableServiceInformationDetail mutableServiceInformationDetail = new PersistentServiceInformationDetail();
    mBuilder.build(mutableServiceInformationDetail, pJsonObject.getJsonObject("entries"), localCache);
    Long id = mManager.create(mutableServiceInformationDetail);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(id), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  public JsonObject get(final Long pId, final UriInfo pUriInfo) {
    if(mManager.exists(pId)) {
      List<ServiceInformationDetail> serviceInformationDetailList = mManager.getServiceDetail(pId);
      return buildJsonResponse(serviceInformationDetailList, pUriInfo);
    }
    return null;
  }

  public Response update(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableServiceInformationDetail mutableServiceInformationDetail = new PersistentServiceInformationDetail();
    mBuilder.build(mutableServiceInformationDetail, pJsonObject.getJsonObject("entries"), localCache);
    mManager.update(mutableServiceInformationDetail);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(mutableServiceInformationDetail.getId()), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  public Response delete(Long id, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableServiceInformationDetail mutableServiceInformationDetail =
        (MutableServiceInformationDetail) mManager.get(id);
    mManager.delete(mutableServiceInformationDetail);
    localCache.invalidate();
    return Response.noContent().build();
  }

  @Override
  protected ContentManager<ServiceInformationDetail, MutableServiceInformationDetail, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<ServiceInformationDetail, MutableServiceInformationDetail> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(ServiceInformationDetail pReadonly) {
    return pReadonly.getLastModified();
  }
}
