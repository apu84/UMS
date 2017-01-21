package org.ums.academic.resource.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.cache.LocalCache;
import org.ums.builder.AdmissionCommentForStudentBuilder;
import org.ums.domain.model.immutable.AdmissionCommentForStudent;
import org.ums.domain.model.mutable.MutableAdmissionCommentForStudent;
import org.ums.manager.AdmissionCommentForStudentManager;
import org.ums.persistent.model.PersistentAdmissionCommentForStudent;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class AdmissionCommentForStudentResourceHelper extends
    ResourceHelper<AdmissionCommentForStudent, MutableAdmissionCommentForStudent, Integer> {

  private static final Logger mLoger = LoggerFactory
      .getLogger(AdmissionCertificatesOfStudentResourceHelper.class);

  @Autowired
  AdmissionCommentForStudentManager mManager;

  @Autowired
  AdmissionCommentForStudentBuilder mBuilder;

  public JsonObject getStudentsSavedComments(final int pSemesterId, final String pReceiptId,
      final UriInfo pUriInfo) {
    List<AdmissionCommentForStudent> pStudentsCertificateComment =
        getContentManager().getComments(pSemesterId, pReceiptId);
    return jsonCreator(pStudentsCertificateComment, pUriInfo);

  }

  private JsonObject jsonCreator(List<AdmissionCommentForStudent> pStudentsCertificateComment,
      UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AdmissionCommentForStudent studentsCertificateComment : pStudentsCertificateComment) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, studentsCertificateComment, pUriInfo, localCache);
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
  protected AdmissionCommentForStudentManager getContentManager() {
    return mManager;
  }

  @Override
  protected AdmissionCommentForStudentBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(AdmissionCommentForStudent pReadonly) {
    return pReadonly.getLastModified();
  }
}
