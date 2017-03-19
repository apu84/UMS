package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionCommentForStudent;
import org.ums.domain.model.mutable.MutableAdmissionCommentForStudent;
import org.ums.formatter.DateFormat;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AdmissionCommentForStudentBuilder implements
    Builder<AdmissionCommentForStudent, MutableAdmissionCommentForStudent> {

  @Autowired
  private DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, AdmissionCommentForStudent pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {

    if(pReadOnly.getComment() == null || pReadOnly.getComment() == "")
      pBuilder.add("comment", "No Previous Comments");
    else
      pBuilder.add("comment", pReadOnly.getComment());

    pBuilder.add("commentedOn", pReadOnly.getCommentedOn());
  }

  @Override
  public void build(MutableAdmissionCommentForStudent pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setReceiptId(pJsonObject.getString("receiptId"));
    pMutable.setComment(pJsonObject.getString("comment"));
  }
}
