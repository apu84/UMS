package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.builder.ServiceInformationBuilder;
import org.ums.builder.ServiceInformationDetailBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.ServiceInformation;
import org.ums.domain.model.mutable.registrar.*;
import org.ums.enums.common.EmploymentPeriod;
import org.ums.manager.ContentManager;
import org.ums.manager.registrar.*;
import org.ums.persistent.model.registrar.*;
import org.ums.resource.ResourceHelper;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceInformationResourceHelper extends
    ResourceHelper<ServiceInformation, MutableServiceInformation, Integer> {

  private static final Logger mLogger = LoggerFactory.getLogger(ServiceInformationResourceHelper.class);

  @Autowired
  private UserManager mUserManager;

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
      pServiceInformation =
          mManager.getServiceInformation(mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString())
              .getEmployeeId());
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

    List<MutableServiceInformation> createMutableServiceInformation = new ArrayList<>();
    for(int i = 0; i < sizeOfServiceJsonArray; i++) {
      MutableServiceInformation mutableServiceInformation = new PersistentServiceInformation();
      mBuilder.build(mutableServiceInformation, serviceJsonArray.getJsonObject(i), localCache);
      createMutableServiceInformation.add(mutableServiceInformation);
    }
    mManager.saveServiceInformation(createMutableServiceInformation);

    JsonArray serviceDetailJsonArray = serviceJsonArray.getJsonObject(0).getJsonArray("intervalDetails");
    int sizeOfServiceDetailsJsonArray = serviceDetailJsonArray.size();
    List<MutableServiceInformationDetail> pMutableServiceInformationDetail = new ArrayList<>();
    for(int i = 0; i < sizeOfServiceDetailsJsonArray; i++) {
      MutableServiceInformationDetail mutableServiceInformationDetail = new PersistentServiceInformationDetail();
      mServiceInformationDetailBuilder.build(mutableServiceInformationDetail, serviceDetailJsonArray.getJsonObject(i),
          localCache);
      pMutableServiceInformationDetail.add(mutableServiceInformationDetail);
    }
    mServiceInformationDetailManager.saveServiceInformationDetail(pMutableServiceInformationDetail);

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
  protected ContentManager<ServiceInformation, MutableServiceInformation, Integer> getContentManager() {
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
