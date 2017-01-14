package org.ums.common.academic.resource.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.cache.LocalCache;
import org.ums.common.builder.AdmissionStudentCertificateBuilder;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateHistory;
import org.ums.manager.AdmissionStudentCertificateManager;
import org.ums.manager.BinaryContentManager;
import org.ums.persistent.model.PersistentAdmissionStudent;
import org.ums.persistent.model.PersistentAdmissionStudentCertificate;
import org.ums.persistent.model.PersistentAdmissionStudentsCertificateHistory;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdmissionStudentCertificateResourceHelper extends
    ResourceHelper<AdmissionStudentCertificate, MutableAdmissionStudentCertificate, Integer> {

  private static final Logger mLogger = LoggerFactory
      .getLogger(AdmissionStudentCertificateResourceHelper.class);

  @Autowired
  @Qualifier("fileContentManager")
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Autowired
  AdmissionStudentCertificateManager mManager;

  @Autowired
  AdmissionStudentCertificateBuilder mBuilder;

  public JsonObject getCertificates(final UriInfo pUriInfo) {
    List<AdmissionStudentCertificate> pAdmissionStudentCertificate =
        getContentManager().getAdmissionStudentCertificateLists();
    return jsonCreator(pAdmissionStudentCertificate, pUriInfo);
  }

  private JsonObject jsonCreator(List<AdmissionStudentCertificate> pAdmissionStudentCertificate,
      UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AdmissionStudentCertificate studentCertificate : pAdmissionStudentCertificate) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, studentCertificate, pUriInfo, localCache);
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
  protected AdmissionStudentCertificateManager getContentManager() {
    return mManager;
  }

  @Override
  protected AdmissionStudentCertificateBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(AdmissionStudentCertificate pReadonly) {
    return pReadonly.getLastModified();
  }
}
