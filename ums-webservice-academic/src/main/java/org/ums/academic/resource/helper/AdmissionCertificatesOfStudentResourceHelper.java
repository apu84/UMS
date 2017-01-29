package org.ums.academic.resource.helper;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.cache.LocalCache;
import org.ums.builder.AdmissionCertificatesOfStudentBuilder;
import org.ums.domain.model.immutable.AdmissionCertificatesOfStudent;
import org.ums.domain.model.mutable.MutableAdmissionCertificatesOfStudent;
import org.ums.manager.AdmissionCertificatesOfStudentManager;
import org.ums.persistent.model.PersistentAdmissionCertificatesOfStudent;
import org.ums.resource.ResourceHelper;
import org.slf4j.LoggerFactory;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdmissionCertificatesOfStudentResourceHelper extends
    ResourceHelper<AdmissionCertificatesOfStudent, MutableAdmissionCertificatesOfStudent, Integer> {

  private static final Logger mLoger = LoggerFactory
      .getLogger(AdmissionCertificatesOfStudentResourceHelper.class);

  @Autowired
  AdmissionCertificatesOfStudentManager mManager;

  @Autowired
  AdmissionCertificatesOfStudentBuilder mBuilder;

  public JsonObject getStudentsSavedCertificates(final int pSemesterId, final String pReceiptId,
      final UriInfo pUriInfo) {
    List<AdmissionCertificatesOfStudent> pStudentsCertificate =
        getContentManager().getStudentsSavedCertificateLists(pSemesterId, pReceiptId);
    return jsonCreator(pStudentsCertificate, pUriInfo);

  }

  private JsonObject jsonCreator(List<AdmissionCertificatesOfStudent> pStudentsCertificateHistory,
      UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AdmissionCertificatesOfStudent studentsCertificateHistory : pStudentsCertificateHistory) {
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
  protected AdmissionCertificatesOfStudentManager getContentManager() {
    return mManager;
  }

  @Override
  protected AdmissionCertificatesOfStudentBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AdmissionCertificatesOfStudent pReadonly) {
    return pReadonly.getLastModified();
  }
}
