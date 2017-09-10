package org.ums.employee.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.manager.ContentManager;
import org.ums.manager.EmployeeManager;
import org.ums.manager.registrar.PublicationInformationManager;
import org.ums.resource.ResourceHelper;
import org.ums.services.ApprovePublicationService;
import org.ums.usermanagement.permission.AdditionalRolePermissionsManager;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class PublicationInformationResourceHelper extends
    ResourceHelper<PublicationInformation, MutablePublicationInformation, Long> {

  @Autowired
  PublicationInformationManager mPublicationInformationManager;

  @Autowired
  PublicationInformationBuilder mPublicationInformationBuilder;

  @Autowired
  ApprovePublicationService mApprovePublicationService;

  @Autowired
  AdditionalRolePermissionsManager mAdditionalRolePermissionsManager;

  @Autowired
  EmployeeManager mEmployeeManager;

  public JsonObject getPublicationInformation(final String pEmployeeId, final UriInfo pUriInfo) {
    List<PublicationInformation> pPublicationInformation = new ArrayList<>();
    try {
      pPublicationInformation = mPublicationInformationManager.getEmployeePublicationInformation(pEmployeeId);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(pPublicationInformation, pUriInfo);
  }

  public JsonObject getPublicationWithPagination(final String pEmployeeId, final String pPublicationStatus,
      final int pPageNumber, final int pItemPerPage, final UriInfo pUriInfo) {
    List<PublicationInformation> pPublicationInformation = new ArrayList<>();
    try {
      pPublicationInformation =
          mPublicationInformationManager.getPublicationInformationWithPagination(pEmployeeId, pPublicationStatus,
              pPageNumber, pItemPerPage);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(pPublicationInformation, pUriInfo);
  }

  public JsonObject getPublicationWithPagination(final String pEmployeeId, final int pPageNumber,
      final int pItemPerPage, final UriInfo pUriInfo) {
    List<PublicationInformation> pPublicationInformation = new ArrayList<>();
    try {
      pPublicationInformation =
          mPublicationInformationManager
              .getPublicationInformationWithPagination(pEmployeeId, pPageNumber, pItemPerPage);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(pPublicationInformation, pUriInfo);
  }

  public JsonObject getPublicationInformation(final String pEmployeeId, final String pStatus, final UriInfo pUriInfo) {
    List<PublicationInformation> pPublicationInformation = new ArrayList<>();
    try {
      pPublicationInformation = mPublicationInformationManager.getEmployeePublicationInformation(pEmployeeId, pStatus);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(pPublicationInformation, pUriInfo);
  }

  public JsonObject getTeachersList(final String pPublicationStatus, final UriInfo pUriInfo) {
    List<PublicationInformation> pPublicationInformation = new ArrayList<>();
    try {
      pPublicationInformation = mPublicationInformationManager.getPublicationInformation(pPublicationStatus);
    } catch(EmptyResultDataAccessException e) {

    }
    return toJson(pPublicationInformation, pUriInfo);
  }

  @Transactional
  public Response savePublicationInformation(JsonObject pJsonObject, UriInfo pUriInfo) {
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonArray publicationJsonArray = entries.getJsonObject(0).getJsonArray("publication");
    int sizeOfPublicationJsonArray = publicationJsonArray.size();

    List<MutablePublicationInformation> createMutablePublicationInformation = new ArrayList<>();
    List<MutablePublicationInformation> updateMutablePublicationInformation = new ArrayList<>();
    List<MutablePublicationInformation> deleteMutablePublicationInformation = new ArrayList<>();

    for(int i = 0; i < sizeOfPublicationJsonArray; i++) {
      MutablePublicationInformation publicationInformation = new PersistentPublicationInformation();
      mPublicationInformationBuilder.build(publicationInformation, publicationJsonArray.getJsonObject(i), localCache);
      if(publicationJsonArray.getJsonObject(i).containsKey("dbAction")) {
        if(publicationJsonArray.getJsonObject(i).getString("dbAction").equals("Create")) {
          createMutablePublicationInformation.add(publicationInformation);
        }
        else if(publicationJsonArray.getJsonObject(i).getString("dbAction").equals("Update")) {
          updateMutablePublicationInformation.add(publicationInformation);
        }
        else if(publicationJsonArray.getJsonObject(i).getString("dbAction").equals("Delete")) {
          deleteMutablePublicationInformation.add(publicationInformation);
        }
      }
      else {
        Response.ResponseBuilder builder = Response.created(null);
        builder.status(Response.Status.NOT_MODIFIED);
        return builder.build();
      }
    }

    if(createMutablePublicationInformation.size() != 0) {
      mPublicationInformationManager.savePublicationInformation(createMutablePublicationInformation);
    }
    if(updateMutablePublicationInformation.size() != 0) {
      mPublicationInformationManager.updatePublicationInformation(updateMutablePublicationInformation);
    }
    if(deleteMutablePublicationInformation.size() != 0) {
      mPublicationInformationManager.deletePublicationInformation(deleteMutablePublicationInformation);
    }
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Transactional
  public Response updatePublicationStatus(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutablePublicationInformation publicationInformation = new PersistentPublicationInformation();
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonObject jsonObject = entries.getJsonObject(0).getJsonObject("publication");
    mPublicationInformationBuilder.updatePublicationInformationBuilder(publicationInformation, jsonObject, localCache);
    mPublicationInformationManager.updatePublicationStatus(publicationInformation);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  private JsonObject toJson(List<PublicationInformation> pPublicationInformation, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(PublicationInformation publicationInformation : pPublicationInformation) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, publicationInformation, pUriInfo, localCache);
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
  protected ContentManager<PublicationInformation, MutablePublicationInformation, Long> getContentManager() {
    return mPublicationInformationManager;
  }

  @Override
  protected Builder<PublicationInformation, MutablePublicationInformation> getBuilder() {
    return mPublicationInformationBuilder;
  }

  @Override
  protected String getETag(PublicationInformation pReadonly) {
    return pReadonly.getLastModified();
  }
}
