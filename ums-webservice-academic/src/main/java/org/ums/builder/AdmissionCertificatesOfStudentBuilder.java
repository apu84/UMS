package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionCertificatesOfStudent;
import org.ums.domain.model.mutable.MutableAdmissionCertificatesOfStudent;

import javax.json.*;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AdmissionCertificatesOfStudentBuilder implements
    Builder<AdmissionCertificatesOfStudent, MutableAdmissionCertificatesOfStudent> {

  @Override
  public void build(JsonObjectBuilder pBuilder, AdmissionCertificatesOfStudent pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {

    pBuilder.add("name", pReadOnly.getCertificateName());
    pBuilder.add("id", pReadOnly.getCertificateId());
    pBuilder.add("type", pReadOnly.getCertificateType());
  }

  @Override
  public void build(MutableAdmissionCertificatesOfStudent pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setReceiptId(pJsonObject.getString("receiptId"));
    pMutable.setCertificateId(pJsonObject.getInt("id"));
  }

  public void customCertificateBuilder(MutableAdmissionCertificatesOfStudent pMutable, JsonObject pJsonObject, int j,
      LocalCache pLocalCache) {

    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setReceiptId(pJsonObject.getString("receiptId"));
    pMutable.setCertificateId(pJsonObject.getJsonArray("certificateIds").getJsonObject(j).getInt("id"));
  }
}
