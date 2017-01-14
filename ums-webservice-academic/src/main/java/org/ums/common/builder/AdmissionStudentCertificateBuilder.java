package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.domain.model.mutable.MutableAdmissionStudentCertificate;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AdmissionStudentCertificateBuilder implements
    Builder<AdmissionStudentCertificate, MutableAdmissionStudentCertificate> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AdmissionStudentCertificate pReadOnly,
      UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("certificateId", pReadOnly.getCertificateId());
    pBuilder.add("certificateTitle", pReadOnly.getCertificateTitle());
    pBuilder.add("certificateType", pReadOnly.getCertificateType());
    pBuilder.add("certificateCategory", pReadOnly.getCertificateCategory());
  }

  @Override
  public void build(MutableAdmissionStudentCertificate pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache) {
    pMutable.setCertificateId(pJsonObject.getInt("certificate_id"));
    pMutable.setCertificateTitle(pJsonObject.getString("certificate_title"));
    pMutable.setCertificateType(pJsonObject.getString("certificate_type"));
    pMutable.setCertificateCategory((pJsonObject.getString("certificate_catagory")));
  }

}
