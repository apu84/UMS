package org.ums.academic.resource.fee.certificate;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Department;
import org.ums.fee.FeeType;
import org.ums.fee.certificate.*;
import org.ums.manager.ContentManager;
import org.ums.resource.ResourceHelper;
import org.ums.resource.filter.FilterItem;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class CertificateStatusHelper extends ResourceHelper<CertificateStatus, MutableCertificateStatus, Long> {
  @Autowired
  CertificateStatusBuilder mCertificateStatusBuilder;
  @Autowired
  CertificateStatusManager mCertificateStatusManager;
  @Autowired
  CertificateStatusLogManager mCertificateStatusLogManager;

  private List<FilterItem> mFilterItems;

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
    if (pageNumber > 1) {
      addLink("previous", pageNumber, itemsPerPage, pUriInfo, jsonObjectBuilder);
    }
    return jsonObjectBuilder.build();
  }

  JsonObject getFilteredCertificateStatus(int itemsPerPage, int pageNumber, FeeType pFeeType, Department pDepartment,
      JsonObject pJsonObject, UriInfo pUriInfo) {
    List<CertificateStatus> certificateStatusList =
        mCertificateStatusManager.paginatedFilteredList(itemsPerPage, pageNumber, buildFilterQuery(pJsonObject),
            pFeeType, pDepartment);
    return getJsonObject(itemsPerPage, pageNumber, pUriInfo, certificateStatusList);
  }

  private JsonObject getJsonObject(int itemsPerPage, int pageNumber, UriInfo pUriInfo, List<CertificateStatus> pCertificateStatusList) {
    LocalCache cache = new LocalCache();
    JsonArrayBuilder array = Json.createArrayBuilder();
    pCertificateStatusList.forEach((certificate) -> {
      array.add(toJson(certificate, pUriInfo, cache));
    });
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("entries", array);
    addLink("next", pageNumber, itemsPerPage, pUriInfo, jsonObjectBuilder);
    if (pCertificateStatusList.size() > 0) {
      addLink("next", pageNumber, itemsPerPage, pUriInfo, jsonObjectBuilder);
    }
    if (pageNumber > 1) {
      addLink("previous", pageNumber, itemsPerPage, pUriInfo, jsonObjectBuilder);
    }
    return jsonObjectBuilder.build();
  }

  JsonObject getCertificateStatusByStatusAndFeeTypePaginated(int itemsPerPage, int pageNumber,
      CertificateStatus.Status pStatus, FeeType pFeeType, UriInfo pUriInfo) {
    List<CertificateStatus> certificateStatusList =
        mCertificateStatusManager.getByStatusAndFeeTypePaginated(itemsPerPage, pageNumber, pStatus, pFeeType);
    return getJsonObject(itemsPerPage, pageNumber, pUriInfo, certificateStatusList);
  }

  JsonObject getCertificateStatusByStatusAndFeeType(CertificateStatus.Status pStatus, FeeType pFeeType, UriInfo pUriInfo) {
    List<CertificateStatus> certificateStatusList = mCertificateStatusManager.getByStatusAndFeeType(pStatus, pFeeType);
    LocalCache cache = new LocalCache();
    JsonArrayBuilder array = Json.createArrayBuilder();
    certificateStatusList.forEach((certificate) -> {
      array.add(toJson(certificate, pUriInfo, cache));
    });
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    jsonObjectBuilder.add("entries", array);
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
    builder.path(pUriInfo.getPath()).queryParam("pageNumber", nextPage).queryParam("itemsPerPage", itemsPerPage);
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
    insertIntoLog(mutableCertificateStatuses);
    return Response.ok().build();
  }

  private void insertIntoLog(List<MutableCertificateStatus> pCertificateStatuses) {
    List<MutableCertificateStatusLog> logs = new ArrayList<>();
    for(CertificateStatus status : pCertificateStatuses) {
      MutableCertificateStatusLog log = new PersistentCertificateStatusLog();
      log.setCertificateStatusId(status.getId());
      log.setStatus(status.getStatus());
      log.setProcessedOn(status.getProcessedOn());
      log.setProcessedBy(status.getUserId());
      logs.add(log);
    }
    mCertificateStatusLogManager.create(logs);
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

  JsonArray getFilterItems() {
    if(mFilterItems == null) {
      mFilterItems = buildFilter();
    }
    return getFilterJson(mFilterItems);
  }

  private List<FilterItem> buildFilter() {
    List<FilterItem> filters = new ArrayList<>();

    filters.add(new FilterItem("Student Id", CertificateStatusManager.FilterCriteria.STUDENT_ID.toString(),
        FilterItem.Type.INPUT));

    FilterItem status =
        new FilterItem("Status", CertificateStatusManager.FilterCriteria.STATUS.toString(), FilterItem.Type.SELECT);
    status.addOption("Applied", 1);
    status.addOption("Processed", 2);
    status.addOption("Delivered", 3);
    status.addOption("Waiting for Head's forwarding", 4);
    status.addOption("Forwarded by Head", 5);
    filters.add(status);

    FilterItem feeId =
        new FilterItem("Fee Id", CertificateStatusManager.FilterCriteria.FEE_ID.toString(), FilterItem.Type.SELECT);
    filters.add(feeId);

    return filters;
  }
}
