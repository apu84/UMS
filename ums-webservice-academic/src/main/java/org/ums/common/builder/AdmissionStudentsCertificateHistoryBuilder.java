package org.ums.common.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionStudentsCertificateHistory;
import org.ums.domain.model.mutable.MutableAdmissionStudentsCertificateHistory;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AdmissionStudentsCertificateHistoryBuilder implements
    Builder<AdmissionStudentsCertificateHistory, MutableAdmissionStudentsCertificateHistory> {

  @Override
  public void build(JsonObjectBuilder pBuilder, AdmissionStudentsCertificateHistory pReadOnly,
      UriInfo pUriInfo, LocalCache pLocalCache) {

    pBuilder.add("rowId", pReadOnly.getRowId());
    pBuilder.add("semesterId", pReadOnly.getSemesterId());
    pBuilder.add("receiptId", pReadOnly.getReceiptId());
    pBuilder.add("certificateId", pReadOnly.getCertificateId());
  }

  @Override
  public void build(MutableAdmissionStudentsCertificateHistory pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache) {

    // pMutable.setId(pJsonObject.getInt("rowId"));
    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setReceiptId(pJsonObject.getString("receiptId"));
    pMutable.setCertificateId(pJsonObject.getInt("certificateId"));
  }

  public void savedStudentsCertificates(JsonObjectBuilder pBuilder,
      AdmissionStudentsCertificateHistory pReadOnly, UriInfo pUriInfo, LocalCache localCache) {

    pBuilder.add("certificateId", pReadOnly.getCertificateId());
  }
}
