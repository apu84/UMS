package org.ums.employee.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.manager.ContentManager;
import org.ums.manager.EmployeeManager;
import org.ums.resource.ResourceHelper;
import org.ums.services.ApprovePublicationService;
import org.ums.usermanagement.permission.AdditionalRolePermissionsManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class PublicationInformationResourceHelper extends
    ResourceHelper<PublicationInformation, MutablePublicationInformation, Long> {

  @Autowired
  PublicationInformationManager mManager;

  @Autowired
  PublicationInformationBuilder mBuilder;

  @Autowired
  ApprovePublicationService mApprovePublicationService;

  @Autowired
  AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;

  @Autowired
  EmployeeManager mEmployeeManager;

  @Override
  public Response post(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutablePublicationInformation mutablePublicationInformation = new PersistentPublicationInformation();
    mBuilder.build(mutablePublicationInformation, pJsonObject.getJsonObject("entries"), localCache);
    Long id = mManager.create(mutablePublicationInformation);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(id), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  public JsonObject get(final String pEmployeeId, final UriInfo pUriInfo) {
    if(mManager.exists(pEmployeeId)) {
      List<PublicationInformation> publicationInformationList = mManager.get(pEmployeeId);
      return buildJsonResponse(publicationInformationList, pUriInfo);
    }
    return null;
  }

  public Response update(JsonObject pJsonObject, final UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutablePublicationInformation mutablePublicationInformation = new PersistentPublicationInformation();
    mBuilder.build(mutablePublicationInformation, pJsonObject.getJsonObject("entries"), localCache);
    mManager.update(mutablePublicationInformation);
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    mBuilder.build(objectBuilder, mManager.get(mutablePublicationInformation.getId()), pUriInfo, localCache);
    localCache.invalidate();
    return Response.ok(objectBuilder.build()).build();
  }

  public Response delete(Long id, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    MutablePublicationInformation mutablePublicationInformation = (MutablePublicationInformation) mManager.get(id);
    mManager.delete(mutablePublicationInformation);
    localCache.invalidate();
    return Response.noContent().build();
  }

  @Override
  protected ContentManager<PublicationInformation, MutablePublicationInformation, Long> getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<PublicationInformation, MutablePublicationInformation> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(PublicationInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
