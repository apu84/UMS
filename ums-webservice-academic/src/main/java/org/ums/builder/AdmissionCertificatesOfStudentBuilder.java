package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionCertificatesOfStudent;
import org.ums.domain.model.mutable.MutableAdmissionCertificatesOfStudent;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AdmissionCertificatesOfStudentBuilder implements
    Builder<AdmissionCertificatesOfStudent, MutableAdmissionCertificatesOfStudent> {

  @Override
  public void build(JsonObjectBuilder pBuilder, AdmissionCertificatesOfStudent pReadOnly,
      UriInfo pUriInfo, LocalCache pLocalCache) {

    pBuilder.add("id", pReadOnly.getCertificateId());
  }

  @Override
  public void build(MutableAdmissionCertificatesOfStudent pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache) {

    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setReceiptId(pJsonObject.getString("receiptId"));
    pMutable.setCertificateId(pJsonObject.getInt("certificateId"));
  }

  public void savedStudentsCertificates(JsonObjectBuilder pBuilder,
      AdmissionCertificatesOfStudent pReadOnly, UriInfo pUriInfo, LocalCache localCache) {

    pBuilder.add("certificateId", pReadOnly.getCertificateId());
  }
}
