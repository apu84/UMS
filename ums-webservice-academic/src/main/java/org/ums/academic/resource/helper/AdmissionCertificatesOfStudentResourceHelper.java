package org.ums.academic.resource.helper;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.builder.AdmissionCertificatesOfStudentBuilder;
import org.ums.domain.model.immutable.AdmissionCertificatesOfStudent;
import org.ums.domain.model.mutable.MutableAdmissionCertificatesOfStudent;
import org.ums.enums.ProgramType;
import org.ums.manager.AdmissionCertificatesOfStudentManager;
import org.ums.report.generator.UndertakenFormGenerator;
import org.ums.resource.ResourceHelper;
import org.slf4j.LoggerFactory;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.OutputStream;
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

  @Autowired
  UndertakenFormGenerator mUndertakenFormGenerator;

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

  // TODO remove ir
  public void getUndertakenForm(final ProgramType pProgramType, final int pSemesterId,
      final String pReceiptId, final OutputStream pOutputStream, final Request pRequest,
      final UriInfo pUriInfo) throws IOException, DocumentException {
    mUndertakenFormGenerator.createUndertakenForm(pProgramType, pSemesterId, pReceiptId,
        pOutputStream);

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
  protected String getEtag(AdmissionCertificatesOfStudent pReadonly) {
    return pReadonly.getLastModified();
  }
}
