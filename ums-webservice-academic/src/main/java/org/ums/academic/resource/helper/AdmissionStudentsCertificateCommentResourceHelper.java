package org.ums.academic.resource.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.builder.AdmissionStudentsCertificateCommentBuilder;
import org.ums.domain.model.immutable.AdmissionStudentsCertificateComment;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateComment;
import org.ums.manager.AdmissionStudentsCertificateCommentManager;
import org.ums.persistent.model.PersistentAdmissionStudentsCertificateComment;
import org.ums.resource.ResourceHelper;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by kawsu on 1/12/2017.
 */
@Component
public class AdmissionStudentsCertificateCommentResourceHelper
    extends
    ResourceHelper<AdmissionStudentsCertificateComment, MutableAdmissionStudentsCertificateComment, Integer> {

  private static final Logger mLoger = LoggerFactory
      .getLogger(AdmissionStudentsCertificateHistoryResourceHelper.class);

  @Autowired
  AdmissionStudentsCertificateCommentManager mManager;

  @Autowired
  AdmissionStudentsCertificateCommentBuilder mBuilder;

  public JsonObject getStudentsSavedComments(final int pSemesterId, final String pReceiptId,
      final UriInfo pUriInfo) {
    List<AdmissionStudentsCertificateComment> pStudentsCertificateComment =
        getContentManager().getComments(pSemesterId, pReceiptId);
    return jsonCreator(pStudentsCertificateComment, pUriInfo);

  }

  private JsonObject jsonCreator(
      List<AdmissionStudentsCertificateComment> pStudentsCertificateComment, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(AdmissionStudentsCertificateComment studentsCertificateComment : pStudentsCertificateComment) {
      JsonObjectBuilder jsonObject = Json.createObjectBuilder();
      getBuilder().build(jsonObject, studentsCertificateComment, pUriInfo, localCache);
      children.add(jsonObject);
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableAdmissionStudentsCertificateComment mutableAdmissionStudentsCertificateComment =
        new PersistentAdmissionStudentsCertificateComment();
    JsonArray entries = pJsonObject.getJsonArray("entries");
    LocalCache localCache = new LocalCache();
    getBuilder().build(mutableAdmissionStudentsCertificateComment, entries.getJsonObject(0),
        localCache);
    // mutableAdmissionStudentsCertificateComment.commit(false);
    getContentManager().create(mutableAdmissionStudentsCertificateComment);
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  protected AdmissionStudentsCertificateCommentManager getContentManager() {
    return mManager;
  }

  @Override
  protected AdmissionStudentsCertificateCommentBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(AdmissionStudentsCertificateComment pReadonly) {
    return pReadonly.getLastModified();
  }
}
