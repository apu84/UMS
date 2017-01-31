package org.ums.academic.resource.helper;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.AdmissionCertificatesOfStudentBuilder;
import org.ums.builder.AdmissionCommentForStudentBuilder;
import org.ums.builder.AdmissionStudentBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionCertificatesOfStudent;
import org.ums.domain.model.mutable.MutableAdmissionCommentForStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.manager.AdmissionCertificatesOfStudentManager;
import org.ums.manager.AdmissionCommentForStudentManager;
import org.ums.manager.AdmissionStudentManager;
import org.ums.persistent.model.PersistentAdmissionCertificatesOfStudent;
import org.ums.persistent.model.PersistentAdmissionCommentForStudent;
import org.ums.persistent.model.PersistentAdmissionStudent;
import org.ums.report.generator.UndertakenFormGenerator;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdmissionCertificateSubmissionResourceHelper extends
    ResourceHelper<AdmissionStudent, MutableAdmissionStudent, String> {

  private static final Logger mLoger = LoggerFactory
      .getLogger(AdmissionCertificatesOfStudentResourceHelper.class);

  @Autowired
  AdmissionStudentManager mManager;

  @Autowired
  AdmissionStudentBuilder mBuilder;

  @Autowired
  AdmissionCertificatesOfStudentManager mAdmissionCertificatesOfStudentManager;

  @Autowired
  AdmissionCertificatesOfStudentBuilder mAdmissionCertificatesOfStudentBuilder;

  @Autowired
  AdmissionCommentForStudentManager mAdmissionCommentForStudentManager;

  @Autowired
  AdmissionCommentForStudentBuilder mAdmissionCommentForStudentBuilder;

  @Transactional
  public Response saveAllCertificates(JsonObject pJsonObject, UriInfo pUriInfo) {

    MutableAdmissionStudent student = new PersistentAdmissionStudent();
    LocalCache localCache = new LocalCache();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    JsonObject jsonObject = entries.getJsonObject(0);
    getBuilder().setVerificationStatusAndUndertakenDateBuilder(student, jsonObject, localCache);
    getContentManager().setVerificationStatusAndUndertakenDate(student);

    if(entries.getJsonObject(0).getString("comment").equals("")) {
    }
    else {
      MutableAdmissionCommentForStudent mutableAdmissionCommentForStudent =
          new PersistentAdmissionCommentForStudent();
      mAdmissionCommentForStudentBuilder.build(mutableAdmissionCommentForStudent, jsonObject,
          localCache);
      mAdmissionCommentForStudentManager.saveComment(mutableAdmissionCommentForStudent);
    }

    if(entries.getJsonObject(0).getJsonArray("certificateIds") != null) {
      List<MutableAdmissionCertificatesOfStudent> studentsCertificates = new ArrayList<>();
      for(int i = 0; i < entries.getJsonObject(0).getJsonArray("certificateIds").size(); i++) {
        MutableAdmissionCertificatesOfStudent studentsCertificate =
            new PersistentAdmissionCertificatesOfStudent();
        mAdmissionCertificatesOfStudentBuilder.customCertificateBuilder(studentsCertificate,
            jsonObject, i, localCache);
        studentsCertificates.add(studentsCertificate);
      }
      mAdmissionCertificatesOfStudentManager
          .saveAdmissionStudentsCertificates(studentsCertificates);
    }

    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();

  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected AdmissionStudentManager getContentManager() {
    return mManager;
  }

  @Override
  protected AdmissionStudentBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getETag(AdmissionStudent pReadonly) {
    return pReadonly.getLastModified();
  }
}
