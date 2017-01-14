package org.ums.academic.resource.helper;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.cache.LocalCache;
import org.ums.builder.AdmissionStudentsCertificateHistoryBuilder;
import org.ums.domain.model.immutable.AdmissionStudentsCertificateHistory;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateHistory;
import org.ums.manager.AdmissionStudentsCertificateHistoryManager;
import org.ums.persistent.model.PersistentAdmissionStudentsCertificateHistory;
import org.ums.resource.ResourceHelper;
import org.slf4j.LoggerFactory;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kawsu on 1/11/2017.
 */
@Component
public class AdmissionStudentsCertificateHistoryResourceHelper
    extends
    ResourceHelper<AdmissionStudentsCertificateHistory, MutableAdmissionStudentsCertificateHistory, Integer> {

  private static final Logger mLoger = LoggerFactory
      .getLogger(AdmissionStudentsCertificateHistoryResourceHelper.class);

  @Autowired
  AdmissionStudentsCertificateHistoryManager mManager;

  @Autowired
  AdmissionStudentsCertificateHistoryBuilder mBuilder;

  @Transactional
  public Response postCertificateList(JsonObject pJsonObject, UriInfo pUriInfo) {
    List<MutableAdmissionStudentsCertificateHistory> studentsCertificates = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      MutableAdmissionStudentsCertificateHistory studentsCertificate =
          new PersistentAdmissionStudentsCertificateHistory();
      getBuilder().build(studentsCertificate, jsonObject, localCache);
      studentsCertificates.add(studentsCertificate);
    }
    getContentManager().saveAdmissionStudentsCertificates(studentsCertificates);
    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getStudentsSavedCertificates(final int pSemesterId, final String pReceiptId,
      final UriInfo pUriInfo) {
    List<AdmissionStudentsCertificateHistory> pStudentsCertificate =
        getContentManager().getStudentsSavedCertificateLists(pSemesterId, pReceiptId);
    return jsonCreator(pStudentsCertificate, pUriInfo);

  }

  private JsonObject jsonCreator(
      List<AdmissionStudentsCertificateHistory> pStudentsCertificateHistory, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AdmissionStudentsCertificateHistory studentsCertificateHistory : pStudentsCertificateHistory) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, studentsCertificateHistory, pUriInfo, localCache);
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
  protected AdmissionStudentsCertificateHistoryManager getContentManager() {
    return mManager;
  }

  @Override
  protected AdmissionStudentsCertificateHistoryBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(AdmissionStudentsCertificateHistory pReadonly) {
    return pReadonly.getLastModified();
  }
}
