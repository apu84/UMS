package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionAllTypesOfCertificate;
import org.ums.domain.model.mutable.MutableAdmissionAllTypesOfCertificate;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AdmissionAllTypesOfCertificateBuilder implements
    Builder<AdmissionAllTypesOfCertificate, MutableAdmissionAllTypesOfCertificate> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AdmissionAllTypesOfCertificate pReadOnly,
      UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getCertificateId());
    pBuilder.add("name", pReadOnly.getCertificateTitle());
    pBuilder.add("type", pReadOnly.getCertificateType());
    pBuilder.add("disableChecked", "false");
  }

  @Override
  public void build(MutableAdmissionAllTypesOfCertificate pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache) {
    pMutable.setCertificateId(pJsonObject.getInt("id"));
    pMutable.setCertificateTitle(pJsonObject.getString("name"));
    pMutable.setCertificateType(pJsonObject.getString("type"));
  }

}
