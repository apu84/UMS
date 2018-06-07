package org.ums.employee.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
public class ServiceInformationResourceHelper extends
    ResourceHelper<ServiceInformation, MutableServiceInformation, Long> {

  private static final Logger mLogger = LoggerFactory.getLogger(ServiceInformationResourceHelper.class);

  @Autowired
  private ServiceInformationManager mManager;

  @Autowired
  private ServiceInformationBuilder mBuilder;

  @Autowired
  private ServiceInformationDetailManager mServiceInformationDetailManager;

  @Autowired
  private ServiceInformationDetailBuilder mServiceInformationDetailBuilder;

  @Override
  @Transactional
  public Response post(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableServiceInformation mutableServiceInformation = new PersistentServiceInformation();
    mBuilder.build(mutableServiceInformation, pJsonObject.getJsonObject("entries"), localCache);
    Long id = mManager.create(mutableServiceInformation);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(id), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  public JsonObject get(final String pEmployeeId, final UriInfo pUriInfo) {
    if(mManager.exists(pEmployeeId)) {
      List<ServiceInformation> serviceInformationList = mManager.get(pEmployeeId);
      return buildJsonResponse(serviceInformationList, pUriInfo);
    }
    return null;
  }

  public Response update(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableServiceInformation mutableServiceInformation = new PersistentServiceInformation();
    mBuilder.build(mutableServiceInformation, pJsonObject.getJsonObject("entries"), localCache);
    mManager.update(mutableServiceInformation);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(mutableServiceInformation.getId()), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  @Transactional
  public Response delete(Long id, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutableServiceInformation mutableServiceInformation = (MutableServiceInformation) mManager.get(id);
    List<ServiceInformationDetail> serviceInformationDetailList =
        mServiceInformationDetailManager.getServiceDetail(mutableServiceInformation.getId());
    if(!serviceInformationDetailList.isEmpty()) {
      for(ServiceInformationDetail serviceInformationDetail : serviceInformationDetailList) {
        mServiceInformationDetailManager.delete((MutableServiceInformationDetail) serviceInformationDetail);
      }
    }
    mManager.delete(mutableServiceInformation);
    localCache.invalidate();
    return Response.noContent().build();
  }

  @Override
  protected ContentManager<ServiceInformation, MutableServiceInformation, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<ServiceInformation, MutableServiceInformation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(ServiceInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
