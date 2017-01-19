package org.ums.academic.resource.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.builder.AdmissionAllTypesOfCertificateBuilder;
import org.ums.domain.model.immutable.AdmissionAllTypesOfCertificate;
import org.ums.domain.model.mutable.MutableAdmissionAllTypesOfCertificate;
import org.ums.manager.AdmissionAllTypesOfCertificateManager;
import org.ums.manager.BinaryContentManager;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class AdmissionAllTypesOfCertificateResourceHelper extends
    ResourceHelper<AdmissionAllTypesOfCertificate, MutableAdmissionAllTypesOfCertificate, Integer> {

  private static final Logger mLogger = LoggerFactory
      .getLogger(AdmissionAllTypesOfCertificateResourceHelper.class);

  @Autowired
  @Qualifier("fileContentManager")
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Autowired
  AdmissionAllTypesOfCertificateManager mManager;

  @Autowired
  AdmissionAllTypesOfCertificateBuilder mBuilder;

  public JsonObject getCertificates(final UriInfo pUriInfo) {
    List<AdmissionAllTypesOfCertificate> pAdmissionAllTypesOfCertificates =
        getContentManager().getAdmissionStudentCertificateLists();
    return jsonCreator(pAdmissionAllTypesOfCertificates, pUriInfo);
  }

  private JsonObject jsonCreator(
      List<AdmissionAllTypesOfCertificate> pAdmissionAllTypesOfCertificates, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AdmissionAllTypesOfCertificate studentCertificate : pAdmissionAllTypesOfCertificates) {
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
  protected AdmissionAllTypesOfCertificateManager getContentManager() {
    return mManager;
  }

  @Override
  protected AdmissionAllTypesOfCertificateBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(AdmissionAllTypesOfCertificate pReadonly) {
    return pReadonly.getLastModified();
  }
}
