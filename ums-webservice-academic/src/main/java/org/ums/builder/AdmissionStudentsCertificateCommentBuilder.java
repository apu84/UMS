package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionStudentsCertificateComment;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateComment;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by kawsu on 1/12/2017.
 */

@Component
public class AdmissionStudentsCertificateCommentBuilder implements
    Builder<AdmissionStudentsCertificateComment, MutableAdmissionStudentsCertificateComment> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AdmissionStudentsCertificateComment pReadOnly,
      UriInfo pUriInfo, LocalCache pLocalCache) {

    pBuilder.add("semesterId", pReadOnly.getSemesterId());
    pBuilder.add("receiptId", pReadOnly.getReceiptId());
    if(pReadOnly.getComment() == null || pReadOnly.getComment() == "")
      pBuilder.add("comment", "No Previous Comments");
    else
      pBuilder.add("comment", pReadOnly.getComment());

  }

  @Override
  public void build(MutableAdmissionStudentsCertificateComment pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache) {

    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setReceiptId(pJsonObject.getString("receiptId"));
    pMutable.setComment(pJsonObject.getString("comment"));
  }
}
