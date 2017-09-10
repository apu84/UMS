package org.ums.employee.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.manager.ContentManager;
import org.ums.manager.registrar.*;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
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

  public JsonObject getServiceInformation(final String pEmployeeId, final UriInfo pUriInfo) {
    List<ServiceInformation> pServiceInformation = new ArrayList<>();
    try {
      pServiceInformation = mManager.getServiceInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {
    }
    return toJson(pServiceInformation, pUriInfo);
  }

  @Transactional
  public Response saveOrUpdateServiceInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonArray serviceJsonArray = entries.getJsonObject(0).getJsonArray("service");
    int sizeOfServiceJsonArray = serviceJsonArray.size();

    for(int i = 0; i < sizeOfServiceJsonArray; i++) {
      MutableServiceInformation mutableServiceInformation = new PersistentServiceInformation();
      mBuilder.build(mutableServiceInformation, serviceJsonArray.getJsonObject(i), localCache);
      if(serviceJsonArray.getJsonObject(i).containsKey("dbAction")) {
        if(serviceJsonArray.getJsonObject(i).getString("dbAction").equals("Create")) {
          Long serviceId = mManager.saveServiceInformation(mutableServiceInformation);
          JsonArray serviceDetailJsonArray = serviceJsonArray.getJsonObject(i).getJsonArray("intervalDetails");
          int sizeOfServiceDetailsJsonArray = serviceDetailJsonArray.size();
          List<MutableServiceInformationDetail> createMutableServiceInformationDetail = new ArrayList<>();
          List<MutableServiceInformationDetail> updateMutableServiceInformationDetail = new ArrayList<>();
          List<MutableServiceInformationDetail> deleteMutableServiceInformationDetail = new ArrayList<>();
          for(int j = 0; j < sizeOfServiceDetailsJsonArray; j++) {
            MutableServiceInformationDetail mutableServiceInformationDetail = new PersistentServiceInformationDetail();
            mServiceInformationDetailBuilder.serviceInformationDetailBuilder(mutableServiceInformationDetail,
                serviceDetailJsonArray.getJsonObject(j), localCache, serviceId);
            if(serviceDetailJsonArray.getJsonObject(j).containsKey("dbAction")) {
              if(serviceDetailJsonArray.getJsonObject(j).getString("dbAction").equals("Create")) {
                createMutableServiceInformationDetail.add(mutableServiceInformationDetail);
              }
              else if(serviceDetailJsonArray.getJsonObject(j).getString("dbAction").equals("Update")) {
                updateMutableServiceInformationDetail.add(mutableServiceInformationDetail);
              }
              else if(serviceDetailJsonArray.getJsonObject(j).getString("dbAction").equals("Delete")) {
                deleteMutableServiceInformationDetail.add(mutableServiceInformationDetail);
              }
            }
          }

          if(createMutableServiceInformationDetail.size() > 0) {
            mServiceInformationDetailManager.saveServiceInformationDetail(createMutableServiceInformationDetail);
          }
          if(updateMutableServiceInformationDetail.size() > 0) {
            mServiceInformationDetailManager.updateServiceInformationDetail(updateMutableServiceInformationDetail);
          }
          if(deleteMutableServiceInformationDetail.size() > 0) {
            mServiceInformationDetailManager.deleteServiceInformationDetail(deleteMutableServiceInformationDetail);
          }
        }
        else if(serviceJsonArray.getJsonObject(i).getString("dbAction").equals("Update")) {
          mManager.updateServiceInformation(mutableServiceInformation);
          Long serviceId = Long.parseLong(serviceJsonArray.getJsonObject(i).getString("id"));
          JsonArray serviceDetailJsonArray = serviceJsonArray.getJsonObject(i).getJsonArray("intervalDetails");
          int sizeOfServiceDetailsJsonArray = serviceDetailJsonArray.size();
          List<MutableServiceInformationDetail> createMutableServiceInformationDetail = new ArrayList<>();
          List<MutableServiceInformationDetail> updateMutableServiceInformationDetail = new ArrayList<>();
          List<MutableServiceInformationDetail> deleteMutableServiceInformationDetail = new ArrayList<>();
          for(int j = 0; j < sizeOfServiceDetailsJsonArray; j++) {
            MutableServiceInformationDetail mutableServiceInformationDetail = new PersistentServiceInformationDetail();
            mServiceInformationDetailBuilder.serviceInformationDetailBuilder(mutableServiceInformationDetail,
                serviceDetailJsonArray.getJsonObject(j), localCache, serviceId);
            if(serviceDetailJsonArray.getJsonObject(j).containsKey("dbAction")) {
              if(serviceDetailJsonArray.getJsonObject(j).getString("dbAction").equals("Create")) {
                createMutableServiceInformationDetail.add(mutableServiceInformationDetail);
              }
              else if(serviceDetailJsonArray.getJsonObject(j).getString("dbAction").equals("Update")) {
                updateMutableServiceInformationDetail.add(mutableServiceInformationDetail);
              }
              else if(serviceDetailJsonArray.getJsonObject(j).getString("dbAction").equals("Delete")) {
                deleteMutableServiceInformationDetail.add(mutableServiceInformationDetail);
              }
            }
          }

          if(createMutableServiceInformationDetail.size() > 0) {
            mServiceInformationDetailManager.saveServiceInformationDetail(createMutableServiceInformationDetail);
          }
          if(updateMutableServiceInformationDetail.size() > 0) {
            mServiceInformationDetailManager.updateServiceInformationDetail(updateMutableServiceInformationDetail);
          }
          if(deleteMutableServiceInformationDetail.size() > 0) {
            mServiceInformationDetailManager.deleteServiceInformationDetail(deleteMutableServiceInformationDetail);
          }
        }
        else if(serviceJsonArray.getJsonObject(i).getString("dbAction").equals("Delete")) {
          mManager.deleteServiceInformation(mutableServiceInformation);
          Long serviceId = Long.parseLong(serviceJsonArray.getJsonObject(i).getString("id"));
          JsonArray serviceDetailJsonArray = serviceJsonArray.getJsonObject(i).getJsonArray("intervalDetails");
          int sizeOfServiceDetailsJsonArray = serviceDetailJsonArray.size();
          List<MutableServiceInformationDetail> createMutableServiceInformationDetail = new ArrayList<>();
          List<MutableServiceInformationDetail> updateMutableServiceInformationDetail = new ArrayList<>();
          List<MutableServiceInformationDetail> deleteMutableServiceInformationDetail = new ArrayList<>();
          for(int j = 0; j < sizeOfServiceDetailsJsonArray; j++) {
            MutableServiceInformationDetail mutableServiceInformationDetail = new PersistentServiceInformationDetail();
            mServiceInformationDetailBuilder.serviceInformationDetailBuilder(mutableServiceInformationDetail,
                serviceDetailJsonArray.getJsonObject(j), localCache, serviceId);
            if(serviceDetailJsonArray.getJsonObject(j).containsKey("dbAction")) {
              if(serviceDetailJsonArray.getJsonObject(j).getString("dbAction").equals("Create")) {
                createMutableServiceInformationDetail.add(mutableServiceInformationDetail);
              }
              else if(serviceDetailJsonArray.getJsonObject(j).getString("dbAction").equals("Update")) {
                updateMutableServiceInformationDetail.add(mutableServiceInformationDetail);
              }
              else if(serviceDetailJsonArray.getJsonObject(j).getString("dbAction").equals("Delete")) {
                deleteMutableServiceInformationDetail.add(mutableServiceInformationDetail);
              }
            }
          }

          if(createMutableServiceInformationDetail.size() > 0) {
            mServiceInformationDetailManager.saveServiceInformationDetail(createMutableServiceInformationDetail);
          }
          if(updateMutableServiceInformationDetail.size() > 0) {
            mServiceInformationDetailManager.updateServiceInformationDetail(updateMutableServiceInformationDetail);
          }
          if(deleteMutableServiceInformationDetail.size() > 0) {
            mServiceInformationDetailManager.deleteServiceInformationDetail(deleteMutableServiceInformationDetail);
          }
        }
      }
    }

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(List<ServiceInformation> pServiceInformation, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(ServiceInformation serviceInformation : pServiceInformation) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, serviceInformation, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
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
