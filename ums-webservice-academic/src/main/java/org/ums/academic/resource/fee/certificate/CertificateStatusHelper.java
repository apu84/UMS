package org.ums.academic.resource.fee.certificate;

import java.util.ArrayList;
import java.util.List;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.fee.certificate.CertificateStatus;
import org.ums.fee.certificate.CertificateStatusManager;
import org.ums.fee.certificate.MutableCertificateStatus;
import org.ums.fee.certificate.PersistentCertificateStatus;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;

@Component
public class CertificateStatusHelper extends ResourceHelper<CertificateStatus, MutableCertificateStatus, Long> {
  @Autowired
  CertificateStatusBuilder mCertificateStatusBuilder;
  @Autowired
  CertificateStatusManager mCertificateStatusManager;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    throw new NotImplementedException();
  }

  JsonObject getCertificateStatus(int itemsPerPage, int pageNumber, UriInfo pUriInfo) {
    List<CertificateStatus> certificateStatusList = mCertificateStatusManager.paginatedList(itemsPerPage, pageNumber);
    LocalCache cache = new LocalCache();
    JsonArrayBuilder array = Json.createArrayBuilder();
    certificateStatusList.forEach((certificate) -> {
      array.add(toJson(certificate, pUriInfo, cache));
    });
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("entries", array);
    addLink("next", pageNumber, itemsPerPage, pUriInfo, jsonObjectBuilder);
    if(pageNumber > 1) {
      addLink("previous", pageNumber, itemsPerPage, pUriInfo, jsonObjectBuilder);
    }
    return jsonObjectBuilder.build();
  }

  JsonObject getFilteredCertificateStatus(int itemsPerPage, int pageNumber, Integer status, UriInfo pUriInfo) {
    List<CertificateStatus> certificateStatusList =
        mCertificateStatusManager.paginatedFilteredList(itemsPerPage, pageNumber, CertificateStatus.Status.get(status));
    LocalCache cache = new LocalCache();
    JsonArrayBuilder array = Json.createArrayBuilder();
    certificateStatusList.forEach((certificate) -> {
      array.add(toJson(certificate, pUriInfo, cache));
    });
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("entries", array);
    addLink("next", pageNumber, itemsPerPage, pUriInfo, jsonObjectBuilder);
    if(pageNumber > 1) {
      addLink("previous", pageNumber, itemsPerPage, pUriInfo, jsonObjectBuilder);
    }
    return jsonObjectBuilder.build();
  }

  JsonObject getCertificateStatus(String pStudentId, UriInfo pUriInfo) {
    List<CertificateStatus> certificateStatusList = mCertificateStatusManager.getByStudent(pStudentId);
    LocalCache cache = new LocalCache();
    JsonArrayBuilder array = Json.createArrayBuilder();
    certificateStatusList.forEach((certificate) -> {
      array.add(toJson(certificate, pUriInfo, cache));
    });
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("entries", array);
    return jsonObjectBuilder.build();
  }

  private void addLink(String direction, Integer pCurrentPage, Integer itemsPerPage, UriInfo pUriInfo,
      JsonObjectBuilder pJsonObjectBuilder) {
    UriBuilder builder = pUriInfo.getBaseUriBuilder();
    Integer nextPage = direction.equalsIgnoreCase("next") ? pCurrentPage + 1 : pCurrentPage - 1;
    builder.path(pUriInfo.getPath()).queryParam("page", nextPage).queryParam("itemsPerPage", itemsPerPage);
    pJsonObjectBuilder.add(direction, builder.build().toString());
  }

  Response updateCertificateStatus(JsonObject pJsonObject, UriInfo pUriInfo) {
    Validate.notEmpty(pJsonObject);
    Validate.notEmpty(pJsonObject.getJsonArray("entries"));
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache cache = new LocalCache();
    List<MutableCertificateStatus> mutableCertificateStatuses = new ArrayList<>();

    for(JsonValue entry : entries) {
      JsonObject entryObject = (JsonObject) entry;
      MutableCertificateStatus updated = new PersistentCertificateStatus();
      getBuilder().build(updated, entryObject, cache);
      updated.setUserId(SecurityUtils.getSubject().getPrincipal().toString());
      CertificateStatus latest = mCertificateStatusManager.get(Long.parseLong(entryObject.getString("id")));
      if(!hasUpdatedVersion(latest, updated)) {
        return Response.status(Response.Status.PRECONDITION_FAILED).build();
      }
      updated.setFeeCategoryId(latest.getFeeCategoryId());
      updated.setSemesterId(latest.getSemesterId());
      updated.setStudentId(latest.getStudentId());
      mutableCertificateStatuses.add(updated);
    }
    mCertificateStatusManager.update(mutableCertificateStatuses);
    return Response.ok().build();
  }

  @Override
  protected ContentManager<CertificateStatus, MutableCertificateStatus, Long> getContentManager() {
    return mCertificateStatusManager;
  }

  @Override
  protected Builder<CertificateStatus, MutableCertificateStatus> getBuilder() {
    return mCertificateStatusBuilder;
  }

  @Override
  protected String getETag(CertificateStatus pReadonly) {
    return pReadonly.getLastModified();
  }
}
